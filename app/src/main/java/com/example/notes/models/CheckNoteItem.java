package com.example.notes.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CheckNoteItem implements Parcelable {

    public boolean isChecked;
    public String descriptionItem;

    public CheckNoteItem() {
    }

    protected CheckNoteItem(Parcel in) {
        isChecked = in.readByte() != 0;
        descriptionItem = in.readString();
    }

    public static final Creator<CheckNoteItem> CREATOR = new Creator<CheckNoteItem>() {
        @Override
        public CheckNoteItem createFromParcel(Parcel in) {
            return new CheckNoteItem(in);
        }

        @Override
        public CheckNoteItem[] newArray(int size) {
            return new CheckNoteItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (isChecked ? 1 : 0));
        parcel.writeString(descriptionItem);
    }
}
