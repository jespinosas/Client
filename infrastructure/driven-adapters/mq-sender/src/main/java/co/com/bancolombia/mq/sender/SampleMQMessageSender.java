package co.com.bancolombia.mq.sender;

import co.com.bancolombia.commons.jms.api.MQMessageSender;
import co.com.bancolombia.commons.jms.mq.EnableMQGateway;
import co.com.bancolombia.model.events.gateway.MQGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import jakarta.jms.Message;

@Component
@AllArgsConstructor
@EnableMQGateway(scanBasePackages = "co.com.bancolombia")
public class SampleMQMessageSender implements MQGateway {
    private final MQMessageSender sender;
//    private final MQQueuesContainer container; // Inject it to reference a queue

    @Override
    public Mono<String> send(String message) {
        return sender.send(context -> {
                    System.out.println("Sending message: " + message);
                    //            textMessage.setJMSReplyTo(container.get("any-custom-value")); // Inject the reply to queue from container
                    return (Message) context.createTextMessage(message);
                })
                .name("mq_send_message")
                .tag("operation", "my-operation") // TODO: Change operation name
                ;
    }
}
