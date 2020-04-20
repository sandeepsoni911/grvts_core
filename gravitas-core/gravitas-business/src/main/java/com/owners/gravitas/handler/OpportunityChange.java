package com.owners.gravitas.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

import com.owners.gravitas.enums.OpportunityChangeType;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface OpportunityChange {
	String value() default "";
	OpportunityChangeType type();
}