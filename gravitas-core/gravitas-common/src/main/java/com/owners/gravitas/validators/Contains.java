package com.owners.gravitas.validators;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint( validatedBy = ContainsValidator.class )
@Target( { METHOD, FIELD, PARAMETER } )
@Retention( RUNTIME )
public @interface Contains {

    /**
     * Message.
     *
     * @return the string
     */
    String message() default "Invalid action property value";

    /**
     * Type.
     *
     * @return the class
     */
    String propertyKey();

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
