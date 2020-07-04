package task.tracker.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import task.tracker.service.AnalysisService;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

@Controller("/v1")
public class AnalysisController {

    @Inject
    AnalysisService analysisService;

    @Post("/analysis")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Map<String, BigDecimal>> analysis(@Body Set<String> taskNames) {
        return HttpResponse.ok(analysisService.analyze(taskNames));
    }

}
