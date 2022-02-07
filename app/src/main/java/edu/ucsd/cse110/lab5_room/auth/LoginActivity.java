package edu.ucsd.cse110.lab5_room.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Objects;

import edu.ucsd.cse110.lab5_room.R;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // hide action bar
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    public void onGoogleLoginClicked(View v) {
        Log.d(TAG, "Google login clicked");
    }

    public void onLoginNextClicked(View v) {
        Log.d(TAG, "Login next clicked");
    }
}