package com.group8.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.group8.entity.Activity;
import com.group8.model.*;
import com.mongodb.client.model.Filters;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class ProjectController {

//    private Project currentProject;
//
//    public ProjectController(Project currentProject) {
//        this.currentProject = currentProject;
//    }
//
//    public ProjectController() {
//        this.currentProject = currentProject;
//    }

//    public ProjectController(String name, LocalDate startDate, LocalDate endDate, ProjectType type) {
//        this.currentProject = new Project(name, startDate, endDate, type);
//    }

    private static String EOL = System.lineSeparator();
    private static DatabaseController mongoDb = new DatabaseController();


    public boolean createProject(String name, LocalDate startDate, LocalDate endDate, String type) {
        boolean result = false;
        Project newProject = new Project(name, startDate, endDate, type);
        mongoDb.getProjectCollection().insertOne(newProject);
        System.out.println("New project created successfully!");

        return true;

    }

    public boolean createUser(String username, String password, String fullname, String emailAddress, String userRole) {
        boolean result = false;
        User newUser;

        if (userRole.equals("Developer")) {
            newUser = new Developer(username, password, fullname, emailAddress, userRole);
            // users.add(developer);
        } else {
            newUser = new Manager(username, password, fullname, emailAddress, userRole);
            // users.add(manager);
        }

        mongoDb.getUserCollection().insertOne(newUser);
        System.out.println("New user created successfully!");

        return true;
    }

    public boolean openProject(ObjectId projectId) {
        // Receive selected project Id from Table View

        Session.setOpenProjectId(projectId);
        boolean success = true;
        return success;
    }


//
//    public void deleteProject(Project currentProject) {
//
//        mongoDb.getProjectCollection().deleteOne(Filters.eq(currentProject));
//        System.out.println("This project was successfully deleted!");
//
//    }

    public void openProject() {
        //should open new window here

    }

    public void closeProject() {
        // should go back to previous window here

    }

    public List getProjectList() {

        List<Project> projects = mongoDb.getProjectCollection().find().into(new ArrayList<Project>());
        return projects;
    }

    public void findProject() {

    }

    public void modifyProject(String name, LocalDate startDate, LocalDate endDate, String type) {
        Project project = (Project)Session.getOpenItem();
        ObjectId id = project.getId();

        mongoDb.getProjectCollection().updateOne(eq("_id", id), combine(set("name", name), set("startDate", startDate), set("endDate", endDate), set("type", type), set("status", "Open")));
        System.out.println("Project details updated!");
    }



    /*public void addUserToTeam(User user) {
        ArrayList<User> tempList = currentProject.getDeveloperTeam();
        tempList.add(user);
        currentProject.setDeveloperTeam(tempList);

    }*/

//    public void closeProject() {
//        currentProject.setClosed(true);
//    }
//
//    public void reopenProject() {
//        currentProject.setClosed(false);
//
//    }

}
