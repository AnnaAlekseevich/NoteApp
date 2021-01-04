package com.example.notes.ui.activities.createnotefragment.reminderfragment;

import com.example.notes.models.Reminder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

public class ReminderTypeConverter {

    @TypeConverter
    public static Reminder fromString(String value) {
        Type reminderType = new TypeToken<Reminder>() {
        }.getType();
        Reminder reminder = new Gson().fromJson(value, reminderType);
        return reminder;
    }

    @TypeConverter
    public static String listToString(Reminder reminder) {
        Gson gson = new Gson();
        return gson.toJson(reminder);
    }

}
