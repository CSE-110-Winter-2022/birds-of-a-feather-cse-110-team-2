package edu.ucsd.cse110.lab5_room.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import edu.ucsd.cse110.lab5_room.Constants;
import edu.ucsd.cse110.lab5_room.model.Course;
import edu.ucsd.cse110.lab5_room.model.RosterEntry;
import edu.ucsd.cse110.lab5_room.model.Student;
import edu.ucsd.cse110.lab5_room.data.FilterableMatchList;
import edu.ucsd.cse110.lab5_room.model.db.AppDatabase;
import edu.ucsd.cse110.lab5_room.model.db.RosterDao;

public class StudentCreator {
    private static FilterableMatchList matches;

    public static void init(Context c) {
        if (matches == null)
            matches = new FilterableMatchList(c, new ArrayList<>());
    }

    public static void create(Context c, boolean me, String name, String pfp, Collection<Course> courses) {
        init(c);

        AppDatabase db = AppDatabase.singleton(c);
        RosterDao roster = db.rosterDao();

        // first, insert student into Students table, with UID 1 if me or otherwise unset,
        //   and save ID
        Student s = new Student((me ? Constants.ME_UID : 0), name, pfp, false);
        int sid   = (int) db.studentDao().insert(s);

        // now add a roster entry for every course this student has taken
        RosterEntry[] entries = new RosterEntry[courses.size()];
        Iterator<Course> coursesIt = courses.iterator();
        for (int i = 0; i < courses.size(); i++) {
            Course curr = coursesIt.next();
            entries[i] = new RosterEntry(sid, curr.getId());

            if (!me) {
                if (roster.amEnrolled(curr))
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
