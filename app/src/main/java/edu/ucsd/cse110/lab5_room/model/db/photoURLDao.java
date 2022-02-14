package edu.ucsd.cse110.lab5_room.model.db;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface photoURLDao {

    @Transaction
    @Query("SELECT * FROM URLs")
    List<photoURL> getAll();

    @Query("SELECT * FROM URLs WHERE photoURL_id=:id")
    photoURL get(int id);
}
