package com.owners.gravitas.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class LeadResponse.
 *
 * @author harshads
 */
public class LeadResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7052976013457454473L;

    /** The id. */
    private String id;

    /**
     * Instantiates a new lead response.
     *
     * @param status
     *            the status
     * @param message
     *            the message
     */
    public LeadResponse( final Status status, final String message, final String id ) {
        super( status, message );
        this.id = id;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the id to set
     */
    @JsonProperty( "id" )
    public void setId( final String id ) {
        this.id = id;
    }

}
