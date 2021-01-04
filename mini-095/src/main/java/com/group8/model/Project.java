package com.group8.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import com.group8.model.Activity;
import org.bson.types.ObjectId;

public class Project {
    private ObjectId id;
    private String name;
    private String description;
    private ArrayList<ObjectId> activities;
    private ArrayList<ObjectId> developerTeam;
    private ArrayList<ObjectId> sprints;
    private LocalDate startDate;
    private LocalDate endDate;
    private String type;
    private int sprintDuration;
    private String status;
    private ObjectId currentSprint;

    public Project() {}

    public Project(String name, String description, LocalDate startDate, LocalDate endDate, String type, int sprintDuration) {
        this.name = name;
        this.description = description;
        this.activities = new ArrayList<>();
        this.developerTeam = new ArrayList<>();
        this.sprints = new ArrayList<>();
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.sprintDuration = sprintDuration;
        this.currentSprint = null;
        this.status = "Open";
    }

    public ObjectId getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() { return description; }
    public ArrayList<ObjectId> getActivities() {
        return activities;
    }
    public ArrayList<ObjectId> getDeveloperTeam() {
        return developerTeam;
    }
    public ArrayList<ObjectId> getSprints() {
        return sprints;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public String getType() {
        return type;
    }
    public int getSprintDuration() { return sprintDuration; }
    public ObjectId getCurrentSprint() { return currentSprint; }
    public String getStatus() {
        return status;
    }

    public void setId(ObjectId id) { this.id = id; }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) { this.description = description; }
    public void setActivities(ArrayList<ObjectId> activities) {
        this.activities = activities;
    }
    public void setDeveloperTeam(ArrayList<ObjectId> developerTeam) {
        this.developerTeam = developerTeam;
    }
    public void setSprints(ArrayList<ObjectId> sprints) {
        this.sprints = sprints;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setSprintDuration(int sprintDuration) { this.sprintDuration = sprintDuration; }
    public void setCurrentSprint(ObjectId currentSprint) { this.currentSprint = currentSprint; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", name='" + name + '\'' + ", activities=" + activities + ", developerTeam="
                + developerTeam + ", sprints=" + sprints + ", startDate=" + startDate + ", endDate=" + endDate
                + ", type=" + type + ", status='" + status + '\'' + '}';
    }

}
