package com.example.notes.utils;

import com.example.notes.models.UserModel;

import androidx.annotation.Nullable;

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
        this.currentUser = currentUser;
        PreferenceManager.saveLastUserName(currentUser.name);
    }
}
