package com.owners.gravitas.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.annotation.Audit;
import com.owners.gravitas.business.AuditTrailBusinessService;

/**
 * The Class AuditTrailAspect.
 *
 * @author vishwanathm
 *
 */
@Aspect
@Component
public class AuditTrailAspect {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AuditTrailAspect.class );

    /** The Constant EXECUTION. */
    private static final String EXECUTION = "execution(public * *(..)) && @annotation(com.owners.gravitas.annotation.Audit) && @annotation(audit)";

    /** The audit trail business service. */
    @Autowired
    private AuditTrailBusinessService auditTrailBusinessService;

    /**
     * Store request params.
     *
     * @param joinPoint
     *            the join point
     * @param audit
     *            the action
     * @param postResponse
     *            the post response
     * @return the object
     */
    @AfterReturning( pointcut = EXECUTION, returning = "returnObj" )
    public Object auditActions( final JoinPoint joinPoint, final Audit audit, final Object returnObj ) {
        LOGGER.debug( "Inside audit tail aspect" );
        auditTrailBusinessService.createAuditLog( joinPoint.getArgs(), audit, returnObj );
        return joinPoint;
    }
}
