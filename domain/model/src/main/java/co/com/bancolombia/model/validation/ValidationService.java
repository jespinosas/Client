package co.com.bancolombia.model.validation;

import co.com.bancolombia.model.validation.exceptions.BusinessException;

import java.util.function.Predicate;
import java.util.function.Supplier;

import static co.com.bancolombia.model.validation.exceptions.message.BusinessErrorMessage.VALIDATION_DATA_ERROR;

public class ValidationService {

    private ValidationService(){}

    public static <T> void validate(T object, Predicate<T> predicate, String error){
        if(predicate.test(object)){
            throw new BusinessException(VALIDATION_DATA_ERROR, error);
        }
    }

    public static <T> void validate(T object, Predicate<T> predicate, Supplier<String> error){
        if(predicate.test(object)){
            throw new BusinessException(VALIDATION_DATA_ERROR, error.get());
        }
    }
}
