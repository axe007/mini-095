package com.group8.model;

import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Sprint {
    private ObjectId id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    public ArrayList<Activity> activities;
    private boolean isComplete;// Sprint is either "ongoing"-False or "completed"-True.

    public Sprint(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activities = null;
        this.isComplete = false;
    }

    public ObjectId getId() {
        return this.id;
    }
    public String getName() {
        return name;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public boolean isComplete() {
        return isComplete;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public void setComplete(boolean isComplete) { this.isComplete = isComplete; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Sprint other = (Sprint) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}