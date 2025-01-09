package co.com.bancolombia.validationandhomologationservica.peq;

import co.com.bancolombia.model.validation.exceptions.TechnicalException;
import co.com.bancolombia.peq.EquivalenceParameterizer;
import co.com.bancolombia.peq.dtos.CriterioParametrizacion;
import co.com.bancolombia.peq.dtos.EncabezadoHomologacion;
import co.com.bancolombia.peq.dtos.request.PeqRequestData;
import co.com.bancolombia.transformservice.config.PatternModel;
import co.com.bancolombia.transformservice.config.toplaintext.data_types.HomologatedJsonValue;
import co.com.bancolombia.transformservice.config.utils.Type;
import co.com.bancolombia.validationandhomologationservica.peq.config.PEQConfigProperties;
import co.com.bancolombia.validationandhomologationservica.peq.config.PEQService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Collectors;

import static co.com.bancolombia.model.validation.exceptions.message.TechnicalErrorMessage.EQUIVALENCE_SERVICE_ERROR;
import static co.com.bancolombia.transformservice.config.utils.Type.HOMOLOGATION;

@AllArgsConstructor
public class PEQServiceEnabled implements PEQService {

    private final EquivalenceParameterizer equivalenceParameterizer;
    private final PEQConfigProperties properties;
    @Override
    public Mono<Map<String, String>> getRequestEquivalences(JsonNode request, PatternModel patternModel) {
        var criterios = patternModel.requestModel().stream().filter(
                requestModel -> requestModel.getType().equals(HOMOLOGATION)
                        && request.at(((HomologatedJsonValue) requestModel).getAttribute()).textValue() != null)
                .map(requestModel ->
                        new CriterioParametrizacion(((HomologatedJsonValue) requestModel).getTypology(),
                                request.at(((HomologatedJsonValue) requestModel).getAttribute()).textValue()))
                .toList();
        if(criterios.isEmpty())
            return Mono.just(Map.of());
        var header = new EncabezadoHomologacion(properties.getOriginApplication(),properties.getDestinationApplication(),
                properties.getOriginSociety(),properties.getTargetSociety());

        var peqRequest = PeqRequestData.builder()
                .encabezadoHomologacion(header)
                .criterioParametrizacion(criterios).build();

        return getPeqEquivalences(peqRequest);

    }

    private Mono<Map<String, String>> getPeqEquivalences(PeqRequestData request){
        return equivalenceParameterizer.getParameterization(request)
                .map(response -> response.stream().collect(Collectors.toMap(
                      result -> result.criterioParametrizacion().key(),
                      result -> result.valorParametrizado().valorDestino(), (acc, next) -> acc)
                )).onErrorResume(error ->
                        Mono.error(new TechnicalException(EQUIVALENCE_SERVICE_ERROR, error)));
    }
}
