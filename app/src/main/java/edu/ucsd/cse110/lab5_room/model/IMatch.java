package edu.ucsd.cse110.lab5_room.model;

import android.content.Context;

import java.time.LocalDateTime;
import java.util.Set;

// a match is an instance of a course that you shared with one person
public interface IMatch {
    int getId();
//    String getName();
//    String getPhotoURL();
    Student getClassmate(Context c);
    Course getCourse();
    LocalDateTime savedAt();
}
