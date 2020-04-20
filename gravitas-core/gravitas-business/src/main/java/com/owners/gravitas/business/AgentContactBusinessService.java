package com.owners.gravitas.business;

import java.util.Map;

import com.owners.gravitas.amqp.OpportunityContact;
import com.owners.gravitas.dto.response.AgentResponse;

public interface AgentContactBusinessService {

    /**
     * Update contact.
     *
     * @param agentId
     *            the agent id
     * @param contactId
     *            the contact id
     * @param updateRequest
     *            the contact update request
     * @return the agent response
     */
    AgentResponse updateContact( String agentId, String contactId, Map< String, String > updateRequest );

    /**
     * Handle opportunity contact change.
     *
     * @param opportunityContact the opportunity contact
     */
    void handleOpportunityContactChange( OpportunityContact opportunityContact );

}
