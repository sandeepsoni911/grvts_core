package com.owners.gravitas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.PotentialOpportunityAssignmentLog;
import com.owners.gravitas.repository.OpportunityAssignmentLogRepository;
import com.owners.gravitas.service.OpportunityAssignmentLogService;

/**
 * The Class OpportunityAssignmentLogServiceImpl.
 *
 * @author amits
 */
@Service
@Transactional
public class OpportunityAssignmentLogServiceImpl implements OpportunityAssignmentLogService {

    /** The agent lookup log repository. */
    @Autowired
    private OpportunityAssignmentLogRepository opportunityAssignmentLogRepository;

    /**
     * Save.
     *
     * @param agentLookupLog
     *            the agent lookup log
     * @return the action log
     */
    @Override
    public PotentialOpportunityAssignmentLog save( final PotentialOpportunityAssignmentLog agentLookupLog ) {
        return opportunityAssignmentLogRepository.save( agentLookupLog );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.OpportunityAssignmentLogService#
     * updateAgentAssignmentAudit(java.lang.String, java.lang.String)
     */
    @Override
    public void updateAgentAssignmentAudit( final String crmId, final String agentEmail, final String currentUser ) {
        opportunityAssignmentLogRepository.updateAgentAssignmentAudit( crmId, agentEmail,currentUser );
    }
}
