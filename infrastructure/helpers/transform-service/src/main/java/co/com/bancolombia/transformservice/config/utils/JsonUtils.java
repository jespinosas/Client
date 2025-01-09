package co.com.bancolombia.transformservice.config.utils;

import co.com.bancolombia.model.validation.exceptions.BusinessException;
import co.com.bancolombia.transformservice.config.toplaintext.ReqJsonValue;
import co.com.bancolombia.transformservice.config.toplaintext.ReqValue;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import static co.com.bancolombia.model.validation.exceptions.message.BusinessErrorMessage.VALIDATION_DATA_ERROR;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class JsonUtils {

    public static String getValueJson(String attribute, JsonNode json, boolean optional) {

        var node = json.at(attribute);
        if (node.isMissingNode() && !optional) {
            return "";
        }
        if(node.isMissingNode()){
            throw new BusinessException(
                    VALIDATION_DATA_ERROR, String.format("%s: parameter not found", attribute));
        }
        if(!node.isValueNode()){
            throw new BusinessException(
                    VALIDATION_DATA_ERROR, String.format("%s: is not a value node", attribute));
        }

        return node.asText();
    }

    public static JsonNode getArrayValueOfJson(String attribute, JsonNode json, boolean optional) {
        var node = json.at(attribute);

        if(node.isMissingNode()){
            throw new BusinessException(
                    VALIDATION_DATA_ERROR, String.format("%s: parameter not found", attribute));
        }
        if(!node.isArray()){
            throw new BusinessException(
                    VALIDATION_DATA_ERROR, String.format("%s: is not an array", attribute));
        }
        return node;
    }

    public static String fillValue(String value, ReqJsonValue reqJsonValue){
        if(value.length() > reqJsonValue.getLength()){
            throw new BusinessException(
                    VALIDATION_DATA_ERROR, String.format("%s invalid length (%s)", reqJsonValue.getType(),
                    reqJsonValue.getLength()));
        }

        return switch (reqJsonValue.getFillDirection()){
            case RIGHT -> StringUtils.rightPad(value, reqJsonValue.getLength(), reqJsonValue.getFillValue());
            default -> StringUtils.leftPad(value, reqJsonValue.getLength(), reqJsonValue.getFillValue());
        };
    }

    public static String fillValue(String value, ReqValue reqValue){
        if(value.length() > reqValue.getLength()){
            throw new BusinessException(
                    VALIDATION_DATA_ERROR, String.format("%s invalid length (%s)", reqValue.getType(),
                    reqValue.getLength()));
        }

        return switch (reqValue.getFillDirection()){
            case RIGHT -> StringUtils.rightPad(value, reqValue.getLength(), reqValue.getFillValue());
            default -> StringUtils.leftPad(value, reqValue.getLength(), reqValue.getFillValue());
        };
    }
}
