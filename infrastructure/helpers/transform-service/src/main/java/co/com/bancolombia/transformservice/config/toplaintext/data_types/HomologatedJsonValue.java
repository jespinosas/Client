package co.com.bancolombia.transformservice.config.toplaintext.data_types;

import co.com.bancolombia.transformservice.config.toplaintext.ReqValue;
import co.com.bancolombia.transformservice.config.utils.FillDirection;
import co.com.bancolombia.transformservice.config.utils.Type;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;

import java.util.Map;

import static co.com.bancolombia.model.validation.ValidationService.validate;
import static co.com.bancolombia.transformservice.config.utils.HomologationUtils.getHomologationValue;
import static co.com.bancolombia.transformservice.config.utils.JsonUtils.fillValue;
import static co.com.bancolombia.transformservice.config.utils.JsonUtils.getValueJson;
import static co.com.bancolombia.transformservice.config.utils.Type.HOMOLOGATION;

@Getter
public class HomologatedJsonValue extends ReqValue {

    protected final String attribute;
    protected final String typology;

    public HomologatedJsonValue(
            @JsonProperty("attribute") String attribute,
            @JsonProperty("length") int length,
            @JsonProperty("optional") boolean optional,
            @JsonProperty("fillValue") String fillValue,
            @JsonProperty("fillDirection") FillDirection fillDirection,
            @JsonProperty("typology") String typology
    ) {
        super(length, optional, fillValue, fillDirection);
        validate(attribute, StringUtils::isEmpty, "Invalid attribute");
        validate(typology, StringUtils::isEmpty, "Invalid typology");
        this.attribute = "/" + attribute.replace(".", "/");
        this.typology = typology;


    }

    @Override
    public Type getType() {
        return HOMOLOGATION;
    }


    public String getHomologatedValue(JsonNode jsonNode, Map<String, String> equivalences) {
        var valueOfJson = getValueJson(attribute, jsonNode, optional);
        var value = optional && valueOfJson.isEmpty() ? valueOfJson :
                getHomologationValue(valueOfJson, typology, equivalences);
        return fillValue(value, this);
    }
}

