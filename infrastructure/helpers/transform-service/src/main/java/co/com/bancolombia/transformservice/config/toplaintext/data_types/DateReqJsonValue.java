package co.com.bancolombia.transformservice.config.toplaintext.data_types;

import co.com.bancolombia.transformservice.config.toplaintext.ReqJsonValue;
import co.com.bancolombia.transformservice.config.utils.FillDirection;
import co.com.bancolombia.transformservice.config.utils.Type;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;

import static co.com.bancolombia.model.validation.ValidationService.validate;
import static co.com.bancolombia.transformservice.config.utils.FormatUtils.getDateWithFormat;
import static co.com.bancolombia.transformservice.config.utils.JsonUtils.fillValue;
import static co.com.bancolombia.transformservice.config.utils.JsonUtils.getValueJson;
import static co.com.bancolombia.transformservice.config.utils.Type.DATE;

public class DateReqJsonValue extends ReqJsonValue {

    private final String format;

    public DateReqJsonValue(
            @JsonProperty("attribute") String attribute,
            @JsonProperty("length") int length,
            @JsonProperty("optional") boolean optional,
            @JsonProperty("fillValue") String fillValue,
            @JsonProperty("fillDirection") FillDirection fillDirection,
            @JsonProperty("format") String format) {
        super(attribute, length, optional, fillValue, fillDirection);
        validate(format, StringUtils::isEmpty, "Format is invalid");
        this.format = format;
    }
    @Override
    public String getValue(JsonNode jsonNode) {
        var valueOfJson = getValueJson(attribute, jsonNode, optional);
        var value = optional && valueOfJson.isEmpty() ? valueOfJson : getDateWithFormat(valueOfJson, format);
        return fillValue(value, this);
    }

    @Override
    public String getValue(String fieldValue) {
        var value = optional && fieldValue.isEmpty() ? fieldValue : getDateWithFormat(fieldValue, format);
        return fillValue(value, this);
    }

    @Override
    public Type getType() {
        return DATE;
    }
}
