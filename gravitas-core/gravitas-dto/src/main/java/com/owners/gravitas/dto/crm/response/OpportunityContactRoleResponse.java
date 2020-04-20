/**
 *
 */
package com.owners.gravitas.dto.crm.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.owners.gravitas.dto.response.BaseResponse;

/**
 * The Class OpportunityContactRoleResponse.
 *
 * @author amits
 */
public class OpportunityContactRoleResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6607028478432142881L;

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
