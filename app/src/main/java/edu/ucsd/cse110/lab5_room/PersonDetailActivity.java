package edu.ucsd.cse110.lab5_room;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

//        TextView personNotesView = findViewById(R.id.person_detail_notes);
        Intent intent = getIntent();
//
//        String personName = intent.getStringExtra("person_name");
//        String[] personNotes = intent.getStringArrayExtra("person_notes");
//
//        setTitle(personName);
//        personNotesView.setText(String.join("\n", personNotes));
        TextView class1ID = findViewById(R.id.class1View);
        TextView ImageURLID = findViewById(R.id.imageURLView);
        TextView studentNameID = findViewById(R.id.studentNameView);
        TextView isCloseID = findViewById(R.id.isCloseView);

        String[] class1 = intent.getStringArrayExtra("student_classes");
        String personName = intent.getStringExtra("student_name");
        String imageURL = intent.getStringExtra("student_photo_url");
        Boolean isClose = intent.getBooleanExtra("student_is_close",true);

        studentNameID.setText(personName);
        class1ID.setText(class1[0]);
        ImageURLID.setText(imageURL);

        if(isClose){
            isCloseID.setText(personName + " is close!");
        }
        else{
            isCloseID.setText(personName + " is not close!");
        }
    }

    public void onGoBackClicked(View view) {
        finish();
    }

}