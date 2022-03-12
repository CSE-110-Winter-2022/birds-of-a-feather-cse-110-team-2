package edu.ucsd.cse110.lab5_room.internal;

import android.app.Application;

import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.ucsd.cse110.lab5_room.model.db.AppDatabase;

public class BoFApplication extends Application {
    public ExecutorService executorService = Executors.newFixedThreadPool(4);
//    private AppDatabase db;

//    public AppDatabase getDb() {
//        if (this.db == null || !this.db.isOpen()) {
////            this.db = Room.databaseBuilder(this, AppDatabase.class, "bof")
////                    .build();
//            this.db = AppDatabase.singleton(this);
//        }
//
//        return this.db;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
