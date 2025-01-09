package co.com.bancolombia.transformservice.config.toplaintext.data_types;

import co.com.bancolombia.transformservice.config.toplaintext.ReqJsonValue;
import co.com.bancolombia.transformservice.config.utils.FillDirection;
import co.com.bancolombia.transformservice.config.utils.Type;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

import static co.com.bancolombia.model.validation.ValidationService.validate;
import static co.com.bancolombia.transformservice.config.utils.ExpressionUtils.getExpressionValue;
import static co.com.bancolombia.transformservice.config.utils.JsonUtils.fillValue;
import static co.com.bancolombia.transformservice.config.utils.JsonUtils.getValueJson;
import static co.com.bancolombia.transformservice.config.utils.Type.EXPRESSION;

@Getter
public class ExpressionReqJsonValue extends ReqJsonValue {

    private final String expression;

    public ExpressionReqJsonValue(
            @JsonProperty("attribute") String attribute,
            @JsonProperty("length") int length,
            @JsonProperty("optional") boolean optional,
            @JsonProperty("fillValue") String fillValue,
            @JsonProperty("fillDirection") FillDirection fillDirection,
            @JsonProperty("expression") String expression
    ) {
        super(attribute, length, optional, fillValue, fillDirection);
        validate(length, l -> l < 0, "Invalid length");
        this.expression = expression;
    }

    @Override
    public Type getType() {
        return EXPRESSION;
    }

    @Override
    public String getValue(JsonNode jsonNode) {
        var valueOfJson = getValueJson(attribute, jsonNode, optional);
        var value = optional && valueOfJson.isEmpty() ? valueOfJson : getExpressionValue(valueOfJson, expression);
        return fillValue(value, this);
    }

    @Override
    public String getValue(String fieldValue) {
        var value = optional && fieldValue.isEmpty() ? fieldValue : getExpressionValue(fieldValue, expression);
        return fillValue(value, this);
    }
}
