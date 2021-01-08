package com.group8.model;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;

import java.time.LocalDate;

@BsonDiscriminator
public class UserStory extends Activity {

    private Double storyPoints;
    private String activityStatus;

    public UserStory() {
    }

    public UserStory(String name, String description, LocalDate startDate, LocalDate endDate, Double storyPoints,
            double priority) {
        super(name, description, startDate, endDate, priority);
        this.storyPoints = storyPoints;
        this.activityStatus = "TODO";
    }

    public double getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(double storyPoints) {
        this.storyPoints = storyPoints;
    }

    @Override
    public String toString() {
        return "ID: " + this.getId() + " " + this.getName() + " : " + this.getDescription() + " Start: "
                + this.getStartDate() + " End: " + this.getEndDate() + "priority: " + this.getPriority()
                + " Completion Status: " + this.getActivityStatus() + "Story Points: " + this.storyPoints;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }
}