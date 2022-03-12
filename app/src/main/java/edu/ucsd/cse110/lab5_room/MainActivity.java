package edu.ucsd.cse110.lab5_room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.nearby.messages.Message;

import java.util.Objects;

import edu.ucsd.cse110.lab5_room.data.match.MatchFilter;
import edu.ucsd.cse110.lab5_room.data.match.MatchSorter;
import edu.ucsd.cse110.lab5_room.internal.*;
import edu.ucsd.cse110.lab5_room.nearby.*;

import edu.ucsd.cse110.lab5_room.data.AutoSave;
import edu.ucsd.cse110.lab5_room.data.SearchManager;
import edu.ucsd.cse110.lab5_room.data.match.StatefulMatchList;
import edu.ucsd.cse110.lab5_room.model.db.AppDatabase;
import edu.ucsd.cse110.lab5_room.ui.MatchListPresenter;
import edu.ucsd.cse110.lab5_room.ui.MatchListView;
import edu.ucsd.cse110.lab5_room.ui.SaveListDialog;
import edu.ucsd.cse110.lab5_room.ui.SavedSelectDialog;

public class MainActivity extends AppCompatActivity {
    private static boolean searchActive = false;

    private static       int           currState  = 0;
    private static final MatchSorter[] sortStates = MatchSorter.values();

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

        // get matches and populate view with them
        StatefulMatchList matchList = SearchManager.getMatches(this);
        MatchListView studentList = findViewById(R.id.student_list);
        MatchListPresenter presenter = new MatchListPresenter(matchList);
//        studentList.setPresenter(presenter);
        presenter.register(studentList);
//        studentList.setPresenter(presenter);

        // sort button toggles sort state
        Button sortButton = findViewById(R.id.btn_filter);
        sortButton.setOnClickListener(view -> {
            currState = (currState + 1) % sortStates.length;
            presenter.setSorter(sortStates[currState]);
            sortButton.setText(sortStates[currState].toString());
        });

        // Filter spinner
        Spinner filterSpinner = findViewById(R.id.filterSpinner);
        EnumAdapter<MatchFilter> filterAdapter = new EnumAdapter<>(
                MatchFilter.class,
                chosen -> runOnUiThread(() -> presenter.setFilter(chosen))
        );
        filterSpinner.setAdapter(filterAdapter.toArrayAdapter(this));
        filterSpinner.setOnItemSelectedListener(filterAdapter);

        // view saved button creates dialog
        Button viewSaved = findViewById(R.id.btn_saved);
        viewSaved.setOnClickListener(view -> {
            DialogFragment viewSavedDialog = new SavedSelectDialog(chosen -> {
                runOnUiThread(() -> presenter.setList(chosen));
            });
            viewSavedDialog.show(getSupportFragmentManager(), "Saved Lists");
        });

        // set up Nearby
        NearbyMessageHandler  handler        = NearbyMessageHandler.singleton(this);
        NearbyMessageListener nearbyListener = new NearbyMessageListener(this, handler);

        // change internal search state with search button
        final Button searchButton = findViewById(R.id.start);
        searchButton.setOnClickListener(v -> {
            // toggle search
            searchActive = !searchActive;

            // update UI
            searchButton.setText((searchActive) ? "Stop" : "Start");
            viewSaved.setVisibility((searchActive) ? View.INVISIBLE : View.VISIBLE);

            if (searchActive) {
                // begin search and broadcast myself
                app.executorService.submit(() -> {
                    nearbyListener.start();
                    Message broadcast = NearbyBroadcastComposer.broadcast(this);
                    nearbyListener.send(broadcast);

                    // begin autosaving
                    AutoSave.register(this, matchList);
                });
            }
            else {
                // stop search
                nearbyListener.stop();

                // stop autosave
                AutoSave.deregister(this);

                // prompt user to save
                DialogFragment saveListDialog = new SaveListDialog(matchList);
                saveListDialog.show(getSupportFragmentManager(), "Save List");
            }
        });
    }
}