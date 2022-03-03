package edu.ucsd.cse110.lab5_room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.ucsd.cse110.lab5_room.auth.LoginActivity;
import edu.ucsd.cse110.lab5_room.internal.Constants;
import edu.ucsd.cse110.lab5_room.model.DummyStudent;
import edu.ucsd.cse110.lab5_room.model.Student;

public class MainActivity extends AppCompatActivity {
    protected RecyclerView personsRecyclerView;
    protected RecyclerView.LayoutManager personsLayoutManager;
    protected PersonsViewAdapter personsViewAdapter;

    private boolean start = true;

    protected Student[] studentData = {
            new DummyStudent("Alice", "https://www.wallpapers13.com/wp-content/uploads/2015/12/Nature-Lake-Bled.-Desktop-background-image-915x515.jpg", new String[]{"CSE 11"}, true, false),
            new DummyStudent("Bob", "bob.net", new String[]{"ENG 3"}, false, false),
            new DummyStudent("Carl", "https://www.bible-bridge.com/wp-content/uploads/favicon-256x256.png?x59487", new String[]{"CSE 101"}, true, false)
    };

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

        personsRecyclerView = findViewById(R.id.persons_view);

        personsLayoutManager = new LinearLayoutManager(this);
        personsRecyclerView.setLayoutManager(personsLayoutManager);

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


        personsViewAdapter = new PersonsViewAdapter(studentData);
        personsRecyclerView.setAdapter(personsViewAdapter);
    }


}