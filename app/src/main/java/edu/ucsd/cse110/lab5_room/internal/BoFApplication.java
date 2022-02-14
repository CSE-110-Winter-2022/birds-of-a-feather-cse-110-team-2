package edu.ucsd.cse110.lab5_room.internal;

import android.app.Application;

import com.google.gson.Gson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BoFApplication extends Application {
    public ExecutorService executorService = Executors.newFixedThreadPool(4);
}
