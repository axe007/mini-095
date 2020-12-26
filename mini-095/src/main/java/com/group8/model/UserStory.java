package com.group8.model;

import com.group8.model.User;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;

public class UserStory extends Activity {

    private ActivityStatus status;
    private Double storyPoints;

    public UserStory() {}

    public UserStory(String name, String description, LocalDate startDate, LocalDate endDate, Double storyPoints, double priority) {
        super(name, description, startDate, endDate, priority);
        this.status = ActivityStatus.TODO;
        this.storyPoints = storyPoints;
    }

    public double getStoryPoints() {
        return storyPoints;
    }
    public ActivityStatus getActivityStatus() { return this.status; }

    public void setStoryPoints(double storyPoints) {
        this.storyPoints = storyPoints;
    }
    public void setActivityStatus(ActivityStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "ID: " + this.getId() + " " + this.getName() + " : " + this.getDescription() + " Start: "
                + this.getStartDate() + " End: " + this.getEndDate() + "priority: " + this.getPriority()
                + " Completion Status: " + this.getStatus() + "Story Points: " + this.storyPoints;
    }
}
