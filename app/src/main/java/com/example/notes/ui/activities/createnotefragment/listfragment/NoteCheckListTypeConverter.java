package com.example.notes.ui.activities.createnotefragment.listfragment;


import com.example.notes.models.CheckNoteItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

public class NoteCheckListTypeConverter {

    @TypeConverter
    public static List<CheckNoteItem> fromString(String value) {
        Type listType = new TypeToken<List<CheckNoteItem>>() {
        }.getType();
        List<CheckNoteItem> notifications = new Gson().fromJson(value, listType);
        return notifications;
    }

    @TypeConverter
    public static String listToString(List<CheckNoteItem> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}

