package com.owners.gravitas.business;

/**
 * The Interface AgentAnalyicsBusinessService.
 *
 * @author vishwanathm
 */
public interface AgentAnalyicsBusinessService {

    /**
     * Start agent uid analytics.
     */
    void startAgentAnalytics();

    /**
     * Map firebase and CRM opportunity ids.
     */
    void opportunityMappingAnalytics();
}
