package co.com.bancolombia.transformservice.config.utils;

import co.com.bancolombia.model.validation.exceptions.BusinessException;;
import co.com.bancolombia.transformservice.config.toplaintext.data_types.ValueReqJsonValue;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.junit.jupiter.api.Test;

import static co.com.bancolombia.transformservice.config.utils.JsonUtils.fillValue;
import static co.com.bancolombia.transformservice.config.utils.JsonUtils.getValueJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonUtilsTest {

    @Test
    void getValueJsonOptionalTest(){
        var json = JsonNodeFactory.instance.objectNode();
        var value = getValueJson("/attribute", json, true);
        assertEquals("", value);
    }

    @Test
    void getValueJsonParameterNotFoundTest(){
        String expectedMessage = "Validation data error : /attribute: parameter not found";
        var json = JsonNodeFactory.instance.objectNode();
        BusinessException exception = assertThrows(
                BusinessException.class, () -> getValueJson("/attribute", json, false)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getValueJsonIsNotValueNodeTest(){
        String expectedMessage = "Validation data error : /attribute: is not a value node";
        var json = JsonNodeFactory.instance.objectNode().set("attribute", JsonNodeFactory.instance.objectNode());
        BusinessException exception = assertThrows(
                BusinessException.class, () -> getValueJson("/attribute", json, false)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void fillValueTest(){
        var jsonValue = new ValueReqJsonValue("type", 3,false, "0",
                FillDirection.LEFT);

        var value = fillValue("1", jsonValue);

        assertEquals("001", value);
    }

    @Test
    void fillValueInvalidLengthTest(){
        String expectedMessage = "Validation data error : VALUE invalid length (3)";
        var jsonValue = new ValueReqJsonValue("type", 3,false, "0",
                FillDirection.LEFT);

        BusinessException exception = assertThrows(
                BusinessException.class, () -> fillValue("1234", jsonValue)
        );
        assertEquals(expectedMessage, exception.getMessage());
    }
}
