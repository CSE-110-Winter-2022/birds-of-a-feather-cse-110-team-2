package edu.ucsd.cse110.lab5_room.model.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import edu.ucsd.cse110.lab5_room.model.Student;

@Dao
public interface StudentDao {
    @Query("select * from student where id=:id")
    Student getById(int id);

    @Insert
    void insert(Student... students);

    @Delete
    void delete(Student student);
}
