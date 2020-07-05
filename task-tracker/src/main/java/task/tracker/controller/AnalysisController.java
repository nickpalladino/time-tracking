package task.tracker.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import task.tracker.model.request.AnalysisRequest;
import task.tracker.service.AnalysisService;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Map;

@Controller("/v1")
public class AnalysisController {

    @Inject
    AnalysisService analysisService;

    @Post("/analysis")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Map<String, BigDecimal>> analysis(@Body AnalysisRequest request) {
        return HttpResponse.ok(analysisService.analyze(request));
    }

}
