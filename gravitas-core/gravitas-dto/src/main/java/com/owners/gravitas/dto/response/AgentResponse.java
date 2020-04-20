package com.owners.gravitas.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class AgentResponse.
 *
 * @author vishwanathm
 */
public class AgentResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8173259203567317433L;

    /** The id. */
    private String id;

    /**
     * Instantiates a new agent response.
     *
     * @param id
     *            the id
     */
    public AgentResponse( String id ) {
        super();
        this.id = id;
    }

    /**
     * Instantiates a new agent response.
     *
     * @param id
     *            the id
     * @param status
     *            the status
     * @param message
     *            the message
     */
    public AgentResponse( String id, Status status, String message ) {
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
