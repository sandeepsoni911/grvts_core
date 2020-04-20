package com.owners.gravitas.amqp;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.BaseDTO;

/**
 * The Class BuyerWebActivitySource.
 *
 * @author pabhishek
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class BuyerWebActivitySource extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5944937713400736406L;

    /** The user id. */
    private String userId;

    /** The created on. */
    private long createdOn;

    /** The alert details. */
    private List< AlertDetails > alertDetails;

    /**
     * Instantiates a new lead web activity source.
     */
    public BuyerWebActivitySource() {

    }

    /**
     * Instantiates a new buyer web activity source.
     *
     * @param userId
     *            the user id
     * @param createdOn
     *            the created on
     * @param alertDetails
     *            the alert details
     */
    public BuyerWebActivitySource( final String userId, final long createdOn,
            final List< AlertDetails > alertDetails ) {
        super();
        this.userId = userId;
        this.createdOn = createdOn;
        this.alertDetails = alertDetails;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId
     *            the new user id
     */
    public void setUserId( final String userId ) {
        this.userId = userId;
    }

    /**
     * Gets the created on.
     *
     * @return the created on
     */
    public long getCreatedOn() {
        return createdOn;
    }

    /**
     * Sets the created on.
     *
     * @param createdOn
     *            the new created on
     */
    public void setCreatedOn( final long createdOn ) {
        this.createdOn = createdOn;
    }

    /**
     * Gets the alert details.
     *
     * @return the alert details
     */
    public List< AlertDetails > getAlertDetails() {
        return alertDetails;
    }

    /**
     * Sets the alert details.
     *
     * @param alertDetails
     *            the new alert details
     */
    public void setAlertDetails( final List< AlertDetails > alertDetails ) {
        this.alertDetails = alertDetails;
    }

}
