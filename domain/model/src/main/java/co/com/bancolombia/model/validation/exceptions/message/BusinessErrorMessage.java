package co.com.bancolombia.model.validation.exceptions.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessErrorMessage {
    VALIDATION_DATA_ERROR("BP9001", "Validation data error", 400, "");

    private final String code;
    private final String message;
    private final Integer status;
    private final String title;
}
