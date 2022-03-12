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

    @ColumnInfo(name = "wave_received")
    private boolean waveReceived;

    public Student(@NonNull UUID id, boolean isMe, String name, String photoURL, boolean favorite, boolean waveReceived) {
        this.id = id;
        this.isMe = isMe;
        this.name = name;
        this.photoURL = photoURL;
        this.favorite = favorite;
        this.waveReceived = waveReceived;
    }

    public boolean getWaveReceived() {
        return this.waveReceived;
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

    public void setFavorite(boolean isFavorite) {
        this.favorite = isFavorite;
    }

    public void setWaveReceived(boolean w) {
        this.waveReceived = w;
    }

    public void setId(UUID newID) {
        this.id = newID;
    }

    public boolean getFavorite() {
        return this.favorite;
    }
}