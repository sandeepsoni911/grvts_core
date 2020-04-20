package com.owners.gravitas.service;

import com.owners.gravitas.domain.entity.PotentialOpportunityAssignmentLog;

// TODO: Auto-generated Javadoc
/**
 * The Interface OpportunityAssignmentLogService.
 */
public interface OpportunityAssignmentLogService {

    /**
     * Save.
     *
     * @param opportunityAssignmentLog
     *            the opportunity assignment log
     * @return the opportunity assignment log
     */
    PotentialOpportunityAssignmentLog save( PotentialOpportunityAssignmentLog opportunityAssignmentLog );

    /**
     * Mark agent opportunity status.
     *
     * @param crmId
     *            the crm id
     * @param agentEmail
     *            the agent email
     */
    void updateAgentAssignmentAudit( String crmId, String agentEmail, String user );

}
