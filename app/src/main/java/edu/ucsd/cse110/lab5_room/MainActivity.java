package edu.ucsd.cse110.lab5_room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Objects;
import java.util.Set;

import edu.ucsd.cse110.lab5_room.auth.LoginActivity;
import edu.ucsd.cse110.lab5_room.data.SearchManager;
import edu.ucsd.cse110.lab5_room.internal.BoFApplication;
import edu.ucsd.cse110.lab5_room.data.FilterableMatchList;
import edu.ucsd.cse110.lab5_room.internal.EnumAdapter;
import edu.ucsd.cse110.lab5_room.model.db.AppDatabase;
import edu.ucsd.cse110.lab5_room.ui.MatchListView;
import edu.ucsd.cse110.lab5_room.ui.SaveListDialog;
import edu.ucsd.cse110.lab5_room.ui.SavedSelectDialog;

public class MainActivity extends AppCompatActivity {
    private static boolean searchActive = true;

    private static int currState = 0;
    private static final FilterableMatchList.SortType[] filterStates = FilterableMatchList.SortType.values();
    private static FilterableMatchList.SortType sort = filterStates[currState];

    private static final Set<Runnable> filterObservers = new ArraySet<>();
    private static FilterableMatchList matchList;
    private static MatchListView studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_title);

        // redirect to login if no user yet
        BoFApplication app = (BoFApplication) getApplication();

        // make sure we are logged in and redirect to login if not
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

        // button to add a new mocked user
        Button mockButton = findViewById(R.id.btn_mock);
        mockButton.setOnClickListener(view -> {
            Intent i = new Intent(this, NearbyMockActivity.class);
            startActivity(i);
        });

        // get matches
        matchList = SearchManager.getMatches(this);
        studentList = findViewById(R.id.student_list);
        registerFilterObserver(() -> studentList.updateList(matchList.sort(sort)));

        // sort button toggles sort state
        Button sortButton = findViewById(R.id.btn_filter);
        sortButton.setOnClickListener(view -> advanceFilter());
        registerFilterObserver(() -> sortButton.setText(sort.toString()));

        // Filter spinner
        Spinner filterSpinner = findViewById(R.id.filterSpinner);
        EnumAdapter<FilterableMatchList.StandardFilter> filterAdapter = new EnumAdapter<>(
                FilterableMatchList.StandardFilter.class,
                chosen -> studentList.updateList(matchList.generate(sort, chosen.filter))
        );
        filterSpinner.setAdapter(filterAdapter.toArrayAdapter(this));
        filterSpinner.setOnItemSelectedListener(filterAdapter);

        // view saved button creates dialog
        Button viewSaved = findViewById(R.id.btn_saved);
        viewSaved.setOnClickListener(view -> {
            DialogFragment viewSavedDialog = new SavedSelectDialog((chosen) -> {
                runOnUiThread(()-> {
                    matchList = chosen;
                    studentList.updateList(chosen.sort(sort));
                });
            });

            viewSavedDialog.show(getSupportFragmentManager(), "Saved Lists");
        });

        // change internal search state with search button
        // TODO move this to a new class and implement Nearby
        final Button searchButton = findViewById(R.id.start);
        searchButton.setOnClickListener(v -> {
            // update UI
            searchButton.setText((searchActive) ? "Stop" : "Start");
            viewSaved.setVisibility((searchActive) ? View.INVISIBLE : View.VISIBLE);

            // prompt user to save when stop button pressed
            if (!searchActive) {
                DialogFragment saveListDialog = new SaveListDialog(matchList);
                saveListDialog.show(getSupportFragmentManager(), "Save List");
            }

            // stop search
            searchActive = !searchActive;
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