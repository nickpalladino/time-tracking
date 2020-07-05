package task.tracker.model.request;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
public class AnalysisRequest {

    private Set<String> taskNames;
    private Set<OffsetDateTime> days;

}
