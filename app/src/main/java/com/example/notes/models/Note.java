package com.example.notes.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.notes.db.NoteTypeConverter;
import com.example.notes.ui.activities.createnotefragment.listfragment.NoteCheckListTypeConverter;
import com.example.notes.ui.activities.createnotefragment.reminderfragment.ReminderTypeConverter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class Note implements Parcelable {
    @PrimaryKey
    public long id;
    private long userId;
    private String name; //= new String[]{"firstNote", "secondNote"};
    private String text;
    public boolean basket;
    public boolean favorites;
    private long createDate;
    private long changeDate;
    @TypeConverters({NoteTypeConverter.class})
    private NoteType noteType;
    @TypeConverters({NoteCheckListTypeConverter.class})
    private List<CheckNoteItem> checkItems;
    @TypeConverters({ReminderTypeConverter.class})
    private Reminder reminder;

    public Note(long id, @NonNull NoteType noteType) {
        this.noteType = noteType;
        this.id = id;
    }

    protected Note(Parcel in) {
        id = in.readLong();
        userId = in.readLong();
        name = in.readString();
        text = in.readString();
        basket = in.readByte() != 0;
        favorites = in.readByte() != 0;
        createDate = in.readLong();
        changeDate = in.readLong();
        noteType = NoteType.valueOf(in.readString());
        Log.d("Type_Problem", "Note parcel constructor = " + noteType);
        checkItems = new ArrayList<>();
        in.readTypedList(checkItems, CheckNoteItem.CREATOR);
        reminder = in.readParcelable(Reminder.class.getClassLoader());
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };


    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        updateTime();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        updateTime();
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(long changeDate) {
        this.changeDate = changeDate;
    }

    private void updateTime() {
        changeDate = System.currentTimeMillis();
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public List<CheckNoteItem> getCheckItems() {
        return checkItems;
    }

    public void setCheckItems(List<CheckNoteItem> checkItems) {
        this.checkItems = checkItems;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", createDate=" + createDate +
                ", changeDate=" + changeDate +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(userId);
        parcel.writeString(name);
        parcel.writeString(text);
        parcel.writeByte((byte) (basket ? 1 : 0));
        parcel.writeByte((byte) (favorites ? 1 : 0));
        parcel.writeLong(createDate);
        parcel.writeLong(changeDate);
        Log.d("Type_Problem", "writeToParcel = " + noteType);
        parcel.writeString(noteType.name());
        parcel.writeTypedList(checkItems);
        parcel.writeParcelable(reminder, 0);
    }
}
