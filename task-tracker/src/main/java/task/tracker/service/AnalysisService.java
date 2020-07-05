package task.tracker.service;

import lombok.NonNull;
import task.tracker.client.ActivityWatcherClient;
import task.tracker.client.model.Event;
import task.tracker.model.request.AnalysisRequest;

import javax.inject.Singleton;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

@Singleton
public class AnalysisService {

    private ActivityWatcherClient client;


    public AnalysisService() throws MalformedURLException {
        client = new ActivityWatcherClient(new URL("http://localhost:5600"));
    }


    public Map<String, BigDecimal> analyze(@NonNull AnalysisRequest request) {

        List<Event> events = client.getWindowActiveNotAfkEventsSortedByTimestamp(request.getDays());
        return calculateTaskTimes(events, request.getTaskNames());
    }




    public Map<String, BigDecimal> calculateTaskTimes(List<Event> events, Set<String> taskNames) {

        Map<String, BigDecimal> taskTimes = new HashMap<>();
        String currentTask = "INITIAL";

        for (Event event : events) {
            for (String taskName : taskNames) {
                if (containsIgnoreCase(event.getData().getTitle(), taskName)) {
                    currentTask = taskName;
                    break;
                }
            }

            taskTimes.merge(currentTask, event.getDuration(), BigDecimal::add);
        }

        // convert to hours from seconds with 1 decimal place and rounding
        taskTimes.replaceAll((k, v) -> v.divide(BigDecimal.valueOf(3600), 1, RoundingMode.HALF_UP));

        return taskTimes;
    }




}
