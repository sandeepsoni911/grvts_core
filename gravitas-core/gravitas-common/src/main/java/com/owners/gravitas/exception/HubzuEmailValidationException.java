package com.owners.gravitas.exception;

/**
 * The Class HubzuEmailValidationException.
 */
public class HubzuEmailValidationException extends ApplicationException {

    /** For serialization. */
    private static final long serialVersionUID = -7083840111449308871L;

    /**
     * Instantiates a new hubzu email validation exception.
     *
     * @param message
     *            the message
     * @param emailValidationException
     *            the email validation exception
     */
    public HubzuEmailValidationException( final String message,
            final AffiliateEmailValidationException emailValidationException ) {
        super( message, emailValidationException );
    }
}
