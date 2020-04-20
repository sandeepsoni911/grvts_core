package com.owners.gravitas.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.owners.gravitas.enums.ActionEntity;
import com.owners.gravitas.enums.ActionType;

/**
 * The Interface Audit.
 *
 * @author vishwanathm
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.METHOD } )
public @interface Audit {

    /**
     * Gets the action entity.
     *
     * @return the action entity
     */
    public ActionEntity entity();

    /**
     * Args.
     *
     * @return the string[]
     */
    public String[] args() default {};

    /**
     * Audit type.
     *
     * @return the action type
     */
    public ActionType type();
}
