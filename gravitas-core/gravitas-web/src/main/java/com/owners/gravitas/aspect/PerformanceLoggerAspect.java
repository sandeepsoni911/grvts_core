package com.owners.gravitas.aspect;

import static com.owners.gravitas.constants.Constants.PERIOD;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PerformanceLogger;

/**
 * The Class PerformanceLoggerAspect.
 *
 * @author nishak
 */
@Aspect
@Component
public class PerformanceLoggerAspect {

    /** Logger instance. */
    private static final Logger LOGGER = LoggerFactory.getLogger( PerformanceLoggerAspect.class );

    /** The Constant EXECUTION. */
    private static final String EXECUTION = "execution(public * *(..)) && @annotation(com.owners.gravitas.annotation.PerformanceLog)";

    /**
     * Performance logger execution starts and stops are logged.
     *
     * @param joinPoint
     *            the join point
     * @throws Throwable
     *             the throwable
     */
    @Around( EXECUTION )
    public Object logPerformance( final ProceedingJoinPoint joinPoint ) throws Throwable {
        final PerformanceLogger prefLog = PerformanceLogger.createAndStart();
        try {
            return joinPoint.proceed();
        } finally {
            final Signature sig = joinPoint.getSignature();
            LOGGER.info( "Method " + sig.getDeclaringType().getName() + PERIOD + sig.getName() + "(...) took: "
                    + prefLog.stop() );
        }
    }

}
