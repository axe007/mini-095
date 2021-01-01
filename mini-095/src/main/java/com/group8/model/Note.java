package com.group8.model;

public class Note {
    private String noteID;
    private String title;
    private String content;
    private String date;


    public Note (String noteID, String title, String content, String date){
        this.noteID = noteID;
        this.title = title;
        this.content = content;
        this.date = date; //TODO: should be set to whatever the date actually is.
    }

    public String getNoteID() {
        return noteID;
    }

    public void setNoteID(String noteID) {
        this.noteID = noteID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Note{" +
                "noteID='" + noteID + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

