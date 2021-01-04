package com.example.notes.db;

import com.example.notes.models.Note;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NotesDao notesDao();
}
