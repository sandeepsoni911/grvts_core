package com.owners.gravitas.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * The Interface EmailList.
 *
 * @author vishwanathm
 */
@Documented
@Constraint( validatedBy = { ItemSizeValidator.class } )
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER } )
@Retention( RetentionPolicy.RUNTIME )
public @interface ItemSize {

    /**
     * Message.
     *
     * @return the string
     */
    public abstract String message() default "Invalid email(s) size";

    /**
     * Message.
     *
     * @return the string
     */
    public abstract int min() default 0;

    /**
     * Message.
     *
     * @return the string
     */
    public abstract int max();

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
