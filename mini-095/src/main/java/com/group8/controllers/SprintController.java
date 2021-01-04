package com.group8.controllers;

import java.time.LocalDate;

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

public class SprintController {
    private static DatabaseController mongoDb = new DatabaseController();
    private static ProjectController projectController = new ProjectController();

    public ObjectId createSprint(String name, LocalDate sprintStartDate, LocalDate sprintEndDate) {
        final Sprint newSprint = new Sprint(name, sprintStartDate, sprintEndDate);
        MongoCollection sprintCollection = mongoDb.getSprintCollection().withCodecRegistry(mongoDb.createCodecRegistry("Sprints"));

        newSprint.setId();
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

}
