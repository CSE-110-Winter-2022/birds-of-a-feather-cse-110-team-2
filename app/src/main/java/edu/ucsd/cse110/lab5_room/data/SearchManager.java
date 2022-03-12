package edu.ucsd.cse110.lab5_room.data;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import edu.ucsd.cse110.lab5_room.model.Course;
import edu.ucsd.cse110.lab5_room.model.RosterEntry;
import edu.ucsd.cse110.lab5_room.model.Student;
import edu.ucsd.cse110.lab5_room.model.db.AppDatabase;
import edu.ucsd.cse110.lab5_room.model.db.RosterDao;

public class SearchManager {
    private static FilterableMatchList matches;

    public static void init(Context c) {
        if (matches == null)
            matches = new FilterableMatchList(c, new ArrayList<>());
    }

    public static void checkMatches(Context c, UUID uuid, boolean me, String name, String pfp, Collection<Course> courses) {
        init(c);

        AppDatabase db = AppDatabase.singleton(c);
        RosterDao roster = db.rosterDao();

        // first, insert student into Students table
        // if the user already exists then we just move on
        try {
            db.studentDao().insert(new Student(uuid, me, name, pfp, false, false));
        }
        catch (SQLiteConstraintException e) {
            e.printStackTrace();
        }

        // now add a roster entry for every course this student has taken
        RosterEntry[] entries = new RosterEntry[courses.size()];
        Iterator<Course> coursesIt = courses.iterator();
        for (int i = 0; i < courses.size(); i++) {
            Course curr = coursesIt.next();
            entries[i] = new RosterEntry(uuid, curr.getId());

            if (!me) {
                if (roster.amEnrolled(c, curr))
                    matches.add(c, entries[i]);
            }
        }

        roster.insert(entries);
    }

    public static FilterableMatchList getMatches(Context c) {
        init(c);
        return matches;
    }
}
