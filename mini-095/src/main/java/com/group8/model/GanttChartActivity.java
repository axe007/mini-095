package com.group8.model;

import java.time.LocalDate;

import org.bson.types.ObjectId;

public class GanttChartActivity {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private ObjectId topLevelID;
    private ObjectId middleLevelID;
    private ObjectId bottomLevelID;

    public GanttChartActivity(String title, LocalDate startDate, LocalDate endDate, ObjectId topLevelID,
            ObjectId middleLevelID, ObjectId bottomLevelID) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.topLevelID = topLevelID;
        this.middleLevelID = middleLevelID;
        this.bottomLevelID = bottomLevelID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ObjectId getTopLevelID() {
        return topLevelID;
    }

    public void setTopLevelID(ObjectId topLevelID) {
        this.topLevelID = topLevelID;
    }

    public ObjectId getMiddleLevelID() {
        return middleLevelID;
    }

    public void setMiddleLevelID(ObjectId middleLevelID) {
        this.middleLevelID = middleLevelID;
    }

    public ObjectId getBottomLevelID() {
        return bottomLevelID;
    }

    public void setBottomLevelID(ObjectId bottomLevelID) {
        this.bottomLevelID = bottomLevelID;
    }

}
