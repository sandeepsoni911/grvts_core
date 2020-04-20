package com.owners.gravitas.exception;

import javax.mail.Message;

import com.owners.gravitas.enums.ErrorCode;

/**
 * The Class AffiliateEmailParsingException.
 *
 * @author vishwanathm
 */
public class AffiliateEmailException extends ApplicationException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4417422214084023995L;

    /** The mailMessage. */
    private final Message mailMessage;

    /**
     * Instantiates a new affiliate email exception.
     *
     * @param mailMessage
     *            the mail message
     * @param message
     *            the message
     * @param exp
     *            the exp
     */
    public AffiliateEmailException( final Message mailMessage, final String message, final Throwable exp ) {
        this( ErrorCode.AFFILIATE_EMAIL_SCRAPING_ERROR, mailMessage, message, exp );
    }

    /**
     * Instantiates a new affiliate email parsing exception.
     *
     * @param errorCode
     *            the error code
     * @param message
     *            the exception mailMessage
     * @param mailMessage
     *            mail mailMessage
     * @param exp
     *            the exception
     */
    public AffiliateEmailException( final ErrorCode errorCode, final Message mailMessage, final String message,
            final Throwable exp ) {
        super( message, exp, errorCode );
        this.mailMessage = mailMessage;
    }

    /**
     * Instantiates a new affiliate email exception.
     *
     * @param errorCode
     *            the error code
     * @param mailMessage
     *            the mail message
     * @param message
     *            the message
     */
    public AffiliateEmailException( final ErrorCode errorCode, final Message mailMessage, final String message ) {
        super( message, errorCode );
        this.mailMessage = mailMessage;
    }

    /**
     * Gets the mail message.
     *
     * @return the mailMessage
     */
    public Message getMailMessage() {
        return mailMessage;
    }
}
