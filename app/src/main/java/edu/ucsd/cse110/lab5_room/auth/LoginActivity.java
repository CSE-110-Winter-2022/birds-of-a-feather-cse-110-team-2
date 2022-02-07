package edu.ucsd.cse110.lab5_room.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import edu.ucsd.cse110.lab5_room.R;

public class LoginActivity extends AuthActivity {
    private final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkBluetoothStatus(this);
    }

    public void onGoogleLoginClicked(View v) {
        Log.d(TAG, "Google login clicked");
    }

    public void onLoginNextClicked(View v) {
        Intent intent = new Intent(this, CreateProfilePictureActivity.class);
        startActivity(intent);
    }
}