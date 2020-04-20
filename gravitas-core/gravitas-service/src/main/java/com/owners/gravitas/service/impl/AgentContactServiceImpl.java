package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.ACTION_OBJ;
import static com.owners.gravitas.constants.Constants.AGENT_ID;
import static com.owners.gravitas.constants.Constants.ENTITY_ID;
import static com.owners.gravitas.enums.ActionEntity.CONTACT;
import static com.owners.gravitas.enums.ActionType.CREATE;
import static com.owners.gravitas.enums.ActionType.UPDATE;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.annotation.Audit;
import com.owners.gravitas.dao.AgentContactDao;
import com.owners.gravitas.domain.Contact;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.service.AgentContactService;

/**
 * The Class AgentOpportunityServiceImpl.
 *
 * @author harshads
 */
@Service
public class AgentContactServiceImpl implements AgentContactService {

    /** The agent contact dao. */
    @Autowired
    private AgentContactDao agentContactDao;

    /**
     * Save opportunity.
     *
     * @param agentId
     *            the agent id
     * @param contact
     *            the contact
     * @return the post response
     */
    @Override
    @Audit( type = CREATE, entity = CONTACT, args = { AGENT_ID, ACTION_OBJ } )
    public PostResponse saveContact( final String agentId, final Contact contact ) {
        return agentContactDao.saveContact( agentId, contact );
    }

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
    @Override
    @Audit( type = UPDATE, entity = CONTACT, args = { AGENT_ID, ENTITY_ID, ACTION_OBJ } )
    public void patchContact( final String agentId, final String contactId, final Map< String, Object > requestMap ) {
        agentContactDao.patchContact( agentId, contactId, requestMap );
    }

    /**
     * Gets the contact by id.
     *
     * @param agentId
     *            the agent id
     * @param contactId
     *            the contact id
     * @return the contact by id
     */
    @Override
    public Contact getContactById( final String agentId, final String contactId ) {
        return agentContactDao.getContactById( agentId, contactId );
    }
}
