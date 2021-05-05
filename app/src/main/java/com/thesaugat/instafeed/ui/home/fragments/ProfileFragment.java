package com.thesaugat.instafeed.ui.home.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thesaugat.instafeed.R;
import com.thesaugat.instafeed.adapters.ProfileFeedAdapter;
import com.thesaugat.instafeed.pojo.Feed;
import com.thesaugat.instafeed.pojo.FeedData;
import com.thesaugat.instafeed.ui.activities.PostViewerActivity;
import com.thesaugat.instafeed.utils.APIClient;
import com.thesaugat.instafeed.utils.Constants;
import com.thesaugat.instafeed.utils.SharedPreferencesUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ProfileFragment extends Fragment implements ProfileFeedAdapter.OnProfileClickLister {
    RecyclerView feedRv;
    public static String FEED_KEY = "sdf";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        feedRv = view.findViewById(R.id.profileFeedRV);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDataFromServer();

    }

    public void getDataFromServer() {
        String token = SharedPreferencesUtils.getStringPreference(getContext(), Constants.TOKEN_KEY);
        if (token != null) {
            Call<FeedData> feedDataCall = APIClient.getClient().getMyFeed(token);
            feedDataCall.enqueue(new Callback<FeedData>() {
                @Override
                public void onResponse(Call<FeedData> call, retrofit2.Response<FeedData> response) {
                    if (response.isSuccessful()) {
                        if (!response.body().getError()) {
                            setRecyclerView(response.body().getFeed());

                        }

                    } else {
                        Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<FeedData> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(getContext(), "Please Login Again", Toast.LENGTH_SHORT).show();
            SharedPreferencesUtils.setBooleanPreference(getContext(), Constants.IS_LOGGED, false);
        }

    }

    public void setRecyclerView(List<Feed> feedList) {
        feedRv.setHasFixedSize(true);
        feedRv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        ProfileFeedAdapter feedAdapter = new ProfileFeedAdapter(feedList, LayoutInflater.from(getContext()));
        feedAdapter.setOnProfileClickListner(this);
        feedRv.setAdapter(feedAdapter);
    }

    @Override
    public void onItemClick(Feed feed, int postion) {
        Intent intent = new Intent(getContext(), PostViewerActivity.class);
        intent.putExtra(FEED_KEY, feed);
        getActivity().startActivity(intent);

    }
}