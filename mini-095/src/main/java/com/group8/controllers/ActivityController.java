package com.group8.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

//import  com.group8.entity.User;
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
        //ArrayList<User> teamMembers = new ArrayList<>();
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

        System.out.println("Enter team members");
        //teamMembers.add(null); // TODO: Needs to be fixed, should be able to choose from a list or something
                               // similar

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

        // 2. ask for specific input
        // 3. call the method
    }
}
