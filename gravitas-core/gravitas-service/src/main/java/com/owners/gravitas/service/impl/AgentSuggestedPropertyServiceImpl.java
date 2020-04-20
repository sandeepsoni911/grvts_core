package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.ACTION_OBJ;
import static com.owners.gravitas.constants.Constants.AGENT_ID;
import static com.owners.gravitas.enums.ActionEntity.SUGGESTED_PROPERTY;
import static com.owners.gravitas.enums.ActionType.CREATE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.annotation.Audit;
import com.owners.gravitas.dao.AgentSuggestedPropertyDao;
import com.owners.gravitas.dto.request.AgentSuggestedPropertyRequest;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.service.AgentSuggestedPropertyService;

// TODO: Auto-generated Javadoc
/**
 * The Class AgentSuggestedPropertyServiceImpl.
 *
 * @author javeedsy
 */
@Service
public class AgentSuggestedPropertyServiceImpl implements AgentSuggestedPropertyService {

    /** The agent suggested property dao. */
    @Autowired
    private AgentSuggestedPropertyDao agentSuggestedPropertyDao;

    /**
     * Save agent task.
     *
     * @param task
     *            the task
     * @param agentId
     *            the agent id
     * @return the post response
     */
    @Override
    @Audit( type = CREATE, entity = SUGGESTED_PROPERTY, args = { ACTION_OBJ, AGENT_ID } )
    public PostResponse saveAgentSuggestedProperty( final AgentSuggestedPropertyRequest suggestedProperty,
            final String agentId ) {
        return agentSuggestedPropertyDao.saveAgentSuggestedProperty( suggestedProperty, agentId );
    }

}
