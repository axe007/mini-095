package com.group8;
import java.time.LocalDate;
import java.util.UUID;

public class UserStory {
    private UUID id;
    private String name;
    private double storyPoints;
    private String description;
    private String acceptanceCriteria;
    private boolean completion;
    private String teamMember;
    private String priority;
    private LocalDate startDate;
    private LocalDate endDate;

    public UserStory(UUID id, String name, double storyPoints, String description, String acceptanceCriteria, boolean completion, String teamMember, String priority, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.storyPoints = storyPoints;
        this.description = description;
        this.acceptanceCriteria = acceptanceCriteria;
        this.completion = completion;
        this.teamMember = teamMember;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(double storyPoints) {
        this.storyPoints = storyPoints;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAcceptanceCriteria() {
        return acceptanceCriteria;
    }

    public void setAcceptanceCriteria(String acceptanceCriteria) {
        this.acceptanceCriteria = acceptanceCriteria;
    }

    public boolean isCompletion() {
        return completion;
    }

    public void setCompletion(boolean completion) {
        this.completion = completion;
    }

    public String getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(String teamMember) {
        this.teamMember = teamMember;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
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


}
