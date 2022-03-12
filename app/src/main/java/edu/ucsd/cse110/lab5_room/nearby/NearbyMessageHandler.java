package edu.ucsd.cse110.lab5_room.nearby;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import edu.ucsd.cse110.lab5_room.data.SearchManager;
import edu.ucsd.cse110.lab5_room.internal.BoFApplication;
import edu.ucsd.cse110.lab5_room.internal.Constants;
import edu.ucsd.cse110.lab5_room.model.Course;
import edu.ucsd.cse110.lab5_room.model.db.AppDatabase;

public class NearbyMessageHandler {
    private static NearbyMessageHandler listener;
    public static NearbyMessageHandler singleton(Context c) {
        if (listener == null)
            listener = new NearbyMessageHandler(c);

        return listener;
    }

    public static final String MSG_WAVE = "wave";

    private final Context ctx;
    private final AppDatabase db;
//    private UUID myUUID;
    public NearbyMessageHandler(Context c) {
        BoFApplication app = (BoFApplication) c.getApplicationContext();

        this.ctx = c;
        this.db  = AppDatabase.singleton(c);
//        this.myUUID = this.db.studentDao().getMe().getId();
//        app.executorService.submit(() -> {
//
//        });
    }

    private void addStudent(UUID id, String name, String pfp, List<Course> courses) {
        if ((id != null) && (name != null) && (pfp != null) && !courses.isEmpty())
            SearchManager.checkMatches(this.ctx, id, false, name, pfp, courses);
    }

    public void parseRaw(String raw) {
        CsvReader reader = CsvReader.builder()
                .build(raw);

        // loop through message
        int     dataIndex = 0;
        UUID    currId    = null;
        String  name      = null;
        String  pfp       = null;
        List<Course> courses = new ArrayList<>();
        for (final CsvRow row : reader) {
            try {
                // if we ever find a new UUID, reset what we're doing and start dealing with a new user
                UUID newId = UUID.fromString(row.getField(0).trim());
                switch (row.getField(1)) {
                    case MSG_WAVE:
                        // we have a wave from currId to newId
                        if (newId == Constants.MY_UUID) {
                            // TODO handle wave
                            this.db.studentDao().setWave(currId, true);
                        }
                        break;
                }

                // create old user if needed
                addStudent(currId, name, pfp, courses);

                dataIndex = 0;
                currId = newId;
                name = null;
                pfp = null;
                courses = new ArrayList<>();
                continue;
            }
            catch (IllegalArgumentException e) {
                // collect data for new user
                if (dataIndex == 0) {
                    // first data passed in is name
                    name = row.getField(0).trim();
                }
                else if (dataIndex == 1) {
                    // then profile picture
                    pfp  = row.getField(0).trim();
                }
                else {
                    // anything after that is classes
                    List<String> fields = row.getFields();

                    int year = Integer.decode(fields.get(0).trim());
                    Course.Quarter quarter = Course.Quarter.valueOf(fields.get(1).trim());
                    Course.Department department = Course.Department.valueOf(fields.get(2).trim());
                    int number = Integer.decode(fields.get(3).trim());
                    Course.Size size = Course.Size.valueOf(fields.get(4).trim().toUpperCase());

                    Course course = this.db.courseDao()
                            .getOrCreate(department, number, size, quarter, year);

                    courses.add(course);
                }
            }

            dataIndex++;
        }

        addStudent(currId, name, pfp, courses);
    }
}
