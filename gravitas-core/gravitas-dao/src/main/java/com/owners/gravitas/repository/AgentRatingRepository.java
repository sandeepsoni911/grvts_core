package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.AgentRating;

/**
 * The Interface AgentRatingRepository.
 * 
 * @author ankusht
 */
public interface AgentRatingRepository extends JpaRepository< AgentRating, String > {

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
