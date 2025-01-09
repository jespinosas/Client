package co.com.bancolombia.validationandhomologationservica.peq.config;

import co.com.bancolombia.transformservice.config.PatternModel;
import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;

import java.util.Map;

public  interface PEQService {
    Mono<Map<String, String>> getRequestEquivalences(JsonNode request, PatternModel patternModel);

}
