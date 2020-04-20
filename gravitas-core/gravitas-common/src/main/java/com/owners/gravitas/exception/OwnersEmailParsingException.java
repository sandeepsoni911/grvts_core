package com.owners.gravitas.exception;

/**
 * The Class OwnersEmailParsingException.
 */
public class OwnersEmailParsingException extends ApplicationException {

    /** For serialization. */
    private static final long serialVersionUID = -4399880505524343004L;

    /**
     * Instantiates a new owner email parsing exception.
     *
     * @param message
     *            the message
     * @param emailParsingException
     *            the email parsing exception
     */
    public OwnersEmailParsingException( final String message,
            final AffiliateEmailParsingException emailParsingException ) {
        super( message, emailParsingException );
    }
}
