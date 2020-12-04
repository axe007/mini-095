package com.group8.model;

public class Note {
    private String content;
    private String title;
    private String date;
    private String author;

    public Note (String content, String title, String date, String author) {
        this.title = title;
        this.content = content;
        this.date = date; //TODO: should be set to whatever the date actually is.
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    } //edit
}
