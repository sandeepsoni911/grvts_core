package com.owners.gravitas.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.springframework.stereotype.Component;

/**
 * Component to do validation of objects annotated with javax.validation.
 * 
 * @author bhardrah
 *
 */
@Component
public class GenericValidator<T> {
    public boolean isValid(T type) {
        Set<ConstraintViolation<T>> violations = Validation.buildDefaultValidatorFactory().getValidator()
                .validate(type);
        if (!violations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                msg.append(violation.getPropertyPath().toString() + ":" + violation.getMessage() + ";");
            }
            throw new IllegalArgumentException(msg.toString());
        }
        return true;
    }
}
