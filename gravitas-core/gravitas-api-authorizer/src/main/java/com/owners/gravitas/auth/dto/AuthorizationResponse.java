package com.owners.gravitas.auth.dto;

/**
 * The Class AuthorizationResponse.
 */
public class AuthorizationResponse {

    /** The principal id. */
    private String principalId;;

    /** The policy document. */
    private String policyDocument;

    /**
     * Instantiates a new authorization response.
     *
     * @param principalId
     *            the principal id
     */
    public AuthorizationResponse( String principalId ) {
        this.principalId = principalId;
    }

    /**
     * Gets the principal id.
     *
     * @return the principal id
     */
    public String getPrincipalId() {
        return principalId;
    }

    /**
     * Sets the principal id.
     *
     * @param principalId
     *            the new principal id
     */
    public void setPrincipalId( String principalId ) {
        this.principalId = principalId;
    }

    /**
     * Gets the policy document.
     *
     * @return the policy document
     */
    public String getPolicyDocument() {
        return policyDocument;
    }

    /**
     * Sets the policy document.
     *
     * @param policyDocument
     *            the new policy document
     */
    public void setPolicyDocument( String policyDocument ) {
        this.policyDocument = policyDocument;
    }
}
