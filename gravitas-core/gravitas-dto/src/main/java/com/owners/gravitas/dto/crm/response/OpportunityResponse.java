package com.owners.gravitas.dto.crm.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.owners.gravitas.dto.response.BaseResponse;

/**
 * The Class OpportunityResponse.
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class OpportunityResponse extends BaseResponse{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2213352654156773401L;
    
    /** The id. */
    private String id;

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
     * @param id the id to set
     */
    @JsonProperty("id")
    public void setId( String id ) {
        this.id = id;
    }

}
