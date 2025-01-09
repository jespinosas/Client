package co.com.bancolombia.kstreamconsumer;

import co.com.bancolombia.kstreamprocessor.dto.BasicData;
import co.com.bancolombia.kstreamprocessor.dto.ContactData;
import co.com.bancolombia.kstreamprocessor.gateway.KStreamProcessorGateway;
import co.com.bancolombia.kstreamprocessor.serializer.BasicDataSerde;
import co.com.bancolombia.kstreamprocessor.serializer.ContactDataSerde;
import co.com.bancolombia.kstreamprocessor.serializer.JsonObjectSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class KStreamConsumer {

    private String inputTopic;
    private String inputTopic1;

    private final KStreamProcessorGateway kProcessorGateway;

    public KStreamConsumer(@Value(value = "${spring.kafka.topic.test-in}") String inputTopic,
                           @Value(value = "${spring.kafka.topic.test-in-1}") String inputTopic1,
                           KStreamProcessorGateway kProcessorGateway) {
        this.inputTopic = inputTopic;
        this.inputTopic1 = inputTopic1;
        this.kProcessorGateway = kProcessorGateway;
    }

    @Bean
    public Mono<Void> consume(StreamsBuilder streamsBuilder) {
        KStream<String, BasicData> stream =
                streamsBuilder.stream(inputTopic, Consumed.with(Serdes.String(), new BasicDataSerde()))
                        ;

        KStream<String, ContactData> stream1 =
                streamsBuilder.stream(inputTopic1, Consumed.with(Serdes.String(), new ContactDataSerde()))
                        ;

        System.out.println("Stream 1: " + stream + " Stream 2: " + stream1);

        this.kProcessorGateway.process(stream, stream1);

        return Mono.empty();
    }

}
