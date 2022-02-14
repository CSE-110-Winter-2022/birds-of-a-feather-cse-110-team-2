package edu.ucsd.cse110.lab5_room.model.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface previousClassesDao {
    @Transaction
    @Query("SELECT * FROM previousClasses")
    List<student> getAll();

    @Query("SELECT * FROM previousClasses WHERE previousClasses_id=:id")
    previousClasses get(int id);

    @Insert
    void insert(student s);

    @Delete
    void delete(student s);
}
