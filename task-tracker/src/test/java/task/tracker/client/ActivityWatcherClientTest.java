package task.tracker.client;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import task.tracker.client.model.Bucket;
import task.tracker.client.model.Event;

import java.net.URL;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ActivityWatcherClientTest {

    private ActivityWatcherClient client;
    private List<Bucket> buckets;

    @BeforeAll
    @SneakyThrows
    void setup() {
        client = new ActivityWatcherClient(new URL("http://localhost:5600"));
    }

    @Test
    void getBucketsTest() {
        buckets = client.getBuckets();

        assertEquals(true, buckets.size() > 0, "Expected buckets");
    }

    @Test
    void getWindowActiveNotAfkEventsSortedByTimestampTest() {

        Set<OffsetDateTime> days = new HashSet<>();
        days.add(OffsetDateTime.parse("2020-06-29T04:15:30+04:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        List<Event> events = client.getWindowActiveNotAfkEventsSortedByTimestamp(days);

        assertEquals(true, events.size() > 0, "Expected events");
    }



}
