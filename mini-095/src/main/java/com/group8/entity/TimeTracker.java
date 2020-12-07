package com.group8.entity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.group8.helper.Helper;

public class TimeTracker {
    private long hours;
    private LocalDateTime startDateTime;

    public TimeTracker(long hours, LocalDateTime startDateTime) {
        this.hours = hours; 
        this.startDateTime = startDateTime; 
    }

    public LocalDateTime getStartDateTime(long hours) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDateTime = now.minus(hours, ChronoUnit.HOURS); 
        return startDateTime; 
    }

    public void setStartDateTime() {
        Helper helper = new Helper();
        this.startDateTime = helper.localDateExceptionHandler(); 
    }
    
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

}
