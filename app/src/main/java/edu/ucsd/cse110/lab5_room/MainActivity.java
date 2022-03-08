package edu.ucsd.cse110.lab5_room;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArraySet;
import android.view.View;
import android.widget.Button;

import java.util.Objects;
import java.util.Set;

import edu.ucsd.cse110.lab5_room.auth.LoginActivity;
import edu.ucsd.cse110.lab5_room.auth.StudentSaver;
import edu.ucsd.cse110.lab5_room.internal.BoFApplication;
import edu.ucsd.cse110.lab5_room.model.data.FilterableMatchList;
import edu.ucsd.cse110.lab5_room.model.db.AppDatabase;
import edu.ucsd.cse110.lab5_room.ui.MatchListView;

public class MainActivity extends AppCompatActivity {
    private boolean searchActive = true;

    Button viewSaved;

//    protected Student[] studentData = {
//            new DummyStudent("Alice", "https://www.wallpapers13.com/wp-content/uploads/2015/12/Nature-Lake-Bled.-Desktop-background-image-915x515.jpg", new String[]{"CSE 11", "CSE 122"}, true),
//            new DummyStudent("Bob", "bob.net", new String[]{"ENG 3"}, false),
//            new DummyStudent("Carl", "https://www.bible-bridge.com/wp-content/uploads/favicon-256x256.png?x59487", new String[]{"CSE 101"}, true)
//    };

    private static int currState = 0;
    private static final FilterableMatchList.SortType[] filterStates = FilterableMatchList.SortType.values();
    private static FilterableMatchList.SortType sort = filterStates[currState];

    Set<Runnable> filterObservers = new ArraySet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_title);

        // redirect to login if no user yet
        BoFApplication app = (BoFApplication) getApplication();
        AppDatabase db = AppDatabase.singleton(this);
        app.executorService.submit(() -> {
            if (!db.studentDao().loggedIn()) {
                Intent i = new Intent(this, LoginActivity.class);
                runOnUiThread(() -> {
                    startActivity(i);
                    finish();
                });
            }
        });

        // hide action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        viewSaved    = findViewById(R.id.btn_saved);

        // button to add a new mocked user
        Button mockButton = findViewById(R.id.btn_mock);
        mockButton.setOnClickListener(view -> {
            Intent i = new Intent(this, LoginActivity.class);
            i.putExtra(Constants.IS_MOCKED, true);
            startActivity(i);
        });

        // get matches
        FilterableMatchList matchList = StudentSaver.getMatches(this);
        MatchListView studentList = findViewById(R.id.student_list);
        registerFilterObserver(() -> studentList.updateList(matchList.sort(sort)));

        // allow button to toggle filtering
        Button filterButton = findViewById(R.id.btn_filter);
        filterButton.setOnClickListener(view -> advanceFilter());
        registerFilterObserver(() -> filterButton.setText(sort.toString()));

        final Button button = findViewById(R.id.start);
        button.setOnClickListener(v -> {
            button.setText((searchActive) ? "Stop" : "Start");
            viewSaved.setVisibility((searchActive) ? View.INVISIBLE : View.VISIBLE);
            searchActive = !searchActive;

            // TODO prompt user to save
        });

        updateFilters();
    }

    private void registerFilterObserver(Runnable r) {
        filterObservers.add(r);
    }

    private void updateFilters() {
        for (Runnable r : filterObservers)
            r.run();
    }

    private void advanceFilter() {
        currState = (currState + 1) % filterStates.length;
        sort = filterStates[currState];
        updateFilters();
    }
}