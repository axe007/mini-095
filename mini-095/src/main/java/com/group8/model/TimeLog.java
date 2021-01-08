package com.group8.model;

import java.time.LocalDate;
import org.bson.types.ObjectId;

public class TimeLog {

    private ObjectId id;
    private ObjectId projectId;
    private ObjectId sprintId;
    private ObjectId activityId;
    private ObjectId userId;
    private double hours;
    private LocalDate createdDate;

    public TimeLog() {
    }

    public TimeLog(ObjectId projectId, ObjectId sprintId, ObjectId activityId, ObjectId userId, double hours,
            LocalDate createdDate) {
        this.projectId = projectId;
        this.sprintId = sprintId;
        this.activityId = activityId;
        this.userId = userId;
        this.hours = hours;
        this.createdDate = createdDate;
    }

    public ObjectId getId() {
        return id;
    }
    public ObjectId getProjectId() {
        return projectId;
    }
    public ObjectId getSprintId() {
        return sprintId;
    }
    public ObjectId getActivityId() {
        return activityId;
    }
    public ObjectId getUserId() {
        return userId;
    }
    public double getHours() {
        return hours;
    }
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
    public void setProjectId(ObjectId projectId) {
        this.projectId = projectId;
    }
    public void setSprintId(ObjectId sprintId) {
        this.sprintId = sprintId;
    }
    public void setActivityId(ObjectId activityId) {
        this.activityId = activityId;
    }
    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }
    public void setHours(double hours) {
        this.hours = hours;
    }
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "TimeLog{" + "id = " + getId() + ", projectId=" + projectId + ", sprintId=" + sprintId + ", activityId="
                + activityId + ", userId=" + userId + ", hours=" + hours + ", createdDate=" + createdDate + '}';
    }
}
