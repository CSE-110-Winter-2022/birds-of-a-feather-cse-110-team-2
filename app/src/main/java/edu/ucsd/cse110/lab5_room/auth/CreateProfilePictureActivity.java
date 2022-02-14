package edu.ucsd.cse110.lab5_room.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import edu.ucsd.cse110.lab5_room.MainActivity;
import edu.ucsd.cse110.lab5_room.R;
import edu.ucsd.cse110.lab5_room.Utilities;
import edu.ucsd.cse110.lab5_room.internal.BoFButton;
import edu.ucsd.cse110.lab5_room.internal.Constants;
import edu.ucsd.cse110.lab5_room.internal.CustomFilter;

public class CreateProfilePictureActivity extends AuthActivity {

    private boolean validURL(EditText url) {
        String urlString = url.getText().toString();

        return Patterns.WEB_URL.matcher(urlString).matches();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile_picture);

        EditText url = findViewById(R.id.pfp_field_url);
        url.setFilters(new CustomFilter(Constants.CHARSET_URL).asSingleton());

        BoFButton nextBtn = findViewById(R.id.pfp_btn_next);

        nextBtn.trackEnable(url);
    }

    public void onProfilePicNextClicked(View v) {

        EditText urlPanel = findViewById(R.id.pfp_field_url);

        if (validURL(urlPanel)) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(Constants.INTENT_LOGGED_IN, true);
            startActivity(intent);
        }
        else
        {
            Utilities.showAlert(this, "Please enter a valid URL");
        }
    }
}