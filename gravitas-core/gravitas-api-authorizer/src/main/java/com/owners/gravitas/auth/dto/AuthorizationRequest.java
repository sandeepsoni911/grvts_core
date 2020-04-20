package com.owners.gravitas.auth.dto;

/**
 * The Class AuthorizationRequest.
 */
public class AuthorizationRequest {

    /** The type. */
    private String type;

    /** The authorization token. */
    private String authorizationToken;

    /** The method arn. */
    private String methodArn;

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type
     *            the new type
     */
    public void setType( String type ) {
        this.type = type;
    }

    /**
     * Gets the authorization token.
     *
     * @return the authorization token
     */
    public String getAuthorizationToken() {
        return authorizationToken;
    }

    /**
     * Sets the authorization token.
     *
     * @param authorizationToken
     *            the new authorization token
     */
    public void setAuthorizationToken( String authorizationToken ) {
        this.authorizationToken = authorizationToken;
    }

    /**
     * Gets the method arn.
     *
     * @return the method arn
     */
    public String getMethodArn() {
        return methodArn;
    }

    /**
     * Sets the method arn.
     *
     * @param methodArn
     *            the new method arn
     */
    public void setMethodArn( String methodArn ) {
        this.methodArn = methodArn;
    }

}
