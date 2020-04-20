package com.owners.gravitas.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * The Interface ValidateAgentLeadType - validates the leadType supported in the
 * lead created through agent app.
 * 
 * @author ankusht
 */
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.LOCAL_VARIABLE } )
@Retention( RetentionPolicy.RUNTIME )
@Constraint( validatedBy = AgentLeadTypeValidator.class )
@Documented
public @interface ValidateAgentLeadType {

    /**
     * Message.
     *
     * @return the string
     */
    String message() default "error.lead.leadType.format";

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
