package co.com.bancolombia.model.validation.exceptions.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SecurityErrorMessage {

    CONSUMER_NOT_ALLOWED("SEC8000", "Application is not allowed to consume this service", 403, "Not Authorized");

    private final String code;
    private final String message;
    private final Integer status;
    private final String title;
}
