package co.com.bancolombia.model.validationandhomologation.gateways;

import reactor.core.publisher.Mono;

public interface HomologationGateway {

    Mono<Object> getEquivalenceError(String responseCode);
}
