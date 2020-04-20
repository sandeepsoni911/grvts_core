package com.owners.gravitas.business;

import java.util.Map;

import com.owners.gravitas.amqp.OpportunityContact;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.OpportunityDetails;
import com.owners.gravitas.dto.crm.response.CRMResponse;
import com.owners.gravitas.dto.crm.response.OpportunityResponse;
import com.owners.gravitas.dto.request.AgentMeetingRequest;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.dto.request.OpportunityRequest;
import com.owners.gravitas.dto.request.ScheduleTourMeetingRequest;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.OpportunityDiscountResponse;
import com.owners.gravitas.enums.RecordType;

// TODO: Auto-generated Javadoc
/**
 * The Interface OpportunityBusinessService.
 *
 * @author vishwanathm
 */
public interface OpportunityBusinessService {

    /**
     * Creates the opportunity.
     *
     * @param opportunityRequest
     *            the opportunity request
     * @param oppRecordType
     *            the opp record type
     * @return the OpportunityResponse response
     */
    OpportunityResponse createSellerOpportunity( OpportunityRequest opportunityRequest, RecordType oppRecordType );

    /**
     * Update opportunity.
     *
     * @param leadRequest
     *            the lead request
     * @param opportunityId
     *            the opportunity id
     * @return the opportunity response
     */
    OpportunityResponse updateOpportunity( LeadRequest leadRequest, String opportunityId );

    /**
     * Gets the opportunity details.
     *
     * @param opportunityId
     *            the opportunity id
     * @return the opportunity details
     */
    OpportunitySource getOpportunity( String opportunityId );

    /**
     * Gets the opportunities.
     *
     * @param agentEmail
     *            the agent email
     * @return the opportunities
     */
    CRMResponse getAgentOpportunities( String agentEmail );

    /**
     * Find contact by id.
     *
     * @param id
     *            the id
     * @param findBy
     *            the find by
     * @return the opportunity contact
     */
    OpportunityContact findContactById( String id, String findBy );

    /**
     * Process buyer request.
     *
     * @param request
     *            the request
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     */
    void processBuyerRequest( LeadRequest request, String agentId, String opportunityId );

    /**
     * Update ocl opportunity.
     *
     * @param crmOpportunityId
     *            the crm opportunity id
     * @param loanPhase
     *            the loan phase
     * @param loanNumber
     *            the loan number
     * @param oppRecordTypeId
     *            the opp record type id
     */
    void updateOclOpportunity( final String crmOpportunityId, final String loanPhase, final int loanNumber,
            final String oppRecordTypeId );

    /**
     * Gets the opportunity create details.
     *
     * @param opportunityId
     *            the opportunity id
     * @return the opportunity create details
     */
    OpportunitySource getOpportunityCreateDetails( final String opportunityId );

    /**
     * Gets the contact details.
     *
     * @param opportunityId
     *            the opportunity id
     * @return the contact details
     */
    Contact getContactDetails( final String opportunityId );

    /**
     * Gets the opportunity by crm id.
     *
     * @param crmId
     *            the crm id
     * @return the opportunity by crm id
     */
    OpportunityDetails getOpportunityByCRMId( String crmId );

    /**
     * Update opportunity.
     *
     * @param opportunityId
     *            the opportunity id
     * @param request
     *            the request
     */
    void updateOpportunity( String opportunityId, Map< String, Object > request );

    /**
     * Gets the opportunity id by record type and loan number.
     *
     * @param recordType
     *            the record type
     * @param loanNumber
     *            the loan number
     * @return the opportunity id by record type and loan number
     */
    String getOpportunityIdByRecordTypeAndLoanNumber( final String recordType, final int loanNumber );

    /**
     * Gets the opportunity id by record type and email.
     *
     * @param recordType
     *            the record type
     * @param email
     *            the email
     * @return the opportunity id by record type and email
     */
    String getOpportunityIdByRecordTypeAndEmail( final String recordType, final String email );

    /**
     * Forward to referral exchange.
     *
     * @param opportunitySource
     *            the opportunity source
     */
    void forwardToReferralExchange( OpportunitySource opportunitySource, Map< String, Object >... maps );

    /**
     * Update CRM opportunity.
     *
     * @param request
     *            the request
     * @param opportunityCrmId
     *            the opportunity crm id
     */
    void updateCRMOpportunity( Map< String, Object > patchRequest, String opportunityCrmId );

    /**
     * Assign opportunity to agent.
     *
     * @param opportunitySource
     *            the opportunity source
     * @param agentEmail
     *            the agent email
     */
    void assignOpportunityToAgent( OpportunitySource opportunitySource, String agentEmail );

    /**
     * Forward to referral exchange.
     *
     * @param opportunity
     *            CRM ID
     *            the opportunity source
     * @param message
     *            the message
     */
    void forwardToReferralExchange( String crmId, String message );

    /**
     * Reject Agent for Opportunity
     *
     * @param opportunity
     *            CRM ID
     *            the opportunity source
     * @param request
     *            the request
     */
    void rejectOpportunity( String crmId, Map< String, Object > request );

    /**
     * 
     * @param crmId
     * @param taskRequest
     * @return
     */
    AgentResponse createScheduledMeeting( String crmId, AgentMeetingRequest meetingRequest );
    
    /**
     * Method to create schedule tour meetings.
     * 
     * @param crmId
     * @param taskRequest
     * @return
     */
    public AgentResponse createScheduledTourMeetings( String crmId, ScheduleTourMeetingRequest meetingRequest );
    
    /**
     * Method to create skip meeting action flow
     * 
     * @param insideSalesEmail
     * @param crmId
     * @return BaseResponse
     */
    BaseResponse createSkipMeetingActionFlow( String insideSalesEmail, String crmId );
    
    /**
     * send app download reminder to buyer
     * 
     * @param crmOpportunityId
     * @return
     */
    void sendAppDownloadReminder( OpportunitySource opportunitySource );
    
    /**
     * @param fbId
     * @return
     */
    OpportunityDiscountResponse getDiscountForOpportunity( final String agentFbId, final String fbId );
    
    /**
     * Update stage to in contact for fb DB sf.
     *
     * @param agentId
     *            the agent id
     * @param opportunityId
     *            the opportunity id
     */
    void updateStageToInContact( final String agentId, final String opportunityId );
}
