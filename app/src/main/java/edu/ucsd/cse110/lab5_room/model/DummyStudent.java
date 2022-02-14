package edu.ucsd.cse110.lab5_room.model;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import edu.ucsd.cse110.lab5_room.internal.Constants;

public class DummyStudent implements Student{
    private final int studentId;
    private final String name;
    private final String photoURL;
    private Set<String> classes;
    private boolean isClose;

    private static DummyStudent currentUser = null;

    public DummyStudent(int studentId, String name, String photoURL, Set<String> classes, boolean isClose) {
        this.studentId = studentId;
        this.name = name;
        this.photoURL = photoURL;
        this.classes = classes;
        this.isClose = isClose;
    }

    @Override
    public int getStudentId() {
        return studentId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPhotoURL() {
        return photoURL;
    }

    @Override
    public boolean isClose() {
        return isClose;
    }

    @Override
    public Set<String> getClasses() {
        return classes;
    }

    // get current signed in user
    public static Student getCurrent(Context context) {
        if (currentUser == null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String name = prefs.getString(Constants.USER_NAME, "");
            String pfp  = prefs.getString(Constants.USER_PFP, "");
            int id      = prefs.getInt(Constants.USER_ID, -1);
            Set<String> classes = prefs.getStringSet(Constants.USER_COURSES, new HashSet<>());

            currentUser = new DummyStudent(id, name, pfp, classes, true);
        }

        return currentUser;
    }

    public static class StudentShortSerializer implements JsonSerializer<Student> {

        @Override
        public JsonElement serialize(Student src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            result.addProperty("studentId", src.getStudentId());
            result.addProperty("name", src.getName());
            result.addProperty("photoURL", src.getPhotoURL());

            return result;
        }
    }
}
