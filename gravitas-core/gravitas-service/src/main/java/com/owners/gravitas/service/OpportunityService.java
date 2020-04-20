package com.owners.gravitas.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.ResourceAccessException;

import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.Opportunity;
import com.owners.gravitas.dto.crm.request.CRMOpportunityRequest;
import com.owners.gravitas.dto.crm.response.CRMOpportunityResponse;
import com.owners.gravitas.dto.crm.response.OpportunityResponse;
import com.owners.gravitas.enums.RecordType;

/**
 * The Interface OpportunityService.
 *
 * @author vishwanathm
 */
public interface OpportunityService {

    /**
     * Creates the opportunity.
     *
     * @param crmOpportunity
     *            the opportunity
     * @return the opportunity response
     */
    @Retryable( maxAttempts = 3, backoff = @Backoff( delay = 1000 ), include = { ResourceAccessException.class } )
    OpportunityResponse createOpportunity( CRMOpportunityRequest crmOpportunity );

    /**
     * Verify existing opportunity.
     *
     * @param mlsID
     *            the mls id
     */
    @Retryable( maxAttempts = 3, backoff = @Backoff( delay = 1000 ), include = { ResourceAccessException.class } )
    void isOpportunityExists( String mlsID );

    /**
     * Checks if is opportunity exists for record type and email.
     *
     * @param emailId
     *            the email id
     * @param type
     *            the type
     * @return true, if is opportunity exists for record type and email
     */
    @Retryable( maxAttempts = 3, backoff = @Backoff( delay = 1000 ), include = { ResourceAccessException.class } )
    boolean isOpportunityExistsForRecordTypeAndEmail( String emailId, RecordType type );

    /**
     * Update opportunity.
     *
     * @param crmOpportunityRequest
     *            the crm opportunity request
     * @param opprortunityId
     *            the opprortunity id
     * @param allowAutoAssignment
     *            the allow auto assignment
     * @return the CRM opportunity response
     */
    @Retryable( maxAttempts = 3, backoff = @Backoff( delay = 1000 ), include = { ResourceAccessException.class } )
    CRMOpportunityResponse updateOpportunity( CRMOpportunityRequest crmOpportunityRequest, String opprortunityId,
            final boolean allowAutoAssignment );

    /**
     * Gets the opportunity.
     *
     * @param opportunityId
     *            the opportunity id
     * @return the opportunity
     */
    @Retryable( maxAttempts = 3, backoff = @Backoff( delay = 1000 ), include = { ResourceAccessException.class } )
    CRMOpportunityResponse getOpportunity( String opportunityId );

    /**
     * Patch opportunity.
     *
     * @param opportunityRequest
     *            the opportunity request
     * @param opprortunityId
     *            the opprortunity id
     */
    @Retryable( maxAttempts = 3, backoff = @Backoff( delay = 1000 ), include = { ResourceAccessException.class } )
    void patchOpportunity( Map< String, Object > opportunityRequest, String opprortunityId );

    /**
     * Gets the opportunity id by record type id and loan number.
     *
     * @param recordTypeId
     *            the record type
     * @param loanNumber
     *            the loan number
     * @return the opportunity id by record type and loan number
     */
    String getOpportunityIdByRecordTypeIdAndLoanNumber( String recordTypeId, int loanNumber );

    /**
     * Gets the opportunity id by record type id and email.
     *
     * @param recordTypeId
     *            the opportunity record type id
     * @param email
     *            the email
     * @return the opportunity id by record type id and email
     */
    String getOpportunityIdByRecordTypeIdAndEmail( String recordTypeId, String email );

    /**
     * Gets the agent opportunities.
     *
     * @param assignedAgentId
     *            the assigned agent id
     * @return the agent opportunities
     */
    List< Opportunity > getAgentOpportunities( String assignedAgentId );

    /**
     * Gets the title closing company by opportunity id.
     *
     * @param opportunityId
     *            the opportunity id
     * @return the title closing company by opportunity id
     */
    String getTitleClosingCompanyByOpportunityId( String opportunityId );

    /**
     * Gets the opportunity by fb id.
     *
     * @param opportunityId
     *            the opportunity id
     * @param isDeleted
     *            the is deleted
     * @return the opportunity by fb id
     */
    Opportunity getOpportunityByFbId( String opportunityId, boolean isDeleted );

    /**
     * Find assigned agent emails by selected stages count.
     *
     * @param selectedStages
     *            the selected stages
     * @param emailsIn
     *            the emails in
     * @return the list
     */
    List< String > findAssignedAgentEmailsBySelectedStagesCount( Collection< String > selectedStages,
            Collection< String > emailsIn );

    /**
     * Find by assigned date after and agent emails in.
     *
     * @param thresholdPeriod
     *            the threshold period
     * @param agentEmails
     *            the agent emails
     * @return the list
     */
    List< AgentDetails > findAgentsByAssignedDate( DateTime thresholdPeriod, Collection< String > agentEmails );

    /**
     * Count by assigned agent id and assigned date before and deleted false.
     *
     * @param agentEmail
     *            the agent email
     * @param assignedDate
     *            the assigned date
     * @return the int
     */
    int getAgentOppCountByAssignedDate( String agentEmail, DateTime assignedDate );

    /**
     * Save.
     *
     * @param opportunity
     *            the opportunity
     */
    void save( Opportunity opportunity );

    /**
     * Gets the opportunity by fb id.
     *
     * @param fbOppId
     *            the fb opp id
     * @return the opportunity by fb id
     */
    Opportunity getOpportunityByFbId( String fbOppId );

    /**
     * Find response time by opportunity ids.
     *
     * @param fbOpportunityIds
     *            the fb opportunity ids
     * @param fbAssignedDate
     *            the fb assigned date
     * @return the list
     */
    List< Long > findResponseTimeByOpportunityIds( final Collection< String > fbOpportunityIds,
            final DateTime fbAssignedDate );

    /**
     * Find response time by assigned agent and opportunity type.
     *
     * @param agentEmail
     *            the agent email
     * @param opportunityType
     *            the opportunity type
     * @param from
     *            the from
     * @param to
     *            the to
     * @return the list
     */
    List< Long > findResponseTimeByAssignedAgentAndOpportunityType( String agentEmail, String opportunityType,
            DateTime from, DateTime to );

    /**
     * Find opportunity by crm id.
     *
     * @param crmId
     *            the crm id
     * @return the opportunity v1
     */
    Opportunity findOpportunityByCrmId( String crmId );

    /**
     * Find assigned agent emails by selected stages count for default.
     *
     * @param selectedStages
     *            the selected stages
     * @param emailsIn
     *            the emails in
     * @return the list
     */
    List< String > findAssignedAgentEmailsBySelectedStagesCountForDefault( Collection< String > selectedStages,
            Collection< String > emailsIn );
    
    /**
     * @param oppPriceRange
     * @return
     */
    String[] getOpportunityStartAndEndPriceRange( String oppPriceRange );
}
