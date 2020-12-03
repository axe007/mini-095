package com.group8.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;

import com.group8.entity.*;
import com.group8.helper.Helper;

public class ActivityController {
    private Activity activity; 
    private Helper helper; 
    // private Project project; 
    public void createActivity(String activityType, Project project) {

        // General Activity attributes
        String content;
        String name;
        LocalDate startDate;
        LocalDate endDate;
        String priority;
        String id = helper.getMenuInput();

        // User Story attributes
        double storyPoints;
        String acceptanceCriteria;
        Map<String, User> userActivityMap; 
        // Task
        // TODO: add task attributes here

        // Bug
        // TODO: add bug attributes here

        // 1. ask for generic input
        System.out.println("Enter name");
        name = helper.getString();

        System.out.println("Enter content");
        content = helper.getString();

        System.out.println("Enter start date");
        startDate = null; // TODO:

        System.out.println("Enter end date");
        endDate = null;

        System.out.println("Enter team members");

        System.out.println("Enter priority");
        priority = helper.getString();

        if (activityType.equals("UserStory")) {
            System.out.println("Enter team story points");
            storyPoints = helper.getDouble();

            System.out.println("Enter acceptance criteria");
            acceptanceCriteria = helper.getString();
            this.activity = new UserStory(name, content, startDate, endDate, priority, id, storyPoints, acceptanceCriteria);
            project.addActivity(activity.getId(), activity);
        } else if (activityType.equals("Bug")) {
            this.activity = new Bug( name,  content,  startDate,  endDate,  priority,  id);
            project.addActivity(activity.getId(), activity);

        } else if (activityType.equals("Task")) {
            this.activity = new Task( name,  content,  startDate,  endDate,  priority,  id);
            project.addActivity(activity.getId(), activity);
        } else {
            System.out.println("Oh no, something went wrong... :'( ");
        }

        // 2. ask for specific input
        // 3. call the method
    }

    public void assignUserStory(String activityType, String activityId, String assigneeId, Project project) {
        
        if (project.getActivities().containsKey(activityId)) {
            if (activityType.equals("UserStory")) {
                UserStory newUserStory = (UserStory) project.getActivities().get(activityId);
                newUserStory.addMember(project.getDeveloperTeam().get(assigneeId));
            } else {
                Task newTask = (Task) project.getActivities().get(activityId);
                newTask.addMember(project.getDeveloperTeam().get(assigneeId));
            }
        }
    }

    // public void logTime()
}
