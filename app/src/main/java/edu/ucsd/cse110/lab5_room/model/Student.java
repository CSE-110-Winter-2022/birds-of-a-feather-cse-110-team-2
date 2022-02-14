package edu.ucsd.cse110.lab5_room.model;

import java.util.Set;

public interface Student {
    int getStudentId();
    String getName();
    String getPhotoURL();
    Set<String> getClasses();
    boolean isClose();
}
