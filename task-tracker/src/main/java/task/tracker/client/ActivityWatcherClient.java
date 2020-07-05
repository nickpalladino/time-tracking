package task.tracker.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.RxHttpClient;
import io.reactivex.Flowable;
import task.tracker.client.model.Bucket;
import task.tracker.client.model.Event;
import task.tracker.client.model.QueryRequest;

import java.net.URL;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static io.micronaut.http.HttpRequest.GET;
import static io.micronaut.http.HttpRequest.POST;

public class ActivityWatcherClient {

    private static final String API_BASE_PATH = "/api/0";
    private static final String BUCKETS_ENDPOINT = API_BASE_PATH + "/buckets";
    // aw-server redirects if you send query without the trailing / and the client gets messed up
    private static final String QUERY_ENDPOINT = API_BASE_PATH + "/query/";

    private RxHttpClient client;
    private ObjectMapper mapper;
    private final URL uri;

    public ActivityWatcherClient(URL uri) {
        this.uri = uri;
        this.client = RxHttpClient.create(uri);
        this.mapper = new ObjectMapper();
    }

    public List<Bucket> getBuckets() {

        Flowable<HttpResponse<Map<String, Bucket>>> call = client.exchange(
                GET(BUCKETS_ENDPOINT), Argument.mapOf(String.class, Bucket.class)
        );

        HttpResponse<Map<String, Bucket>> response = call.blockingFirst();
        Map<String, Bucket> bucketMap = response.getBody().orElse(new HashMap<>());

        return new ArrayList<>(bucketMap.values());
    }

    /**
     * Execute a query to get all active window events where the user is not away from the keyboard
     * and sort chronologically
     *
     * TODO: pass timeperiods in instead of hardcoding
     */
    public List<Event> getWindowActiveNotAfkEventsSortedByTimestamp(Set<OffsetDateTime> timePeriods) {

        // convert to activity watch timeperiod format, do per day
        Set<String> dayRanges = timePeriods.stream()
                .map(day -> day.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) + "/" +
                            day.plusDays(1).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .collect(Collectors.toSet());

        QueryRequest request = QueryRequest.builder()
                .queryStatement("window_events = query_bucket(find_bucket(\"aw-watcher-window_\"));")
                .queryStatement("afk_events = query_bucket(find_bucket(\"aw-watcher-afk_\"));")
                .queryStatement("window_events_active = filter_period_intersect(window_events, filter_keyvals(afk_events, \"status\", [\"not-afk\"]));")
                .queryStatement("events = sort_by_timestamp(window_events_active);")
                .queryStatement("RETURN = events;")
                .timePeriods(dayRanges)
                .build();

        Flowable<HttpResponse<List<List<LinkedHashMap>>>> call = client.exchange(
                POST(QUERY_ENDPOINT, request).contentType(MediaType.APPLICATION_JSON),
                Argument.listOf(Argument.listOf(LinkedHashMap.class).getType())
        );

        // micronaut doesn't deserialize with nested lists and POJOs properly so doing this as a work around
        HttpResponse<List<List<LinkedHashMap>>> response = call.blockingFirst();
        List<List<LinkedHashMap>> hashMapLists = response.getBody().orElse(new ArrayList<>());
        List<List<Event>> eventLists = new ArrayList<>();

        for (List<LinkedHashMap> list : hashMapLists) {
            List<Event> events = mapper.convertValue(list, new TypeReference<List<Event>>() { });
            eventLists.add(events);
        }

        // merge all lists into one
        List<Event> events = eventLists.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        return events;
    }


}
