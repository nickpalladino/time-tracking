package task.tracker.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
public class QueryRequest {

    @Singular
    @JsonProperty("query")
    private List<String> queryStatements;
    @Singular
    @JsonProperty("timeperiods")
    private Set<String> timePeriods;
}
