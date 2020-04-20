package com.owners.gravitas.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class GroupReponse.
 *
 * @author raviz
 */
public class GroupResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3269845271080733048L;

    /** The id. */
    private String id;

    /** The is agent part of group. */
    private Boolean isAgentPartOfGroup;

    /**
     * Instantiates a new group response.
     *
     * @param id
     *            the id
     */
    public GroupResponse( final String id ) {
        super();
        this.id = id;
    }

    /**
     * Instantiates a new group response.
     *
     * @param id
     *            the id
     * @param status
     *            the status
     * @param message
     *            the message
     */
    public GroupResponse( final String id, final Status status, final String message ) {
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

    /**
     * Gets the checks if is agent part of group.
     *
     * @return the checks if is agent part of group
     */
    public Boolean getIsAgentPartOfGroup() {
        return isAgentPartOfGroup;
    }

    /**
     * Sets the checks if is agent part of group.
     *
     * @param isAgentPartOfGroup
     *            the new checks if is agent part of group
     */
    public void setIsAgentPartOfGroup( final Boolean isAgentPartOfGroup ) {
        this.isAgentPartOfGroup = isAgentPartOfGroup;
    }
}
