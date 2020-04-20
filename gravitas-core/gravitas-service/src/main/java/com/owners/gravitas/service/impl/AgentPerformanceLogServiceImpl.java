package com.owners.gravitas.service.impl;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.AgentPerformanceLog;
import com.owners.gravitas.repository.AgentPerformanceLogRepository;
import com.owners.gravitas.service.AgentPerformanceLogService;

/**
 * The Class AgentPerformanceLogServiceImpl.
 *
 * @author ankusht
 */
@Service
@Transactional( readOnly = true )
public class AgentPerformanceLogServiceImpl implements AgentPerformanceLogService {

    /** The agent performance metrics repository. */
    @Autowired
    private AgentPerformanceLogRepository agentPerformanceLogRepository;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.AgentPerformanceLogService#savePerformanceLog
     * (com.owners.gravitas.domain.entity.AgentPerformanceLog)
     */
    @Override
    @Transactional( propagation = REQUIRED )
    public AgentPerformanceLog savePerformanceLog( final AgentPerformanceLog agentPerformanceLog ) {
        return agentPerformanceLogRepository.save( agentPerformanceLog );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.AgentPerformanceLogService#
     * findLatestMetricByAgentFbId(java.lang.String,
     * org.springframework.data.domain.Pageable)
     */
    @Override
    public List< AgentPerformanceLog > findLatestMetricByAgentFbId( final String agentId, final Pageable pageable ) {
        return agentPerformanceLogRepository.findLatestMetricByAgentFbId( agentId, pageable ).getContent();
    }
}
