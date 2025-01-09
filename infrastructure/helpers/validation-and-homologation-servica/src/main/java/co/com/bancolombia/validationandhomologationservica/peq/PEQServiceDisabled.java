package co.com.bancolombia.validationandhomologationservica.peq;

import co.com.bancolombia.transformservice.config.PatternModel;
import co.com.bancolombia.validationandhomologationservica.peq.config.PEQService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@AllArgsConstructor
@Log
public class PEQServiceDisabled implements PEQService {

    @Override
    public Mono<Map<String, String>> getRequestEquivalences(JsonNode request, PatternModel patternModel) {
        log.warning("PEQ Service is disabled");
        return Mono.just(Map.of());
    }
}
