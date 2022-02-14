package edu.ucsd.cse110.lab5_room.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import java.util.Arrays;
import java.util.HashSet;

import edu.ucsd.cse110.lab5_room.MainActivity;
import edu.ucsd.cse110.lab5_room.R;
import edu.ucsd.cse110.lab5_room.internal.BoFButton;
import edu.ucsd.cse110.lab5_room.internal.Constants;
import edu.ucsd.cse110.lab5_room.internal.CustomFilter;

public class CreateProfilePictureActivity extends AuthActivity {
    private EditText url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile_picture);

        this.url = findViewById(R.id.pfp_field_url);
        url.setFilters(new CustomFilter(Constants.CHARSET_URL).asSingleton());

        BoFButton nextBtn = findViewById(R.id.pfp_btn_next);
        nextBtn.trackEnable(url);
    }

    public void onProfilePicNextClicked(View v) {
        // Add all data at once
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.USER_NAME, getIntent().getStringExtra(Constants.USER_NAME));
        editor.putString(Constants.USER_PFP, this.url.getText().toString());
        // TODO add classes before this
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.INTENT_LOGGED_IN, true);
        startActivity(intent);
    }
}