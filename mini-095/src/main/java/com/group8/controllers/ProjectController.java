package com.group8.controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import com.group8.entity.Activity;
import com.group8.entity.Project;
import com.group8.entity.ProjectType;
import com.group8.entity.User;

public class ProjectController {

    private Project currentProject;

    public ProjectController(Project currentProject) {
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

    public void addUserToTeam(User user) {
        ArrayList<User> tempList = currentProject.getDevelperTeam();
        tempList.add(user);
        currentProject.setDevelperTeam(tempList);

    }

    public void addActivityToProject (Activity activity){
        currentProject.addActivity(activity);
    }

    public void closeProject() {
        currentProject.setClosed(true);
    }

    public void reopenProject() {
        currentProject.setClosed(false);

    }

}
