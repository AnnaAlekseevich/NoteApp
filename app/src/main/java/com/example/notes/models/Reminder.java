package com.example.notes.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Reminder implements Parcelable {

    private long reminderDate;
    private boolean isActive;

    public long getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(long reminderDate) {
        this.reminderDate = reminderDate;
    }

    public Reminder() {
    }

    public Reminder(long reminderDate) {
        this.reminderDate = reminderDate;
    }

    protected Reminder(Parcel in) {
        reminderDate = in.readLong();
        isActive = in.readInt() == 1 ? true : false;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public static final Creator<Reminder> CREATOR = new Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(reminderDate);
        parcel.writeInt(isActive == true ? 1 : 0) ;
    }

}
