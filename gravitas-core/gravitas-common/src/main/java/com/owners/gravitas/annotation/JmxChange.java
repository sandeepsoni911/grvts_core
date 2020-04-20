package com.owners.gravitas.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation to be applied when we want to audit the JMX changes.
 * 
 * @author ankusht
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.METHOD } )
public @interface JmxChange {

    /**
     * Args.
     *
     * @return the string[]
     */
    public String[] args() default {};
}
