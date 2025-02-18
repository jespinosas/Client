package co.com.bancolombia.mq.sender;

import co.com.bancolombia.commons.jms.api.MQMessageSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SampleMQMessageSenderTest {

    @Mock
    MQMessageSender sender;

    SampleMQMessageSender sampleMQMessageSender;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        sampleMQMessageSender = new SampleMQMessageSender(sender);
    }

    @Test
    void sendTest() {
        when(sender.send(any())).thenReturn(Mono.just("Message sent"));

        sampleMQMessageSender.send("Message")
                .as(StepVerifier::create)
                .assertNext(response -> {
                    assertEquals("Message sent", response);
                }).verifyComplete();
    }
}
