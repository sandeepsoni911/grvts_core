package com.owners.gravitas.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.owners.gravitas.enums.JobType;

/**
 * The Interface ScheduledJobLog.
 *
 * @author vishwa
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.METHOD )
public @interface ScheduledJobLog {
    public JobType jobType();
}
