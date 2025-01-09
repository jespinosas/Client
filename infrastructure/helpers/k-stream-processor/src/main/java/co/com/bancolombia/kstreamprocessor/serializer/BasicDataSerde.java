package co.com.bancolombia.kstreamprocessor.serializer;

import co.com.bancolombia.kstreamprocessor.dto.BasicData;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class BasicDataSerde extends Serdes.WrapperSerde<BasicData> {
    public BasicDataSerde() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(BasicData.class));
    }
}
