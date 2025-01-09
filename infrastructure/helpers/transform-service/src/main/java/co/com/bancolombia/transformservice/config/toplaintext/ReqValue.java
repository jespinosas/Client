package co.com.bancolombia.transformservice.config.toplaintext;

import co.com.bancolombia.transformservice.config.toplaintext.data_types.*;
import co.com.bancolombia.transformservice.config.utils.FillDirection;
import co.com.bancolombia.transformservice.config.utils.Type;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

import static co.com.bancolombia.model.validation.ValidationService.validate;
import static co.com.bancolombia.transformservice.config.utils.FillDirection.LEFT;
import static co.com.bancolombia.transformservice.config.utils.FillDirection.RIGHT;

@Getter
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({

        @JsonSubTypes.Type(value = ValueReqJsonValue.class, name = "VALUE"),
        @JsonSubTypes.Type(value = NumericReqJsonValue.class, name = "NUMBER"),
        @JsonSubTypes.Type(value = DateReqJsonValue.class, name = "DATE"),
        @JsonSubTypes.Type(value = ConstantJsonValueReq.class, name = "CONSTANT"),
        @JsonSubTypes.Type(value = GeneratedJsonValueReq.class, name = "GENERATED"),
        @JsonSubTypes.Type(value = ExpressionReqJsonValue.class, name = "EXPRESSION"),
        @JsonSubTypes.Type(value = HomologatedJsonValue.class, name = "HOMOLOGATION"),
        @JsonSubTypes.Type(value = ArrayReqJsonValue.class, name = "ARRAY")

})
public abstract class ReqValue {
    protected final int length;
    protected final boolean optional;
    protected final String fillValue;
    protected final FillDirection fillDirection;


    public ReqValue(int length, boolean optional, String fillValue, FillDirection fillDirection) {
        validate(length, l -> l < 0, "Invalid length");
        this.length = length;
        this.optional = optional;

        this.fillValue = (fillValue == null && this instanceof NumericReqJsonValue) ? "0" : fillValue == null ? "" : fillValue;
        this.fillDirection = (fillDirection == null && this instanceof NumericReqJsonValue) ? LEFT : fillDirection == null ? RIGHT : fillDirection;

    }



    // Tipo de dto
    public abstract Type getType();


}