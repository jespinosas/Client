package co.com.bancolombia.kstreamconsumer;

import co.com.bancolombia.kstreamprocessor.dto.BasicData;
import co.com.bancolombia.kstreamprocessor.dto.ContactData;
import co.com.bancolombia.kstreamprocessor.dto.JoinJson;
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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

        JoinJson joinJson = new JoinJson();
        String contactDataKey = "";

         KStream<String, ContactData> streamContactData =
                 streamsBuilder.stream(inputTopic1, Consumed.with(Serdes.String(), new ContactDataSerde()))
                .mapValues(value1 -> {
                    System.out.println("Value1: " + value1);
                    joinJson.setTipoDocContacto(value1.CD_TIPO_DOCUMENTO().string());
                    joinJson.setNumeroDocContacto(value1.NUMERO_DOCUMENTO().string());
                    contactDataKey = value1.LLAVE_MDM();

                    return null;
                })
        ;

                streamsBuilder.stream(inputTopic, Consumed.with(Serdes.String(), new BasicDataSerde()))
                        .mapValues(value -> {
                            System.out.println("Value: " + value);

                            joinJson.setType(value.CD_TIPO_DOCUMENTO().string());
                            joinJson.setNumeroID(value.NUMERO_DOCUMENTO().string());
                            joinJson.setPrimerNombre(value.PRIMER_NOMBRE().string());
                            joinJson.setSegundoNombre(value.SEGUNDO_NOMBRE().string());
                            joinJson.setPrimerApellido(value.PRIMER_APELLIDO().string());
                            joinJson.setSegundoApellido(value.SEGUNDO_APELLIDO().string());
                            joinJson.setGenero(value.CD_GENERO().string());
                            joinJson.setFechaNacimiento(value.FECHA_NACIMIENTO().string());
                            joinJson.setPaisNacimiento(value.CD_PAIS_NACIMIENTO().string());
                            joinJson.setCiudadNacimiento(value.CD_CIUDAD_NACIMIENTO().string());

                            if(!contactDataKey.equals(value.LLAVE_MDM().string())) {
                                joinJson.setTipoDocContacto("");
                            }

                            this.kProcessorGateway.process(joinJson);
                            return joinJson;
                        })
                        ;


        //System.out.println("Stream 1: " + stream + " Stream 2: " + stream1);

        //this.kProcessorGateway.process(stream, stream1);

        return Mono.empty();
    }

}
