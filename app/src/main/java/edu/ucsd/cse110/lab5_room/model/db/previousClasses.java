package edu.ucsd.cse110.lab5_room.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity (tableName = "previousClasses")
public class previousClasses {
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "previousClasses_id")
    private int previousClassesId = 0;

    @ColumnInfo(name = "previousClasses_list")
    private List<String> previousClasses;

    public previousClasses(List<String> previousClasses ) {
        this.previousClasses = previousClasses;
    }

    public int getPhotoURLId() {
        return previousClassesId;
    }

    public void setPreviousClassesId(int previousClassesId) {
        this.previousClassesId = previousClassesId;
    }

    public List<String> getPreviousClasses() {
        return this.previousClasses;
    }

    public void setPhotoURL(List<String> previousClasses) {
        this.previousClasses = previousClasses;
    }

    public void insert(String course){
        this.previousClasses.add(course);
    }
    public void delete(String course){
        this.previousClasses.remove(course);
    }

}
