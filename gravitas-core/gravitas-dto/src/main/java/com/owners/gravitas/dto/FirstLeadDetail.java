package com.owners.gravitas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class FirstLeadDetail.
 * 
 * @author javeedsy
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class FirstLeadDetail extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3767447741473487818L;

    /** The source. */
    private String leadSource;

    /** The lead source url. */
    private String leadSourceUrl;

    /** The created on. */
    private Long createdOn;

    /**
     * @return the leadSource
     */
    public String getLeadSource() {
        return leadSource;
    }

    /**
     * @param leadSource
     *            the leadSource to set
     */
    public void setLeadSource( final String leadSource ) {
        this.leadSource = leadSource;
    }

    /**
     * @return the leadSourceUrl
     */
    public String getLeadSourceUrl() {
        return leadSourceUrl;
    }

    /**
     * @param leadSourceUrl
     *            the leadSourceUrl to set
     */
    public void setLeadSourceUrl( final String leadSourceUrl ) {
        this.leadSourceUrl = leadSourceUrl;
    }

    /**
     * @return the createdOn
     */
    public Long getCreatedOn() {
        return createdOn;
    }

    /**
     * @param createdOn
     *            the createdOn to set
     */
    public void setCreatedOn( final Long createdOn ) {
        this.createdOn = createdOn;
    }

}
