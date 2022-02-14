package edu.ucsd.cse110.lab5_room.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "URLs")
public class photoURL {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "photoURL_id")
    private int photoURLId = 0;
    @ColumnInfo(name = "photoURL")
    private String photoURL;

    public photoURL(String URL) {
        this.photoURL = photoURL;
    }

    public int getPhotoURLId() {
        return photoURLId;
    }

    public void setPhotoURLId(int PhotoURLId) {
        this.photoURLId = photoURLId;
    }

    public String getURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }


}
