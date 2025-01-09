package co.com.bancolombia.transformservice.config.toplaintext;

import co.com.bancolombia.transformservice.config.utils.FillDirection;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import static co.com.bancolombia.model.validation.ValidationService.validate;

@Getter
public abstract class ReqJsonValue extends ReqValue{

    protected final String attribute;

    public ReqJsonValue(String attribute, int length, boolean optional,
                        String fillValue, FillDirection fillDirection){
        super(length, optional, fillValue, fillDirection);
        validate(attribute, StringUtils::isEmpty, "Attribute is invalid");
        this.attribute = "/" + attribute.replace(".", "/");
    }

    public abstract String getValue(JsonNode jsonNode);

    public abstract String getValue(String fieldValue);
}
