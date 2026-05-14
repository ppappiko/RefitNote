package com.example.myapplication;

public class CalendarDay {
    String date;      // 날짜 (1, 2, 3...)
    int addCount;     // 등록한 지식 수
    int learnCount;   // 학습한 횟수

    public CalendarDay(String date, int addCount, int learnCount) {
        this.date = date;
        this.addCount = addCount;
        this.learnCount = learnCount;
    }
}