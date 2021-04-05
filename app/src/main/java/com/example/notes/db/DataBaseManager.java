package com.example.notes.db;

import android.content.Context;
import android.util.Log;

import com.example.notes.models.Note;
import com.example.notes.models.NoteType;
import com.example.notes.models.UserModel;
import com.example.notes.utils.UserProviderSingleton;

import java.util.List;

import androidx.room.Room;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class DataBaseManager {

    private AppDatabase database;

    public DataBaseManager(Context context) {
        database = Room.databaseBuilder(context, AppDatabase.class, "notes")
                .build();
    }

    public Completable insertUser(UserModel userModel) {
        if (database == null) return Completable.error(new Exception("Database is not available"));
        Log.d("Registration_PROBLEM", "insertUser name=" + userModel.name + "; pass= " + userModel.pass);
        final UserDao dao = database.userDao();
        return dao.insert(userModel);
    }

    public Single<UserModel> getUserWithName(String name) {
        if (database == null) return Single.error(new Exception("Database is not available"));
        final UserDao dao = database.userDao();
        Log.d("Registration_PROBLEM", "getUserWithName name=" + name);
        return dao.getUserByName(name);
    }

    public Completable insertNote(Note note) {

        if (database == null) return Completable.error(new Exception("Database is not available"));
        note.setUserId(UserProviderSingleton.getInstance().getCurrentUser().id);

        final NotesDao dao = database.notesDao();

        return dao.insert(note);
    }

    public Completable deleteNote(Note note) {

        if (database == null) return Completable.error(new Exception("Database is not available"));

        final NotesDao dao = database.notesDao();

        return dao.delete(note);
    }

    public Completable clearBasket() {

        if (database == null) return Completable.error(new Exception("Database is not available"));

        final NotesDao dao = database.notesDao();

        return dao.clearBasket(UserProviderSingleton.getInstance().getCurrentUser().id);
    }

    public Completable updateNote(Note note) {

        if (database == null) return Completable.error(new Exception("Database is not available"));

        note.setUserId(UserProviderSingleton.getInstance().getCurrentUser().id);

        final NotesDao dao = database.notesDao();

        return dao.update(note);
    }

    public Single<List<Note>> getAllNotes() {
        if (database == null) return Single.error(new Exception("Database is not available"));
        final NotesDao dao = database.notesDao();
        Log.d("USER_PROBLEM", "getAllNotes UserProviderSingleton.getInstance().getCurrentUser() = " + UserProviderSingleton.getInstance().getCurrentUser());
        return dao.getAllNotes(UserProviderSingleton.getInstance().getCurrentUser().id, false);
    }

    public Flowable<List<Note>> getFavoriteNotes() {
        if (database == null) return Flowable.error(new Exception("Database is not available"));
        final NotesDao dao = database.notesDao();
        Log.d("USER_PROBLEM", "getFavoriteNotes UserProviderSingleton.getInstance().getCurrentUser() = " + UserProviderSingleton.getInstance().getCurrentUser());
        return dao.getFavoritesNotes(UserProviderSingleton.getInstance().getCurrentUser().id, true);
    }

    public Flowable<List<Note>> getDeletedNotes() {
        if (database == null) return Flowable.error(new Exception("Database is not available"));
        final NotesDao dao = database.notesDao();
        return dao.getDeletedNotes(UserProviderSingleton.getInstance().getCurrentUser().id, true);
    }

    public Single<List<Note>> getNotesByType(NoteType noteType) {
        if (database == null) return Single.error(new Exception("Database is not available"));
        final NotesDao dao = database.notesDao();
        return dao.getNotesByType(noteType.name(), UserProviderSingleton.getInstance().getCurrentUser().id);
    }

    public Single<Note> getNoteByIdWithoutUserId(long noteId) {
        if (database == null || noteId < 0)
            return Single.error(new Exception("Database is not available"));
        final NotesDao dao = database.notesDao();
        return dao.getNoteByIdWithoutUser(noteId);
    }

    public Single<Note> getNoteById(long noteId) {
        if (database == null || noteId < 0)
            return Single.error(new Exception("Database is not available"));
        final NotesDao dao = database.notesDao();
        return dao.getNoteById(noteId, UserProviderSingleton.getInstance().getCurrentUser().id);
    }
}
