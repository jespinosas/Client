package co.com.bancolombia.transformservice.config.toplaintext;

import co.com.bancolombia.model.validation.exceptions.BusinessException;
import co.com.bancolombia.transformservice.config.toplaintext.data_types.ArrayReqJsonValue;
import co.com.bancolombia.transformservice.config.toplaintext.data_types.DateReqJsonValue;
import co.com.bancolombia.transformservice.config.toplaintext.data_types.ValueReqJsonValue;
import co.com.bancolombia.transformservice.config.utils.FillDirection;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReqJsonValueTest {

    ValueReqJsonValue valueReqJsonValue;

    @Test
    void getValueTest(){
        JsonNode jsonNode = JsonNodeFactory.instance.objectNode()
                .put("any_attribute", "valueAttribute");


        valueReqJsonValue =
                new ValueReqJsonValue("any_attribute",20,
                        false, "0", null);

        assertEquals("valueAttribute000000", valueReqJsonValue.getValue(jsonNode));
    }

    @Test
    void getValueFailTest(){
        valueReqJsonValue = new ValueReqJsonValue(
                "name", 10, true, "0", FillDirection.RIGHT
        );
        JsonNode jsonNode = JsonNodeFactory.instance.objectNode();

        String result = valueReqJsonValue.getValue(jsonNode);

        assertEquals("0000000000", result);
    }

    @Test
    void getValueFailInvalidLengthTest(){
        String expectedMessage = "Validation data error : Invalid length";

        BusinessException exception = assertThrows(BusinessException.class, () ->
                new ValueReqJsonValue("/name", -1, false, "*", FillDirection.RIGHT)
        );

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getDateValueTest(){
        JsonNode jsonNode = JsonNodeFactory.instance.objectNode()
                .put("exampleDate", "2024-11-27T10:15:30");

        DateReqJsonValue dateReqJsonValue =
                new DateReqJsonValue("exampleDate",10,false," ",FillDirection.RIGHT,"yyyy/MM/dd");

        assertEquals("2024/11/27", dateReqJsonValue.getValue(jsonNode));
    }

    @Test
    void getDateValueFailInvalidFormatTest(){
        String expectedMessage = "Validation data error : Format is invalid";

        BusinessException exception = assertThrows(BusinessException.class, () ->
                new DateReqJsonValue("date",8,false," ",FillDirection.RIGHT,""));

        assertEquals(expectedMessage, exception.getMessage());
    }
}
