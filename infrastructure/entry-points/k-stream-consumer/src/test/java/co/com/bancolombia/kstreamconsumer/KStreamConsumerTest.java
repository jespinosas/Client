package co.com.bancolombia.kstreamconsumer;

import co.com.bancolombia.kstreamprocessor.dto.BasicData;
import co.com.bancolombia.kstreamprocessor.dto.ContactData;
import co.com.bancolombia.kstreamprocessor.gateway.KStreamProcessorGateway;
import co.com.bancolombia.kstreamprocessor.serializer.BasicDataSerde;
import co.com.bancolombia.kstreamprocessor.serializer.ContactDataSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.kstream.Consumed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.Properties;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class KStreamConsumerTest {

    KStreamConsumer kStreamConsumer;

    StreamsBuilder streamsBuilder;
    Topology topology;
    @Mock
    KStreamProcessorGateway kProcessorGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        streamsBuilder = new StreamsBuilder();
        streamsBuilder.stream("topic_0", Consumed.with(Serdes.String(), new BasicDataSerde()));
        streamsBuilder.stream("topic_1", Consumed.with(Serdes.String(), new ContactDataSerde()));
        topology = streamsBuilder.build();
        kStreamConsumer = new KStreamConsumer("topic_0", "topic_1", kProcessorGateway);
    }

    @Test
    void consumeBasicDataTest() {

        when(kProcessorGateway.process(any())).thenReturn(Mono.empty());
        TopologyTestDriver topologyTestDriver = new TopologyTestDriver(topology, new Properties(), Instant.now());
        TestInputTopic<String, BasicData> inputTopic0 = topologyTestDriver.createInputTopic(
                "topic_0", Serdes.String().serializer(), new BasicDataSerde().serializer());

        inputTopic0.pipeInput("key", new BasicData(
                new BasicData.TextType("1"),
                new BasicData.TextType("2"),
                new BasicData.TextType("3"),
                new BasicData.TextType("4"),
                new BasicData.TextType("5"),
                new BasicData.TextType("6"),
                new BasicData.TextType("7"),
                new BasicData.TextType("8"),
                false,
                "9",
                false,
                "10",
                false,
                new BasicData.TextType("11"),
                false,
                new BasicData.TextType("12"),
                false,
                new BasicData.TextType("13"),
                false,
                "14",
                false,
                new BasicData.TextType("15"),
                false,
                new BasicData.TextType("16"),
                false,
                "17",
                false,
                "String CD_ENTIDAD_ESTATAL",
                false,
                "String CD_ENTIDAD_ESTATAL_DESCENT",
                false,
                new BasicData.TextType("18"),
                false,
                "String CD_ESTADO_CLIENTE",
                false,
                "String CD_ESTADO_DOCUMENTO",
                false,
                new BasicData.TextType("19"),
                false,
                "String CD_ESTRATO",
                false,
                new BasicData.TextType("20"),
                false,
                "String CD_INCAPAZ",
                false,
                new BasicData.TextType("21"),
                false,
                "String CD_NATURALEZA_ENTIDAD",
                false,
                "String CD_NIVEL_ACADEMICO",
                false,
                "String CD_OCUPACION",
                false,
                new BasicData.TextType("22"),
                false,
                new BasicData.TextType("23"),
                false,
                new BasicData.TextType("24"),
                false,
                "String CD_RELACIONAMIENTO",
                false,
                "String CD_REPORTE_COSTOS_TOTALES",
                false,
                new BasicData.TextType("25"),
                false,
                new BasicData.TextType("26"),
                false,
                "String CD_SEGMENTACION_SARLAFT",
                false,
                new BasicData.TextType("27"),
                false,
                "String CD_SOCIEDAD_COMERC_O_CIVIL",
                false,
                new BasicData.TextType("28"),
                false,
                new BasicData.TextType("29"),
                false,
                new BasicData.TextType("30"),
                false,
                "String CD_TIPO_CONTRATO",
                false,
                new BasicData.TextType("31"),
                false,
                "String CD_TIPO_ENTIDAD",
                false,
                new BasicData.TextType("32"),
                false,
                new BasicData.TextType("33"),
                false,
                "String CD_TIPO_VIVIENDA",
                false,
                new BasicData.TextType("34"),
                false,
                "String CODIGO_SISBEN",
                false,
                new BasicData.NumberType(35),
                false,
                "String EMPRESA_DONDE_LABORA",
                false,
                new BasicData.TextType("36"),
                false,
                new BasicData.TextType("37"),
                false,
                "String FECHA_CLIENTE_HASTA",
                false,
                new BasicData.TextType("38"),
                false,
                "String FECHA_INGRESO",
                false,
                new BasicData.TextType("39"),
                false,
                new BasicData.TextType("40"),
                false,
                "String FECHA_VINCULACION",
                false,
                new BasicData.TextType("41"),
                false,
                new BasicData.TextType("42"),
                false,
                "String FL_EMPRESA_LIQUIDADA",
                false,
                "String FL_FALLECIDO",
                false,
                "String FL_LEY_INTERVENCION",
                false,
                new BasicData.TextType("43"),
                false,
                new BasicData.TextType("44"),
                false,
                new BasicData.TextType("45"),
                false,
                new BasicData.TextType("46"),
                false,
                new BasicData.TextType("47"),
                false,
                "String FL_PERSONA_BLOQUEADA",
                false,
                "String FL_PERSONA_NO_OBJETIVO_COM",
                false,
                new BasicData.TextType("48"),
                false,
                "String FL_SECUESTRADO",
                false,
                "String FL_TESORERIA",
                false,
                new BasicData.NumberType(49),
                false,
                new BasicData.TextType("50"),
                false,
                new BasicData.TextType("51"),
                false,
                new BasicData.NumberType(52),
                false,
                new BasicData.TextType("53"),
                false,
                new BasicData.TextType("54"),
                false,
                new BasicData.TextType("55"),
                false,
                new BasicData.TextType("56"),
                false,
                new BasicData.TextType("57"),
                false,
                "String REGISTRO_DESPLAZADO",
                false,
                new BasicData.TextType("58"),
                false,
                new BasicData.TextType("59"),
                false,
                new BasicData.TextType("60"),
                false,
                new BasicData.TextType("61"),
                false));

       StepVerifier.create(kStreamConsumer.consume(streamsBuilder))
                .expectComplete();
    }

    @Test
    void consumeContactDataTest(){
        when(kProcessorGateway.process(any())).thenReturn(Mono.empty());
        TopologyTestDriver topologyTestDriver = new TopologyTestDriver(topology, new Properties(), Instant.now());
        TestInputTopic<String, ContactData> inputTopic1 = topologyTestDriver.createInputTopic(
                "topic_1", Serdes.String().serializer(), new ContactDataSerde().serializer());

        inputTopic1.pipeInput("key", new ContactData(
                new ContactData.TextType("1"),
                new ContactData.TextType("2"),
                new ContactData.TextType("3"),
                new ContactData.TextType("4"),
                new ContactData.TextType("5"),
                new ContactData.TextType("6"),
                new ContactData.TextType("7"),
                new ContactData.TextType("8"),
                false,
                new ContactData.TextType("9"),
                new ContactData.TextType("10")));

        StepVerifier.create(kStreamConsumer.consume(streamsBuilder))
                .expectComplete();
    }
}
