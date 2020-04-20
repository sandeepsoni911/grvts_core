package com.owners.gravitas.business;

import java.util.Map;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.dto.LeadDetailsList;
import com.owners.gravitas.dto.crm.response.CRMLeadResponse;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.dto.request.LeadDetailsRequest;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.dto.request.OclLeadRequest;
import com.owners.gravitas.dto.request.RequestTypeLeadRequest;
import com.owners.gravitas.dto.request.SyncUpRequest;
import com.owners.gravitas.dto.response.ClaimLeadResponse;
import com.owners.gravitas.dto.response.LeadDetailsResponse;
import com.owners.gravitas.dto.response.LeadLayoutResponse;
import com.owners.gravitas.dto.response.LeadResponse;
import com.owners.gravitas.dto.response.MenuConfigResponse;

/**
 * The Interface LeadBusinessService.
 *
 * @author vishwanathm
 */
public interface LeadBusinessService {

    /**
     * Creates the buyer lead by de-dupliacate check with CRM, and send the
     * after hour notification if lead request is made after configured office
     * hours.
     *
     * @param request
     *            the lead request
     * @param isAfterHoursNotificationRequired
     *            the is after hours notification required
     * @param allowAutoAssignment
     *            the allow auto assignment
     * @return the lead response
     */
    public LeadResponse createLead( final LeadRequest request, final boolean isAfterHoursNotificationRequired,
            final Boolean allowAutoAssignment, final String contactStatusId );

    /**
     * Creates the buyer lead by de-dupliacate check with CRM, and send the
     * after hour notification if lead request is made after configured office
     * hours.
     *
     * @param request
     *            new lead request.
     * @param isAfterHoursNotificationRequired
     *            the after hours notification required
     * @return the lead response
     */
    public LeadResponse createLead( LeadRequest request, boolean isAfterHoursNotificationRequired, final String contactStatusId );

    /**
     * Creates the lead with default request types.
     *
     * @param request
     *            the request
     * @return the lead response
     */
    public LeadResponse createLead( RequestTypeLeadRequest request, final String contactStatusId );

    /**
     * Gets the CRM lead.
     *
     * @param leadId
     *            the lead id
     * @return the CRM lead
     */
    CRMLeadResponse getCRMLead( String leadId );

    /**
     * Creates the unbounce lead.
     *
     * @param jsonData
     *            the json data
     * @return the lead response
     */
    LeadResponse createUnbounceLead( String jsonData, final String contactStatusId );

    /**
     * Handle lead change.
     *
     * @param leadSource
     *            the lead source
     */
    void handleLeadChange( LeadSource leadSource );

    /**
     * Convert lead to opportunity.
     *
     * @param leadId
     *            the lead id
     * @return the string
     */
    String convertLeadToOpportunity( String leadId );

    /**
     * Gets the lead id by request type and email.
     *
     * @param recordType
     *            the record type
     * @param email
     *            the email
     * @return the lead id by request type and email
     */
    String getLeadIdByRequestTypeAndEmail( String recordType, String email );

    /**
     * Handle lead create.
     *
     * @param leadSource
     *            the lead source
     */
    void handleLeadCreate( LeadSource leadSource );

    /**
     * Send notification.
     *
     * @param request
     *            the request
     * @param isAfterHoursNotificationRequired
     *            the is after hours notification required
     */
    void sendAfterHoursNotification( LeadRequest request, boolean isAfterHoursNotificationRequired );

    /**
     * Creates the lead.
     *
     * @param leadSource
     *            the lead source
     */
    void createorUpdateLead( LeadSource leadSource, String recordTypeName );
    
    /**
     * Prepares the lead details list of the public leads
     * 
     * @param leadDetailsRequest
     * s
     * @return LeadDetailsList
     */
    LeadDetailsList getAvailableLeads( LeadDetailsRequest leadDetailsRequest );
    
    /**
     * Prepares the lead details list of the my leads
     * 
     * @param leadDetailsRequest
     * s
     * @return LeadDetailsList
     */
    LeadDetailsList getAllMyLeads( LeadDetailsRequest leadDetailsRequest );
        
    /**
     * 
     * @param crmId
     * @return
     */
    LeadDetailsResponse getLeadDetails( String crmId );
    
    /**
     * 
     * @param crmId
     * @return
     */
    LeadDetailsResponse getOpportunityDetails( String crmId );
    
    /**
     * 
     * @param crmId
     * @return
     */
    ClaimLeadResponse claimLead( String crmId );
    
    /**
     * 
     * @param source
     * @return
     */
    LeadLayoutResponse getLeadLayout( String source );
    
    /**
     * 
     * @param source
     * @return
     */
    LeadLayoutResponse saveLeadLayout( String source, String layout );
    
    /**
     * 
     * @param champignId,
     *            sessionId
     * @return
     */
    void enterValueIntoRedisCache( Map< String, Object > request );
    
    /**
     * 
     * @param role,
     *            role
     * @return MenuConfigResponse
     */
    MenuConfigResponse getMenuConfigurationOfRole(String role);

    /**
     * @return MenuConfigResponse
     */
	public MenuConfigResponse getListOfMenusForAllRole();
	
	/**
     * @param request
     * @param isAfterHoursNotificationRequired
     * @return
     */
    LeadResponse createLead( OclLeadRequest request, boolean isAfterHoursNotificationRequired );
    
    /**
     * @param request
     * @return
     */
    public LeadResponse createOclLead( final GenericLeadRequest request, final String contactStatusId);
    
    
    /**
     * Creates the ocl lead.
     *
     * @param leadSource
     *            the lead source
     * @param allowAutoAssignment
     *            the allow auto assignment
     * @param isRetryTrigger
     *            the is retry trigger
     * @return the lead response
     */
    LeadResponse createOclLead( final LeadSource leadSource, final Boolean allowAutoAssignment, final String contactStatusId ); 
    
    /**
     * @return MenuConfigResponse
     */
	public MenuConfigResponse getListOfRolesForAllRoutePath();
	
	/**
	 * @param request
	 */
	void syncUpLead(SyncUpRequest request, boolean isCrone);
	
}
