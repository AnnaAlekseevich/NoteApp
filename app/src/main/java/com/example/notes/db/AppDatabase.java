package com.example.notes.db;

import com.example.notes.models.Note;
import com.example.notes.models.UserModel;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class, UserModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NotesDao notesDao();
    public abstract UserDao userDao();
}
