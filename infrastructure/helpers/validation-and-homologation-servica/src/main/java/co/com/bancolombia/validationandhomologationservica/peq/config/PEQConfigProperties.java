package co.com.bancolombia.validationandhomologationservica.peq.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PEQConfigProperties {
    private String originApplication;
    private String destinationApplication;
    private String originSociety;
    private String targetSociety;
}
