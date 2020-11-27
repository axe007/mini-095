package com.group8.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

public class Sprint {
    private String name;
    private UUID id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Activity[] activities;
    private double status;// 100% or use enum status

    public Sprint(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = UUID.randomUUID();
        this.activities = null;
        this.status = 0.0;

    }

    @Override
    public String toString() {
        return "Sprint [activities=" + Arrays.toString(activities) + ", endDate=" + endDate + ", id=" + id + ", name="
                + name + ", startDate=" + startDate + ", status=" + status + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Activity[] getActivities() {
        return activities;
    }

    public void setActivities(Activity[] activities) {
        this.activities = activities;
    }

    public double getStatus() {
        return status;
    }

    public void setStatus(double status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

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