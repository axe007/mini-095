package com.group8.entity;

import com.group8.model.User;

import java.time.LocalDate;
import java.util.ArrayList;

public class UserStory extends Activity {
    //private UUID id;
    //private String name;
    private double storyPoints;
    //private String description;
    private String acceptanceCriteria;
    //private boolean completion;
    //private String teamMember;
    //private String priority;
    //private LocalDate startDate;
    //private LocalDate endDate;

    // public Activity (String shortDescription, String content, LocalDate startDate, LocalDate endDate, User assignee, String priority, String id) {
    //     this.shortDescription = shortDescription; 
    //     this.content = content;
    //     this.startDate = startDate;
    //     this.endDate = endDate;
    //     this.assignee = assignee;
    //     this.priority = priority;
    //     this.id = id;
    //     this.completion = false;
    // }

    public UserStory (String name, String content, LocalDate startDate, LocalDate endDate,
                      ArrayList<User> teamMembers, String priority, String id, double storyPoints, String acceptanceCriteria) {
        super(name, content, startDate, endDate, teamMembers, priority, id);
        this.storyPoints = storyPoints;
        this.acceptanceCriteria = acceptanceCriteria;
        
    }

    /*public UUID getId() {
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
    }*/

    public double getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(double storyPoints) {
        this.storyPoints = storyPoints;
    }
    /*
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    } */

    public String getAcceptanceCriteria() {
        return acceptanceCriteria;
    }

    public void setAcceptanceCriteria(String acceptanceCriteria) {
        this.acceptanceCriteria = acceptanceCriteria;
    }
    /*
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
*/
    @Override
    public String toString() {
        return "ID: " + this.getId() +  " " + this.getName() +
        " : " + this.getContent() + " Start: " + this.getStartDate() + " End: " + this.getEndDate() +
         "priority: " + this.getPriority() + " Completion Status: " + this.getCompletion() + "Story Points: " + 
         this.storyPoints + " AcceptanceCriteria: " + this.acceptanceCriteria ;

    }

}
