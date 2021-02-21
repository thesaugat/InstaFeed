package com.thesaugat.instafeed.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.thesaugat.instafeed.R;
import com.thesaugat.instafeed.ui.home.fragments.HomeFragment;
import com.thesaugat.instafeed.ui.home.fragments.NotificationFragment;
import com.thesaugat.instafeed.ui.home.fragments.ProfileFragment;
import com.thesaugat.instafeed.ui.home.fragments.SearchFragment;
import com.thesaugat.instafeed.pojo.Feed;

import java.util.List;

public class HomeMainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView home, search, notifications, profile, newPost, mSelected;

    private static final int PICK_FILE = 2;
    private static final int TAKE_PICTURE = 1;
    RecyclerView feedRv;
    Uri imageUri;

    HomeFragment homeFragment;
    SearchFragment searchFragment;
    ProfileFragment profileFragment;
    NotificationFragment notificationFragment;

    List<Feed> instagramFeedList;
    String iconsStoragePath;
    Fragment currentFragment;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        home = findViewById(R.id.homeBtnIv);
        search = findViewById(R.id.searchBtnIv);
        notifications = findViewById(R.id.notificationsBtnIv);
        profile = findViewById(R.id.profileBtnIv);
        newPost = findViewById(R.id.addBtnIv);
        setSelected(home);
        setClickListeners();


        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        profileFragment = new ProfileFragment();
        notificationFragment = new NotificationFragment();

        currentFragment = homeFragment;
        getSupportFragmentManager().beginTransaction().add(R.id.frameContainer, homeFragment, "homeFragments").commit();


    }

    private void setClickListeners() {
        home.setOnClickListener(this);
        search.setOnClickListener(this);
        notifications.setOnClickListener(this);
        profile.setOnClickListener(this);
        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newPostIntent = new Intent(HomeMainActivity.this, AddPostActivity.class);
                startActivity(newPostIntent);
            }
        });

    }


    private void setSelected(ImageView imageView) {
        if (mSelected != null)
            mSelected.setColorFilter(ContextCompat.getColor(this, R.color.de_selected_color), android.graphics.PorterDuff.Mode.SRC_IN);
        imageView.setColorFilter(ContextCompat.getColor(this, R.color.selected_color), android.graphics.PorterDuff.Mode.SRC_IN);
        mSelected = imageView;

    }


    @Override
    public void onClick(View v) {
        if (v == home) {
            setSelected(home);
            changeFragment(homeFragment);
        } else if (v == search) {

            setSelected(search);
            changeFragment(searchFragment);

        } else if (v == notifications) {
            setSelected(notifications);
            changeFragment(notificationFragment);

        } else if (v == profile) {
            setSelected(profile);
            changeFragment(profileFragment);

        }


    }

    private void changeFragment(Fragment fragment) {
        if (fragment == currentFragment) {
            return;
        }
        getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();
        if (fragment.isAdded())
            getSupportFragmentManager().beginTransaction().show(fragment).commit();
        else
            getSupportFragmentManager().beginTransaction().add(R.id.frameContainer, fragment, "homeFragments").commit();
        currentFragment = fragment;

    }
}