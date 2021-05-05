package com.thesaugat.instafeed.ui.home.story;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.thesaugat.instafeed.R;
import com.thesaugat.instafeed.dataHolder.StoryData;
import com.thesaugat.instafeed.utils.DataHolder;

import java.util.List;

public class StoryActivity extends AppCompatActivity {
   public static String STORY_DATA = "Sdfa";
   public List<StoryData> storyDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        storyDataList = DataHolder.storyDataList;
        DataHolder.storyDataList = null;
        if( storyDataList == null)
            finish();

        int currentPosition  = getIntent().getIntExtra(STORY_DATA, 0);

    }
}