package com.example.myapplication;

public class RecordItem {
    private String title;
    private String date;

    public RecordItem(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() { return title; }
    public String getDate() { return date; }
}
