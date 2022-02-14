package edu.ucsd.cse110.lab5_room.model.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {student.class, photoURL.class, previousClasses.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{

    private static AppDatabase singletonInstance;


    public static AppDatabase singleton(Context context) {
        if (singletonInstance == null) {
            singletonInstance = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
            .allowMainThreadQueries()
            .build();
        }
        return singletonInstance;
    }

    public abstract studentDao personsDao();
    public abstract photoURLDao photoURLsDao();
    public abstract previousClassesDao previousClassesListDao();

}
