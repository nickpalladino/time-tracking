package task.tracker.client.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Event {

    private int id;
    private String timestamp;
    private BigDecimal duration;
    private EventData data;

}
