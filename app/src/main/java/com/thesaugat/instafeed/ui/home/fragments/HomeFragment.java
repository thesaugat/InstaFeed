package com.thesaugat.instafeed.ui.home.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thesaugat.instafeed.R;
import com.thesaugat.instafeed.ui.userAccount.UserAccountActivity;
import com.thesaugat.instafeed.adapters.InstagramAdapter;
import com.thesaugat.instafeed.pojo.Feed;
import com.thesaugat.instafeed.pojo.FeedData;
import com.thesaugat.instafeed.pojo.ServerResponse;
import com.thesaugat.instafeed.utils.APIClient;
import com.thesaugat.instafeed.utils.Constants;
import com.thesaugat.instafeed.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements InstagramAdapter.ClickListeners {

    RecyclerView feedRv;
    List<Feed> instagramFeedList;
    InstagramAdapter instagramAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        instagramFeedList = new ArrayList<>();
        feedRv = v.findViewById(R.id.feedRV);
        servercall();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void servercall() {
        String token = SharedPreferencesUtils.getStringPreference(getContext(), Constants.TOKEN_KEY);
        Log.d("LOGIN_API_KEY_CHECK", token);
        if (token != null) {
            Call<FeedData> feedDataCall = APIClient.getClient().getNewsFeed(token);
            feedDataCall.enqueue(new Callback<FeedData>() {
                @Override
                public void onResponse(Call<FeedData> call, retrofit2.Response<FeedData> response) {
                    if (response.isSuccessful()) {
                        if (!response.body().getError()) {
                            setrecyclerView(response.body().getFeed());

                        }

                    } else {
                        logOut();
                        getActivity().finish();
                    }
                }

                @Override
                public void onFailure(Call<FeedData> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(getContext(), "Please Login Again", Toast.LENGTH_SHORT).show();
            SharedPreferencesUtils.setBooleanPreference(getContext(), Constants.IS_LOGGED, false);
            openLogin();
            getActivity().finish();
        }
    }


    private void setrecyclerView(List<Feed> feed) {
        instagramFeedList = feed;
        feedRv.setHasFixedSize(true);
        feedRv.setLayoutManager(new LinearLayoutManager(getContext()));
        instagramAdapter = new InstagramAdapter(instagramFeedList, getContext());
        instagramAdapter.setClickListeners(HomeFragment.this);
        feedRv.setAdapter(instagramAdapter);

    }


    private void logOut() {
        SharedPreferencesUtils.setBooleanPreference(getContext(), Constants.IS_LOGGED, false);
        openLogin();
        getActivity().finish();
    }

    private void openLogin() {
        Intent loginIntent = new Intent(getContext(), UserAccountActivity.class);
        startActivity(loginIntent);
    }

    @Override
    public void onFollowingClicked(int position, Boolean follow) {
        Feed feed = instagramFeedList.get(position);
        String apiKey = SharedPreferencesUtils.getStringPreference(getContext(), Constants.TOKEN_KEY);
        if (follow) {
            Call<ServerResponse> followCall = APIClient.getClient().follow(apiKey, feed.getUserId());
            followCall.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                    if (response.isSuccessful()) {
                        if (!response.body().getError()) {
                            instagramAdapter.toggleFollow(position, follow);

                        }
                    }

                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {

                }
            });


        } else {
            Call<ResponseBody> unFollowCall = APIClient.getClient().unFollow(apiKey, feed.getUserId());
            unFollowCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 204) {
                        instagramAdapter.toggleFollow(position, follow);

                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });

        }


    }

    @Override
    public void onPostClicked(int Position) {

    }
}