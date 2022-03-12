package edu.ucsd.cse110.lab5_room.model.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.UUID;

import edu.ucsd.cse110.lab5_room.model.Student;

@Dao
public abstract class StudentDao {
    @Query("select * from student where id=:id")
    public abstract Student getById(UUID id);

    @Query("select exists(select 1 from student where id=:id)")
    public abstract boolean exists(UUID id);

    @Query("select exists(select 1 from student where is_me=1)")
    public abstract boolean loggedIn();

    @Query("select * from student where is_me=1")
    public abstract Student getMe();

    @Query("update student set favorite=:isFavorite where id=:id")
    public abstract void setFavorite(UUID id, boolean isFavorite);

    @Insert
    public abstract void insert(Student student);

    @Delete
    public abstract void delete(Student student);
}
