package co.com.bancolombia.kstreamprocessor.serializer;

import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class JsonObjectSerde extends Serdes.WrapperSerde<Object> {
    public JsonObjectSerde() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(Object.class));
    }
}
