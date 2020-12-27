package com.group8.model;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;

@BsonDiscriminator
public class Bug extends Activity {

    private ObjectId parentId;
    private double estimatedHours;
    private ActivityStatus activityStatus;

    public Bug() {}

    public Bug(ObjectId parentId, String name, String description, LocalDate startDate, LocalDate endDate, Double estimatedHours, double priority) {
        super(name, description, startDate, endDate, priority);
        this.parentId = parentId;
        this.estimatedHours = estimatedHours;
        this.activityStatus = ActivityStatus.TODO;
    }

    public ObjectId getParentId() { return parentId; }
    public double getEstimatedHours() { return this.estimatedHours; }
    public ActivityStatus getActivityStatus() { return activityStatus; }

    public void setParentId(ObjectId parentId) { this.parentId = parentId; }
    public void setEstimatedHours(double estimatedHours) { this.estimatedHours = estimatedHours; }
    public void setActivityStatus(ActivityStatus status) { this.activityStatus = status; }

    @Override
    public String toString() {
        return "ID: " + this.getId() + " " + this.getName() + " : " + this.getDescription() + " Start: "
                + this.getStartDate() + " End: " + this.getEndDate() + "priority: " + this.getPriority()
                + " Completion Status: " + this.getActivityStatus();
    }
}