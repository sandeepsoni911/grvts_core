package com.owners.gravitas.service;

import java.util.List;

import com.owners.gravitas.domain.entity.AgentStatistics;

/**
 * The Interface AgentStatisticsService.
 *
 * @author madhavk
 */
public interface AgentStatisticsService {

    /**
     * Gets the agent score statistics.
     *
     * @param email
     *            the email
     * @return the agent score statistics
     */
    List< AgentStatistics > getAgentScoreStatistics( final String email );

    /**
     * Save.
     *
     * @param agentStatisticsList
     *            the agent statistics list
     */
    void save( List< AgentStatistics > agentStatisticsList );

    /**
     * Save.
     *
     * @param agentStatistics
     *            the agent statistics
     */
    void save( AgentStatistics agentStatistics );

}
