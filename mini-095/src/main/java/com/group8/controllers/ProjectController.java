package com.group8.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.group8.model.*;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class ProjectController {

    private static String EOL = System.lineSeparator();
    private static DatabaseController mongoDb = new DatabaseController();

    public void createProject(String name, String description, LocalDate startDate, LocalDate endDate, String type, int sprintDuration) {
        Project newProject = new Project(name, description, startDate, endDate, type, sprintDuration);
        mongoDb.getProjectCollection().insertOne(newProject);
    }

    public boolean openProject(Project project) {
        // Receive selected project Id from Table View
        Session.setOpenProjectId(project.getId());
        ObjectId sprintId = project.getCurrentSprint();
        if (sprintId == null) {
            Session.setCurrentSprintId(null);
        } else {
            Session.setCurrentSprintId(sprintId);
        }

        String projectName = project.getName();
        LocalDate projectStartDate = project.getStartDate();
        LocalDate projectEndDate = project.getEndDate();
        Session.setOpenProjectName(projectName);
        Session.setProjectStartDate(projectStartDate);
        Session.setProjectEndDate(projectEndDate);

        if (sprintId != null) {
            Session.setCurrentSprintId(sprintId);
        }

        boolean success = true;
        return success;
    }

    public String getProjectDetail(ObjectId projectId, String projectAttribute) {
        String projectDetail = null;
        Project project = mongoDb.getProjectCollection().withCodecRegistry(mongoDb.createCodecRegistry("Projects"))
                .find(eq("_id", projectId)).first();
        switch (projectAttribute) {
            case "projectName" -> projectDetail = project.getName();
            case "projectDescription" -> projectDetail = project.getDescription();
            case "projectType" -> projectDetail = project.getType();
            case "projectStatus" -> projectDetail = project.getStatus();
        }
        return projectDetail;
    }

    public LocalDate getProjectDate(ObjectId projectId, String date) {
        LocalDate projectDate;
        Project project = mongoDb.getProjectCollection().withCodecRegistry(mongoDb.createCodecRegistry("Projects"))
                .find(eq("_id", projectId)).first();
        switch (date) {
            case "startDate" -> projectDate = project.getStartDate();
            case "endDate" -> projectDate = project.getEndDate();
            default -> throw new IllegalStateException("Unexpected value: " + date);
        }
        return projectDate;
    }

    public ArrayList<Project> getProjectList() {
        ArrayList<Project> projects = mongoDb.getProjectCollection().find().into(new ArrayList<Project>());
        return projects;
    }

    public void modifyProject(String name, String description, LocalDate startDate, LocalDate endDate, String type, int sprintDuration) {
        Project project = (Project) Session.getOpenItem();
        ObjectId id = project.getId();

        mongoDb.getProjectCollection().updateOne(eq("_id", id), combine(set("name", name), set("description", description), set("startDate", startDate),
                set("endDate", endDate), set("type", type), set("status", "Open"), set("sprintDuration", sprintDuration)));
        System.out.println("Project details updated!");
    }

    public ArrayList<ObjectId> getProjectList(ObjectId projectId, String listType) {
        if (projectId == null || projectId.equals(null)) {
            projectId = Session.getOpenProjectId();
        }

        Project project = mongoDb.getProjectCollection().withCodecRegistry(mongoDb.createCodecRegistry("Projects"))
                .find(eq("_id", projectId)).first();
        ArrayList<ObjectId> projectList = new ArrayList<>();
        if (listType.equals("users")) {
            projectList = project.getDeveloperTeam();
        } else if (listType.equals("activities")) {
            projectList = project.getActivities();
        } else {
            projectList = project.getSprints();
        }
        return projectList;
    }

    public ArrayList<String> getProjectUsernameList(ObjectId projectId) {
        ArrayList<String> projectUsernameList = new ArrayList<>();
        List<ObjectId> projectCurrentUsers = getProjectList(projectId, "users");
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

    public void updateActivityList(List<ObjectId> activityId) {
        ObjectId projectId = Session.getOpenProjectId();
        Project project = mongoDb.getProjectCollection().withCodecRegistry(mongoDb.createCodecRegistry("Projects"))
                .find(eq("_id", projectId)).first();
        List<ObjectId> projectActivities = project.getActivities();

        for (ObjectId activity : activityId) {
            projectActivities.add(activity);
        }
        mongoDb.getProjectCollection().updateOne(eq("_id", projectId), set("activities", projectActivities));
    }

    public ObjectId getCurrentSprintId() {
        ObjectId projectId = Session.getOpenProjectId();
        Project project = mongoDb.getProjectCollection().withCodecRegistry(mongoDb.createCodecRegistry("Projects"))
                .find(eq("_id", projectId)).first();
        ObjectId currentSprintId = project.getCurrentSprint();

        return currentSprintId;
    }

    public void updateSprints(ObjectId sprintId) {
        ObjectId projectId = Session.getOpenProjectId();
        Project project = mongoDb.getProjectCollection().withCodecRegistry(mongoDb.createCodecRegistry("Projects"))
                .find(eq("_id", projectId)).first();

        ArrayList<ObjectId> sprintList = project.getSprints();
        sprintList.add(sprintId);
        project.setSprints(sprintList);
        project.setCurrentSprint(sprintId);

        mongoDb.getProjectCollection().updateOne(eq("_id", projectId), combine(set("sprints", sprintList), set("currentSprint", sprintId)));
    }

    public void completeSprint() {
        ObjectId projectId = Session.getOpenProjectId();
        Project project = mongoDb.getProjectCollection().withCodecRegistry(mongoDb.createCodecRegistry("Projects"))
                .find(eq("_id", projectId)).first();
        mongoDb.getProjectCollection().updateOne(eq("_id", projectId), set("currentSprint", null));
    }


    // TEST METHOD - DONT RUN
    public void overwriteActivityListDelete() {
        ActivityController activityController = new ActivityController();
        ArrayList<Activity> allActivities = activityController.getActivitiesList();
        ArrayList<ObjectId> allActivityIds = new ArrayList<>();

        for (Activity activity : allActivities) {
            ObjectId activityId = activity.getId();
            allActivityIds.add(activityId);
        }

        ObjectId projectId = Session.getOpenProjectId();
        Project project = mongoDb.getProjectCollection().withCodecRegistry(mongoDb.createCodecRegistry("Projects"))
                .find(eq("_id", projectId)).first();
        project.setActivities(allActivityIds);
        mongoDb.getProjectCollection().updateOne(eq("_id", projectId), set("activities", allActivityIds));
    }
}