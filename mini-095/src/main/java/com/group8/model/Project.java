package com.group8.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import com.group8.model.Sprint;

public class Project {
    private String name;
    private ArrayList<Activity> activities;
    private UUID id;
    // private ArrayList<User> developerTeam;
    private ArrayList<Sprint> sprints;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectType type;
    private double status;
    private boolean isClosed;

    public Project(String name, LocalDate startDate, LocalDate endDate, ProjectType type) {
        this.name = name;
        this.activities = new ArrayList<>();
        this.id = UUID.randomUUID();
        // this.developerTeam = new ArrayList<>();
        this.sprints = sprints;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.status = 0.0;
        this.isClosed = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }

    public UUID getId() {
        return id;
    }

    /*
     * public ArrayList<User> getDeveloperTeam() { return developerTeam; }
     */

    /*
     * public void setDeveloperTeam(ArrayList<User> developerTeam) {
     * this.developerTeam = developerTeam; }
     */

    public ArrayList<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(ArrayList<Sprint> sprints) {
        this.sprints = sprints;
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

    public ProjectType getType() {
        return type;
    }

    public void setType(ProjectType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Project [activities=" + activities + ", endDate=" + endDate + ", id=" + id + ", name=" + name
                + ", sprints=" + sprints + ", startDate=" + startDate + ", type=" + type + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Project other = (Project) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public double getStatus() {
        return status;
    }

    public void setStatus(double status) {
        this.status = status;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    // gantt chart

}