package com.group8;

public class Message {
    private String senderID;
    private String receiverID;
    private String title;
    private String content;
    private String date;
    private boolean isRead;
    private boolean isReplied;

    public Message (String senderID, String recieverID, String title, String content, String date){
        this.senderID = senderID;
        this.receiverID = recieverID;
        this.title = title;
        this.content = content;
        this.date = date; //TODO: should be set to whatever the date actually is.
        this.isRead = false;
        this.isReplied = false;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getRecieverID() {
        return receiverID;
    }

    public void setRecieverID(String recieverID) {
        this.receiverID = recieverID;
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

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isReplied() {
        return isReplied;
    }

    public void setReplied(boolean replied) {
        isReplied = replied;
    }

    @Override
    public String toString() {
        return
                "senderID: " + senderID  +
                ", receiverID: " + receiverID +
                ", title: " + title +
                ", content: " + content +
                ", date: " + date +
                ", isRead: " + isRead +
                ", isReplied: " + isReplied;
    }
}
