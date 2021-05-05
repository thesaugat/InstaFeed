package com.thesaugat.instafeed.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thesaugat.instafeed.R;
import com.thesaugat.instafeed.pojo.Feed;

import java.util.List;

public class ProfileFeedAdapter extends RecyclerView.Adapter<ProfileFeedAdapter.ViewHolder> {

    List<Feed> feedList;
    LayoutInflater layoutInflater;
    OnProfileClickLister onProfileClickLister;


    public ProfileFeedAdapter(List<Feed> feedList, LayoutInflater layoutInflater) {
        this.feedList = feedList;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.layout_profile_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("feed size", feedList.get(position).getPostImage() + "");
        if (feedList.get(position).getPostImage() != null)
            Picasso.get().load(feedList.get(position).getPostImage()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profileFeedIV);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Feed feed = feedList.get(getAdapterPosition());
                    onProfileClickLister.onItemClick(feed, getAdapterPosition());
                }
            });

        }
    }

    public void setOnProfileClickListner(OnProfileClickLister onProfileClickListner){
        this.onProfileClickLister = onProfileClickListner;

    }


    public interface OnProfileClickLister {
        public void onItemClick(Feed feed, int postion);

    }


}
