package com.thesaugat.instafeed.ui.userAccount.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thesaugat.instafeed.R;
import com.thesaugat.instafeed.ui.home.HomeMainActivity;
import com.thesaugat.instafeed.pojo.LoginResponse;
import com.thesaugat.instafeed.utils.APIClient;
import com.thesaugat.instafeed.utils.Constants;
import com.thesaugat.instafeed.utils.SharedPreferencesUtils;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    TextView registerTV;
    EditText emailET, passwordFieldET;
    Button loginBtn;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
//                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
//                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        emailET = view.findViewById(R.id.emailET);
        passwordFieldET = view.findViewById(R.id.passwordFieldET);
        loginBtn = view.findViewById(R.id.loginBtn);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerTV = view.findViewById(R.id.registerTV);
        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new SignUpFragment()).commit();

            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    sendAServerRequest();
                }
            }
        });


    }

    private void sendAServerRequest() {
        Call<LoginResponse> loginResponseCall = APIClient.getClient().login(emailET.getText().toString(), passwordFieldET.getText().toString());
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        goTOHome();
                        SharedPreferencesUtils.setBooleanPreference(getContext(), Constants.IS_LOGGED, true);
                        SharedPreferencesUtils.setStringPreference(getContext(), Constants.TOKEN_KEY, response.body().getApiKey());

                    } else
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


            }
        });
    }


    private boolean validate() {
        boolean email;
        boolean password;
        email = validateEmail();
        password = validatePassword();
        if (email && password)
            return true;
        else
            return false;

    }

    private boolean validatePassword() {
        Boolean validated = true;
        String password = passwordFieldET.getText().toString();
        if (password.isEmpty()) {
            passwordFieldET.setError("Password Field Cannot be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            passwordFieldET.setError("Password Must contain One upper case letter");
            return false;
        }


        return validated;
    }

    private boolean validateEmail() {
        Boolean validated = true;
        String email = emailET.getText().toString();
        if (email.isEmpty()) {
            emailET.setError("Email Field Cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError("Email Provided Is not Valid");
            return false;
        }


        return validated;

    }

    private void goTOHome() {
        Intent homeIntent = new Intent(getContext(), HomeMainActivity.class);
        startActivity(homeIntent);
        getActivity().finish();
    }

}