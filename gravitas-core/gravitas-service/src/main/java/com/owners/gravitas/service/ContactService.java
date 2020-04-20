package com.owners.gravitas.service;

import java.util.Collection;
import java.util.Map;

import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.Opportunity;
import com.owners.gravitas.dto.crm.request.CRMContactRequest;
import com.owners.gravitas.dto.crm.response.ContactResponse;

/**
 * The Interface ContactService.
 *
 * @author vishwanathm
 */
public interface ContactService {

    /**
     * Find contact by id.
     *
     * @param id
     *            the id
     * @param findBy
     *            the find by
     * @return the contact
     */
    Map< String, Object > findContactById( String id, String findBy );

    /**
     * Creates the contact.
     *
     * @param crmContact
     *            the contact
     * @return the contact response
     */
    ContactResponse createContact( CRMContactRequest crmContact );

    /**
     * Update contact.
     *
     * @param contactRequest
     *            the contact request
     * @param contactId
     *            the contact id
     * @return the contact response
     */
    ContactResponse updateContact( CRMContactRequest contactRequest, String contactId );

    /**
     * Patch contact.
     *
     * @param patchRequest
     *            the patch request
     * @param contactId
     *            the contact id
     */
    void patchContact( Map< String, Object > patchRequest, String contactId );

    /**
     * Find contact by email.
     *
     * @param contactEmail
     *            the contact email
     * @return the map
     */
    Map< String, Object > findContactByEmail( String contactEmail );

    /**
     * Gets the contact by opportunity id.
     *
     * @param opportunityId
     *            the opportunity id
     * @return the contact by opportunity id
     */
    com.owners.gravitas.domain.Contact getContactByOpportunityId( String opportunityId );

    /**
     * Find contact id by opportunity id.
     *
     * @param crmOpportunityId
     *            the crm opportunity id
     * @return the string
     */
    String findContactIdByOpportunityId( String crmOpportunityId );

    /**
     * Find by opportunities.
     *
     * @param opportunities
     *            the opportunities
     * @return the contact
     */
    Contact findByOpportunities( Collection< Opportunity > opportunities );
    
    /**
     * Find by crmId.
     *
     * @param crmId
     *            the crmId
     * @return the email
     */
    String findEmailByCrmId(String crmId);
}
