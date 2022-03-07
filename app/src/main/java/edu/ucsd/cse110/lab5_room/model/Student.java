package edu.ucsd.cse110.lab5_room.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Set;

@Entity
public class Student implements IStudent {
    @PrimaryKey(autoGenerate = true)
    private final int id;

    @ColumnInfo(name = "name")
    private final String name;

    @ColumnInfo(name = "pfp")
    private final String photoURL;

//    @ColumnInfo(name = "courses")
//    private final Set<Course> courses;

    public Student(int id, String name, String photoURL/*, Set<Course> courses*/) {
        this.id = id;
        this.name = name;
        this.photoURL = photoURL;
//        this.courses = courses;
    }

//    @Override
    public int getId() {
        return this.id;
    }

//    @Override
    public String getName() {
        return name;
    }

//    @Override
    public String getPhotoURL() {
        return photoURL;
    }

//    @Override
//    public Set<Course> getClasses() {
//        return this.courses;
//    }
}