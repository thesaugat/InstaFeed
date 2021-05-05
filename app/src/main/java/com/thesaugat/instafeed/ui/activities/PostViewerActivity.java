package com.thesaugat.instafeed.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thesaugat.instafeed.R;
import com.thesaugat.instafeed.dataHolder.InstagramFeed;
import com.thesaugat.instafeed.pojo.Feed;
import com.thesaugat.instafeed.ui.home.fragments.ProfileFragment;

public class PostViewerActivity extends AppCompatActivity {
    static String POST_DATA_KEY = "dfadsfads";

    ImageView imageBody, ivBack;
    TextView name, viewsCount, heartsCount, commentCount, description;
    RecyclerView commentRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_viewe);

        Feed feed = (Feed) getIntent().getSerializableExtra(ProfileFragment.FEED_KEY);

        imageBody = findViewById(R.id.ivBody);
//        name = findViewById(R.id.tvName);
        viewsCount = findViewById(R.id.tvViews);
        ivBack = findViewById(R.id.ivBack);
        heartsCount = findViewById(R.id.tvHearts);
        commentCount = findViewById(R.id.tvComments);
        description = findViewById(R.id.tvDesc);
        commentRV = findViewById(R.id.commentsRV);

        viewsCount.setText( "122");
        heartsCount.setText("21");
        commentCount.setText("32");
        description.setText(feed.getPostDesc());

        Picasso.get().load(feed.getPostImage()).into(imageBody);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}