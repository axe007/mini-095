package com.group8.model;

import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bug extends Activity {

    private ActivityStatus status;
    private double estimatedHours;

    public Bug() {}

    public Bug(String name, String content, LocalDate startDate, LocalDate endDate, double estimatedHours, double priority) {
        super(name, content, startDate, endDate, priority);
        this.status = ActivityStatus.TODO;
        this.estimatedHours = estimatedHours;
    }

    public double getEstimatedHours() { return this.estimatedHours; }
    public ActivityStatus getActivityStatus() { return this.status; }

    public void setEstimatedHours(double estimatedHours) { this.estimatedHours = estimatedHours; }
    public void setActivityStatus(ActivityStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "ID: " + this.getId() + " " + this.getName() + " : " + this.getDescription() + " Start: "
                + this.getStartDate() + " End: " + this.getEndDate() + "priority: " + this.getPriority()
                + " Completion Status: " + this.getStatus();
    }
}