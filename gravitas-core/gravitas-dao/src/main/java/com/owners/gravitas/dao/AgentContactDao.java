package com.owners.gravitas.dao;

import java.util.Map;

import com.owners.gravitas.domain.Contact;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Interface AgentContactDao.
 *
 * @author harshads
 */
public interface AgentContactDao {

    /**
     * Save contact.
     *
     * @param agentId
     *            the agent id
     * @param contact
     *            the contact
     */
    PostResponse saveContact( String agentId, Contact contact );

    /**
     * Update contact.
     *
     * @param agentId
     *            the agent id
     * @param contactId
     *            the contact id
     * @param contact
     *            the contact
     */
    void updateContact( String agentId, String contactId, Contact contact );

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
     * Gets the contact by opportunity id.
     *
     * @param opportunityId
     *            the opportunity id
     * @param agentId
     *            the agent id
     * @return the contact by opportunity id
     */
    Contact getContactByOpportunityId( String opportunityId, String agentId );
}
