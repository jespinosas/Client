package co.com.bancolombia.kstreamprocessor;

import co.com.bancolombia.kstreamprocessor.dto.BasicData;
import co.com.bancolombia.kstreamprocessor.dto.ContactData;
import co.com.bancolombia.kstreamprocessor.dto.JoinJson;
import co.com.bancolombia.kstreamprocessor.gateway.KStreamProcessorGateway;

import co.com.bancolombia.model.events.gateway.MQGateway;
import co.com.bancolombia.model.validation.exceptions.BusinessException;
import co.com.bancolombia.transformservice.config.PatternModel;


import co.com.bancolombia.validationandhomologationservica.peq.config.PEQService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

import org.apache.kafka.streams.kstream.KStream;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


import java.util.Map;
import java.util.Optional;

import static co.com.bancolombia.model.validation.exceptions.message.BusinessErrorMessage.VALIDATION_DATA_ERROR;
import static co.com.bancolombia.transformservice.TransformService.generateRequest;

@Component
@AllArgsConstructor
public class KStreamProcessorImpl implements KStreamProcessorGateway {

    private final Map<String, PatternModel> configModels;
    private final MQGateway mqGateway;
    private final PEQService peqService;

    @Override
    public Mono<Void> process(JoinJson joinJson) {

        System.out.println("entering process method");
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        JsonNode jsonNode = om.convertValue(joinJson, JsonNode.class);
        PatternModel configModel = getPatternModel("PlainTextMQ");

        generateApprovedRequest(jsonNode, configModel).flatMap(string -> {
            System.out.println("String: " + string);

            return this.mqGateway.send(string).then();
        }).subscribe();

        return Mono.empty();

        /*KTable<String, Object> basicDataTable = stream.toTable();
        KTable<String, Object> contactDataTable = stream1.toTable();*/

        /*stream.groupByKey().aggregate(
                () -> stream1,
                (key, value, aggregate) -> {
                    System.out.println("Key: " + key + " Value: " + value + " Aggregate: " + aggregate);
                    aggregate.flatMap((k, v) -> {
                        System.out.println("K: " + k + " V: " + v + " Object: " + value);
                        return null;
                    });
                    return aggregate;
                }
        );*/
        /*JoinJson joinJson = new JoinJson();

        stream.mapValues(value -> {
            System.out.println("Value1: " +  value.toString());

            joinJson.setTipoID(value.CD_TIPO_DOCUMENTO().string());
            joinJson.setNumeroID(value.NUMERO_DOCUMENTO().string());
            joinJson.setPrimerNombre(value.PRIMER_NOMBRE().string());
            joinJson.setSegundoNombre(value.SEGUNDO_NOMBRE().string());
            joinJson.setPrimerApellido(value.PRIMER_APELLIDO().string());
            joinJson.setSegundoApellido(value.SEGUNDO_APELLIDO().string());
            joinJson.setGenero(value.CD_GENERO().string());
            joinJson.setFechaNacimiento(value.FECHA_NACIMIENTO().string());
            joinJson.setPaisNacimiento(value.CD_PAIS_NACIMIENTO().string());
            joinJson.setCiudadNacimiento(value.CD_CIUDAD_NACIMIENTO().string());*/

            /*try {
                BasicData basicData = om.readValue(om.writeValueAsString(value), BasicData.class);
                System.out.println("BasicData: " + basicData.getNUMERO_DOCUMENTO());
                joinJson.setBasicData(om.readValue(om.writeValueAsString(value), BasicData.class));
                System.out.println("JoinJson: " + joinJson.getBasicData().getNUMERO_DOCUMENTO());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }*/
         /*   return null;
        });
        stream1.mapValues(value -> {

                System.out.println("ContactData: " + value.BARRIO() + " " + value.NUMERO_DOCUMENTO());
                //System.out.println("JoinJson: " + joinJson.getContactData().getNUMERO_DOCUMENTO());
            PatternModel configModel = getPatternModel("PlainTextMQ");

            if(value != null){
                joinJson.setTipoDocContacto(value.CD_TIPO_DOCUMENTO().string());
                joinJson.setNumeroDocContacto(value.NUMERO_DOCUMENTO().string());
            }
            ObjectMapper om = new ObjectMapper();
            om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            JsonNode jsonNode = om.convertValue(joinJson, JsonNode.class);


            //System.out.println("JoinJson: " + joinJson.getBasicData().getNUMERO_DOCUMENTO());

            return generateApprovedRequest(jsonNode, configModel).flatMap(string -> {
                System.out.println("String: " + string);
                this.mqGateway.send(string).subscribe();
                return null;
            });
        });
*/

        /*stream.join(stream1, (v1, v2) -> {
            System.out.println("v1: " + v1.toString());
            System.out.println("v2: " + v2.toString());

           *//* try {
                BasicData basicData = new ObjectMapper().readValue(v1.toString(), BasicData.class);
                ContactData contactData = new ObjectMapper().readValue(v2.toString(), ContactData.class);

                JoinJson joinJson = JoinJson.builder()
                        .basicData(basicData)
                        .contactData(contactData)
                        .build();

                System.out.println("JoinJson: " + joinJson);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }*//*


            // Send the message to the MQ
            // Apply transformation from JSON to PlainText

            this.mqGateway.send("hello").subscribe();

            return v1;
        });
*/
       /* stream.join(stream1, (v1, v2) -> {
            System.out.println("v1: " + v1);
            System.out.println("v2: " + v2);
            BasicData basicData = (BasicData) v1;
            ContactData contactData = (ContactData) v2;
            JoinJson joinJson = JoinJson.builder()
                    .basicData(basicData)
                    .contactData(contactData)
                    .build();

            // Send the message to the MQ
            // Apply transformation from JSON to PlainText

            this.mqGateway.send(joinJson.toString());

            return joinJson;
        }, JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofSeconds(10)));*/

    }

    private PatternModel getPatternModel(String operation) {
        return Optional.ofNullable(configModels.get(operation))
                .orElseThrow(() -> new BusinessException(VALIDATION_DATA_ERROR, "Config model not found"));
    }

    private Mono<String> generateApprovedRequest (JsonNode request, PatternModel patternModel) {
        System.out.println("Request: " + request);

        return peqService.getRequestEquivalences(request, patternModel)
                .map(equivalence -> {
                    System.out.println("Equivalence: " + equivalence);
                    var iastMessage = generateRequest(request ,patternModel.requestModel(), equivalence);
                    // will change to debug

                    return iastMessage;
                });
    }
}
