package co.com.bancolombia.kstreamprocessor.gateway;




import co.com.bancolombia.kstreamprocessor.dto.BasicData;
import co.com.bancolombia.kstreamprocessor.dto.ContactData;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

public interface KStreamProcessorGateway {
    void process(KStream<String, BasicData> stream, KStream<String, ContactData> stream1);
}
