/**
 * 
 */
package com.owners.gravitas.config;

/**
 * This class holds details about sender and receiver email ids.
 * 
 * @author harshads
 *
 */
public class NotificationEmailConfig {

    /** The notify to. */
    private String notifyTo;

    /** The notify from. */
    private String notifyFrom;

    /**
     * Instantiates a new notification email config.
     *
     * @param to
     *            the to
     * @param from
     *            the from
     */
    public NotificationEmailConfig( final String to, final String from ) {
        this.notifyTo = to;
        this.notifyFrom = from;
    }

    /**
     * Gets the notify to.
     *
     * @return the notifyTo
     */
    public String getNotifyTo() {
        return notifyTo;
    }

    /**
     * Sets the notify to.
     *
     * @param notifyTo
     *            the notifyTo to set
     */
    public void setNotifyTo( final String notifyTo ) {
        this.notifyTo = notifyTo;
    }

    /**
     * Gets the notify from.
     *
     * @return the notifyFrom
     */
    public String getNotifyFrom() {
        return notifyFrom;
    }

    /**
     * Sets the notify from.
     *
     * @param notifyFrom
     *            the notifyFrom to set
     */
    public void setNotifyFrom( final String notifyFrom ) {
        this.notifyFrom = notifyFrom;
    }

}
