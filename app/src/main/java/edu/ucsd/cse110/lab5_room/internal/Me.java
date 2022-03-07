package edu.ucsd.cse110.lab5_room.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import edu.ucsd.cse110.lab5_room.Constants;
import edu.ucsd.cse110.lab5_room.model.Course;
import edu.ucsd.cse110.lab5_room.model.IStudent;
import edu.ucsd.cse110.lab5_room.model.db.AppDatabase;
import edu.ucsd.cse110.lab5_room.model.db.CourseDao;

public class Me {
    private static String name;
    private static String pfp;
    private static Set<Course> courses;
    private static boolean exists;

    public static void save(Context c, String nameL, String pfpL) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(Constants.USER_NAME, nameL);
        editor.putString(Constants.USER_PFP, pfpL);
        editor.apply();

        name = nameL;
        pfp  = pfpL;
    }

    private static void populate(Context c) {
        if (!exists) {
            // get list of courses
            CourseDao courseDao = AppDatabase.singleton(c).courseDao();
            courses = new HashSet<>(courseDao.getAll());

            // populate name and pfp from sharedpreferences
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
            name = prefs.getString(Constants.USER_NAME, null);
            pfp  = prefs.getString(Constants.USER_PFP, null);

            // if everything is populated then we are logged in
            exists = !((courses.size() <= 0) || (name == null) || (pfp == null));
        }
    }

    public static boolean loggedIn(Context c) {
        populate(c);
        return exists;
    }

    public static String getName(Context c) {
        populate(c);
        return name;
    }

    public static String getPhotoURL(Context c) {
        populate(c);
        return pfp;
    }

    public static Set<Course> getCourses(Context c) {
        populate(c);
        return courses;
    }
}
