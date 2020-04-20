package com.owners.gravitas.exception;

/**
 * The Class HubzuEmailParsingException.
 */
public class HubzuEmailParsingException extends ApplicationException {

    /** For serialization. */
    private static final long serialVersionUID = -5218752635528296902L;

    /**
     * Instantiates a new hubzu email parsing exception.
     *
     * @param message
     *            the message
     * @param emailParsingException
     *            the email parsing exception
     */
    public HubzuEmailParsingException( final String message,
            final AffiliateEmailParsingException emailParsingException ) {
        super( message, emailParsingException );
    }
}
