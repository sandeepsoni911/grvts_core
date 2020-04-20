package com.owners.gravitas.dto;

/**
 * The Class RabbitMQAccess
 */
public class RabbitMQAccess extends BaseDTO {

    /** For serialization. */
    private static final long serialVersionUID = -953859696717988756L;

    /** The access token. */
    private String accessToken;

    /**
     * Gets the access token.
     *
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets the access token.
     *
     * @param accessToken
     *            the accessToken to set
     */
    public void setAccessToken( String accessToken ) {
        this.accessToken = accessToken;
    }

}
