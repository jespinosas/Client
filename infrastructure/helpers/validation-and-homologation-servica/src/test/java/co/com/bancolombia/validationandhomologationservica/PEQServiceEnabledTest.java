package co.com.bancolombia.validationandhomologationservica;

import co.com.bancolombia.model.validation.exceptions.TechnicalException;
import co.com.bancolombia.peq.EquivalenceParameterizer;
import co.com.bancolombia.peq.dtos.CriterioParametrizacion;
import co.com.bancolombia.peq.dtos.request.PeqRequestData;
import co.com.bancolombia.peq.dtos.response.DatosRespuesta;
import co.com.bancolombia.peq.dtos.response.ResultadoParametrizacion;
import co.com.bancolombia.peq.dtos.response.ValorParametrizado;
import co.com.bancolombia.transformservice.config.PatternModel;
import co.com.bancolombia.transformservice.config.toplaintext.data_types.HomologatedJsonValue;
import co.com.bancolombia.transformservice.config.toplaintext.data_types.ValueReqJsonValue;
import co.com.bancolombia.validationandhomologationservica.peq.PEQServiceEnabled;
import co.com.bancolombia.validationandhomologationservica.peq.config.PEQConfigProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

import static co.com.bancolombia.model.validation.exceptions.message.TechnicalErrorMessage.EQUIVALENCE_SERVICE_ERROR;
import static co.com.bancolombia.transformservice.config.utils.Type.HOMOLOGATION;
import static co.com.bancolombia.transformservice.config.utils.Type.VALUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PEQServiceEnabledTest {
    @Mock
    EquivalenceParameterizer equivalenceParameterizer;

    PEQServiceEnabled peqServiceEnabled;

    PEQConfigProperties properties;

    JsonNode jsonRequest;
    @Mock
    PatternModel patternModel;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);


        properties = new PEQConfigProperties(
                "MDM-SINCRO_CLIENTES_KAFKA",
                "CIF-SINCRO_CLIENTES_KAFKA",
                "1000",
                "1000");


        var result = new ResultadoParametrizacion(
                new CriterioParametrizacion(
                        "TIPIDENT",
                        "TIPDOC_FS001"
                ),
                new ValorParametrizado(
                        "1",
                        "CEDULA DE CIUDADANIA",
                        new DatosRespuesta(
                                "00",
                                "Exitoso"
                        )
                )
        );

        when(equivalenceParameterizer.getParameterization(any(PeqRequestData.class)))
                .thenReturn(Mono.just(List.of(result)));



    }

    @Test
    void getRequestEquivalencesTest() {

        jsonRequest = JsonNodeFactory.instance.objectNode()
                .set("data", JsonNodeFactory.instance.objectNode()
                        .put("somePath", "TIPDOC_FS001"));


        var requestModel = mock(HomologatedJsonValue.class);
        when(requestModel.getType()).thenReturn(HOMOLOGATION);
        when(requestModel.getAttribute()).thenReturn("/data/somePath");
        when(requestModel.getTypology()).thenReturn("TIPIDENT");

        when(patternModel.requestModel()).thenReturn(List.of(requestModel));

        peqServiceEnabled = new PEQServiceEnabled(equivalenceParameterizer,properties);

        peqServiceEnabled.getRequestEquivalences(jsonRequest,patternModel)
                .as(StepVerifier::create)
                .assertNext(rta -> {
                    assertNotNull(rta);
                    assertTrue(rta.containsKey("TIPIDENT-TIPDOC_FS001"));
                    assertEquals("1", rta.get("TIPIDENT-TIPDOC_FS001"));
                    assertEquals(1, rta.size());
                }).verifyComplete();
    }

    @Test
    void getEmptyRequestEquivalencesTest() {

        var requestModel = mock(ValueReqJsonValue.class);
        when(requestModel.getType()).thenReturn(VALUE);

        peqServiceEnabled = new PEQServiceEnabled(equivalenceParameterizer,properties);

        peqServiceEnabled.getRequestEquivalences(jsonRequest,patternModel)
                .as(StepVerifier::create)
                .assertNext(rta -> {
                    assertNotNull(rta);
                    assertEquals(Map.of(), rta);
                }).verifyComplete();
    }

    @Test
    void getRequestEquivalencesErrorTest() {

        jsonRequest = JsonNodeFactory.instance.objectNode()
                .set("data", JsonNodeFactory.instance.objectNode()
                        .put("somePath", "CC")
                        .put("otherPath", "TI"));


        var requestModel = mock(HomologatedJsonValue.class);
        when(requestModel.getType()).thenReturn(HOMOLOGATION);
        when(requestModel.getAttribute()).thenReturn("/data/somePath");
        when(requestModel.getTypology()).thenReturn("TIPIDENT");

        when(patternModel.requestModel()).thenReturn(List.of(requestModel));




        when( equivalenceParameterizer.getParameterization(any(PeqRequestData.class)))
                .thenReturn(Mono.error(new RuntimeException("Simulated error")));

        peqServiceEnabled = new PEQServiceEnabled(equivalenceParameterizer,properties);

        peqServiceEnabled.getRequestEquivalences(jsonRequest, patternModel)
                .as(StepVerifier::create)
                .expectErrorSatisfies(throwable -> {
                    TechnicalException exception = (TechnicalException) throwable;
                    assertEquals(EQUIVALENCE_SERVICE_ERROR.getMessage(), exception.getTechnicalErrorMessage().getMessage());

                }).verify();
    }
}
