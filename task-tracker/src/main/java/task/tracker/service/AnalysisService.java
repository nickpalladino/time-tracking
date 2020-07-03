package task.tracker.service;

import task.tracker.client.model.Event;

import java.math.BigDecimal;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class AnalysisService {

    public Map<String, BigDecimal> calculateTaskTimes(List<Event> events, Set<String> taskNames) {

        Map<String, BigDecimal> taskTimes = new HashMap<>();
        String currentTask = "INITIAL";

        for (Event event : events) {

            for (String taskName : taskNames) {
                if (containsIgnoreCase(event.getData().getTitle(), taskName)) {
                    currentTask = taskName;
                }
            }

            taskTimes.merge(currentTask, event.getDuration(), BigDecimal::add);
        }
        return taskTimes;
    }


}
