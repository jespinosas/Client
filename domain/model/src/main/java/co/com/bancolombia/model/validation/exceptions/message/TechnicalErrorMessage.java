package co.com.bancolombia.model.validation.exceptions.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TechnicalErrorMessage {

    EQUIVALENCE_SERVICE_ERROR("SP9102", "Equivalence service error", 503, "Service Unavailable"),
    INVALID_EXPRESSION("SP9110", "Invalid expression", 400, "Invalid Input"),
    INVALID_QUERY_EXPRESSION("SP9111", "Invalid query expression", 400, "Invalid Input"),
    INVALID_NUMBER("SP9112", "Invalid number format", 400, "Invalid Input"),
    INVALID_DATE("SP9113", "Invalid date format", 400, "Invalid Input");

    private final String code;
    private final String message;
    private final Integer status;
    private final String title;
}
