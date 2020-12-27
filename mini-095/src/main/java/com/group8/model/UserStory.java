package com.group8.model;

import com.group8.model.User;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;

@BsonDiscriminator
public class UserStory extends Activity {

    private Double storyPoints;
    private ActivityStatus activityStatus;

    public UserStory() {}

    public UserStory(String name, String description, LocalDate startDate, LocalDate endDate, Double storyPoints, double priority) {
        super(name, description, startDate, endDate, priority);
        this.storyPoints = storyPoints;
        this.activityStatus = ActivityStatus.TODO;
    }

    public double getStoryPoints() {
        return storyPoints;
    }
    public ActivityStatus getActivityStatus() { return activityStatus; }

    public void setStoryPoints(double storyPoints) {
        this.storyPoints = storyPoints;
    }
    public void setActivityStatus(ActivityStatus status){
        this.activityStatus = status;
    }

    @Override
    public String toString() {
        return "ID: " + this.getId() + " " + this.getName() + " : " + this.getDescription() + " Start: "
                + this.getStartDate() + " End: " + this.getEndDate() + "priority: " + this.getPriority()
                + " Completion Status: " + this.getActivityStatus() + "Story Points: " + this.storyPoints;
    }
}