package edu.ucsd.cse110.lab5_room.model;

import java.util.Set;

import edu.ucsd.cse110.lab5_room.internal.Course;

public interface Match {
    String getName();
    String getPhotoURL();
    Set<Course> getClasses();
}
