package co.com.bancolombia.transformservice.config.toplaintext.data_types;

import co.com.bancolombia.transformservice.config.toplaintext.ReqInnerValue;
import co.com.bancolombia.transformservice.config.utils.FillDirection;
import co.com.bancolombia.transformservice.config.utils.Type;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;

import static co.com.bancolombia.model.validation.ValidationService.validate;
import static co.com.bancolombia.transformservice.config.utils.ExpressionUtils.getExpressionValue;
import static co.com.bancolombia.transformservice.config.utils.JsonUtils.fillValue;
import static co.com.bancolombia.transformservice.config.utils.Type.GENERATED;

@Getter
public class GeneratedJsonValueReq extends ReqInnerValue {
    private final String expression;

    public GeneratedJsonValueReq(
            @JsonProperty("length") int length,
            @JsonProperty("fillValue") String fillValue,
            @JsonProperty("fillDirection") FillDirection fillDirection,
            @JsonProperty("expression") String expression
    ) {
        super(length, false, fillValue, fillDirection);

        validate(expression, StringUtils::isEmpty, "Invalid expression");
        this.expression = expression;
    }

    @Override
    public Type getType() {
        return GENERATED;
    }
    @Override
    public String getValue() {
        // Es vacio porque acá la expresión es para generar un dato no para validar
        var value = getExpressionValue("EMPTY", expression);
        return fillValue(value, this);
    }
}

