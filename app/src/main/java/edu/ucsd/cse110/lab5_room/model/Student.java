package edu.ucsd.cse110.lab5_room.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
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

    @ColumnInfo
    private final boolean favorite;

    public Student(int id, String name, String photoURL, boolean favorite) {
        this.name = name;
        this.photoURL = photoURL;
        this.id = id;
        this.favorite = favorite;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getPhotoURL() {
        return this.photoURL;
    }

    public boolean getFavorite() {
        return this.favorite;
    }
}