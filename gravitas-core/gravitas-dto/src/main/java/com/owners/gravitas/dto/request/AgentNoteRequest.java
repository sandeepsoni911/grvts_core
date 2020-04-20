package com.owners.gravitas.dto.request;

import org.hibernate.validator.constraints.NotBlank;

/**
 * The Class AgentOpportunityRequest.
 *
 * @author amits
 */
public class AgentNoteRequest {

    /** The details. */
    @NotBlank( message = "error.agent.note.details.required" )
    private String details;

    /**
     * Gets the details.
     *
     * @return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets the details.
     *
     * @param details
     *            the new details
     */
    public void setDetails( String details ) {
        this.details = details;
    }
}
