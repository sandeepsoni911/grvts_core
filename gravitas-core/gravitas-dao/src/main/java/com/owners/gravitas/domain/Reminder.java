/**
 *
 */
package com.owners.gravitas.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// TODO: Auto-generated Javadoc
/**
 * The Class Reminder.
 *
 * @author harshads
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class Reminder extends BaseDomain {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2033125242435462483L;

    /** The trigger dtm. */
    private Long triggerDtm;

    /** The created dtm. */
    private Long createdDtm;

    /** The last modified dtm. */
    private Long lastModifiedDtm;

    /** The reminder ids. */
    private List< String > notificationIds;

    /**
     * Instantiates a new reminder.
     */
    public Reminder() {
        // do nothing
    }

    /**
     * Instantiates a new reminder.
     *
     * @param triggerDtm
     *            the trigger dtm
     * @param notificationIds
     *            the notification ids
     */
    public Reminder( final Long triggerDtm, final List< String > notificationIds ) {
        this.triggerDtm = triggerDtm;
        this.notificationIds = notificationIds;
    }

    /**
     * Gets the trigger dtm.
     *
     * @return the triggerDtm
     */
    public Long getTriggerDtm() {
        return triggerDtm;
    }

    /**
     * Sets the trigger dtm.
     *
     * @param triggerDtm
     *            the triggerDtm to set
     */
    public void setTriggerDtm( final Long triggerDtm ) {
        this.triggerDtm = triggerDtm;
    }

    /**
     * Gets the reminder id.
     *
     * @return the reminderId
     */
    public List< String > getNotificationIds() {
        return notificationIds;
    }

    /**
     * Sets the reminder id.
     *
     * @param notificationIds
     *            the new notification ids
     */
    public void setNotificationIds( final List< String > notificationIds ) {
        this.notificationIds = notificationIds;
    }

    /**
     * Gets the created dtm.
     *
     * @return the created dtm
     */
    public Long getCreatedDtm() {
        return createdDtm;
    }

    /**
     * Sets the created dtm.
     *
     * @param createdDtm
     *            the new created dtm
     */
    public void setCreatedDtm( final Long createdDtm ) {
        this.createdDtm = createdDtm;
    }

    /**
     * Gets the last modified dtm.
     *
     * @return the last modified dtm
     */
    public Long getLastModifiedDtm() {
        return lastModifiedDtm;
    }

    /**
     * Sets the last modified dtm.
     *
     * @param lastModifiedDtm
     *            the new last modified dtm
     */
    public void setLastModifiedDtm( final Long lastModifiedDtm ) {
        this.lastModifiedDtm = lastModifiedDtm;
    }
}
