package edu.ucsd.cse110.lab5_room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import edu.ucsd.cse110.lab5_room.auth.LoginActivity;
import edu.ucsd.cse110.lab5_room.internal.Constants;
import edu.ucsd.cse110.lab5_room.internal.Course;
import edu.ucsd.cse110.lab5_room.internal.MatchList;
import edu.ucsd.cse110.lab5_room.model.Classmate;
import edu.ucsd.cse110.lab5_room.model.Match;

public class MainActivity extends AppCompatActivity {
    protected RecyclerView personsRecyclerView;
    protected RecyclerView.LayoutManager personsLayoutManager;
    protected PersonsViewAdapter personsViewAdapter;

    private boolean start = true;

//    protected Student[] studentData = {
//            new DummyStudent("Alice", "https://www.wallpapers13.com/wp-content/uploads/2015/12/Nature-Lake-Bled.-Desktop-background-image-915x515.jpg", new String[]{"CSE 11", "CSE 122"}, true),
//            new DummyStudent("Bob", "bob.net", new String[]{"ENG 3"}, false),
//            new DummyStudent("Carl", "https://www.bible-bridge.com/wp-content/uploads/favicon-256x256.png?x59487", new String[]{"CSE 101"}, true)
//    };

    // dummy courses
    Course cse110 = new Course(Course.Department.CSE, 110, Course.Size.LARGE, 6);
    Course cse101 = new Course(Course.Department.CSE, 101, Course.Size.GIGANTIC, 2);
    Course ece080 = new Course(Course.Department.ECE, 80, Course.Size.TINY, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_title);

        // TODO change this to an actual login system
        Intent launchedWith = getIntent();
        boolean loggedIn = launchedWith.getBooleanExtra(Constants.INTENT_LOGGED_IN, false);
        if (!loggedIn) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }

        MatchList matches = new MatchList(Arrays.asList(new Match[]{
                new Classmate("Joe", "f", new HashSet<>(Arrays.asList(cse110, ece080))),
                new Classmate("Noah", "f", new HashSet<>(Collections.singletonList(cse101)))
        }));
        MatchListView studentList = findViewById(R.id.student_list);
        studentList.trackStudents(matches);
        studentList.sortBy(MatchListView.SortType.CLASS_SIZE);

        //TODO: add start/stop button here to process student data to a new array

        final Button button = findViewById(R.id.start);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (start) {
                    button.setText("Stop");
                    //button.setBackgroundColor(R.color.darker_pinkish);
                }
                else {
                    button.setText("Start");
                    //button.setBackgroundColor(R.color.pinkish);
                }
                start = !start;
            }
        });
        //TODO: process out person who is not close
        //I'll have to figure it out later
    }


}