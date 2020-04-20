package com.owners.gravitas.service;

import com.owners.gravitas.dto.request.AgentSuggestedPropertyRequest;
import com.owners.gravitas.dto.response.PostResponse;

// TODO: Auto-generated Javadoc
/**
 * The Interface AgentSuggestedPropertyService.
 *
 * @author javeedsy
 */
public interface AgentSuggestedPropertyService {

    /**
     * Save agent suggested property.
     *
     * @param suggestedProperty
     *            the suggested Property details
     * @return the post response
     */
    PostResponse saveAgentSuggestedProperty( final AgentSuggestedPropertyRequest suggestedProperty,
            final String agentId );
}
