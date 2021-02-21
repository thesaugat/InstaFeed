package com.thesaugat.instafeed.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thesaugat.instafeed.R;
import com.thesaugat.instafeed.pojo.Feed;

import java.util.List;

public class InstagramAdapter extends RecyclerView.Adapter<InstagramAdapter.ViewHolder> {

    List<Feed> instagramFeedList;
    LayoutInflater layoutInflater;
    Context context;
    ClickListeners clickListeners;

    public InstagramAdapter(List<Feed> instagramFeedList, Context context) {
        this.instagramFeedList = instagramFeedList;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.list_feed_instagram, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(instagramFeedList.get(position).getUserName());
        holder.time.setText(instagramFeedList.get(position).getTime());
        holder.viewsCount.setText("22");
        holder.heartsCount.setText("221");
        holder.commentCount.setText("1212");
        holder.description.setText(instagramFeedList.get(position).getPostDesc());
        Picasso.get().load(instagramFeedList.get(position).getProfileImage()).into(holder.profilePic);
        Picasso.get().load(instagramFeedList.get(position).getPostImage()).into(holder.imageBody);
        if (!instagramFeedList.get(position).getFollowing()) {
            holder.followingTV.setText("FOLLOW");
            holder.followingTV.setBackgroundResource(0);
            holder.followingTV.setTextColor(ContextCompat.getColor(context, R.color.instafeed_color));
        }else {
            holder.followingTV.setText("FOLLOWING");
            holder.followingTV.setBackgroundResource(R.drawable.rectangle_following);
            holder.followingTV.setTextColor(ContextCompat.getColor(context, R.color.white));
        }

    }

    @Override
    public int getItemCount() {
        return instagramFeedList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profilePic, imageBody;
        TextView name, time, viewsCount, heartsCount, commentCount, description, followingTV;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.personImage);
            imageBody = itemView.findViewById(R.id.ivBody);
            name = itemView.findViewById(R.id.tvName);
            time = itemView.findViewById(R.id.tvTime);
            viewsCount = itemView.findViewById(R.id.tvViews);
            heartsCount = itemView.findViewById(R.id.tvHearts);
            commentCount = itemView.findViewById(R.id.tvComments);
            description = itemView.findViewById(R.id.tvDesc);
            followingTV = itemView.findViewById(R.id.followingTV);
            followingTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListeners.onFollowingClicked(getAdapterPosition(), !instagramFeedList.get(getAdapterPosition()).getFollowing());
                }
            });
            description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListeners.onPostClicked(getAdapterPosition());
                }
            });


        }
    }

    public void toggleFollow(int position, Boolean follow) {
        instagramFeedList.get(position).setFollowing(follow);
        notifyItemChanged(position);

    }

    public void setClickListeners(ClickListeners clickListeners) {
        this.clickListeners = clickListeners;
    }

    public interface ClickListeners {
        void onFollowingClicked(int position, Boolean follow);

        void onPostClicked(int Position);
    }
}
