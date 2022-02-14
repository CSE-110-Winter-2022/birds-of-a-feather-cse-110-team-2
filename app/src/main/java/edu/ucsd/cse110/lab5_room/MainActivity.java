package edu.ucsd.cse110.lab5_room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import edu.ucsd.cse110.lab5_room.auth.LoginActivity;
import edu.ucsd.cse110.lab5_room.internal.Constants;
import edu.ucsd.cse110.lab5_room.model.DummyStudent;
import edu.ucsd.cse110.lab5_room.model.Student;

public class MainActivity extends AppCompatActivity {
    protected RecyclerView personsRecyclerView;
    protected RecyclerView.LayoutManager personsLayoutManager;
    protected NearbySearchAdapter personsViewAdapter;

    private boolean start = true;

//    protected Student[] studentData = {
//            new DummyStudent(0, "Alice", "https://www.wallpapers13.com/wp-content/uploads/2015/12/Nature-Lake-Bled.-Desktop-background-image-915x515.jpg", new String[]{"CSE 11"}, true),
//            new DummyStudent(1, "Bob", "bob.net", new String[]{"ENG 3"}, false),
//            new DummyStudent(2, "Carl", "https://www.bible-bridge.com/wp-content/uploads/favicon-256x256.png?x59487", new String[]{"CSE 101"}, true)
//    };

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

        // RecyclerView to list current users
        personsRecyclerView = findViewById(R.id.persons_view);
        personsLayoutManager = new LinearLayoutManager(this);
        personsRecyclerView.setLayoutManager(personsLayoutManager);

        // adapter to list nearby bluetooth devices
        personsViewAdapter = new NearbySearchAdapter(this);
        personsRecyclerView.setAdapter(personsViewAdapter);

        //TODO: add start/stop button here to process student data to a new array

        final Button button = findViewById(R.id.start);
        button.setOnClickListener(v -> {
            personsViewAdapter.enableScan(start);
            button.setText(start ? "Stop" : "Start");
            start = !start;
        });
    }
}