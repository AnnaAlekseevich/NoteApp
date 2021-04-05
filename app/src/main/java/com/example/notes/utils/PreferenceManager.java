package com.example.notes.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PERSISTENT_STORAGE_NAME = "NOTES_PREFS";
    private static final String NAME_KEY = "NAME_KEY";

    private static SharedPreferences sharedPref;

    private static Context context;

    public static void init(Context c) {
        context = c;
        sharedPref = context.getSharedPreferences(PERSISTENT_STORAGE_NAME, Context.MODE_PRIVATE);
    }

    public static void saveLastUserName(String name) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(NAME_KEY, name);
        editor.apply();
    }

    public static String getLastUserName() {
        return sharedPref.getString(NAME_KEY, "");
    }
}
