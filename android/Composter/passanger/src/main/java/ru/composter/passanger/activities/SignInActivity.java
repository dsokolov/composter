package ru.composter.passanger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.composter.passanger.R;
import ru.composter.passanger.api.Api;
import ru.composter.passanger.api.ApiSingleton;
import ru.composter.passanger.http.request.AuthRequest;
import ru.composter.passanger.http.response.ProfileResponse;
import ru.composter.passanger.model.User;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setTitle("Регистрация");
        final Api api = ApiSingleton.instance().getApi();
        if (User.getUserInfo(this).getId() != null) {
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            setOnRegisterClick(api);
        }
    }

    private void setOnRegisterClick(final Api api) {

        findViewById(R.id.auth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.login)).getText().toString();
                String password = ((EditText) findViewById(R.id.password)).getText().toString();
                AuthRequest authRequest = new AuthRequest(name, password);
                Call<ProfileResponse> responseCall = api.auth(authRequest);
                responseCall.enqueue(new Callback<ProfileResponse>() {
                    @Override
                    public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                        ProfileResponse profileResponse = response.body();
                        User user = new User(profileResponse.getId(), profileResponse.getName(), profileResponse.getBalance(), System.currentTimeMillis());
                        user.save(SignInActivity.this);
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<ProfileResponse> call, Throwable t) {
                        Toast.makeText(SignInActivity.this, "Ошибка авторизации.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
