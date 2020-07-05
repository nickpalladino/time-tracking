package task.tracker.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import task.tracker.client.ActivityWatcherClient;
import task.tracker.client.model.Event;

import java.math.BigDecimal;
import java.net.URL;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AnalysisServiceTest {

    private ActivityWatcherClient client;
    private List<Event> events;
    private AnalysisService service;
    private Set<String> tasks;
    private Set<OffsetDateTime> days;

    @BeforeAll
    @SneakyThrows
    void setup() {
        client = new ActivityWatcherClient(new URL("http://localhost:5600"));
        // get some data to test with
        days = new HashSet<>();
        days.add(OffsetDateTime.parse("2020-06-29T04:15:30+04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        events = client.getWindowActiveNotAfkEventsSortedByTimestamp(days);
        service = new AnalysisService();
        // put in test tasks
        tasks = new HashSet<>();
        tasks.add("INF-163");
        tasks.add("INF-164");
    }

    @Test
    void calculateTaskTimesTest() {
        Map<String, BigDecimal> times = service.calculateTaskTimes(events, tasks);

        assertEquals(true, times.size() > 0, "Expected times");
    }
}
