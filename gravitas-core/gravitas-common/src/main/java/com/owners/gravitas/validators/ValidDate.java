package com.owners.gravitas.validators;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * The Interface FutureDate.
 *
 * @author vishwanathm
 */
@Documented
@Constraint( validatedBy = ValidDateValidator.class )
@Target( { METHOD, FIELD, ANNOTATION_TYPE, PARAMETER, CONSTRUCTOR } )
@Retention( RUNTIME )
public @interface ValidDate {

    /**
     * Message.
     *
     * @return the string
     */
    String message() default "Invalid date and time type";

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

    /**
     * Format.
     *
     * @return the string
     */
    String format();

}
