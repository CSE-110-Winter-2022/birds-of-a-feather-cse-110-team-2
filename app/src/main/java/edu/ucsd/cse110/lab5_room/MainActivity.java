package edu.ucsd.cse110.lab5_room;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

import edu.ucsd.cse110.lab5_room.auth.LoginActivity;
import edu.ucsd.cse110.lab5_room.internal.BoFApplication;
import edu.ucsd.cse110.lab5_room.internal.MatchFilterer;
import edu.ucsd.cse110.lab5_room.internal.Me;

public class MainActivity extends AppCompatActivity {
    private boolean searchActive = true;

    Button filterButton;
    Button viewSaved;

//    protected Student[] studentData = {
//            new DummyStudent("Alice", "https://www.wallpapers13.com/wp-content/uploads/2015/12/Nature-Lake-Bled.-Desktop-background-image-915x515.jpg", new String[]{"CSE 11", "CSE 122"}, true),
//            new DummyStudent("Bob", "bob.net", new String[]{"ENG 3"}, false),
//            new DummyStudent("Carl", "https://www.bible-bridge.com/wp-content/uploads/favicon-256x256.png?x59487", new String[]{"CSE 101"}, true)
//    };

    // dummy courses
//    Course cse110 = new Course(Course.Department.CSE, 110, Course.Size.LARGE, 6);
//    Course cse101 = new Course(Course.Department.CSE, 101, Course.Size.GIGANTIC, 2);
//    Course ece080 = new Course(Course.Department.ECE, 80, Course.Size.TINY, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_title);

        // hide action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        filterButton = findViewById(R.id.btn_filter);
        viewSaved    = findViewById(R.id.btn_saved);

        BoFApplication app = (BoFApplication) getApplication();
        app.executorService.submit(() -> {
            if (!Me.loggedIn(this)) {
                Intent i = new Intent(this, LoginActivity.class);
                runOnUiThread(() -> startActivity(i));
            }
        });

//        MatchFilterer matches = new MatchFilterer(this);

//        MatchList matches = new MatchList(Arrays.asList(new Match[]{
//                new Student("Joe", "f", new HashSet<>(Arrays.asList(cse110, ece080))),
//                new Student("Noah", "f", new HashSet<>(Collections.singletonList(cse101)))
//        }));
//        MatchListView studentList = findViewById(R.id.student_list);
//        studentList.trackStudents(matches);
//        studentList.sortBy(MatchListView.SortType.CLASS_SIZE);

        //TODO: add start/stop button here to process student data to a new array

        final Button button = findViewById(R.id.start);
        button.setOnClickListener(v -> {
            button.setText((searchActive) ? "Stop" : "Start");
            viewSaved.setVisibility((searchActive) ? View.INVISIBLE : View.VISIBLE);
            searchActive = !searchActive;
        });
    }
}