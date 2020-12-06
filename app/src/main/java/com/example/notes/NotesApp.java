package com.example.notes;

import android.app.Application;

import com.example.notes.db.AppDatabase;
import com.example.notes.db.DataBaseManager;

import androidx.room.Room;

public class NotesApp extends Application {

    private static NotesApp instance;

    private DataBaseManager dataBaseManager;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        dataBaseManager = new DataBaseManager(this);

    }

    public static NotesApp getInstance() {
        return instance;
    }

    public DataBaseManager getDatabaseManager() {
        return dataBaseManager;
    }

}
