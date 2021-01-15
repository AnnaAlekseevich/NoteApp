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

    @Query("SELECT * FROM note WHERE userId = :userId")
    Single<List<Note>> getAllNotes(long userId);

    @Query("SELECT * FROM note WHERE id = :id AND userId = :userId")
    Note getById(long id, long userId);

    @Query("SELECT * FROM note WHERE noteType = :typeName AND userId = :userId")
    Single<List<Note>> getNotesByType(String typeName, long userId);

    @Insert
    Completable insert(Note note);

    @Update
    Completable update(Note note);

    @Delete
    Completable delete(Note note);
}
