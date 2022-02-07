package edu.ucsd.cse110.lab5_room.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.ucsd.cse110.lab5_room.R;
import edu.ucsd.cse110.lab5_room.internal.BoFButton;

public class LoginActivity extends AuthActivity {
    private final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkBluetoothStatus(this);

        EditText firstName = findViewById(R.id.login_field_firstname);
        BoFButton nextButton = findViewById(R.id.login_btn_next);
        nextButton.trackEnable(firstName);
    }

    public void onGoogleLoginClicked(View v) {
        Log.d(TAG, "Google login clicked");
    }

    public void onLoginNextClicked(View v) {
        Intent intent = new Intent(this, CreateProfilePictureActivity.class);
        startActivity(intent);
    }
}