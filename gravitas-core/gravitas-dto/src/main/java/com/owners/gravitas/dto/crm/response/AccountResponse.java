/**
 * 
 */
package com.owners.gravitas.dto.crm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.owners.gravitas.dto.response.BaseResponse;

/**
 * The Class AccountResponse.
 *
 * @author harshads
 */
public class AccountResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4212003223251209297L;

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
     * @param id
     *            the id to set
     */
    @JsonProperty( "id" )
    public void setId( final String id ) {
        this.id = id;
    }

}
