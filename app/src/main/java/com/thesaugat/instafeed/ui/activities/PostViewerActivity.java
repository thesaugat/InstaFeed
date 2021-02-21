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

public class PostViewerActivity extends AppCompatActivity {
    static String POST_DATA_KEY = "dfadsfads";

    ImageView imageBody, ivBack;
    TextView name, viewsCount, heartsCount, commentCount, description;
    RecyclerView commentRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_viewe);
        InstagramFeed instagramFeed = (InstagramFeed) getIntent().getSerializableExtra(POST_DATA_KEY);
        imageBody = findViewById(R.id.ivBody);
        name = findViewById(R.id.tvName);
        viewsCount = findViewById(R.id.tvViews);
        ivBack = findViewById(R.id.ivBack);
        heartsCount = findViewById(R.id.tvHearts);
        commentCount = findViewById(R.id.tvComments);
        description = findViewById(R.id.tvDesc);
        commentRV = findViewById(R.id.commentsRV);

        name.setText(instagramFeed.personName);
        viewsCount.setText(instagramFeed.viewsCount + "");
        heartsCount.setText(instagramFeed.heartsCount + "");
        commentCount.setText(instagramFeed.commentsCount + "");
        description.setText(instagramFeed.description);

        Picasso.get().load(instagramFeed.bodyImage).into(imageBody);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}