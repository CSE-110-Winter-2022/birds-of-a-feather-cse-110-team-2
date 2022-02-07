package edu.ucsd.cse110.lab5_room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import edu.ucsd.cse110.lab5_room.model.DummyPerson;
import edu.ucsd.cse110.lab5_room.model.IPerson;

public class MainActivity extends AppCompatActivity {
    protected RecyclerView personsRecyclerView;
    protected RecyclerView.LayoutManager personsLayoutManager;
    protected PersonsViewAdapter personsViewAdapter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_title);

        // TODO change this!! temporary
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);

        personsRecyclerView = findViewById(R.id.persons_view);

        personsLayoutManager = new LinearLayoutManager(this);
        personsRecyclerView.setLayoutManager(personsLayoutManager);

        personsViewAdapter = new PersonsViewAdapter(data);
        personsRecyclerView.setAdapter(personsViewAdapter);
    }
}