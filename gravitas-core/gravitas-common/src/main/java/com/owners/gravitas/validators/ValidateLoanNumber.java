package com.owners.gravitas.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * The Interface ValidateLoanNumber.
 * 
 * @author ankusht
 */
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.LOCAL_VARIABLE } )
@Retention( RetentionPolicy.RUNTIME )
@Constraint( validatedBy = LoanNumberValidator.class )
@Documented
public @interface ValidateLoanNumber {

    /**
     * Message.
     *
     * @return the string
     */
    String message() default "error.ocl.loannumber.invalid";

    /**
     * Groups.
     *
     * @return the class[]
     */
    Class< ? >[] groups() default {};

    /**
     * Payload.
     *
     * @return the class<? extends payload>[]
     */
    Class< ? extends Payload >[] payload() default {};

}
