package com.group8.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.group8.model.Activity;
import com.group8.model.*;
import com.mongodb.client.model.Filters;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class ProjectController {

    private static String EOL = System.lineSeparator();
    private static DatabaseController mongoDb = new DatabaseController();

    public boolean createProject(String name, LocalDate startDate, LocalDate endDate, String type) {
        boolean result = false;
        Project newProject = new Project(name, startDate, endDate, type);
        mongoDb.getProjectCollection().insertOne(newProject);
        System.out.println("New project created successfully!");

        return true;

    }

    public boolean openProject(ObjectId projectId) {
        // Receive selected project Id from Table View
        Session.setOpenProjectId(projectId);
        boolean success = true;
        return success;
    }

    public String getProjectDetail(ObjectId projectId, String projectAttribute) {
        String projectDetail = null;
        Project project = mongoDb.getProjectCollection().withCodecRegistry(mongoDb.createCodecRegistry("Projects")).find(eq("_id", projectId)).first();
        switch (projectAttribute) {
            case "projectName" -> projectDetail = project.getName();
            case "projectType" -> projectDetail = project.getType();
            case "projectStatus" -> projectDetail = project.getStatus();
        }
        return projectDetail;
    }

    public LocalDate getProjectDate(ObjectId projectId, String date) {
        LocalDate projectDate;
        Project project = mongoDb.getProjectCollection().withCodecRegistry(mongoDb.createCodecRegistry("Projects")).find(eq("_id", projectId)).first();
        switch (date) {
            case "startDate" -> projectDate = project.getStartDate();
            case "endDate" -> projectDate = project.getEndDate();
            default -> throw new IllegalStateException("Unexpected value: " + date);
        }
        return projectDate;
    }


    public void closeProject() {
        // should go back to previous window here

    }

    public List getProjectList() {
        List<Project> projects = mongoDb.getProjectCollection().find().into(new ArrayList<Project>());
        return projects;
    }

    public void modifyProject(String name, LocalDate startDate, LocalDate endDate, String type) {
        Project project = (Project) Session.getOpenItem();
        ObjectId id = project.getId();

        mongoDb.getProjectCollection().updateOne(eq("_id", id), combine(set("name", name), set("startDate", startDate),
                set("endDate", endDate), set("type", type), set("status", "Open")));
        System.out.println("Project details updated!");
    }

    public List<ObjectId> getProjectUsers(ObjectId projectId) {
        Project project = mongoDb.getProjectCollection().withCodecRegistry(mongoDb.createCodecRegistry("Projects")).find(eq("_id", projectId)).first();
        List<ObjectId> projectUsers = project.getDeveloperTeam();
        return projectUsers;
    }
    public List<ObjectId> getProjectActivities(ObjectId projectId) {
        Project project = mongoDb.getProjectCollection().withCodecRegistry(mongoDb.createCodecRegistry("Projects")).find(eq("_id", projectId)).first();
        List<ObjectId> projectActivities = project.getActivities();
        return projectActivities;
    }

    public ArrayList<String> getProjectUsernameList(ObjectId projectId) {
        ArrayList<String> projectUsernameList = new ArrayList<>();
        List<ObjectId> projectCurrentUsers = getProjectUsers(projectId);
        UserController userController = new UserController();
        for (ObjectId userId : projectCurrentUsers) {
            String userName = userController.getUserDetail(userId, "username");
            projectUsernameList.add(userName);
        }
        return projectUsernameList;
    }

    public void updateDeveloperTeam(List<String> assignedUsers) {
        UserController userController = new UserController();
        List<ObjectId> developerTeam = new ArrayList<ObjectId>();

        for (String user : assignedUsers) {
            ObjectId userId = userController.getUserId("username", user);
            developerTeam.add(userId);
        }

        ObjectId projectId = Session.getOpenProjectId();
        mongoDb.getProjectCollection().updateOne(eq("_id", projectId), set("developerTeam", developerTeam));


    }
}
