package edu.ucsd.cse110.lab5_room.model;


public class DummyStudent implements Student{

    private final String name;
    private final String photoURL;
    private String[] classes; //will modified later, I'm too lazy to add custom classes
    private boolean isClose;

    public DummyStudent(String name, String photoURL, String[] classes, boolean isClose) {
        this.name = name;
        this.photoURL = photoURL;
        this.classes = classes;
        this.isClose = isClose;
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
    public boolean isClose() {
        return isClose;
    }

    @Override
    public String[] getClasses() {
        return classes;
    }
}
