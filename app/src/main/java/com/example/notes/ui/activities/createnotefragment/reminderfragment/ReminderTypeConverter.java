package com.example.notes.ui.activities.createnotefragment.reminderfragment;

import androidx.room.TypeConverter;

import com.example.notes.models.Reminder;
import com.google.gson.Gson;

public class ReminderTypeConverter {

    @TypeConverter
    public static Reminder fromString(String value) {
        return new Gson().fromJson(value, Reminder.class);
    }

    @TypeConverter
    public static String listToString(Reminder reminder) {
        Gson gson = new Gson();
        return gson.toJson(reminder);
    }

}
