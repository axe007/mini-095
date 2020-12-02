package com.group8.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;

import com.group8.entity.Activity;
import com.group8.entity.Project;
import com.group8.entity.ProjectType;
import com.group8.entity.User;
import com.group8.controllers.*;

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

    // public void createActivity(String activity, Project currentProject) {
        
    // }

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
        Map<String, User> tempList = currentProject.getDeveloperTeam();
        tempList.put(user.getUuid(), user);
        currentProject.setDeveloperTeam(tempList);

    }

    public void closeProject() {
        currentProject.setClosed(true);
    }

    public void reopenProject() {
        currentProject.setClosed(false);

    }

}
