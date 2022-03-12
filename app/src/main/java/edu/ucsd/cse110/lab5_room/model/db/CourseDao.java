package edu.ucsd.cse110.lab5_room.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.util.ArraySet;
import android.util.Log;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import java.util.Set;

import edu.ucsd.cse110.lab5_room.model.Course;
import edu.ucsd.cse110.lab5_room.model.RosterEntry;

// store if i have taken a course
@Dao
public abstract class CourseDao {

    public boolean exists(Course c) {
        return exists(c.getDepartment(), c.getNumber(), c.getQuarter(), c.getYear());
    }
    // https://stackoverflow.com/a/60586895/1420247
    @Query("SELECT EXISTS (SELECT 1 FROM course WHERE (department=:department AND number=:number AND quarter=:quarter AND year=:year))")
    public abstract boolean exists(Course.Department department, int number, Course.Quarter quarter, int year);

    public Course getOrCreate(Course.Department department, int number, Course.Size size, Course.Quarter quarter, int year) {
        // otherwise create new
        try {
            Course c = new Course(department, number, size, quarter, year);
            int cid = (int) insert(c);
            return new Course(cid, department, number, size, quarter, year);
        }
        catch (SQLiteConstraintException e) {
            return getCourse(department, number, quarter, year);
        }
    }

    public Set<Course> getMyCourses(Context c) {
        AppDatabase db        = AppDatabase.singleton(c);
        RosterDao   rosterDao = db.rosterDao();

        List<RosterEntry> entries = rosterDao.getRosterByStudentId(db.studentDao().getMe().getId());
        Set<Course> courses = new ArraySet<>();

        for (RosterEntry e : entries)
            courses.add(getById(e.getId()));

        return courses;
    }

    @Query("select * from course where id=:id;")
    public abstract Course getById(int id);

    @Query("select * from course;")
    public abstract List<Course> getAll();

    @Query("SELECT * FROM course WHERE (department=:department AND number=:number AND quarter=:quarter AND year=:year)")
    public abstract Course getCourse(Course.Department department, int number, Course.Quarter quarter, int year);

    @Insert
    public abstract long insert(Course c);

    @Delete
    public abstract void remove(Course c);
}
