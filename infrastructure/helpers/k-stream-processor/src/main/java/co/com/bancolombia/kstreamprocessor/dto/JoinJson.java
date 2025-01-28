package co.com.bancolombia.kstreamprocessor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinJson {

    private String type;
    private String numeroID;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String genero;
    private String fechaNacimiento;
    private String paisNacimiento;
    private String ciudadNacimiento;
    private String tipoDocContacto;
    private String numeroDocContacto;

}
