package edu.ucsd.cse110.lab5_room;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.UUID;

import edu.ucsd.cse110.lab5_room.internal.BoFApplication;
import edu.ucsd.cse110.lab5_room.internal.Constants;
import edu.ucsd.cse110.lab5_room.model.RosterEntry;
import edu.ucsd.cse110.lab5_room.model.Student;
import edu.ucsd.cse110.lab5_room.model.db.AppDatabase;

public class PersonDetailActivity extends AppCompatActivity {
    private AppDatabase db;
    TextView classListView;
    TextView studentNameView;
    ImageView studentPFPView;
    CheckBox favoriteBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("detail1", "init");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        BoFApplication app = (BoFApplication) getApplication();

        Log.d("detail2", "setContent");
        db = AppDatabase.singleton(this);

        classListView = findViewById(R.id.classListTV);
        studentNameView = findViewById(R.id.studentNameTV);
        studentPFPView = findViewById(R.id.studentProfileView);
        favoriteBox = findViewById(R.id.favoriteCheckBox);

        app.executorService.submit(()->{
            Log.d("detail3", "database assigned");
            Intent intent = getIntent();
            Log.d("detail4", "got intent");
            UUID studentId = UUID.fromString(intent.getStringExtra(Constants.USER_ID));

            Student currStudent = db.studentDao().getById(studentId);

            Log.d("detail5", "got current student");

            List<RosterEntry> courses = db.rosterDao().getRosterByStudentId(studentId);
            String studentName = currStudent.getName();
            String studentPFP = currStudent.getPhotoURL();
            String courseNames = "";
            // TODO: Test RosterDao getRosterByStudentId function to make sure it works as intended
            for(RosterEntry course : courses) courseNames += course.getCourse(this).toString() + "\n";
            boolean isFavorite = currStudent.getFavorite();
            classListView.setText(courseNames);
           runOnUiThread(() -> {


               studentNameView.setText(studentName);
               favoriteBox.setChecked(isFavorite);

               Picasso.get().load(studentPFP).resize(320, 320).into(studentPFPView);
           });
        });





    }

    public void onGoBackClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}