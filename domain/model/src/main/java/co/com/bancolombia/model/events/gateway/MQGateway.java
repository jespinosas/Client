package co.com.bancolombia.model.events.gateway;

import reactor.core.publisher.Mono;

public interface MQGateway {
    Mono<String> send(String message);
}
