package com.owners.gravitas.exception.handler;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import com.owners.gravitas.util.ErrorTokenGenerator;

/**
 * The Class CustomAsyncExceptionHandler.
 *
 * @author amits
 */
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    /** Logger instance. */
    private static final Logger LOGGER = LoggerFactory.getLogger( CustomAsyncExceptionHandler.class );

    /*
     * (non-Javadoc)
     * @see org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler#
     * handleUncaughtException(java.lang.Throwable, java.lang.reflect.Method,
     * java.lang.Object[])
     */
    @Override
    public void handleUncaughtException( final Throwable throwable, final Method method, final Object... obj ) {
        LOGGER.error( "Exception message - " + throwable.getMessage() );
        LOGGER.error( "Method name - " + method.getName() );
        for ( Object param : obj ) {
            LOGGER.error( "Parameter value - " + param );
        }
        logException( throwable );
    }

    /**
     * This method is used to log Exceptions.
     *
     * @param exception
     *            that needs to be logged
     * @return Error id
     */
    private String logException( final Throwable exception ) {
        final String errorId = ErrorTokenGenerator.getErrorId();
        final StringBuilder error = new StringBuilder( "Error id->" + errorId );
        error.append( "\n" + exception.getLocalizedMessage() );
        LOGGER.error( error.toString(), exception );
        return errorId;
    }
}
