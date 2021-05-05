package com.thesaugat.instafeed.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thesaugat.instafeed.R;
import com.thesaugat.instafeed.dataHolder.StoryData;
import com.thesaugat.instafeed.ui.home.story.StoryActivity;
import com.thesaugat.instafeed.utils.DataHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.ViewHolder> {

    List<StoryData> storyDataList;
    LayoutInflater inflater;
    Context conetxt;

    public StoryAdapter(List<StoryData> storyDataList, LayoutInflater inflater, Context context) {
        this.storyDataList = storyDataList;
        this.inflater = inflater;
        this.conetxt = conetxt;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_story, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.get().load(storyDataList.get(position).userPP).into(holder.userPPIV);
        holder.textView.setText(storyDataList.get(position).userName);

    }

    @Override
    public int getItemCount() {
        return storyDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView userPPIV;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userPPIV = itemView.findViewById(R.id.userPPIV);
            textView = itemView.findViewById(R.id.userName);

            userPPIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(conetxt, StoryActivity.class);
                    DataHolder.storyDataList = storyDataList;
                    intent.putExtra(StoryActivity.STORY_DATA, getAdapterPosition());
                    conetxt.startActivity(intent);

                }
            });
        }
    }
}
