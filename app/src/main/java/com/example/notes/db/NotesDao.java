package com.example.notes.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notes.models.Note;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;


@Dao
public interface NotesDao {

    @Query("SELECT * FROM note WHERE userId = :userId and basket= :isBasket  and name like :query")
    Single<List<Note>> getSearchAllNotes(long userId, String query, boolean isBasket);

    @Query("SELECT * FROM note WHERE userId = :userId and basket= :isBasket")
    Single<List<Note>> getAllNotes(long userId, boolean isBasket);

    @Query("SELECT * FROM note WHERE userId = :userId and favorites = :isFavorite")
    Flowable<List<Note>> getFavoritesNotes(long userId, boolean isFavorite);

    @Query("SELECT * FROM note WHERE userId = :userId and basket = :isBasket")
    Flowable<List<Note>> getDeletedNotes(long userId, boolean isBasket);

    @Query("DELETE FROM note WHERE userId = :userId AND basket")
    Completable clearBasket(long userId);

    @Query("SELECT * FROM note WHERE id = :id")
    Single<Note> getNoteByIdWithoutUser(long id);

    @Query("SELECT * FROM note WHERE id = :id AND userId = :userId")
    Single<Note> getNoteById(long id, long userId);

    @Query("SELECT * FROM note WHERE userId = :userId")
    Single<List<Note>> getNoteByName(long userId);

    @Query("SELECT * FROM note WHERE noteType = :typeName AND userId = :userId AND not basket AND name like :query")
    Single<List<Note>> getNotesByTypeWithQuery(String typeName, long userId, String query);

    @Query("SELECT * FROM note WHERE noteType = :typeName AND userId = :userId AND not basket")
    Single<List<Note>> getNotesByType(String typeName, long userId);

    @Insert
    Completable insert(Note note);

    @Update
    Completable update(Note note);

    @Delete
    Completable delete(Note note);
}
