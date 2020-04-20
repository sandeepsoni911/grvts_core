package com.owners.gravitas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.AgentStatistics;
import com.owners.gravitas.repository.AgentStatisticsRepository;
import com.owners.gravitas.service.AgentStatisticsService;

/**
 * The Class AgentAnalyticsServiceImpl.
 *
 * @author madhavk
 */
@Service
@Transactional( readOnly = true )
public class AgentStatisticsServiceImpl implements AgentStatisticsService {

    /** The agent analytics repository. */
    @Autowired
    private AgentStatisticsRepository agentStatisticsRepository;

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentStatisticsService#
     * getAgentScoreStatistics(java.lang.String)
     */
    @Override
    public List< AgentStatistics > getAgentScoreStatistics( final String email ) {
        return agentStatisticsRepository.findAgentStatistics( email );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentStatisticsService#save(java.util.List)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public void save( List< AgentStatistics > agentAnalyticsList ) {
        agentStatisticsRepository.save( agentAnalyticsList );

    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentStatisticsService#save(com.owners.
     * gravitas.domain.entity.AgentStatistics)
     */
    @Override
    public void save( final AgentStatistics agentAnalytics ) {
        agentStatisticsRepository.save( agentAnalytics );
    }

}
