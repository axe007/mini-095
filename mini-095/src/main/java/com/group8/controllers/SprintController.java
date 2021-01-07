package com.group8.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.group8.model.Activity;
import com.group8.helper.Helper;
import com.group8.model.Project;
import com.group8.model.Sprint;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.internal.async.SingleResultCallback;
import com.mongodb.internal.async.client.AsyncMongoCollection;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class SprintController {
    private static DatabaseController mongoDb = new DatabaseController();
    private static ProjectController projectController = new ProjectController();

    public ObjectId createSprint(String name, LocalDate sprintStartDate, LocalDate sprintEndDate) {
        final Sprint newSprint = new Sprint(name, sprintStartDate, sprintEndDate);
        MongoCollection sprintCollection = mongoDb.getSprintCollection()
                .withCodecRegistry(mongoDb.createCodecRegistry("Sprints"));

        newSprint.setId(new ObjectId());
        sprintCollection.insertOne(newSprint);

        ObjectId sprintId = newSprint.getId();
        System.out.println("Id: " + sprintId);

        return sprintId;
    }

    public ObjectId getSprintId(String sprintName) {
        Sprint sprint = mongoDb.getSprintCollection().withCodecRegistry(mongoDb.createCodecRegistry("Sprints"))
                .find(eq("name", sprintName)).first();
        ObjectId sprintId = sprint.getId();
        return sprintId;
    }

    public void completeSprint(ObjectId sprintId) {
        mongoDb.getSprintCollection().updateOne(eq("_id", sprintId), combine(set("isComplete", true)));
    }

    public String getSprintName(ObjectId sprintId) {
        String sprintName = "";
        Sprint sprint = mongoDb.getSprintCollection().withCodecRegistry(mongoDb.createCodecRegistry("Sprints"))
                .find(eq("_id", sprintId)).first();

        sprintName = sprint.getName();
        return sprintName;
    }

    public LocalDate getSprintDate(ObjectId sprintId, String date) {
        LocalDate sprintDate;
        Sprint sprint = mongoDb.getSprintCollection().withCodecRegistry(mongoDb.createCodecRegistry("Sprints"))
                .find(eq("_id", sprintId)).first();
        switch (date) {
            case "startDate" -> sprintDate = sprint.getStartDate();
            case "endDate" -> sprintDate = sprint.getEndDate();
            default -> throw new IllegalStateException("Unexpected value: " + date);
        }
        return sprintDate;
    }

    public ArrayList<Sprint> getSprintList() {
        ArrayList<Sprint> sprints = mongoDb.getSprintCollection().find().into(new ArrayList<Sprint>());
        return sprints;
    }

    public ArrayList<Sprint> getProjectSprintList(ObjectId projectId) {
        ArrayList<Sprint> projectSprints = new ArrayList<>();
        ArrayList<ObjectId> sprintsList = projectController.getProjectList(projectId, "sprints");
        for (ObjectId sprintId : sprintsList) {
            Sprint sprint = mongoDb.getSprintCollection().withCodecRegistry(mongoDb.createCodecRegistry("Sprints"))
                    .find(eq("_id", sprintId)).first();
            projectSprints.add(sprint);
        }

        return projectSprints;
    }

}
