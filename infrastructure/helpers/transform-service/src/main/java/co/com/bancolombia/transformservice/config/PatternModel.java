package co.com.bancolombia.transformservice.config;

import co.com.bancolombia.transformservice.config.toplaintext.ReqValue;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static co.com.bancolombia.model.validation.ValidationService.validate;

@Builder(toBuilder = true)
public record PatternModel(String name, String request, List<ReqValue> requestModel) {


    public static PatternModel createValid(String name, String request, List<ReqValue> requestModel) {
        validate(name, StringUtils::isEmpty, "Name is invalid");
        validate(requestModel, rm -> rm == null || rm.isEmpty(), "Request model is invalid");

        return new PatternModel(name, request, requestModel);
    }
}
