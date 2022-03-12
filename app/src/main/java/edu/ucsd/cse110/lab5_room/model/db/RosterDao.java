package edu.ucsd.cse110.lab5_room.model.db;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import java.util.UUID;

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
    public abstract boolean enrolled(UUID sid, int cid);

    public boolean amEnrolled(Context c, Course course) {
        StudentDao studentDao = AppDatabase.singleton(c).studentDao();
        return isEnrolled(studentDao.getMe(), course);
    }

    public boolean isEnrolled(Student s, Course c) {
        return enrolled(s.getId(), c.getId());
    }

    @Insert
    public abstract void insert(RosterEntry... m);

    @Delete
    public abstract void delete(RosterEntry m);
}
