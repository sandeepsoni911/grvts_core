package com.owners.gravitas.amqp;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.BaseDTO;

/**
 * The Class AlertDetails.
 *
 * @author pabhishek
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class AlertDetails extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6121303721787231024L;

    /** The event type. */
    private String eventType;

    /** The event display name. */
    private String eventDisplayName;

    /** The client event details. */
    private List< ClientEventDetails > clientEventDetails;

    /** The additional info. */
    private AdditionalInfo additionalInfo;
    
    /** The user tracking detail. */
    private UserTrackingDetail userTrackingDetail;

    /**
     * Instantiates a new alert details.
     */
    public AlertDetails() {

    }

    /**
     * Instantiates a new alert details.
     *
     * @param eventType
     *            the event type
     * @param clientEventDetails
     *            the client event details
     * @param additionalInfo
     *            the additional info
     */
    public AlertDetails( final String eventType, final List< ClientEventDetails > clientEventDetails,
            final AdditionalInfo additionalInfo ) {
        super();
        this.eventType = eventType;
        this.clientEventDetails = clientEventDetails;
        this.additionalInfo = additionalInfo;
    }

    /**
     * Gets the event type.
     *
     * @return the event type
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Sets the event type.
     *
     * @param eventType
     *            the new event type
     */
    public void setEventType( final String eventType ) {
        this.eventType = eventType;
    }

    /**
     * Gets the event display name.
     *
     * @return the event display name
     */
    public String getEventDisplayName() {
        return eventDisplayName;
    }

    /**
     * Sets the event display name.
     *
     * @param eventDisplayName
     *            the new event display name
     */
    public void setEventDisplayName( final String eventDisplayName ) {
        this.eventDisplayName = eventDisplayName;
    }

    /**
     * Gets the client event details.
     *
     * @return the client event details
     */
    public List< ClientEventDetails > getClientEventDetails() {
        return clientEventDetails;
    }

    /**
     * Sets the client event details.
     *
     * @param clientEventDetails
     *            the new client event details
     */
    public void setClientEventDetails( final List< ClientEventDetails > clientEventDetails ) {
        this.clientEventDetails = clientEventDetails;
    }

    /**
     * Gets the additional info.
     *
     * @return the additional info
     */
    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Sets the additional info.
     *
     * @param additionalInfo
     *            the new additional info
     */
    public void setAdditionalInfo( final AdditionalInfo additionalInfo ) {
        this.additionalInfo = additionalInfo;
    }

    /**
     * @return the userTrackingDetail
     */
    public UserTrackingDetail getUserTrackingDetail() {
        return userTrackingDetail;
    }

    /**
     * @param userTrackingDetail
     *            the userTrackingDetail to set
     */
    public void setUserTrackingDetail( UserTrackingDetail userTrackingDetail ) {
        this.userTrackingDetail = userTrackingDetail;
    }    
}
