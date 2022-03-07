package edu.ucsd.cse110.lab5_room.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import edu.ucsd.cse110.lab5_room.MainActivity;
import edu.ucsd.cse110.lab5_room.R;
import edu.ucsd.cse110.lab5_room.internal.Me;
import edu.ucsd.cse110.lab5_room.ui.BoFButton;
import edu.ucsd.cse110.lab5_room.Constants;
import edu.ucsd.cse110.lab5_room.ui.CustomFilter;

public class CreateProfilePictureActivity extends AuthActivity {

    private EditText url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile_picture);

        url = findViewById(R.id.pfp_field_url);
        url.setFilters(new CustomFilter(Constants.CHARSET_URL).asSingleton());

        BoFButton nextBtn = findViewById(R.id.pfp_btn_next);
        nextBtn.trackEnable(url);
    }

    public void onProfilePicNextClicked(View v) {
        // Add all data at once
        Intent launchedWith = getIntent();
        Me.save(this, launchedWith.getStringExtra(Constants.USER_NAME), this.url.getText().toString());

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}