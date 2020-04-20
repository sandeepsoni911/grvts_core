package com.owners.gravitas.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactAttribute;
import com.owners.gravitas.domain.entity.ObjectType;
import com.owners.gravitas.domain.entity.Opportunity;

/**
 * The Interface ContactEntityService.
 *
 * @author shivamm
 */
public interface ContactEntityService {

    public void addOrUpdateAttribute( final Contact contact, final ObjectType objectType, final String key,
            final String attrValue );
    
    public void addOrUpdateJsonAttribute( final Contact contact, final ObjectType objectType, final String key,
            final String attrValue );
    
    public void addContactAttribute(final Set<ContactAttribute> attributes, final String key, final String value,
            final ObjectType objectType);
    /**
     * Save.
     *
     * @param prospect
     *            the contact
     * @return the contact
     */
    Contact save( Contact contact );
    
    /**
     * Save.
     *
     * @param prospect
     *            the contact
     * @return the contact
     */
    List<Contact> save( List<Contact> contacts );

    /**
     * Find by crm id.
     *
     * @param crmId
     *            the crm id
     * @return the contact
     */
    Contact findByCrmId( String crmId );

    /**
     * Find by email and type.
     *
     * @param email
     *            the email
     * @param recordType
     *            the record type
     * @return the contact
     */
    Contact getContact( String email, String recordType );

    /**
     * Gets the revenue of agents.
     *
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @param rankFilter
     *            the rank filter
     * @return the revenue of agents
     */
    List< Object[] > getRevenueOfAgents( String fromDate, String toDate, int rankFilter );

    /**
     * Gets the revenue of agents.
     *
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @return the revenue of agents
     */
    List< Object[] > getRevenueOfAgents( String fromDate, String toDate );

    /**
     * Gets the revenue of agent.
     *
     * @param agentEmail
     *            the agent email
     * @param fromDate
     *            the from date
     * @param toDate
     *            the to date
     * @return the revenue of agent
     */
    List< Object[] > getRevenueOfAgent( String agentEmail, String fromDate, String toDate );

    /**
     * Gets the contact by owners com id.
     *
     * @param ownersComId
     *            the owners com id
     * @return the contact by owners com id
     */
    Contact getContactByOwnersComId( String ownersComId );

    /**
     * Gets the outbound attempt contacts.
     *
     * @param stage
     *            the stage
     * @param objectType
     *            the object type
     * @return the outbound attempt contacts
     */
    List< Contact > getOutboundAttemptContacts( String stage, String objectType );

    /**
     * Gets the contact by email id.
     *
     * @param email
     *            the email id
     * @return the contact by email id
     */
    Contact getContactByEmail( String email );

    /**
     * Gets the contact by crm id.
     *
     * @param crmId
     *            the crm id
     * @return the contact by crm id
     */
    Contact getContactByCrmId( String crmId );

    /**
     * Gets the contact by opportunities.
     *
     * @param opportunities
     *            the opportunities
     * @return the contact by opportunities
     */
    Contact getContactByOpportunities( Set< Opportunity > opportunities );

    /***
     * Gets the contact by email and type.
     *
     * @param email
     *            the email
     * @param type
     *            the type
     * @return the contact by email and type
     */
    Contact getContactByEmailAndType( String email, String type );

    /**
     * Find save search status.
     *
     * @param contactId
     *            the contact id
     * @return the list
     */
    List< Object[] > findSaveSearchStatus( final String contactId );

    /**
     * Find contact by fb opportunity id
     * 
     * @param fbOpportunityId
     * @return
     */
    Contact getContactByFbOpportunityId( String fbOpportunityId );

    /**
     * Find all contact by fb opportunity id
     * 
     * @param fbOpportunityId
     * @return
     */
    List< Contact > getAllContactByFbOpportunityId( Set< String > fbOpportunityIds );

    /**
     * find All Contacts By Fb OpportunityId
     * 
     * @param contactId
     * @return
     */
    List< Object[] > findAllContactsByFbOpportunityId( Set< String > fbOpportunityId );

    /**
     * Get user full name
     * 
     * @param contact
     * @return
     */
    String getUserName( Contact contact );

    /**
     * Gets the contact attribute.
     *
     * @param contactAttributeSet
     *            the contact attribute set
     * @param attributeName
     *            the attribute name
     * @return the contact attribute
     */
    String getContactAttribute( Set< ContactAttribute > contactAttributeSet, String attributeName );
    
    /**
     * Get all the public leads 
     * 
     * @param page
     * @param size
     * @param direction
     * @param property
     * @return Page object GR_CONTACT
     */
    Page< Contact > getAllPublicLeads( int page, int size, String direction,
            String property );
    
    /**
    * Get all my leads 
    * 
    * @param page
    * @param size
    * @param direction
    * @param property
    * @return Page object GR_CONTACT
    */
   Page< Contact > getAllMyLeads( int page, int size, String direction, String property, String emailId, String type );
    
    /**
     * Find by crmId.
     * @param crmId
     *            the crmId
     * @return the Contact
     */
    Contact getCrmIdAndDeletedIsFalse( final String crmId, final String type );
    
    /**
     * Find Opportunity by crmId.
     * @param crmId
     *            the crmId
     * @return the Contact
     */
    Contact getOpportunityByCrmId(final String crmId);
    
    /**
     * Method to fetch the additional lead attributes for given set of CRM Ids
     * 
     * @param crmIdList
     * @return
     */
    List< Object[] > getLeadAttributesForCrmId( Set< String > crmIdList );
    
    /**
     * Method to fetch the additional Opportunity attributes for given set of CRM Ids
     * 
     * @param crmIdList
     * @return
     */
    List< Object[] > getOpportunityAttributesForCrmId( Set< String > crmIdList );
    
    /**
     * Get Contact Attributes
     * 
     * @param contact
     * @param key
     * @param objectTypeValue
     * @return
     */
    String getContactAttribute( final Contact contact, final String key, final String objectTypeValue );
    
    /**
     * Get leads By	OwnersComId	IsNull
     * 
     * @param page
     * @param size
     * @param direction
     * @param property
     * @return Page object GR_CONTACT
     */
    List< Contact > findLeadsByOwnersComIdIsNull( String fromDate,String toDate );
    
    /**
     * Get leads By	OwnersComId	IsNull
     * 
     * @param page
     * @param size
     * @param direction
     * @param property
     * @return Page object GR_CONTACT
     */
    List< Contact > findAllLeadsByOwnersComIdIsNull();
}
