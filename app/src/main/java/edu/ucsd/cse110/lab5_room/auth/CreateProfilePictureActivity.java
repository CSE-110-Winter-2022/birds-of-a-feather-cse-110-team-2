package edu.ucsd.cse110.lab5_room.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import edu.ucsd.cse110.lab5_room.MainActivity;
import edu.ucsd.cse110.lab5_room.R;
import edu.ucsd.cse110.lab5_room.internal.BoFButton;

public class CreateProfilePictureActivity extends AuthActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile_picture);

        EditText url = findViewById(R.id.pfp_field_url);
        BoFButton nextBtn = findViewById(R.id.pfp_btn_next);
        nextBtn.trackEnable(url);
    }

    public void onProfilePicNextClicked(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("logged_in", true);
        startActivity(intent);
    }
}