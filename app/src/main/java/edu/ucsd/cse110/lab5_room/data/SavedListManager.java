package edu.ucsd.cse110.lab5_room.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import java.util.HashSet;
import java.util.Set;

import edu.ucsd.cse110.lab5_room.data.match.StatefulMatchList;

public class SavedListManager {
    private static SavedListManager savedListManager;
    public static SavedListManager singleton(Context c) {
        if (savedListManager == null)
            savedListManager = new SavedListManager(c);

        return savedListManager;
    }

    // Constants
    private static final String SAVE_PREFIX = "save_";
    private static final String ALL_SAVES   = "all_saves";

    private final SharedPreferences prefs;
    public SavedListManager(Context c) {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(c);
    }

    public Set<String> getSaveNames() {
        return this.prefs.getStringSet(ALL_SAVES, new HashSet<>());
    }

    public void save(String name, StatefulMatchList list) {
        // add new save to list of all saves
        Set<String> names = getSaveNames();
        // TODO throw an error if duplicate
        names.add(name);

        String b64 = Base64.encodeToString(list.serialize(), Base64.DEFAULT);

        SharedPreferences.Editor editor = prefs.edit();

        // serialize list to byte array and save as base64 string
        editor.putString(SAVE_PREFIX + name, b64);
        editor.putStringSet(ALL_SAVES, names);
        editor.apply();
    }

    public StatefulMatchList get(Context c, String name) {
        String b64  = this.prefs.getString(SAVE_PREFIX + name, "");
        byte[] list = Base64.decode(b64, Base64.DEFAULT);

        return StatefulMatchList.deserialize(c, list);
    }

    public void remove(String name) {
        Set<String> names = getSaveNames();
        names.remove(name);

        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(SAVE_PREFIX + name);
        editor.putStringSet(ALL_SAVES, names);
        editor.apply();
    }
}
