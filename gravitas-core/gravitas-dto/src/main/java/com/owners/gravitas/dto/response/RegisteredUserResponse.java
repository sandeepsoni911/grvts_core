package com.owners.gravitas.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.owners.gravitas.dto.BaseDTO;
import com.owners.gravitas.dto.RegisteredUser;

@JsonIgnoreProperties( ignoreUnknown = true )
public class RegisteredUserResponse extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5434814044243520953L;

    /** The user. */
    private RegisteredUser user;

    /** The status. */
    private String status;

    /** The message. */
    private String message;

    /**
     * Gets the user.
     *
     * @return the user
     */
    public RegisteredUser getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user
     *            the new user
     */
    @JsonProperty( "data" )
    public void setUser( final RegisteredUser user ) {
        this.user = user;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status
     *            the new status
     */
    @JsonProperty( "status" )
    public void setStatus( final String status ) {
        this.status = status;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message
     *            the new message
     */
    @JsonProperty( "message" )
    public void setMessage( final String message ) {
        this.message = message;
    }

}
