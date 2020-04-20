/*
 *
 */
package com.owners.gravitas.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.owners.gravitas.dto.BaseDTO;
import com.owners.gravitas.dto.ReferralExchangeDetails;

/**
 * The Class ReferralExchangeRequest.
 *
 * @author vishwanathm
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class ReferralExchangeRequest extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 625105278774670630L;

    /** The token. */
    private String token;

    /** The version. */
    private String version;

    /** The test flag. */
    private boolean testFlag;

    /** The apiName. */
    private String apiName;

    /** The data. */
    private ReferralExchangeDetails data = new ReferralExchangeDetails();

    /**
     * Gets the token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token.
     *
     * @param token
     *            the new token
     */
    public void setToken( final String token ) {
        this.token = token;
    }

    /**
     * Gets the version.
     *
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the new version
     */
    public void setVersion( final String version ) {
        this.version = version;
    }

    /**
     * Gets the apiName.
     *
     * @return the apiName
     */
    @JsonProperty( "api" )
    public String getApiName() {
        return apiName;
    }

    /**
     * Sets the apiName.
     *
     * @param apiName
     *            the new apiName
     */
    public void setApiName( final String api ) {
        this.apiName = api;
    }

    /**
     * Gets the data.
     *
     * @return the data
     */
    public ReferralExchangeDetails getData() {
        return data;
    }

    /**
     * Sets the data.
     *
     * @param data
     *            the new data
     */
    public void setData( final ReferralExchangeDetails data ) {
        this.data = data;
    }

    /**
     * @return the testFlag
     */
    @JsonProperty( "test" )
    public boolean isTestFlag() {
        return testFlag;
    }

    /**
     * Sets the test flag.
     *
     * @param testFlag the testFlag to set
     */
    public void setTestFlag( final boolean testFlag ) {
        this.testFlag = testFlag;
    }
}
