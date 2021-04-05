package com.example.notes.utils;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.notes.models.UserModel;

public class UserProviderSingleton {

    private UserProviderSingleton() {
    }

    private static UserProviderSingleton instance;

    public static UserProviderSingleton getInstance() {
        if (instance == null) {
            instance = new UserProviderSingleton();
        }

        return instance;
    }

    @Nullable
    private UserModel currentUser;

    @Nullable
    public UserModel getCurrentUser() {
        return currentUser;
    }

    public void updateCurrentUser(@Nullable UserModel currentUser) {
        Log.d("USER_PROBLEM", "updateCurrentUser currentUser = " + currentUser);
        Thread.dumpStack();
        this.currentUser = currentUser;
        PreferenceManager.saveLastUserName(currentUser.name);
    }

}
