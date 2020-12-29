package com.group8.constants;

import java.time.LocalDate;

import com.group8.model.Activity;
import com.group8.model.GanttChartActivity;
import com.group8.model.ProjectForGanttChart;

public class FakeDataForGanttChart {
        public static ProjectForGanttChart fakeProject = new ProjectForGanttChart("Fake project",
                        LocalDate.of(2020, 10, 20), LocalDate.of(2020, 12, 20));
        public static GanttChartActivity fakeActivitiy1 = new GanttChartActivity("Fake activity1",
                        LocalDate.of(2020, 10, 22), LocalDate.of(2020, 10, 30), 1, 0, 0);
        public static GanttChartActivity fakeActivitiy2 = new GanttChartActivity("Fake activity2",
                        LocalDate.of(2020, 10, 24), LocalDate.of(2020, 10, 30), 1, 1, 0);

        public static GanttChartActivity fakeActivitiy3 = new GanttChartActivity("Fake activity3",
                        LocalDate.of(2020, 11, 02), LocalDate.of(2020, 11, 30), 1, 1, 1);

        public static GanttChartActivity fakeActivitiy4 = new GanttChartActivity("Fake activity4",
                        LocalDate.of(2020, 11, 22), LocalDate.of(2020, 12, 30), 2, 0, 0);
        public static GanttChartActivity fakeActivitiy5 = new GanttChartActivity("Fake activity4",
                        LocalDate.of(2020, 10, 22), LocalDate.of(2020, 11, 30), 1, 2, 0);

}
