package edu.ucsd.cse110.lab5_room.model;


public class DummyStudent implements Student{

    private final String name;
    private final String photoURL;
    private String[] classes; //will modified later, I'm too lazy to add custom classes
    private boolean isClose;
    private boolean isFavorite;

    public DummyStudent(String name, String photoURL, String[] classes, boolean isClose, boolean isFavorite) {
        this.name = name;
        this.photoURL = photoURL;
        this.classes = classes;
        this.isClose = isClose;
        this.isFavorite = isFavorite;
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

    @Override
    public boolean isFavorite() {
        return this.isFavorite;
    }

    @Override
    public void toggleFavorite() {
        if(this.isFavorite()) this.isFavorite = false;
        else this.isFavorite = true;
    }
}
