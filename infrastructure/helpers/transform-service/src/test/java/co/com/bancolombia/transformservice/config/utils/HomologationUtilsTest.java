package co.com.bancolombia.transformservice.config.utils;

import co.com.bancolombia.transformservice.config.toplaintext.data_types.HomologatedJsonValue;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static co.com.bancolombia.transformservice.config.utils.FillDirection.RIGHT;
import static co.com.bancolombia.transformservice.config.utils.Type.HOMOLOGATION;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HomologationUtilsTest {

    @Test
    void getHomologationValueTest(){

        var jsonValue = new HomologatedJsonValue("value", 3,
                false, "", RIGHT, "TIPIDENT");

        var json = JsonNodeFactory.instance.objectNode().put("value", "1");

        var value = jsonValue.getHomologatedValue(json, Map.of("TIPIDENT-1", "one"));
        assertEquals("one", value);
        assertEquals(HOMOLOGATION, jsonValue.getType());
    }
}
