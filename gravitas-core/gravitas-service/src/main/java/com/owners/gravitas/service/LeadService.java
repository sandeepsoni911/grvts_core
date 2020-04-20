package com.owners.gravitas.service;

import java.util.Date;
import java.util.Map;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.ResourceAccessException;

import com.amazonaws.services.machinelearning.model.PredictResult;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.dto.crm.request.CRMLeadRequest;
import com.owners.gravitas.dto.crm.response.CRMLeadResponse;
import com.owners.gravitas.dto.request.SyncUpRequest;

/**
 * The Interface LeadService.
 *
 * @author vishwanathm
 */
public interface LeadService {

    /**
     * Creates the buyer lead by calling saleforce api.
     *
     * @param crmLeadRequest
     *            lead request
     * @return the CRM lead response
     */
    @Retryable( maxAttempts = 3, backoff = @Backoff( delay = 1000 ), include = { ResourceAccessException.class } )
    CRMLeadResponse createLead( CRMLeadRequest crmLeadRequest );

    /**
     * Creates the buyer lead by calling saleforce api.
     *
     * @param crmLeadRequest
     *            the crm lead request
     * @param allowAutoAssignment
     *            the allow auto assignment
     * @return the CRM lead response
     */
    @Retryable( maxAttempts = 3, backoff = @Backoff( delay = 1000 ), include = { ResourceAccessException.class } )
    CRMLeadResponse createLead( CRMLeadRequest crmLeadRequest, boolean allowAutoAssignment );

    /**
     * Update lead.
     *
     * @param crmLeadRequest
     *            the crm lead request
     * @param leadId
     *            the lead id
     * @param allowAutoAssignment
     *            the allow auto assignment
     * @return the CRM lead response
     */
    @Retryable( maxAttempts = 3, backoff = @Backoff( delay = 1000 ), include = { ResourceAccessException.class } )
    CRMLeadResponse updateLead( CRMLeadRequest crmLeadRequest, final String leadId, final boolean allowAutoAssignment );

    /**
     * Gets the lead.
     *
     * @param leadId
     *            the lead id
     * @return the lead
     */
    @Retryable( maxAttempts = 3, backoff = @Backoff( delay = 1000 ), include = { ResourceAccessException.class } )
    CRMLeadResponse getLead( String leadId );

    /**
     * Update lead.
     *
     * @param crmMap
     *            the crm map
     * @param leadId
     *            the lead id
     * @return the CRM lead response
     */
    @Retryable( maxAttempts = 3, backoff = @Backoff( delay = 1000 ), include = { ResourceAccessException.class } )
    CRMLeadResponse updateLead( Map< String, Object > crmMap, String leadId );

    /**
     * Gets the mortgage lead id by email and record type.
     *
     * @param emailId
     *            the email id
     * @param recordTypeId
     *            the record type id
     * @return the mortgage lead id by email and record type
     */
    String getLeadIdByEmailAndRecordTypeId( String emailId, String recordTypeId );

    /**
     * Convert lead to opportunity.
     *
     * @param leadId
     *            the lead id
     * @return the string
     */
    String convertLeadToOpportunity( final String leadId );

    /**
     * Update lead.
     *
     * @param crmMap
     *            the crm map
     * @param leadId
     *            the lead id
     * @param allowAutoAssignment
     *            the assignement rule
     * @return the CRM lead response
     */
    CRMLeadResponse updateLead( Map< String, Object > crmMap, String leadId, boolean allowAutoAssignment );

    /**
     * Delete lead.
     *
     * @param leadId
     *            the lead id
     */
    void deleteLead( String leadId );

    /**
     * Gets the lead score.
     *
     * @param records
     *            the records
     * @return the lead score
     */
    PredictResult getLeadScore( Map< String, String > records );

    /**
     * @param contact
     * @return
     */
    boolean isValidPhoneNumber( Contact contact );
    
    /**
     * @param fromDate
     * @param toDate
     * @return
     */
    Date[] getFromAndToDates( final SyncUpRequest request , boolean isCrone );
    
    /**
     * @return
     */
    Date[] getFromAndToDatesBasedOnHours();

}
