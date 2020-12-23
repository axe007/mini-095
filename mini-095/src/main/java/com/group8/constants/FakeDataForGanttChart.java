package com.group8.constants;

import java.time.LocalDate;

import com.group8.entity.ActivityForGanttChart;
import com.group8.entity.ProjectForGanttChart;

public class FakeDataForGanttChart {
    public static ProjectForGanttChart fakeProject = new ProjectForGanttChart("Fake project",
            LocalDate.of(2020, 10, 20), LocalDate.of(2020, 12, 20));
    public static ActivityForGanttChart fakeActivitiy1 = new ActivityForGanttChart("Fake activity1", "1",
            LocalDate.of(2020, 10, 22), LocalDate.of(2020, 10, 30));
    public static ActivityForGanttChart fakeActivitiy2 = new ActivityForGanttChart("Fake activity2", "1.1",
            LocalDate.of(2020, 10, 22), LocalDate.of(2020, 10, 30));

    public static ActivityForGanttChart fakeActivitiy3 = new ActivityForGanttChart("Fake activity3", "1.2",
            LocalDate.of(2020, 10, 22), LocalDate.of(2020, 10, 30));

    public static ActivityForGanttChart fakeActivitiy4 = new ActivityForGanttChart("Fake activity4", "2",
            LocalDate.of(2020, 10, 22), LocalDate.of(2020, 10, 30));

}
