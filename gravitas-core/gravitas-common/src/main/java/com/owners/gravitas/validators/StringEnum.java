package com.owners.gravitas.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * The Interface StringEnum.
 *
 * @author vishwanathm
 */
@Documented
@Constraint( validatedBy = StringEnumerationValidator.class )
@Target( { METHOD, FIELD, ANNOTATION_TYPE, PARAMETER, CONSTRUCTOR } )
@Retention( RUNTIME )
public @interface StringEnum {

    /**
     * Message.
     *
     * @return the string
     */
    String message() default "Invalid Enum type";

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
     * Enum class.
     *
     * @return the class<? extends enum<?>>
     */
    Class< ? extends Enum< ? > > enumClass();
}
