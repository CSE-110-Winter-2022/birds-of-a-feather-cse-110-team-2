package edu.ucsd.cse110.lab5_room.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import edu.ucsd.cse110.lab5_room.MainActivity;
import edu.ucsd.cse110.lab5_room.R;
import edu.ucsd.cse110.lab5_room.internal.BoFApplication;
import edu.ucsd.cse110.lab5_room.model.Course;
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
        Intent intent = new Intent(this, MainActivity.class);

        // Add all data at once
        String name = getAccumulatedString(Constants.USER_NAME);
        String pfp  = this.url.getText().toString();
        List<Course> courses = (List<Course>) getAccumulatedList(Constants.USER_COURSES);
        boolean mocked = getAccumulatedBool(Constants.IS_MOCKED);

        BoFApplication app = (BoFApplication) getApplication();
        app.executorService.submit(() -> {
            StudentSaver.save(this, !mocked, name, pfp, courses);
        });

        // launch main intent
        startActivity(intent);
    }
}