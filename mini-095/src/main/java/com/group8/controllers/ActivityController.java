package com.group8.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import com.group8.entity.Activity;
import com.group8.entity.Project;
import  com.group8.entity.User;
import com.group8.entity.UserStory;
import com.group8.helper.Helper;

public class ActivityController {

    public void createActivity(String activityType) {

        // General Activity attributes
        Helper helper = new Helper();
        String content;
        String name;
        LocalDate startDate;
        LocalDate endDate;
        String priority;
        ArrayList<User> teamMembers = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        // User Story attributes
        double storyPoints;
        String acceptanceCriteria;

        // 1. ask for generic input
        System.out.println("Enter name");
        name = helper.getString();

        System.out.println("Enter content");
        content = helper.getString();

        System.out.println("Enter start date as DD/MM/YYYY: ");
        startDate = helper.getDate();

        System.out.println("Enter end date as DD/MM/YYYY");
        endDate = helper.getDate();

        //System.out.println("Enter team members");
        //teamMembers.add(null); // TODO: Needs to be fixed, should be able to choose from a list or something
        //viewDevelopers();
        System.out.println("Enter ID of developers you want to add");
        helper.getString();

        System.out.println("Enter priority");
        priority = helper.getString();

        if (activityType.equals("UserStory")) {
            System.out.println("Enter team story points");
            storyPoints = helper.getDouble();

            System.out.println("Enter acceptance criteria");
            acceptanceCriteria = helper.getString();

        } else if (activityType.equals("Bug")) {
            // TODO: Under construction

            //activityModel.createActivity(activityType, name, content, startDate, endDate,
            // teamMembers, priority, id, null, null);

        } else if (activityType.equals("Task")) {
            // TODO: Under construction
            //createActivity(activityType,activityType, name, content, startDate, endDate,
            //             teamMembers, priority, id, null, null);

        } else {
            System.out.println("Oh no, something went wrong... :'( ");
        }
    }

    public void viewDevelopers(Project currentProject){
        ArrayList<User> developerTeam = currentProject.getDevelperTeam();
        for (User developer: developerTeam) {
            System.out.println(developer);
        }
    }

    public void assignDevelopers(User developer){

    }

    public void viewActivities(ArrayList<Activity> activities){
        for (Activity activity: activities) {
            System.out.println(activity);
        }
    }

    public void modifyContent(Activity activity, String content) {
        activity.setContent(content);
    }

    public void modifyName(Activity activity, String name) {
        activity.setName(name);
    }

    public void modifyStartDate(Activity activity, LocalDate startDate) {
        activity.setStartDate(startDate);
    }

    public void modifyEndDate(Activity activity, LocalDate endDate) {
        activity.setEndDate(endDate);
    }

    public void modifyPriority(Activity activity, String priority) {
        activity.setPriority(priority);
    }

    //Modify User Story Attributes
    public void modifyStoryPoints(Activity activity, double storyPoints) {

        if( activity instanceof UserStory){
            ((UserStory) activity).setStoryPoints(storyPoints);
        }else {
            System.out.println("Not a User Story");
        }
    }

    public void modifyAcceptanceCriteria(Activity activity, String acceptanceCriteria) {

        if( activity instanceof UserStory){
            ((UserStory) activity).setAcceptanceCriteria(acceptanceCriteria);
        }else {
            System.out.println("Not a User Story");
        }
    }
}
