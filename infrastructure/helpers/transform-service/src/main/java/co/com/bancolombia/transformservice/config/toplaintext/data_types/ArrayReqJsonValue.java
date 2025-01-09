package co.com.bancolombia.transformservice.config.toplaintext.data_types;

import co.com.bancolombia.transformservice.config.toplaintext.ReqJsonValue;
import co.com.bancolombia.transformservice.config.toplaintext.ReqValue;
import co.com.bancolombia.transformservice.config.utils.FillDirection;
import co.com.bancolombia.transformservice.config.utils.Type;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;

import static co.com.bancolombia.model.validation.ValidationService.validate;
import static co.com.bancolombia.transformservice.config.utils.JsonPathExpressionsUtils.getJsonExpressionValue;
import static co.com.bancolombia.transformservice.config.utils.JsonUtils.fillValue;
import static co.com.bancolombia.transformservice.config.utils.JsonUtils.getArrayValueOfJson;
import static co.com.bancolombia.transformservice.config.utils.Type.ARRAY;

@Getter
public class ArrayReqJsonValue extends ReqJsonValue {

    private final String expression;
    private final ReqValue arrayReqValue;


    @JsonCreator
    public ArrayReqJsonValue(
            @JsonProperty("attribute") String attribute,
            @JsonProperty("length") int length,
            @JsonProperty("optional") boolean optional,
            @JsonProperty("fillValue") String fillValue,
            @JsonProperty("fillDirection") FillDirection fillDirection,
            @JsonProperty("expression") String expression,
            @JsonProperty("arrayValue") ReqValue arrayReqValue
    ) {
        super(attribute, length, optional, fillValue, fillDirection);
        validate(expression, StringUtils::isBlank, "Invalid expression, it cannot be empty");

        this.arrayReqValue = arrayReqValue;
        this.expression = expression;
    }

    @Override
    public Type getType() {
        return ARRAY;
    }

    @Override
    public String getValue(JsonNode jsonNode) {


        var arrayNode = getArrayValueOfJson(attribute, jsonNode, optional);
        var value = buildStringFromArray(arrayNode ,this.expression);

        // Realiza el fillValue a nivel de concatenación final !!
        return getValue(value);

    }

    @Override
    public String getValue(String fieldValue) {
        return fillValue(fieldValue, this);
    }


    private String buildStringFromArray(JsonNode jsonArrayNode, String expression) {

        var consultationResult  = getJsonExpressionValue(jsonArrayNode,expression);
        var isMap = consultationResult.stream().allMatch( rta -> rta instanceof Map);

        return isMap
                ? consultationResult.stream().map(item -> ((Map<?, ?>) item)
                .values().stream()
                .map(value -> this.arrayReqValue != null && this.arrayReqValue instanceof ReqJsonValue ?
                        ((ReqJsonValue) this.arrayReqValue).getValue(value.toString()) : value.toString()) // Usar fillValue solo si subAttribute no es null
                .collect(Collectors.joining())).collect(Collectors.joining())
                :
                consultationResult.stream()
                        .map(value -> this.arrayReqValue != null && this.arrayReqValue instanceof ReqJsonValue
                                ? ((ReqJsonValue) this.arrayReqValue).getValue(value.toString())
                                : value.toString()).collect(Collectors.joining()) ;


    }
}
