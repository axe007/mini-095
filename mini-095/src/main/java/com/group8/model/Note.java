package com.group8.model;

import java.time.LocalDate;

import org.bson.types.ObjectId;

public class Note {
    private ObjectId noteID;
    private ObjectId projectID;
    private ObjectId targetID; // SprintID or ActivityID or projectID
    private String type;// SprintNote or ActivityNote or ProjectNote
    private ObjectId userID;
    private LocalDate createDate;
    private String content;

    public Note(ObjectId noteID, ObjectId projectID, ObjectId targetID, String type, ObjectId userID,
            LocalDate createDate, String content) {
        this.noteID = noteID;
        this.projectID = projectID;
        this.targetID = targetID;
        this.type = type;
        this.userID = userID;
        this.createDate = createDate;
        this.content = content;
    }

    public ObjectId getNoteID() {
        return noteID;
    }

    public void setNoteID(ObjectId noteID) {
        this.noteID = noteID;
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

}
