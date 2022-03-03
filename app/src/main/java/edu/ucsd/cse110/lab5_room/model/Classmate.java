package edu.ucsd.cse110.lab5_room.model;

import java.util.Set;

import edu.ucsd.cse110.lab5_room.internal.Course;

public class Classmate implements Match {
    private final String name;
    private final String photoURL;
    private final Set<Course> courses;

    public Classmate(String name, String photoURL, Set<Course> courses) {
        this.name = name;
        this.photoURL = photoURL;
        this.courses = courses;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPhotoURL() {
        return photoURL;
    }

    @Override
    public Set<Course> getClasses() {
        return this.courses;
    }
}