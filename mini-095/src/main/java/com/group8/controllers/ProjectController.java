package com.group8.controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import com.group8.model.Project;
import com.group8.model.User;

public class ProjectController {

    private Project currentProject;

    public ProjectController(Project currentProject) {
        this.currentProject = currentProject;
    }

    public ProjectController() {
        this.currentProject = currentProject;
    }

    public ProjectController(String name, LocalDate startDate, LocalDate endDate, ProjectType type) {
        this.currentProject = new Project(name, startDate, endDate, type);
    }

    public void createSprint() {

    }

    public void createActivity(Activity activity) {

    }

    public void removeSprint() {

    }

    public void removeActivity() {

    }

    public void modifyStartDate(LocalDate newStartDate) {
        this.currentProject.setStartDate(newStartDate);

    }

    public void modifyEndDate(LocalDate newEndDate) {
        this.currentProject.setEndDate(newEndDate);

    }

    /*
     * public void addUserToTeam(User user) { ArrayList<User> tempList =
     * currentProject.getDeveloperTeam(); tempList.add(user);
     * currentProject.setDeveloperTeam(tempList);
     * 
     * }
     */

    public void closeProject() {
        currentProject.setClosed(true);
    }

    public void reopenProject() {
        currentProject.setClosed(false);

    }

}
