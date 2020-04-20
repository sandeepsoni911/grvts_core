package com.owners.gravitas.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.annotation.JmxChange;
import com.owners.gravitas.business.ActionLogBusinessService;
import com.owners.gravitas.dto.JmxChangeDto;

/**
 * The Class JmxChangeAspect.
 * 
 * @author ankusht
 */
@Aspect
@Component
public class JmxChangeAspect {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( JmxChangeAspect.class );

    /** The Constant EXECUTION. */
    private static final String EXECUTION = "execution(public * *(..)) && @annotation(com.owners.gravitas.annotation.JmxChange) && @annotation(jmxChange)";

    /** The audit trail business service. */
    @Autowired
    private ActionLogBusinessService actionLogBusinessService;

    /**
     * Store request params.
     *
     * @param joinPoint
     *            the join point
     * @param jmxChange
     *            the jmx change
     * @param returnObj
     *            the return obj
     * @return the object
     */
    @AfterReturning( pointcut = EXECUTION, returning = "returnObj" )
    public Object auditJmxChange( final JoinPoint joinPoint, final JmxChange jmxChange, final Object returnObj ) {
        LOGGER.debug( "Inside JmxChangeAspect" );
        final String property = joinPoint.getArgs()[0].toString();
        final String value = joinPoint.getArgs()[1].toString();
        final JmxChangeDto dto = new JmxChangeDto( property, value );
        LOGGER.debug( "Property {} is changed to {}", property, value );
        actionLogBusinessService.logJmxChange( dto );
        return joinPoint;
    }
}
