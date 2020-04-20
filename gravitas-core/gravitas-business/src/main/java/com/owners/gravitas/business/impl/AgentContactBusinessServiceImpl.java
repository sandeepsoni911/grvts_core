package com.owners.gravitas.business.impl;

import static com.owners.gravitas.enums.ErrorCode.OPPORTUNITY_CONTACT_NOT_FOUND;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.amqp.OpportunityContact;
import com.owners.gravitas.business.AgentContactBusinessService;
import com.owners.gravitas.business.builder.CRMContactBuilder;
import com.owners.gravitas.business.builder.ContactBuilder;
import com.owners.gravitas.domain.Contact;
import com.owners.gravitas.domain.PhoneNumber;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.dto.crm.request.CRMContactRequest;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.enums.PhoneNumberType;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.AgentContactService;
import com.owners.gravitas.service.ContactService;
import com.owners.gravitas.service.SearchService;

@Service( "agentContactBusinessService" )
public class AgentContactBusinessServiceImpl implements AgentContactBusinessService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( AgentContactBusinessServiceImpl.class );

    /** The search service. */
    @Autowired
    private SearchService searchService;

    /** The agent contact service. */
    @Autowired
    private AgentContactService agentContactService;

    /** The contact service. */
    @Autowired
    private ContactService contactService;

    /** The crm contact builder. */
    @Autowired
    private CRMContactBuilder crmContactBuilder;

    /** The contact builder. */
    @Autowired
    private ContactBuilder contactBuilder;

    /**
     * Update contact.
     *
     * @param agentId
     *            the agent id
     * @param contactId
     *            the contact id
     * @param updateRequest
     *            the update request
     * @return the agent response
     */
    @Override
    public AgentResponse updateContact( final String agentId, final String contactId,
            final Map< String, String > updateRequest ) {
        if (!updateRequest.isEmpty()) {
            final Search contactSearch = searchService.searchByContactId( contactId );
            if (contactSearch == null) {
                throw new ApplicationException(
                        "Opportunity contact not found for agentId: " + agentId + " contactId :" + contactId,
                        OPPORTUNITY_CONTACT_NOT_FOUND );
            }
            final com.owners.gravitas.domain.Contact primaryContact = agentContactService.getContactById( agentId,
                    contactId );
            // update firebase
            final Map< String, Object > requestMap = new HashMap<>();
            requestMap.putAll( updateRequest );
            requestMap.put( "lastModifiedDtm", new Date().getTime() );

            if (null != requestMap.get( "phone" )) {
                final List< PhoneNumber > phoneNumbers = new ArrayList<>();
                final PhoneNumber primaryNumber = new PhoneNumber();
                primaryNumber.setType( PhoneNumberType.PRIMARY );
                primaryNumber.setNumber( String.valueOf( requestMap.get( "phone" ) ) );
                phoneNumbers.add( primaryNumber );
                requestMap.remove( "phone" );
                requestMap.put( "phoneNumbers", phoneNumbers );
            }

            agentContactService.patchContact( agentId, contactId, requestMap );
            LOGGER.info( "firebase updated successfully for " + contactId );

            // update crm
            final Map< String, Object > crmContactDetails = contactService
                    .findContactByEmail( contactSearch.getContactEmail() );
            final CRMContactRequest crmContact = crmContactBuilder.convertTo( crmContactDetails );
            crmContact.setFirstName( getValue( crmContact.getFirstName(), updateRequest.get( "firstName" ) ) );
            crmContact.setLastName( getValue( crmContact.getLastName(), updateRequest.get( "lastName" ) ) );
            crmContact.setPreferredContactMethod(
                    getValue( crmContact.getPreferredContactMethod(), updateRequest.get( "preferredContactMethod" ) ) );
            crmContact.setPreferredContactTime(
                    getValue( crmContact.getPreferredContactTime(), updateRequest.get( "preferredContactTime" ) ) );
            crmContact.setPhone( getValue( crmContact.getPhone(), updateRequest.get( "phone" ) ) );
            contactService.updateContact( crmContact, primaryContact.getCrmId() );
            LOGGER.info( "CRM updated successfully for " + primaryContact.getCrmId() );

        }
        return new AgentResponse( contactId );
    }

    /**
     * Handle buyer contact changes made in CRM.
     *
     * @param opportunityContact
     *            the opportunity contact
     */
    @Override
    public void handleOpportunityContactChange( final OpportunityContact opportunityContact ) {
        if (opportunityContact.getAgentEmail() != null) {
            final Search contactSearch = searchService
                    .searchByContactEmail( opportunityContact.getPrimaryContact().getEmails().get( 0 ) );
            if (null != contactSearch) {
                final Contact contact = agentContactService.getContactById( contactSearch.getAgentId(), contactSearch.getContactId() );
                agentContactService.patchContact( contactSearch.getAgentId(), contactSearch.getContactId(),
                        contactBuilder.convertToMap( opportunityContact.getPrimaryContact(), contact ) );
            }
        }
    }

    /**
     * Gets the value.
     *
     * @param oldValue
     *            the old value
     * @param newValue
     *            the new value
     * @return the value
     */
    private String getValue( final String oldValue, final String newValue ) {
        String returnValue = oldValue;
        if (StringUtils.isNotEmpty( newValue )) {
            returnValue = newValue;
        }
        return returnValue;
    }
}
