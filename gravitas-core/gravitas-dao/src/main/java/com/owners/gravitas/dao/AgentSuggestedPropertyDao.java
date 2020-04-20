/*
 *
 */
package com.owners.gravitas.dao;

import com.owners.gravitas.dto.request.AgentSuggestedPropertyRequest;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Interface AgentSuggestedPropertyDao.
 *
 * @author javeedsy
 */
public interface AgentSuggestedPropertyDao {

    /**
     * Save agent suggested property for provided by agent.
     *
     * @param suggestedProperty
     *            the suggested Property details
     * @return the post response
     */
    PostResponse saveAgentSuggestedProperty( final AgentSuggestedPropertyRequest suggestedProperty,
            final String agentId );
}
