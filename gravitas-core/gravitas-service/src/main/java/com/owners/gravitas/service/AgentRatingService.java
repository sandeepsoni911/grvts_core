package com.owners.gravitas.service;

import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.AgentRating;

/**
 * The Interface AgentRatingService.
 * 
 * @author ankusht
 */
public interface AgentRatingService {

    /**
     * Save.
     *
     * @param agentRating
     *            the agent rating
     * @return the agent rating
     */
    AgentRating save( AgentRating agentRating );

    /**
     * Find one.
     *
     * @param id
     *            the id
     * @return the agent rating
     */
    AgentRating findOne( String id );

    /**
     * Find by crm id and stage and client email and agent details.
     *
     * @param crmId
     *            the crm id
     * @param stage
     *            the stage
     * @param clientEmail
     *            the client email
     * @param agentDetails
     *            the agent details
     * @return the agent rating
     */
    AgentRating findByCrmIdAndStageAndClientEmailAndAgentDetails( String crmId, String stage, String clientEmail,
            AgentDetails agentDetails );
}
