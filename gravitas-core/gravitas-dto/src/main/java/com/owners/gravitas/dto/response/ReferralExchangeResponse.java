package com.owners.gravitas.dto.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class ReferralExchangeResponse.
 *
 * @author vishwanathm
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class ReferralExchangeResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1559368509386197034L;

    /** The uuid. */
    private String uuid;

    private String configuration;

    private Map< String, List< String > > errors = new HashMap<>();

    /**
     * Gets the uuid.
     *
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the uuid.
     *
     * @param uuid
     *            the new uuid
     */
    public void setUuid( final String uuid ) {
        this.uuid = uuid;
    }

    /**
     * @return the configuration
     */
    public String getConfiguration() {
        return configuration;
    }

    /**
     * @param configuration
     *            the configuration to set
     */
    public void setConfiguration( final String configuration ) {
        this.configuration = configuration;
    }

    /**
     * @return the errors
     */
    public Map< String, List< String > > getErrors() {
        return errors;
    }

    /**
     * @param errors
     *            the errors to set
     */
    public void setErrors( final Map< String, List< String > > errors ) {
        this.errors = errors;
    }
}
