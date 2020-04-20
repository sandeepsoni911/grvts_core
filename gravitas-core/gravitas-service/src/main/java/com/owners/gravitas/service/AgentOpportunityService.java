/*
 *
 */
package com.owners.gravitas.service;

import java.util.Map;

import com.amazonaws.services.machinelearning.model.PredictResult;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Class AgentOpportunityService.
 *
 * @author harshads
 */
public interface AgentOpportunityService {

    /**
     * Gets the opportunity by id.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @return the opportunity by id
     */
    Opportunity getOpportunityById( String agentId, String opportunityId );

    /**
     * Save opportunity.
     *
     * @param agentId
     *            the agent id
     * @param agentEmail
     *            the agent email
     * @param opportunity
     *            the opportunity
     * @param contactEmail
     *            the contact email
     * @return the post response
     */
    PostResponse saveOpportunity( String agentId, String agentEmail, Opportunity opportunity, String contactEmail );

    /**
     * Patch opportunity.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param updateParams
     *            the update params
     * @param opportunity
     *            the opportunity
     * @param contactEmail
     *            the contact email
     * @return the opportunity
     */
    Opportunity patchOpportunity( String agentId, String opportunityId, Map< String, Object > updateParams,
            Opportunity opportunity, String contactEmail );

    /**
     * Gets the agent new opportunities count.
     *
     * @param agentId
     *            the agent id
     * @return the agent new opportunities count
     */
    int getAgentNewOpportunitiesCount( String agentId );

    /**
     * Checks for agent claimed opportunity with open tasks.
     *
     * @param agentId
     *            the agent id
     * @return true, if successful
     */
    boolean hasAgentClaimedOpportunityWithOpenTasks( String agentId );

    /**
     * Does agent has opportunities beyond limit.
     *
     * @param agentId
     *            the agent id
     * @param fromDtm
     *            the from dtm is dtm of before 7 days.
     * @return the int
     */
    int getOpportunityCountByDays( String agentId, long fromDtm );

    /**
     * Gets the opportunity score.
     *
     * @param records
     *            the records
     * @return the opportunity score
     */
    PredictResult getOpportunityScore( Map< String, String > records );

    /**
     * Patch opportunity.
     *
     * @param agentId
     *            the agent id
     * @param agentEmail
     *            the agent email
     * @param opportunityId
     *            the opportunity id
     * @param opportunityData
     *            the opportunity data
     */
    void patchOpportunity( String agentId, String agentEmail, String opportunityId,
            Map< String, Object > opportunityData );

    /**
     * Checks for incomplete action flow.
     *
     * @param agentEmail
     *            the agent email
     * @return true, if successful
     */
    boolean hasIncompleteActionFlow( String agentEmail );

    /***
     *
     * @param agentId
     * @param opportunityId
     * @param updateParams
     * @param opportunity
     * @param contactEmail
     * @return
     */
    Opportunity patchOpportunityForCTA( String agentId, String opportunityId, Opportunity opportunity,
            String contactEmail );

    /**
     * Gets the top price.
     *
     * @param priceRange
     *            the price range
     * @return the top price
     */
    int getTopPrice( String priceRange );
    
    /**
     * Patch opportunity stage.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param stage
     *            the stage
     * @return the opportunity
     */
    Opportunity patchOpportunityStage(String agentId, String opportunityId, String stage, Opportunity opportunity);
    
}
