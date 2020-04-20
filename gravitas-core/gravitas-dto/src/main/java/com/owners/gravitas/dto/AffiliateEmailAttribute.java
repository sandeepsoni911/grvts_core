package com.owners.gravitas.dto;

/**
 * The AffiliateEmailAttribute container.
 */
public class AffiliateEmailAttribute extends BaseDTO {

    /** For serialization. */
    private static final long serialVersionUID = 2510470283683622647L;

    /** The error log. */
    private String errorLog;

    /** The error source. */
    private String errorSource;

    /** The message type name. */
    private String messageTypeName;

    /** The notification from. */
    private String notificationFrom;

    /** The notification to. */
    private String notificationTo;

    /**
     * Gets the error log.
     *
     * @return the error log
     */
    public String getErrorLog() {
        return errorLog;
    }

    /**
     * Sets the error log.
     *
     * @param errorLog
     *            the new error log
     */
    public void setErrorLog( String errorLog ) {
        this.errorLog = errorLog;
    }

    /**
     * Gets the error source.
     *
     * @return the error source
     */
    public String getErrorSource() {
        return errorSource;
    }

    /**
     * Sets the error source.
     *
     * @param errorSource
     *            the new error source
     */
    public void setErrorSource( String errorSource ) {
        this.errorSource = errorSource;
    }

    /**
     * Gets the message type name.
     *
     * @return the message type name
     */
    public String getMessageTypeName() {
        return messageTypeName;
    }

    /**
     * Sets the message type name.
     *
     * @param messageTypeName
     *            the new message type name
     */
    public void setMessageTypeName( String messageTypeName ) {
        this.messageTypeName = messageTypeName;
    }

    /**
     * Gets the notification from.
     *
     * @return the notification from
     */
    public String getNotificationFrom() {
        return notificationFrom;
    }

    /**
     * Sets the notification from.
     *
     * @param notificationFrom
     *            the new notification from
     */
    public void setNotificationFrom( String notificationFrom ) {
        this.notificationFrom = notificationFrom;
    }

    /**
     * Gets the notification to.
     *
     * @return the notification to
     */
    public String getNotificationTo() {
        return notificationTo;
    }

    /**
     * Sets the notification to.
     *
     * @param notificationTo
     *            the new notification to
     */
    public void setNotificationTo( String notificationTo ) {
        this.notificationTo = notificationTo;
    }

}
