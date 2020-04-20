package com.owners.gravitas.aspect;

import javax.mail.Message;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.AffiliateEmailBusinessService;
import com.owners.gravitas.business.EmailParsingLogBusinessService;
import com.owners.gravitas.domain.entity.LeadEmailParsingLog;
import com.owners.gravitas.dto.request.GenericLeadRequest;

/**
 * The Class EmailParsingAspect.
 *
 * @author amits
 *
 */
@Aspect
@Component
public class EmailParsingAspect {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( EmailParsingAspect.class );

    /** The Constant EXECUTION. */
    private static final String EXECUTION = "execution(public * *(..)) && @annotation(com.owners.gravitas.annotation.EmailParsingLog)";

    /** The action log business service. */
    @Autowired
    private EmailParsingLogBusinessService emailParsingLogBusinessService;

    /** The affiliate email business service. */
    @Autowired
    private AffiliateEmailBusinessService affiliateEmailBusinessService;

    /**
     * Log parsing success.
     *
     * @param joinPoint
     *            the join point
     * @param leadRequest
     *            the lead request
     * @return the object
     */
    @AfterReturning( pointcut = EXECUTION, returning = "leadRequest" )
    public Object logParsingSuccess( final JoinPoint joinPoint, final GenericLeadRequest leadRequest ) {
        LOGGER.info( "Email parsing log created for lead " + leadRequest.getEmail() );
        final LeadEmailParsingLog logger = affiliateEmailBusinessService
                .getSuccessLogger( ( Message ) joinPoint.getArgs()[0], leadRequest );
        emailParsingLogBusinessService.saveLeadEmailParsingLog( logger );
        LOGGER.info( "Email parsing success: log details " + logger );
        return joinPoint;
    }

    /**
     * Log parsing failure.
     *
     * @param joinPoint
     *            the join point
     * @param exception
     *            the exception
     * @return the object
     */
    @AfterThrowing( pointcut = EXECUTION, throwing = "exception" )
    public Object logParsingFailure( final JoinPoint joinPoint, final Exception exception ) {
        final LeadEmailParsingLog logger = affiliateEmailBusinessService
                .getFailureLogger( ( Message ) joinPoint.getArgs()[0], exception );
        emailParsingLogBusinessService.saveLeadEmailParsingLog( logger );
        LOGGER.debug( "Email parsing failure: log details " + logger );
        return joinPoint;
    }

}
