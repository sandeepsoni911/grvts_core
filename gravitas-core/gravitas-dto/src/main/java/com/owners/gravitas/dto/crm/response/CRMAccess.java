package com.owners.gravitas.dto.crm.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.owners.gravitas.dto.response.BaseResponse;

/**
 * The Class CRMAccessResponse.
 *
 * @author vishwanathm
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class CRMAccess extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8821884788260424494L;

    /** The access token. */
    @JsonProperty( "access_token" )
    private String accessToken;

    /** The instance url. */
    @JsonProperty( "instance_url" )
    private String instanceUrl;

    /** The id. */
    private String id;

    /** The token_type. */
    @JsonProperty( "token_type" )
    private String tokenType;

    /** The issued_at. */
    @JsonProperty( "issued_at" )
    private String issuedAt;

    /** The signature. */
    private String signature;

    /**
     * Gets the access token.
     *
     * @return the access token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets the access token.
     *
     * @param accessToken
     *            the new access token
     */
    public void setAccessToken( final String accessToken ) {
        this.accessToken = accessToken;
    }

    /**
     * Gets the instance url.
     *
     * @return the instance url
     */
    public String getInstanceUrl() {
        return instanceUrl;
    }

    /**
     * Sets the instance url.
     *
     * @param instanceUrl
     *            the new instance url
     */
    public void setInstanceUrl( final String instanceUrl ) {
        this.instanceUrl = instanceUrl;
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
     *            the new id
     */
    public void setId( final String id ) {
        this.id = id;
    }

    /**
     * Gets the token_type.
     *
     * @return the token_type
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * Sets the token_type.
     *
     * @param tokenType
     *            the new token_type
     */
    public void setTokenType( final String tokenType ) {
        this.tokenType = tokenType;
    }

    /**
     * Gets the issued_at.
     *
     * @return the issued_at
     */
    public String getIssuedAt() {
        return issuedAt;
    }

    /**
     * Sets the issued_at.
     *
     * @param issuedAt
     *            the new issued_at
     */
    public void setIssuedAt( final String issuedAt ) {
        this.issuedAt = issuedAt;
    }

    /**
     * Gets the signature.
     *
     * @return the signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Sets the signature.
     *
     * @param signature
     *            the new signature
     */
    public void setSignature( final String signature ) {
        this.signature = signature;
    }
}
