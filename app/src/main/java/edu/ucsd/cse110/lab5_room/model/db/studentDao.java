package edu.ucsd.cse110.lab5_room.model.db;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface studentDao {
    @Transaction
    @Query("SELECT * FROM students")
    List<student> getAll();

    @Query("SELECT * FROM students WHERE student_id=:id")
    student get(int id);
}
