package com.owners.gravitas.exception;

import static com.owners.gravitas.enums.ErrorCode.AFFILIATE_EMAIL_VALIDATION_ERROR;

import java.util.List;
import java.util.Map;

import javax.mail.Message;

/**
 * The Class AffiliateEmailValidationException.
 *
 * @author vishwanathm
 */

public class AffiliateEmailValidationException extends AffiliateEmailException {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4248513364916276577L;

    /** The failed contraints. */
    private final Map< String, List< String > > failedContraints;

    /**
     * Instantiates a new affiliate email validation exception.
     *
     * @param mailMessage
     *            the mail message
     * @param message
     *            the message
     * @param exp
     *            the exp
     */
    public AffiliateEmailValidationException( final Message mailMessage, final String message,
            final Map< String, List< String > > failedContraints ) {
        super( AFFILIATE_EMAIL_VALIDATION_ERROR, mailMessage, message );
        this.failedContraints = failedContraints;
    }

    /**
     * Gets the failed contraints.
     *
     * @return the failedContraints
     */
    public final Map< String, List< String > > getFailedContraints() {
        return failedContraints;
    }
}
