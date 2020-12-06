package com.example.notes.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public long id;
    private String name; //= new String[]{"firstNote", "secondNote"};
    private String text;
    private long createDate;
    private long changeDate;

    public Note() {
    }

    protected Note(Parcel in) {
        id = in.readLong();
        name = in.readString();
        text = in.readString();
        createDate = in.readLong();
        changeDate = in.readLong();
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

    public long getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(long changeDate) {
        this.changeDate = changeDate;
    }

    private void updateTime(){
        changeDate = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
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
        parcel.writeString(name);
        parcel.writeString(text);
        parcel.writeLong(createDate);
        parcel.writeLong(changeDate);
    }
}
