package com.example.notes.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.notes.R;

import androidx.room.PrimaryKey;

public class PreferenceManager {
    private static final String PERSISTANT_STORAGE_NAME = "NOTES_PREFS";
    private static final String NAME_KEY = "NAME_KEY";

    private static Context context;

    public static void init(Context c) {
        context = c;
    }

    public static void saveLastUserName(String name) {
        SharedPreferences sharedPref = context.getSharedPreferences(PERSISTANT_STORAGE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(NAME_KEY, name);
        editor.apply();
    }

    public static String getLastUserName() {
        SharedPreferences sharedPref = context.getSharedPreferences(PERSISTANT_STORAGE_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(NAME_KEY, "");
    }
}
