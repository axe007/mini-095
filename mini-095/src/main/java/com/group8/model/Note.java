package com.group8.model;

import java.time.LocalDate;

import org.bson.types.ObjectId;

public class Note {
    private ObjectId id;
    private ObjectId projectID;
    private ObjectId targetID; // SprintID or ActivityID or projectID
    private String targetName;
    private String type;// SprintNote or ActivityNote or ProjectNote
    private ObjectId userID;
    private String userName;
    private LocalDate createDate;
    private String noteTitle;
    private String content;

    public Note() {
    }

    public Note(ObjectId projectID, ObjectId targetID, String targetName, String type, ObjectId userID, String userName,
            LocalDate createDate, String noteTitle, String content) {
        this.projectID = projectID;
        this.targetID = targetID;
        this.targetName = targetName;
        this.type = type;
        this.userID = userID;
        this.userName = userName;
        this.createDate = createDate;
        this.noteTitle = noteTitle;
        this.content = content;
    }

    public ObjectId getProjectID() {
        return projectID;
    }

    public void setProjectID(ObjectId projectID) {
        this.projectID = projectID;
    }

    public ObjectId getTargetID() {
        return targetID;
    }

    public void setTargetID(ObjectId targetID) {
        this.targetID = targetID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ObjectId getUserID() {
        return userID;
    }

    public void setUserID(ObjectId userID) {
        this.userID = userID;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

}
