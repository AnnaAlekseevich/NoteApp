package com.example.notes.db;

import com.example.notes.models.NoteType;
import androidx.room.TypeConverter;

public class NoteTypeConverter {

    @TypeConverter
    public String fromType(NoteType noteType) {
        return noteType.name();
    }

    @TypeConverter
    public NoteType toType(String data) {
        return NoteType.valueOf(data);
    }

}
