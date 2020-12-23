package com.group8.constants;

import java.time.LocalDate;

import com.group8.model.Activity;
import com.group8.model.ProjectForGanttChart;

public class FakeDataForGanttChart {
    public static ProjectForGanttChart fakeProject = new ProjectForGanttChart("Fake project",
            LocalDate.of(2020, 10, 20), LocalDate.of(2020, 12, 20));
    public static Activity fakeActivitiy1 = new Activity("Fake activity1", "1", LocalDate.of(2020, 10, 22),
            LocalDate.of(2020, 10, 30));
    public static Activity fakeActivitiy2 = new Activity("Fake activity2", "1.1", LocalDate.of(2020, 10, 22),
            LocalDate.of(2020, 10, 30));

    public static Activity fakeActivitiy3 = new Activity("Fake activity3", "1.2", LocalDate.of(2020, 10, 22),
            LocalDate.of(2020, 10, 30));

    public static Activity fakeActivitiy4 = new Activity("Fake activity4", "2", LocalDate.of(2020, 10, 22),
            LocalDate.of(2020, 10, 30));

}
