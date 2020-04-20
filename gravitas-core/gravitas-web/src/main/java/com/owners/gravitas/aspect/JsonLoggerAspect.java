package com.owners.gravitas.aspect;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.util.JsonUtil;
import com.owners.gravitas.util.PropertyWriter;

/**
 * The Class JsonLoggerAspect.
 *
 * @author vishwanathm
 */
@ManagedResource( objectName = "com.owners.gravitas:name=JsonLoggerAspect" )
@Aspect
@Component
public class JsonLoggerAspect {

    /** Logger instance. */
    private static final Logger LOGGER = LoggerFactory.getLogger( JsonLoggerAspect.class );

    /** The Constant EXECUTION. */
    private static final String EXECUTION = "execution(public * *(..)) && @annotation(com.owners.gravitas.annotation.JsonLog)";

    /** The enable json logging. */
    @Value( "${logging.enable.json:false}" )
    private boolean enableJsonLogging;

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    /**
     * Log json string for methods that annotated with @JsonLog.
     *
     * @param object
     *            the object
     */
    @Around( EXECUTION )
    public void logJson( final ProceedingJoinPoint joinPoint ) {
        try {
            if (enableJsonLogging) {
                iterate( joinPoint );
            }
            joinPoint.proceed();
        } catch ( Throwable e ) {
            LOGGER.info( "Problem in printting logs : IGNORE ", e );
            throw new ApplicationException( e.getLocalizedMessage(), e );
        }
    }

    /**
     * Iterate.
     *
     * @param joinPoint
     *            the join point
     */
    private void iterate( final ProceedingJoinPoint joinPoint ) {
        Arrays.asList( joinPoint.getArgs() ).forEach( object -> log( object ) );
    }

    /**
     * Log.
     *
     * @param object
     *            the object
     */
    private void log( final Object object ) {
        try {
            LOGGER.debug( "From host : " + InetAddress.getLocalHost() + ", The JSON string of object : "
                    + JsonUtil.toJson( object ) );
        } catch ( ApplicationException e ) {
            LOGGER.info( "Problem in parsing data to json string :IGNORE ", e );
        } catch ( UnknownHostException e ) {
            LOGGER.info( "Problem in getting host name : Which has no impact on business : IGNORE ", e );
        }
    }

    /**
     * Checks if is enable json logging.
     *
     * @return true, if is enable json logging
     */
    @ManagedAttribute
    public boolean isEnableJsonLogging() {
        return enableJsonLogging;
    }

    /**
     * Sets the enable json logging.
     *
     * @param enableJsonLogging
     *            the new enable json logging
     */
    @ManagedAttribute
    public void setEnableJsonLogging( boolean enableJsonLogging ) {
        this.enableJsonLogging = enableJsonLogging;
        propertyWriter.saveJmxProperty( "logging.enable.json", enableJsonLogging );
    }
}
