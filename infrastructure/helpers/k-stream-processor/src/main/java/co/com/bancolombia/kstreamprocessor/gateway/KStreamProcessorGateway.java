package co.com.bancolombia.kstreamprocessor.gateway;




import co.com.bancolombia.kstreamprocessor.dto.BasicData;
import co.com.bancolombia.kstreamprocessor.dto.ContactData;
import co.com.bancolombia.kstreamprocessor.dto.JoinJson;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import reactor.core.publisher.Mono;

public interface KStreamProcessorGateway {
    Mono<Void> process(JoinJson joinJson);
}
