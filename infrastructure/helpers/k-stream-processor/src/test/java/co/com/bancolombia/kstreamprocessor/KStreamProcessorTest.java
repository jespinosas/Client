package co.com.bancolombia.kstreamprocessor;

import co.com.bancolombia.kstreamprocessor.dto.JoinJson;
import co.com.bancolombia.kstreamprocessor.gateway.KStreamProcessorGateway;
import co.com.bancolombia.model.events.gateway.MQGateway;
import co.com.bancolombia.validationandhomologationservica.peq.config.PEQService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class KStreamProcessorTest {
    @Mock
    MQGateway mqGateway;
    @Mock
    PEQService peqService;

    KStreamProcessorGateway kStreamProcessorGateway;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        kStreamProcessorGateway = new KStreamProcessorImpl(null, mqGateway, peqService);
    }

    @Test
    void processTest() {
        when(mqGateway.send(anyString())).thenReturn(Mono.empty());
        when(peqService.getRequestEquivalences(any(), any())).thenReturn(any());

        JoinJson joinJson = new JoinJson();
        joinJson.setTipoDocContacto("TIPDOC_FS001");
        joinJson.setNumeroDocContacto("1234567890");

        StepVerifier.create(kStreamProcessorGateway.process(joinJson))
            .expectComplete();
    }
}
