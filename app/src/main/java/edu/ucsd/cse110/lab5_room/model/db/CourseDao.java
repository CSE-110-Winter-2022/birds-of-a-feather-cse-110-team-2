package edu.ucsd.cse110.lab5_room.model.db;

import android.util.Log;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.ucsd.cse110.lab5_room.model.Course;
import edu.ucsd.cse110.lab5_room.model.Student;

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
        // try getting and return if exists
        if (exists(department, number, quarter, year))
            return getCourse(department, number, quarter, year);

        // otherwise create new
        Course c = new Course(department, number, size, quarter, year);
        int cid = (int) insert(c);
        return new Course(cid, department, number, size, quarter, year);
    }

    public void enroll(Student s, Course c) {

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
