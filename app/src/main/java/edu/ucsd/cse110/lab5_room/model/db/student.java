package edu.ucsd.cse110.lab5_room.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
public class student {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "student_id")
    private int studentId = 0;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "photo_url")
    private String photo_url;

    public student(String name, String photo_url) {
        this.name = name;
        this.photo_url = photo_url;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoURL() {return this.photo_url;}

    public void setPhotoURL(String newPhotoURL) {this.photo_url = newPhotoURL;}
}
