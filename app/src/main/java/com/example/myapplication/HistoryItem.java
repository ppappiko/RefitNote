package com.example.myapplication;

public class HistoryItem {
    private String title;
    private String dateRange;
    private String content;
    private int imageResId; // 테스트용 이미지 리소스 ID (실제로는 URI나 경로 사용)

    public HistoryItem(String title, String dateRange, String content, int imageResId) {
        this.title = title;
        this.dateRange = dateRange;
        this.content = content;
        this.imageResId = imageResId;
    }

    // Getter들
    public String getTitle() { return title; }
    public String getDateRange() { return dateRange; }
    public String getContent() { return content; }
    public int getImageResId() { return imageResId; }
}