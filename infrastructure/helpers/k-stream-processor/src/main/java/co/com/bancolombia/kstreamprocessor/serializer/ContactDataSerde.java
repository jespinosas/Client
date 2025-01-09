package co.com.bancolombia.kstreamprocessor.serializer;

import co.com.bancolombia.kstreamprocessor.dto.ContactData;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class ContactDataSerde extends Serdes.WrapperSerde<ContactData> {
    public ContactDataSerde() {
        super(new JsonSerializer<>(), new JsonDeserializer<>(ContactData.class));
    }
}
