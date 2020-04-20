package com.owners.gravitas.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class AlertDetail.
 * 
 * @author bhardrah
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class AlertDetail extends BaseDTO {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3767447741473487818L;
    /** The event text. */
    private String eventText;
    /** The event text. */
    private String eventType;
    /** The created on. */
    private Long createdOn;
    /** The list of event details. */
    private List< EventDetail > eventDetails;
    /** The Email Notification details sent based on event. */
    private NotificationDetail notificationDetail;

    /**
     * Gets the event text.
     *
     * @return the eventText
     */
    public String getEventText() {
        return eventText;
    }

    /**
     * Sets the event text.
     *
     * @param eventText
     *            the event text
     */
    public void setEventText( final String eventText ) {
        this.eventText = eventText;
    }

    /**
     * Gets the created on.
     *
     * @return the createdOn
     */
    public Long getCreatedOn() {
        return createdOn;
    }

    /**
     * Sets the created On.
     *
     * @param createdOn
     *            the created On
     */
    public void setCreatedOn( final Long createdOn ) {
        this.createdOn = createdOn;
    }

    /**
     * Gets the event details.
     *
     * @return the eventDetails
     */
    public List< EventDetail > getEventDetails() {
        return eventDetails;
    }

    /**
     * Sets the event details.
     *
     * @param eventDetails
     *            the event details
     */
    public void setEventDetails( final List< EventDetail > eventDetails ) {
        this.eventDetails = eventDetails;
    }

    /**
     * @return the eventType
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * @param eventType
     *            the eventType to set
     */
    public void setEventType( final String eventType ) {
        this.eventType = eventType;
    }

    /**
     * @return the notificationDetail
     */
    public NotificationDetail getNotificationDetail() {
        return notificationDetail;
    }

    /**
     * @param notificationDetail
     *            the notificationDetail to set
     */
    public void setNotificationDetail( final NotificationDetail notificationDetail ) {
        this.notificationDetail = notificationDetail;
    }

}
