package com.group8.controllers;

import java.time.LocalDate;

import com.group8.entity.Activity;
import com.group8.helper.Helper;
import com.group8.model.Sprint;

public class SprintController {
    private Sprint currentSprint;

    public SprintController(Sprint currentSprint) {
        this.currentSprint = currentSprint;

    }

    public SprintController(String name, LocalDate startDate, LocalDate endDate) {

        this.currentSprint = new Sprint(name, startDate, endDate);
    }

    public void closeSprint() {
        modifyStatus(1);

    }
    // this function should belong to projectcontroller
    // public void deleteSprint(){

    // }

    public void addActivityToSprint(Activity activity) {
        this.currentSprint.activities.add(activity);

    }

    public void removeActivityFromSprint(Activity activity) {
        this.currentSprint.activities.remove(activity);

    }

    public void modifyStartDate(LocalDate newStartDate) {
        this.currentSprint.setStartDate(newStartDate);

    }

    public void modifyEndDate(LocalDate newEndDate) {
        this.currentSprint.setEndDate(newEndDate);

    }

    public void modifyStatus(double newStatus) {
        this.currentSprint.setStatus(newStatus);

    }

}
