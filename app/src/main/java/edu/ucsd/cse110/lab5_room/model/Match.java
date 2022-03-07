package edu.ucsd.cse110.lab5_room.model;

import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import edu.ucsd.cse110.lab5_room.internal.BoFApplication;
import edu.ucsd.cse110.lab5_room.model.db.AppDatabase;

@Entity
public class Match {
    @Ignore
    private AppDatabase db;

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "student")
    private int studentId;

    @ColumnInfo(name = "course")
    private int courseId;
//    private Course course;

    @ColumnInfo(name = "saved_at")
    private long savedAt;

    public Match(int id, int studentId, int courseId, long savedAt) {
        this.id = id;
        this.studentId = studentId;
        this.courseId  = courseId;
        this.savedAt = savedAt;
    }

    public int getId() {
        return this.id;
    }

    public int getStudentId() {
        return this.studentId;
    }

    public int getCourseId() {
        return this.courseId;
    }

    public long getSavedAt() {
        return this.savedAt;
    }

    public Student getClassmate(Context c) {
        return AppDatabase.singleton(c).studentDao().getById(this.studentId);
    }

    public Course getCourse(Context c) {
        return AppDatabase.singleton(c).courseDao().getById(this.courseId);
    }
}
