package com.group8.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class Bug extends Activity {

    public Bug(String name, String content, LocalDate startDate, LocalDate endDate, String priority, String id) {

        super(name, content, startDate, endDate, priority, id);

    }
    
    @Override
    public String toString() {
        return "ID: " + this.getId() +  " " + this.getName() +
        " : " + this.getContent() + " Start: " + this.getStartDate() + " End: " + this.getEndDate() +
         "priority: " + this.getPriority() + " Completion Status: " + this.getCompletion() ;
    }
}
