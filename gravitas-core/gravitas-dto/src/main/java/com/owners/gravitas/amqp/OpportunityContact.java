package com.owners.gravitas.amqp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.BaseDTO;
import com.owners.gravitas.dto.Contact;

/**
 * The Class OpportunityContact.
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class OpportunityContact extends BaseDTO {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6511722544142579632L;

    /** The contacts. */
    private Contact primaryContact;

    /** The agent email. */
    private String agentEmail;

    /**
     * Gets the primary contact.
     *
     * @return the primaryContact
     */
    public Contact getPrimaryContact() {
        return primaryContact;
    }

    /**
     * Sets the primary contact.
     *
     * @param primaryContact
     *            the primaryContact to set
     */
    public void setPrimaryContact( final Contact primaryContact ) {
        this.primaryContact = primaryContact;
    }

    /**
     * Gets the agent email.
     *
     * @return the agentEmail
     */
    public String getAgentEmail() {
        return agentEmail;
    }

    /**
     * Sets the agent email.
     *
     * @param agentEmail
     *            the agentEmail to set
     */
    public void setAgentEmail( final String agentEmail ) {
        this.agentEmail = agentEmail;
    }
}
