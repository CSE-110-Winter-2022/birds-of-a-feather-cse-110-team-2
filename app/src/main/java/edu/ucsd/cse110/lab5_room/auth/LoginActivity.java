package edu.ucsd.cse110.lab5_room.auth;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import edu.ucsd.cse110.lab5_room.R;
import edu.ucsd.cse110.lab5_room.ui.BoFButton;
import edu.ucsd.cse110.lab5_room.Constants;
import edu.ucsd.cse110.lab5_room.ui.CustomFilter;

public class LoginActivity extends AuthActivity {
    private final String TAG = "LoginActivity";

    private EditText firstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkBluetoothStatus(this);

        boolean mocked = getAccumulatedBool(Constants.IS_MOCKED);
        if (mocked) {
            // hide Google login if we are setting up a new mocked user
            TextView topText = findViewById(R.id.login_title);
            topText.setText(R.string.welcome_mock);

            View googleButton = findViewById(R.id.login_btn_google);
            View orText       = findViewById(R.id.tv_login_or);
            googleButton.setVisibility(View.GONE);
            orText.setVisibility(View.GONE);
        }

        firstName = findViewById(R.id.login_field_firstname);
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
        accumulateString(Constants.USER_NAME, this.firstName.getText().toString());
        moveOn(AddClassesActivity.class);
    }
}