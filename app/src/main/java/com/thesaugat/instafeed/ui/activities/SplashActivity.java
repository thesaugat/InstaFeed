package com.thesaugat.instafeed.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.thesaugat.instafeed.BaseActivity;
import com.thesaugat.instafeed.R;
import com.thesaugat.instafeed.ui.home.HomeMainActivity;
import com.thesaugat.instafeed.ui.userAccount.UserAccountActivity;
import com.thesaugat.instafeed.utils.Constants;
import com.thesaugat.instafeed.utils.SharedPreferencesUtils;

public class SplashActivity extends BaseActivity {

    boolean is_logged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                is_logged = SharedPreferencesUtils.getBooleanPreference(SplashActivity.this, Constants.IS_LOGGED,false);
                if (is_logged)
                    openHome();
                else
                    openLogin();
            }
        }, 3000);



    }

    private void openLogin() {
        Intent loginIntent = new Intent(this, UserAccountActivity.class);
        startActivity(loginIntent);
        finish();
    }

    private void openHome() {
        Intent homeIntent = new Intent(this, HomeMainActivity.class);
        startActivity(homeIntent);
        finish();
    }
}