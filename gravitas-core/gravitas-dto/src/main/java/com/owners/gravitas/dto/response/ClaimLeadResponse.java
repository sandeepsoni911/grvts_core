package com.owners.gravitas.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class ClaimLeadResponse.
 *
 * @author bhagtanl
 */
public class ClaimLeadResponse extends BaseResponse {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2741286960223690794L;

	/** The id. */
    private String id;
    
    /** The detailMessage. */
    private String detailMessage;

    /**
     * Instantiates a new claim lead response.
     *
     * @param id
     *        the id
     * @param detailMessage
     *        the detailMessage      
     */
    public ClaimLeadResponse( String id, String detailMessage ) {
        super();
        this.id = id;
        this.detailMessage = detailMessage;
    }

    /**
     * Instantiates a new claim lead response.
     *
     * @param id
     *            the id
     * @param detailMessage
     *            the detailMessage           
     * @param status
     *            the status
     * @param message
     *            the message
     */
    public ClaimLeadResponse( String id, String detailMessage, Status status, String message ) {
        super( status, message );
        this.id = id;
        this.detailMessage = detailMessage;
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

    /**
     * Gets the detailMessage.
     *
     * @return the detailMessage
     */
	public String getDetailMessage() {
		return detailMessage;
	}

	/**
     * Sets the detailMessage.
     *
     * @param detailmessage
     *            the detailMessage to set
     */
    @JsonProperty( "detailmessage" )
	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}
    
}
