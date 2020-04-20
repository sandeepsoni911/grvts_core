package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.domain.entity.PotentialOpportunityAssignmentLog;

/**
 * The Interface OpportunityAssignmentLogRepository.
 */
@Repository
public interface OpportunityAssignmentLogRepository extends JpaRepository< PotentialOpportunityAssignmentLog, String > {

    /** The Constant UPDATE_ASSIGNED_AGENT_STATUS. */
    public static final String UPDATE_ASSIGNED_AGENT_STATUS = "UPDATE gr_potential_agent_assignment_log oaa INNER JOIN ( SELECT * FROM gr_potential_opportunity_assignment_log WHERE CRM_ID =:crmId ORDER BY CREATED_ON DESC LIMIT 1) AS oa ON oa.ID = oaa.OPPORTUNITY_ASSIGNMENT_ID SET oaa.status = 'assigned' , oaa.last_updated_by=:currentUser , oaa.last_updated_on=current_timestamp WHERE oaa.agent_email =:agentEmail";

    /**
     * Mark agent opportunity status.
     *
     * @param crmId
     *            the crm id
     * @param agentEmail
     *            the agent email
     */
    @Modifying( clearAutomatically = true )
    @Query( value = UPDATE_ASSIGNED_AGENT_STATUS, nativeQuery = true )
    public void updateAgentAssignmentAudit( @Param( "crmId" ) String crmId, @Param( "agentEmail" ) String agentEmail,
            @Param( "currentUser" ) String currentUser );
}
