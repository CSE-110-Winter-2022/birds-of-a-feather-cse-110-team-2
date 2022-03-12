package edu.ucsd.cse110.lab5_room.model;

import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

import edu.ucsd.cse110.lab5_room.model.db.AppDatabase;

@Entity
public class RosterEntry implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;

    @ColumnInfo(name = "student")
    private final UUID studentId;

    @ColumnInfo(name = "course")
    private final int courseId;

    public RosterEntry(int id, UUID studentId, int courseId) {
        this.id        = id;
        this.studentId = studentId;
        this.courseId  = courseId;
    }

    @Ignore
    public RosterEntry(UUID studentId, int courseId) {
        this(0, studentId, courseId);
    }

    public int getId() {
        return this.id;
    }

    public UUID getStudentId() {
        return this.studentId;
    }

    public int getCourseId() {
        return this.courseId;
    }

    public Student getClassmate(Context c) {
        return AppDatabase.singleton(c).studentDao().getById(this.studentId);
    }

    public Course getCourse(Context c) {
        return AppDatabase.singleton(c).courseDao().getById(this.courseId);
    }
}
