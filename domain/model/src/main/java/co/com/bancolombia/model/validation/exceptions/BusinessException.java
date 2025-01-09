package co.com.bancolombia.model.validation.exceptions;

import co.com.bancolombia.model.validation.exceptions.message.BusinessErrorMessage;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private BusinessErrorMessage businessErrorMessage;

    private String code;
    private int status;
    private String title;
    private final String detail;


    public BusinessException(String code, int status, String title, String detail) {
        this.code = code;
        this.status = status;
        this.title = title;
        this.detail = detail;
    }

    public BusinessException(BusinessErrorMessage businessErrorMessage, String detail) {
        super(businessErrorMessage.getMessage()
                .concat(!detail.isBlank() ? " : "+detail : ""));
        this.detail = detail;
        this.businessErrorMessage = businessErrorMessage;
    }
}
