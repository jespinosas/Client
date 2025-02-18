package co.com.bancolombia.transformservice;

import co.com.bancolombia.transformservice.config.toplaintext.ReqInnerValue;
import co.com.bancolombia.transformservice.config.toplaintext.ReqJsonValue;
import co.com.bancolombia.transformservice.config.toplaintext.ReqValue;
import co.com.bancolombia.transformservice.config.toplaintext.data_types.HomologatedJsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class TransformServiceTest {

    @Mock
    private ReqJsonValue reqJsonValue;

    @Mock
    private ReqInnerValue reqInnerValue;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateRequestWithoutDataHomologated() {

        when(reqJsonValue.getValue(any(JsonNode.class))).thenReturn("reqJsonValue-");
        when(reqInnerValue.getValue()).thenReturn("reqInnerValue");

        List<ReqValue> attributeModels = List.of(reqJsonValue, reqInnerValue);

        assertEquals(TransformService.generateRequest(JsonNodeFactory.instance.objectNode(),
                attributeModels, Map.of()), "reqJsonValue-reqInnerValue");
    }

    @Test
    void generateRequestWithDataHomologated() {

        HomologatedJsonValue homologatedJsonValueMock = mock(HomologatedJsonValue.class);

        when(reqJsonValue.getValue(any(JsonNode.class))).thenReturn("reqJsonValue-");
        when(reqInnerValue.getValue()).thenReturn("reqInnerValue-");
        when(homologatedJsonValueMock.getHomologatedValue(any(JsonNode.class),any())).thenReturn("homologated");

        List<ReqValue> attributeModels = List.of(reqJsonValue, reqInnerValue, homologatedJsonValueMock);

        assertEquals(TransformService.generateRequest(JsonNodeFactory.instance.objectNode(),
                attributeModels, Map.of()), "reqJsonValue-reqInnerValue-homologated");
    }

}
