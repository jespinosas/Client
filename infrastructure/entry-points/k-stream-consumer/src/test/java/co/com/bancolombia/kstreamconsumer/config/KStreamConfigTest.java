package co.com.bancolombia.kstreamconsumer.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class KStreamConfigTest {

    KStreamConfig kStreamConfig;

    @Test
    void kStreamsConfigTest(){
        kStreamConfig = new KStreamConfig("localhost:9092", "SASL_PLAINTEXT");
        assertNotNull(kStreamConfig.kStreamsConfig());
    }

    @Test
    void kafkaListenerContainerFactoryTest(){
        kStreamConfig = new KStreamConfig("localhost:9092", "SASL_PLAINTEXT");
        assertNotNull(kStreamConfig.kafkaListenerContainerFactory());
    }
}
