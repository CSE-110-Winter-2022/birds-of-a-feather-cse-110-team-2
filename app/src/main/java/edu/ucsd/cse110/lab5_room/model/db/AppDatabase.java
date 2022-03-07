package edu.ucsd.cse110.lab5_room.model.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import edu.ucsd.cse110.lab5_room.model.*;

@Database(entities = {Student.class, Match.class, Course.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase db;

    public static AppDatabase singleton(Context c) {
        if (db == null || !db.isOpen()) {
            db = Room.databaseBuilder(c, AppDatabase.class, "bof.db")
                    .build();
        }

        return db;
    }

    public abstract StudentDao studentDao();
    public abstract MatchDao   matchDao();
    public abstract CourseDao  courseDao();
}