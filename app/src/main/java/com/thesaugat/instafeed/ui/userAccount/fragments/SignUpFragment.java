package com.thesaugat.instafeed.ui.userAccount.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.thesaugat.instafeed.R;
import com.thesaugat.instafeed.pojo.ServerResponse;
import com.thesaugat.instafeed.utils.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {
    TextView loginTV;
    EditText nameET, emailET, passwordET, confirmPassword;
    Button signUpBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        emailET = view.findViewById(R.id.emailSignUpET);
        nameET = view.findViewById(R.id.nameEt);
        passwordET = view.findViewById(R.id.passwordFieldET);
        confirmPassword = view.findViewById(R.id.confirmPasswordFieldET);
        signUpBtn = view.findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpLogic();
            }
        });

        return view;
    }

    private void signUpLogic() {
        String name, email, pass, confirmPass;
        pass = passwordET.getText().toString();
        email = emailET.getText().toString();
        confirmPass = confirmPassword.getText().toString();
        name = nameET.getText().toString();

        if (!pass.isEmpty() && !confirmPass.isEmpty() && pass.equals(confirmPass) && !email.isEmpty())
            doSignUp(name, email, pass);


    }

    private void doSignUp(String name, String email, String pass) {
        Log.d("VALIDATION", "SUCCESS");
        // server call

        Call<ServerResponse> registerResponse = APIClient.getClient().register(name, email, pass);
        registerResponse.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful())
                    if(!response.body().getError())
                    {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginTV = view.findViewById(R.id.loginTV);
        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new LoginFragment()).commit();

            }
        });
    }
}
