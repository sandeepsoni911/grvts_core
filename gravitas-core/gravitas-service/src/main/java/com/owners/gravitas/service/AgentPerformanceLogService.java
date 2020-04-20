package com.owners.gravitas.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.owners.gravitas.domain.entity.AgentPerformanceLog;

/**
 * The Interface AgentPerformanceLogService.
 *
 * @author ankusht
 */
public interface AgentPerformanceLogService {

    /**
     * Save performance log.
     *
     * @param agentPerformanceLog
     *            the agent performance log
     * @return the agent performance log
     */
    AgentPerformanceLog savePerformanceLog( final AgentPerformanceLog agentPerformanceLog );

    /**
     * Find latest metric by agent fb id.
     *
     * @param agentId
     *            the agent id
     * @param pageable
     *            the pageable
     * @return the list
     */
    List< AgentPerformanceLog > findLatestMetricByAgentFbId( final String agentId, final Pageable pageable );
}
