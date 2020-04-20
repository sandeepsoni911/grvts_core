package com.owners.gravitas.service;

import java.util.Map;

import com.owners.gravitas.domain.Contact;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Class AgentOpportunityService.
 *
 * @author harshads
 */
public interface AgentContactService {

    /**
     * Save contact.
     *
     * @param agentId
     *            the agent id
     * @param contact
     *            the contact
     * @return the post response
     */
    PostResponse saveContact( String agentId, Contact contact );

    /**
     * Gets the contact by id.
     *
     * @param agentId
     *            the agent id
     * @param contactId
     *            the contact id
     * @return the contact by id
     */
    Contact getContactById( String agentId, String contactId );

    /**
     * Patch contact.
     *
     * @param agentId
     *            the agent id
     * @param contactId
     *            the contact id
     * @param requestMap
     *            the request map
     */
    void patchContact( String agentId, String contactId, Map< String, Object > requestMap );
}
