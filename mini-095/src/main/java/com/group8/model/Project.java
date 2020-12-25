package com.group8.model;

import java.time.LocalDate;
import java.util.ArrayList;

import org.bson.types.ObjectId;

public class Project {
    private ObjectId id;
    private String name;
    private ArrayList<ObjectId> activities;
    private ArrayList<ObjectId> developerTeam;
    private ArrayList<ObjectId> sprints;
    private LocalDate startDate;
    private LocalDate endDate;
    private String type;
    private String status;
    // private double status;
    // private boolean isClosed;

    public Project() {
    }

    public Project(String name, LocalDate startDate, LocalDate endDate, String type) {
        this.name = name;
        this.activities = new ArrayList<>();
        this.developerTeam = new ArrayList<>();
        this.sprints = new ArrayList<>();
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.status = "Open";
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ObjectId> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<ObjectId> activities) {
        this.activities = activities;
    }

    public ArrayList<ObjectId> getDeveloperTeam() {
        return developerTeam;
    }

    public void setDeveloperTeam(ArrayList<ObjectId> developerTeam) {
        this.developerTeam = developerTeam;
    }

    public ArrayList<ObjectId> getSprints() {
        return sprints;
    }

    public void setSprints(ArrayList<ObjectId> sprints) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", name='" + name + '\'' + ", activities=" + activities + ", developerTeam="
                + developerTeam + ", sprints=" + sprints + ", startDate=" + startDate + ", endDate=" + endDate
                + ", type=" + type + ", status='" + status + '\'' + '}';
    }

    // public void createSprint() {
    //
    //
    // }

    // public void createActivity(Activity activity) {
    //
    // }
    //
    // public void removeSprint() {
    //
    // }
    //
    // public void removeActivity() {
    //
    // }
    //
    // public void modifyStartDate(LocalDate newStartDate) {
    // this.currentProject.setStartDate(newStartDate);
    //
    // }
    //
    // public void modifyEndDate(LocalDate newEndDate) {
    // this.currentProject.setEndDate(newEndDate);
    //
    // }
    // public String getName() {
    // return name;
    // }
    //
    // public void setName(String name) {
    // this.name = name;
    // }
    //
    // public ArrayList<Activity> getActivities() {
    // return activities;
    // }
    //
    // public void setActivities(ArrayList<Activity> activities) {
    // this.activities = activities;
    // }
    //
    // public UUID getId() {
    // return id;
    // }
    //
    // /*public ArrayList<User> getDeveloperTeam() {
    // return developerTeam;
    // }*/
    //
    // /*public void setDeveloperTeam(ArrayList<User> developerTeam) {
    // this.developerTeam = developerTeam;
    // }*/
    // public ArrayList<Sprint> getSprints() {
    // return sprints;
    // }
    //
    // public void setSprints(ArrayList<Sprint> sprints) {
    // this.sprints = sprints;
    // }
    //
    // public LocalDate getStartDate() {
    // return startDate;
    // }
    //
    // public void setStartDate(LocalDate startDate) {
    // this.startDate = startDate;
    // }
    //
    // public LocalDate getEndDate() {
    // return endDate;
    // }
    //
    // public void setEndDate(LocalDate endDate) {
    // this.endDate = endDate;
    // }
    //
    // public ProjectType getType() {
    // return type;
    // }
    //
    // public void setType(ProjectType type) {
    // this.type = type;
    // }
    //
    // @Override
    // public String toString() {
    // return "Project [activities=" + activities + ", endDate=" + endDate + ", id="
    // + id + ", name=" + name + ", sprints=" + sprints + ", startDate=" + startDate
    // + ", type=" + type + "]";
    // }
    //
    // @Override
    // public int hashCode() {
    // final int prime = 31;
    // int result = 1;
    // result = prime * result + ((id == null) ? 0 : id.hashCode());
    // return result;
    // }
    //
    // @Override
    // public boolean equals(Object obj) {
    // if (this == obj)
    // return true;
    // if (obj == null)
    // return false;
    // if (getClass() != obj.getClass())
    // return false;
    // Project other = (Project) obj;
    // if (id == null) {
    // if (other.id != null)
    // return false;
    // } else if (!id.equals(other.id))
    // return false;
    // return true;
    // }

    // public double getStatus() {
    // return status;
    // }
    //
    // public void setStatus(double status) {
    // this.status = status;
    // }

    // public boolean isClosed() {
    // return isClosed;
    // }
    //
    // public void setClosed(boolean isClosed) {
    // this.isClosed = isClosed;
    // }

    // gantt chart

}
