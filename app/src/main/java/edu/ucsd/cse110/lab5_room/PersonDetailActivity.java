package edu.ucsd.cse110.lab5_room;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.UUID;

import edu.ucsd.cse110.lab5_room.model.RosterEntry;
import edu.ucsd.cse110.lab5_room.model.Student;
import edu.ucsd.cse110.lab5_room.model.db.AppDatabase;
import edu.ucsd.cse110.lab5_room.model.db.StudentDao;
import edu.ucsd.cse110.lab5_room.model.db.StudentDao_Impl;

public class PersonDetailActivity extends AppCompatActivity {
    private AppDatabase db;
    TextView classListView;
    TextView studentNameView;
    ImageView studentPFPView;
    CheckBox favoriteBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
        db = AppDatabase.singleton(this);

        Intent intent = getIntent();
        UUID studentId = UUID.fromString(intent.getStringExtra(Constants.USER_ID));

        Student currStudent = db.studentDao().getById(studentId);
        List<RosterEntry> courses = db.rosterDao().getRosterByStudentId(studentId);
        String studentName = currStudent.getName();
        String studentPFP = currStudent.getPhotoURL();
        String courseNames = "";
        // TODO: Test RosterDao getRosterByStudentId function to make sure it works as intended
        for(RosterEntry course : courses) courseNames += course.getCourse(this).toString() + "\n";
        boolean isFavorite = currStudent.getFavorite();

        classListView = findViewById(R.id.classListTV);
        studentNameView = findViewById(R.id.studentNameTV);
        studentPFPView = findViewById(R.id.studentProfileView);
        favoriteBox = findViewById(R.id.favoriteCheckBox);

        studentNameView.setText(studentName);
        favoriteBox.setChecked(isFavorite);
        classListView.setText(courseNames);
        Picasso.get().load(studentPFP).into(studentPFPView);

    }

    public void onGoBackClicked(View view) {
        finish();
    }

}