package com.owners.gravitas.business;

/**
 * The Interface AgentPerformanceMetricsTaskImpl.
 *
 * @author ankusht
 */
public interface AgentPerformanceMetricsTask {

    /**
     * Compute and save performance metrics.
     *
     * @param agentId
     *            the agent id
     */
    void computeAndSavePerformanceMetrics( final String agentId );
}
