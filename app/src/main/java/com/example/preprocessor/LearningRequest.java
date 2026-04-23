package com.example.preprocessor;

public class LearningRequest {
    private String subject;
    private String original_text;
    private String cleaned_text;
    private int veracity_score;

    public LearningRequest(String subject, String original_text, String cleaned_text, int veracity_score) {
        this.subject = subject;
        this.original_text = original_text;
        this.cleaned_text = cleaned_text;
        this.veracity_score = veracity_score;
    }

    public String getSubject() { return subject; }
    public String getOriginal_text() { return original_text; }
    public String getCleaned_text() { return cleaned_text; }
    public int getVeracity_score() { return veracity_score; }
}