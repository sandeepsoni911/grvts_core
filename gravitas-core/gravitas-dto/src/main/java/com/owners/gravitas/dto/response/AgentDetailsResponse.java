/**
 *
 */
package com.owners.gravitas.dto.response;

import com.owners.gravitas.dto.Agent;

/**
 * The Class AgentDetailsResponse.
 *
 * @author harshads
 */
public class AgentDetailsResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5880307885731426165L;

    /** The details. */
    private Agent details;

    /**
     * Gets the details.
     *
     * @return the details
     */
    public Agent getDetails() {
        return details;
    }

    /**
     * Sets the details.
     *
     * @param details
     *            the details to set
     */
    public void setDetails( final Agent details ) {
        this.details = details;
    }
}
