package edu.ucsd.cse110.bof;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.ucsd.cse110.bof.model.DummyPerson;
import edu.ucsd.cse110.bof.model.DummyStudent;
import edu.ucsd.cse110.bof.model.IPerson;
import edu.ucsd.cse110.bof.model.Student;

public class MainActivity extends AppCompatActivity {
    protected RecyclerView personsRecyclerView;
    protected RecyclerView.LayoutManager personsLayoutManager;
    protected PersonsViewAdapter personsViewAdapter;

    private boolean start = true;

    protected IPerson[] data = {
            new DummyPerson("Jane Doe", new String[]{
                    "Likes cats.",
                    "Favorite color is blue."
            }),
            new DummyPerson("John Public", new String[]{
                    "Likes dogs.",
                    "Favorite color is red."
            }),
            new DummyPerson("Richard Roe", new String[]{
                    "Likes birds.",
                    "Favorite color is yellow."
            })
    };

    protected Student[] studentData = {
            new DummyStudent("Alice", "https://www.wallpapers13.com/wp-content/uploads/2015/12/Nature-Lake-Bled.-Desktop-background-image-915x515.jpg", new String[]{"CSE 11"}, true),
            new DummyStudent("Bob", "bob.net", new String[]{"ENG 3"}, false),
            new DummyStudent("Carl", "https://www.bible-bridge.com/wp-content/uploads/favicon-256x256.png?x59487", new String[]{"CSE 101"}, true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_title);

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