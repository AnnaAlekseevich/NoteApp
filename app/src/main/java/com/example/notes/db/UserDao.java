package com.example.notes.db;

import com.example.notes.models.UserModel;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface UserDao {

    @Insert
    Completable insert(UserModel user);

    @Query("SELECT * FROM usermodel WHERE name = :name")
    Single<UserModel> getUserByName(String name);
}
