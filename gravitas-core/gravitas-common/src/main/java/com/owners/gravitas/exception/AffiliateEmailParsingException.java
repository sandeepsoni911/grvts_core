package com.owners.gravitas.exception;

import static com.owners.gravitas.enums.ErrorCode.AFFILIATE_EMAIL_PARSING_ERROR;

import javax.mail.Message;

/**
 * The Class AffiliateEmailParsingException.
 *
 * @author vishwanathm
 */
public class AffiliateEmailParsingException extends AffiliateEmailException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4417422214084023995L;

    /**
     * Instantiates a new affiliate email parsing exception.
     *
     * @param message
     *            the exception message
     * @param exp
     *            the exception
     */
    public AffiliateEmailParsingException( Message mailMessage, final String message, final Throwable exp ) {
        super( AFFILIATE_EMAIL_PARSING_ERROR, mailMessage, message, exp );
    }
}
