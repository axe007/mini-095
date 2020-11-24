package com.group8;

import java.util.ArrayList;

public class Sprint {
    private ArrayList<String> tasks; //replace with userStory? or stay with task?
    //private ArrayList<Report> reports;
    private String startDate;
    private String endDate;

    public Sprint (String startDate, String endDate){
        this.tasks = new ArrayList<String>();
        //this.reports = new ArrayList<Report>();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ArrayList<String> getTask() {
        return tasks;
    }

    public void setTask(ArrayList<String> task) {
        this.tasks = task;
    }

    public void addTask(String task){
        this.tasks.add(task);
    }

//    public ArrayList<Report> getReport() {
//        return reports;
//    }
//
//    public void setReport(ArrayList<Report> report) {
//        this.reports = report;
//    }
//
//    public void addReport(Report report) {
//        this.reports.add(report);
//    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Sprint" +
                "task: " + tasks +
                //", report: " + reports +
                ", startDate: " + startDate +
                ", endDate: " + endDate;
    }
}
