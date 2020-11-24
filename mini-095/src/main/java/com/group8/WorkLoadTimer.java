package com.group8;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Timer
 */
public class WorkLoadTimer {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration duration;

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration() {
        this.duration = Duration.between(startTime, endTime);
    }

}