package edu.ucsd.cse110.lab5_room.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import edu.ucsd.cse110.lab5_room.R;
import edu.ucsd.cse110.lab5_room.internal.BoFButton;
import edu.ucsd.cse110.lab5_room.internal.Constants;
import edu.ucsd.cse110.lab5_room.internal.CustomFilter;

public class LoginActivity extends AuthActivity {
    private final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkBluetoothStatus(this);

        EditText firstName = findViewById(R.id.login_field_firstname);
        firstName.setFilters(new InputFilter[]{
                new CustomFilter(Constants.CHARSET_ALPHA_LATIN),
                new InputFilter.LengthFilter(20)
        });

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