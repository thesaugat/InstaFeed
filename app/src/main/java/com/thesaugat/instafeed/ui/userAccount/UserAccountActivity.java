package com.thesaugat.instafeed.ui.userAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.thesaugat.instafeed.R;
import com.thesaugat.instafeed.ui.userAccount.fragments.LoginFragment;

public class UserAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        LoginFragment loginFragment = new LoginFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.frameContainer, loginFragment).commit();



    }
}