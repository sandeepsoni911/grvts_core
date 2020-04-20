package com.owners.gravitas.dao;

import java.util.Map;

import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Interface OpportunityDao.
 *
 * @author harshads
 */
public interface AgentOpportunityDao {

    /**
     * Gets the opportunity by id.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @return the opportunity by id
     */
    public Opportunity getOpportunityById( String agentId, String opportunityId );

    /**
     * Save opportunity.
     *
     * @param agentId
     *            the agent id
     * @param opportunity
     *            the opportunity
     * @return the post response
     */
    PostResponse saveOpportunity( String agentId, Opportunity opportunity );

    /**
     * Update opportunity.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param opportunity
     *            the opportunity
     */
    void updateOpportunity( String agentId, String opportunityId, Opportunity opportunity );

    /**
     * Patch opportunity.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     * @param opportunity
     *            the opportunity
     * @return the opportunity
     */
    Opportunity patchOpportunity( String agentId, String opportunityId, Map< String, Object > opportunity );

    /**
     * Gets the agent opportunites.
     *
     * @param agentId
     *            the agent id
     * @return the agent opportunites
     */
    Map< String, Object > getAgentOpportunites( String agentId );

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
     *            the from dtm
     * @return the int
     */
    int getOpportunityCountByDays( String agentId, long fromDtm );

    /**
     * Gets the opportunities from time.
     *
     * @param agentId
     *            the agent id
     * @param fromDtm
     *            the from dtm
     * @return the opportunities from time
     */
    Map< String, Map > getOpportunitiesFromTime( final String agentId, final long fromDtm );
}
