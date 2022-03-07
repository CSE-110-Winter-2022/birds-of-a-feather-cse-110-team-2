package edu.ucsd.cse110.lab5_room.model.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.ucsd.cse110.lab5_room.model.Course;

// store if i have taken a course
@Dao
public abstract class CourseDao {

    public boolean hasTaken(Course c) {
        return hasTaken(c.getDepartment(), c.getNumber(), c.getQuarter(), c.getYear());
    }

    @Query("select * from course where id=:id;")
    public abstract Course getById(int id);

    @Query("select * from course;")
    public abstract List<Course> getAll();

    // https://stackoverflow.com/a/60586895/1420247
    @Query("SELECT EXISTS (SELECT 1 FROM course WHERE (department=:department AND number=:number AND quarter=:quarter AND year=:year));")
    public abstract boolean hasTaken(Course.Department department, int number, Course.Quarter quarter, int year);

    @Insert
    public abstract void insert(Course c);

    @Delete
    public abstract void remove(Course c);
}
