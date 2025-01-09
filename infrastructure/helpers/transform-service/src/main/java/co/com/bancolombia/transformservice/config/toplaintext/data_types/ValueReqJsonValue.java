package co.com.bancolombia.transformservice.config.toplaintext.data_types;

import co.com.bancolombia.transformservice.config.toplaintext.ReqJsonValue;
import co.com.bancolombia.transformservice.config.utils.FillDirection;
import co.com.bancolombia.transformservice.config.utils.Type;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import static co.com.bancolombia.model.validation.ValidationService.validate;
import static co.com.bancolombia.transformservice.config.utils.JsonUtils.fillValue;
import static co.com.bancolombia.transformservice.config.utils.Type.VALUE;
import static co.com.bancolombia.transformservice.config.utils.JsonUtils.getValueJson;


public class ValueReqJsonValue extends ReqJsonValue {

    @JsonCreator
    public ValueReqJsonValue(
            @JsonProperty("attribute") String attribute,
            @JsonProperty("length") int length,
            @JsonProperty("optional") boolean optional,
            @JsonProperty("fillValue") String fillValue,
            @JsonProperty("fillDirection") FillDirection fillDirection
    ){
        super(attribute, length, optional, fillValue, fillDirection);
        validate(length, l -> l < 0, "Length is invalid");
    }
    @Override
    public String getValue(JsonNode jsonNode) {
        var value = getValueJson(attribute, jsonNode, optional);
        return fillValue(value, this);
    }

    @Override
    public String getValue(String fieldValue) {
        return fillValue(fieldValue, this);
    }

    @Override
    public Type getType() {
        return VALUE;
    }
}
