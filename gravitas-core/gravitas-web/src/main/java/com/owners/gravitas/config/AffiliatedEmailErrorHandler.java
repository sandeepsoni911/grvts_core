/*
 * 
 */
package com.owners.gravitas.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import com.owners.gravitas.business.AffiliateEmailBusinessService;
import com.owners.gravitas.exception.AffiliateEmailException;
import com.owners.gravitas.exception.AffiliateEmailParsingException;
import com.owners.gravitas.exception.AffiliateEmailValidationException;
import com.owners.gravitas.util.ErrorTokenGenerator;

/**
 * The Class provides error handling for integration channels.
 *
 * @author amits
 */
@Component
public class AffiliatedEmailErrorHandler {
    /** Logger instance. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AffiliatedEmailErrorHandler.class );

    /** The affiliate email business service. */
    @Autowired
    private AffiliateEmailBusinessService affiliateEmailBusinessService;

    /** The label applied to a message if lead is not generated. */
    @Value( "${affiliate.mail.lead.failure.label}" )
    private String leadNotGeneratedLabel;

    /**
     * Email parsing error handler.
     *
     * @param exception
     *            the exception
     * @param receiverEmail
     *            the receiver email
     * @param senderEmail
     *            the sender email
     */
    public void emailParsingErrorHandler( final AffiliateEmailParsingException exception,
            final NotificationEmailConfig emailConfig ) {
        emailParsingErrorHandler( exception, emailConfig, leadNotGeneratedLabel );
    }

    /**
     * Email parsing error handler.
     *
     * @param exception
     *            the exception
     * @param emailConfig
     *            the email config
     * @param labelToApply
     *            the label to apply
     */
    public void emailParsingErrorHandler( final AffiliateEmailParsingException exception,
            final NotificationEmailConfig emailConfig, final String labelToApply ) {
        final String errorId = logException( exception );
        affiliateEmailBusinessService.handleEmailParsingError( errorId, exception, emailConfig );
        affiliateEmailBusinessService.addLabelToMessage( labelToApply, exception.getMailMessage() );
    }

    /**
     * Email validation error handler.
     *
     * @param exception
     *            the exception
     * @param receiverEmail
     *            the receiver email
     * @param senderEmail
     *            the sender email
     */
    public void emailValidationErrorHandler( AffiliateEmailValidationException exception,
            final NotificationEmailConfig emailConfig ) {
        emailValidationErrorHandler( exception, emailConfig, leadNotGeneratedLabel );
    }

    /**
     * Email validation error handler.
     *
     * @param exception
     *            the exception
     * @param emailConfig
     *            the email config
     * @param labelToApply
     *            the label to apply
     */
    public void emailValidationErrorHandler( AffiliateEmailValidationException exception,
            final NotificationEmailConfig emailConfig, final String labelToApply ) {
        final String errorId = logException( exception );
        affiliateEmailBusinessService.handleEmailValidationError( errorId, exception, emailConfig );
        affiliateEmailBusinessService.addLabelToMessage( labelToApply, exception.getMailMessage() );
    }

    /**
     * Affiliate email error handler.
     *
     * @param errorMessage
     *            the error message
     */
    public void affiliateEmailErrorHandler( AffiliateEmailException exception ) {
        affiliateEmailErrorHandler( exception, leadNotGeneratedLabel );
    }

    /**
     * Affiliate email error handler.
     *
     * @param exception
     *            the exception
     * @param labelToApply
     *            the label to apply
     */
    public void affiliateEmailErrorHandler( AffiliateEmailException exception, final String labelToApply ) {
        if (exception.getCause() != null
                && HttpStatusCodeException.class.isAssignableFrom( exception.getCause().getClass() )) {
            LOGGER.error( ( ( HttpStatusCodeException ) exception.getCause() ).getResponseBodyAsString() );
        }
        logException( exception );
        affiliateEmailBusinessService.addLabelToMessage( labelToApply, exception.getMailMessage() );
    }

    /**
     * This method is to log Exceptions.
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
