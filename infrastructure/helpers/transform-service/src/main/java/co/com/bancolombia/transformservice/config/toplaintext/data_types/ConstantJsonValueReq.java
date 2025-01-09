package co.com.bancolombia.transformservice.config.toplaintext.data_types;

import co.com.bancolombia.transformservice.config.toplaintext.ReqInnerValue;
import co.com.bancolombia.transformservice.config.utils.FillDirection;
import co.com.bancolombia.transformservice.config.utils.Type;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Objects;

import static co.com.bancolombia.model.validation.ValidationService.validate;
import static co.com.bancolombia.transformservice.config.utils.JsonUtils.fillValue;
import static co.com.bancolombia.transformservice.config.utils.Type.CONSTANT;

@Getter
public class ConstantJsonValueReq extends ReqInnerValue {

    private final String constant;

    @JsonCreator
    public ConstantJsonValueReq(
            @JsonProperty("length") int length,
            @JsonProperty("fillValue") String fillValue,
            @JsonProperty("fillDirection") FillDirection fillDirection,
            @JsonProperty("constant") String constant) {

        super(length, false, fillValue, fillDirection);
        validate(constant, Objects::isNull, "Invalid constant");
        this.constant = constant;
    }




    @Override
    public Type getType() {
        return CONSTANT;
    }

    @Override
    public String getValue() {
        return fillValue(constant, this);
    }
}
