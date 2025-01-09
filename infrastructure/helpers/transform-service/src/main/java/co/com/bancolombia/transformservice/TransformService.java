package co.com.bancolombia.transformservice;

import co.com.bancolombia.transformservice.config.toplaintext.ReqInnerValue;
import co.com.bancolombia.transformservice.config.toplaintext.ReqJsonValue;
import co.com.bancolombia.transformservice.config.toplaintext.ReqValue;
import co.com.bancolombia.transformservice.config.toplaintext.data_types.HomologatedJsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransformService {

    //  Valida si una parte del texto es numerica (es decir si tiene numeros enteros)
    public static final String NUMBER_REGEX = "^\\d+$";

    // Valida si existen puntos en el texto
    public static final String DOT_REGEX = "\\.";

    // Generate request

    public static String generateRequest(JsonNode json, List<ReqValue> attributeModels,
                                          Map<String, String> equivalences) {


        return attributeModels.stream().map(attribute -> {
            return attribute instanceof ReqJsonValue
                    ? ((ReqJsonValue) attribute).getValue(json)
                    : attribute instanceof ReqInnerValue
                    ? ((ReqInnerValue) attribute).getValue()
                    : ((HomologatedJsonValue) attribute).getHomologatedValue(json,equivalences);

        }).collect(Collectors.joining());
    }
}
