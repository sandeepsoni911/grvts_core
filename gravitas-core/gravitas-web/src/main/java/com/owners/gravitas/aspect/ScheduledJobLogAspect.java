package com.owners.gravitas.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.annotation.ScheduledJobLog;
import com.owners.gravitas.domain.entity.SchedulerLog;
import com.owners.gravitas.service.SchedulerLogService;

/**
 * The Class PerformanceLoggerAspect.
 *
 * @author vishwanathm
 */
@Aspect
@Component
public class ScheduledJobLogAspect {

    /** The Constant EXECUTION. */
    private static final String EXECUTION = "execution(public * *(..)) && @annotation(com.owners.gravitas.annotation.ScheduledJobLog) && @annotation(scheduledJobLog)";

    /** The scheduler log service. */
    @Autowired
    private SchedulerLogService schedulerLogService;

    /**
     * Performance logger execution starts and stops are logged.
     *
     * @param joinPoint
     *            the join point
     * @throws Throwable
     *             the throwable
     */
    @Around( EXECUTION )
    public Object logPerformance( final ProceedingJoinPoint joinPoint, final ScheduledJobLog scheduledJobLog )
            throws Throwable {
        final SchedulerLog schedulerLog = schedulerLogService.startJob( scheduledJobLog.jobType() );
        try {
            return joinPoint.proceed();
        } finally {
            schedulerLogService.endJob( schedulerLog );
        }
    }

}
