package edu.ucsd.cse110.lab5_room.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class Student {
    @PrimaryKey @NonNull
    private UUID id;

    @ColumnInfo(name = "is_me")
    private final boolean isMe;

    @ColumnInfo(name = "name")
    private final String name;

    @ColumnInfo(name = "pfp")
    private final String photoURL;

    @ColumnInfo(name = "favorite")
    private boolean favorite;

    public Student(@NonNull UUID id, boolean isMe, String name, String photoURL, boolean favorite) {
        this.id = id;
        this.isMe = isMe;
        this.name = name;
        this.photoURL = photoURL;
        this.favorite = favorite;
    }

    public boolean getIsMe() {
        return this.isMe;
    }

    @NonNull
    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getPhotoURL() {
        return this.photoURL;
    }

    public void setFavorite(boolean isFavorite) {this.favorite = isFavorite;}

    public void setId(UUID newID) {this.id = newID;}

    public boolean getFavorite() {
        return this.favorite;
    }
}