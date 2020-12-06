package com.example.notes.db;

import android.content.Context;

import com.example.notes.models.Note;

import java.util.List;

import androidx.room.Room;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.example.notes.ui.activities.NoteActivity.etName;
import static com.example.notes.ui.activities.NoteActivity.etText;

public class DataBaseManager {

    private AppDatabase database;

    public DataBaseManager(Context context) {
        database = Room.databaseBuilder(context, AppDatabase.class, "notes")
                .build();
    }

    public Completable insertNote(Note note) {

        if (database == null) return Completable.error(new Exception("Database is not available"));

        final NotesDao dao = database.notesDao();

        return dao.insert(note);
    }

    public Completable deleteNote(Note note) {

        if (database == null) return Completable.error(new Exception("Database is not available"));

        final NotesDao dao = database.notesDao();

        return dao.delete(note);
    }

    public Completable updateNote(Note note) {

        if (database == null) return Completable.error(new Exception("Database is not available"));

        final NotesDao dao = database.notesDao();

        return dao.update(note);
    }

    public Single<List<Note>> getAllNotes() {
        if (database == null) return Single.error(new Exception("Database is not available"));
        final NotesDao dao = database.notesDao();
        return dao.getAllNotes();
    }

}
