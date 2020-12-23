package com.group8.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Activity {

    private String content;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private ArrayList<User> teamMembers;
    private String priority;
    private ActivityStatus status;
    private String id;
    private String sessionID;
    // private ActivityType type;
    private ArrayList<LocalDateTime> trackingHistory;

    public Activity(String content, String name, LocalDate startDate, LocalDate endDate, ArrayList<User> teamMembers,
            String priority, ActivityStatus status, String id, String sessionID, ActivityType type,
            ArrayList<LocalDateTime> trackingHistory) {
        this.content = content;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teamMembers = teamMembers;
        this.priority = priority;
        this.status = status;
        this.id = id;
        this.sessionID = sessionID;
        // this.type = type;
        this.trackingHistory = trackingHistory;
    }

    public Activity(String content, String name, LocalDate startDate, LocalDate endDate, String priority, String id,
            String sessionID, ActivityType type) {

        this(content, name, startDate, endDate, new ArrayList<>(), priority, ActivityStatus.Todo, id, sessionID, type,
                new ArrayList<>());

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ArrayList<User> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(ArrayList<User> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    // public ActivityType getType() {
    // return type;
    // }

    // public void setType(ActivityType type) {
    // this.type = type;
    // }

    public ArrayList<LocalDateTime> getTrackingHistory() {
        return trackingHistory;
    }

    public void setTrackingHistory(ArrayList<LocalDateTime> trackingHistory) {
        this.trackingHistory = trackingHistory;
    }

    public void assignUser(User user) {
        this.teamMembers.add(user);
    }

    public void removeUser(User user) {
        this.teamMembers.remove(user);
    }

    public void addTrackTimeToHistory() {

    }

}
