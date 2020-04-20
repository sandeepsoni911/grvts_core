package com.owners.gravitas.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.owners.gravitas.dto.LeadDetailsDTO;

public class LeadDetailsResponse extends BaseResponse {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1160047916773112178L;
	
	/** The Lead Details. */
	@JsonProperty( "leadDetails" )
    private LeadDetailsDTO leadDetails;
    
    /**
     * Instantiates a new LeadDetailsResponse.
     */
    public LeadDetailsResponse() {
        
    }

    /**
     * Instantiates a new LeadDetailsResponse.
     *
     * @param status
     *            the status
     * @param message
     *            the message
     */
    public LeadDetailsResponse( final Status status, final String message ) {
        super( status, message );
    }

	
    /**
	 * @return the leadDetails
	 */
	public LeadDetailsDTO getLeadDetails() {
		return leadDetails;
	}

	/**
	 * @param leadDetails the leadDetails to set
	 */
	public void setLeadDetails(LeadDetailsDTO leadDetails) {
		this.leadDetails = leadDetails;
	}

}
