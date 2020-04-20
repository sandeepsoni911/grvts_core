package com.owners.gravitas.exception;

/**
 * The Class OwnersEmailValidationException.
 */
public class OwnersEmailValidationException extends ApplicationException {

    /** For serialization. */
    private static final long serialVersionUID = -868885340174014628L;

    /**
     * Instantiates a new owner email validation exception.
     *
     * @param message
     *            the message
     * @param emailValidationException
     *            the email validation exception
     */
    public OwnersEmailValidationException( final String message,
            final AffiliateEmailValidationException emailValidationException ) {
        super( message, emailValidationException );
    }
}
