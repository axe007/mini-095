package com.group8.model;

import com.group8.model.User;

import java.util.*;
import java.time.LocalDate;

public class Task extends Activity {
    public Task(String name, String content, LocalDate startDate, LocalDate endDate, ArrayList<User> teamMembers,
            String priority, String id) {

        super(content, name, startDate, endDate, priority, id, "1.1.1  ", ActivityType.Task);

    }

    @Override
    public String toString() {
        return "ID: " + this.getId() + " " + this.getName() + " : " + this.getContent() + " Start: "
                + this.getStartDate() + " End: " + this.getEndDate() + "priority: " + this.getPriority()
                + " Completion Status: " + this.getSessionID();
    }
}
