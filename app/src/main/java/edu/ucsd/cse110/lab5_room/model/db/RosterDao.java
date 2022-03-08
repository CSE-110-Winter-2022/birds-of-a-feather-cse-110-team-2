package edu.ucsd.cse110.lab5_room.model.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.ucsd.cse110.lab5_room.Constants;
import edu.ucsd.cse110.lab5_room.model.Course;
import edu.ucsd.cse110.lab5_room.model.RosterEntry;
import edu.ucsd.cse110.lab5_room.model.Student;

@Dao
public abstract class RosterDao {
    @Query("select * from RosterEntry;")
    public abstract List<RosterEntry> getAll();

//    @Query("select * from RosterEntry where saved_at=:dt")
//    public abstract List<RosterEntry> matchesFrom(long dt);

    @Query("select exists(select 1 from RosterEntry where (student=:sid and course=:cid))")
    public abstract boolean enrolled(int sid, int cid);

    public boolean amEnrolled(Course c) {
        return enrolled(Constants.ME_UID, c.getId());
    }

    public boolean isEnrolled(Student s, Course c) {
        return enrolled(s.getId(), c.getId());
    }

    @Insert
    public abstract void insert(RosterEntry... m);

    @Delete
    public abstract void delete(RosterEntry m);
}
