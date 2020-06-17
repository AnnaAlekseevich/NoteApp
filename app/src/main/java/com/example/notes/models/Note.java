package com.example.notes.models;

public class Note {
    private String name; //= new String[]{"firstNote", "secondNote"};
    private String text;
    private long createDate;
    private long changeDate;

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

    private void updateTime(){
        changeDate = System.currentTimeMillis();
    }
}
