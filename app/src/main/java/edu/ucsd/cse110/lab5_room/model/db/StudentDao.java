package edu.ucsd.cse110.lab5_room.model.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.UUID;

import edu.ucsd.cse110.lab5_room.model.Student;

@Dao
public interface StudentDao {
    @Query("select * from student where id=:id")
    Student getById(UUID id);

    @Query("select exists(select 1 from student where id=:id)")
    boolean exists(UUID id);

    @Query("select exists(select 1 from student where is_me=1)")
    boolean loggedIn();

    @Query("select * from student where is_me=1")
    Student getMe();

    @Insert
    void insert(Student student);

    @Delete
    void delete(Student student);
}
