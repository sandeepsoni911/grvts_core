package com.owners.gravitas.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

/**
 * The Class provides error handling for integration channels.
 *
 * @author amits
 */
@MessageEndpoint
public class ErrorChannelHandler {
    /** Logger instance. */
    private static final Logger LOGGER = LoggerFactory.getLogger( ErrorChannelHandler.class );

    /**
     * Error handler channel.
     *
     * @param errorMessage
     *            the error message
     */
    @ServiceActivator( inputChannel = "errorChannel", outputChannel = "nullChannel" )
    public void errorhandler( final Message< Throwable > errorMessage ) {
        final Throwable exception = errorMessage.getPayload();
        LOGGER.error( exception.getLocalizedMessage(), exception );
    }

}
