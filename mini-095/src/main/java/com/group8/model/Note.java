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

}
