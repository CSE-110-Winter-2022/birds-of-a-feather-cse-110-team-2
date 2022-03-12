package edu.ucsd.cse110.lab5_room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.List;
import java.util.UUID;

import edu.ucsd.cse110.lab5_room.data.SearchManager;
import edu.ucsd.cse110.lab5_room.internal.BoFApplication;
import edu.ucsd.cse110.lab5_room.model.Course;
import edu.ucsd.cse110.lab5_room.ui.AuthActivity;
import edu.ucsd.cse110.lab5_room.ui.BoFButton;
import edu.ucsd.cse110.lab5_room.internal.Constants;
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
        Intent intent = new Intent(this, MainActivity.class);

        // Add all data at once
        String name = getAccumulatedString(Constants.USER_NAME);
        String pfp  = this.url.getText().toString();
        List<Course> courses = (List<Course>) getAccumulatedList(Constants.USER_COURSES);

        BoFApplication app = (BoFApplication) getApplication();
        app.executorService.submit(() -> {
            SearchManager.checkMatches(
                    this,
                    UUID.fromString("4b295157-ba31-4f9f-8401-5d85d9cf659a"),
                    true,
                    name,
                    pfp,
                    courses
            );
        });

        // launch main intent
        startActivity(intent);
    }
}