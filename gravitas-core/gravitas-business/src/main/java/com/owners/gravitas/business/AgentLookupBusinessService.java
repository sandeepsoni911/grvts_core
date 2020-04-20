package com.owners.gravitas.business;

import java.util.Map;

import com.owners.gravitas.amqp.OpportunitySource;

/**
 * The Interface AgentLookupBusinessService.
 *
 * @author amits
 */
public interface AgentLookupBusinessService {

    /**
     * Gets the best performing agents by zipcode V 1.
     *
     * @param zipcode
     *            the zipcode
     * @param opportunityId
     *            the opportunity id
     * @param state
     *            the state
     * @return the best performing agents by zipcode V 1
     */
    Map< String, Object > getBestPerformingAgentsByZipcodeV1( String zipcode, String opportunityId, String state );

    /**
     * Gets the most eligible agent.
     *
     * @param opportunitySource
     *            the opportunity source
     * @param toAutoAssign
     *            the to auto assign
     * @param zipCode
     *            the zip code
     * @param state
     *            the state
     * @return the most eligible agent
     */
    Map< String, Object > getMostEligibleAgent( final OpportunitySource opportunitySource, boolean toAutoAssign,
            String zipCode, String state );
}
