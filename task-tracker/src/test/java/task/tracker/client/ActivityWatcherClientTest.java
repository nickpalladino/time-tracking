package task.tracker.client;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import task.tracker.client.model.Bucket;
import task.tracker.client.model.Event;

import java.net.URL;
import java.util.List;

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

        List<Event> events = client.getWindowActiveNotAfkEventsSortedByTimestamp();

        assertEquals(true, events.size() > 0, "Expected events");
    }



}
