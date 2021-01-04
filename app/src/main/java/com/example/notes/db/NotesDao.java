package com.example.notes.db;

import com.example.notes.models.Note;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;
import io.reactivex.Completable;
import io.reactivex.Single;


@Dao
public interface NotesDao {

    @Query("SELECT * FROM note")
    Single<List<Note>> getAllNotes();

    @Query("SELECT * FROM note WHERE id = :id")
    Note getById(long id);

    @Insert
    Completable insert(Note note);

    @Update
    Completable update(Note note);

    @Delete
    Completable delete(Note note);
}
