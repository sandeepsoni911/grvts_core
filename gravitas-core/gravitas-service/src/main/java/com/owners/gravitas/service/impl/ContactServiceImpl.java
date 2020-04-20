package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.CRMQuery.GET_CONTACT_BY_ID;
import static com.owners.gravitas.constants.CRMQuery.GET_CONTACT_DETAILS_BY_EMAIL_ID;
import static com.owners.gravitas.constants.CRMQuery.GET_CONTACT_ID_BY_OPPORTUNITY_ID;
import static com.owners.gravitas.constants.Constants.EMAIL;
import static com.owners.gravitas.constants.Constants.ID;
import static com.owners.gravitas.constants.Constants.QUERY_PARAM_PRIMARY_CONTACT;
import static com.owners.gravitas.enums.ErrorCode.RESULT_NOT_FOUND;
import static com.owners.gravitas.util.RestUtil.buildRequest;
import static com.owners.gravitas.util.RestUtil.createHttpHeader;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.owners.gravitas.dao.AgentContactDao;
import com.owners.gravitas.dao.SearchDao;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.Opportunity;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.crm.request.CRMContactRequest;
import com.owners.gravitas.dto.crm.response.CRMAccess;
import com.owners.gravitas.dto.crm.response.ContactResponse;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.repository.ContactRepository;
import com.owners.gravitas.service.CRMAuthenticatorService;
import com.owners.gravitas.service.CRMQueryService;
import com.owners.gravitas.service.ContactService;
import com.owners.gravitas.util.RestUtil;

/**
 * The Class ContactServiceImpl.
 *
 * @author vishwanathm
 */
@Service
public class ContactServiceImpl implements ContactService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( ContactServiceImpl.class );

    /** restTemplate for making rest call to owners. */
    @Autowired
    @Qualifier( "crmRestAuthenticator" )
    private CRMAuthenticatorService crmAuthenticator;

    /** restTemplate for making rest call to owners. */
    @Autowired
    private RestTemplate restTemplate;

    /** The crm service. */
    @Autowired
    private CRMQueryService crmQueryService;

    /** The search dao. */
    @Autowired
    private SearchDao searchDao;

    /** The contact url. */
    @Value( value = "${salesforce.contact.url}" )
    private String contactUrl;

    /** The agent contact dao. */
    @Autowired
    private AgentContactDao agentContactDao;

    /** The contact repository. */
    @Autowired
    private ContactRepository contactRepository;

    /**
     * Find contact by id.
     *
     * @param id
     *            the id
     * @param findBy
     *            the find by
     * @return the contact
     */
    @Override
    public Map< String, Object > findContactById( final String id, final String findBy ) {
        final QueryParams params = new QueryParams();
        LOGGER.info( "get contact called, source was " + findBy + " for id " + id );
        params.add( "findBy", findBy );
        params.add( ID, id );
        return crmQueryService.findOne( GET_CONTACT_BY_ID, params );
    }

    /**
     * Creates the contact.
     *
     * @param crmContact
     *            the contact
     * @return the contact response
     */
    @Override
    public ContactResponse createContact( final CRMContactRequest crmContact ) {
        final CRMAccess crmAccess = crmAuthenticator.authenticate();
        final String reqUrl = crmAccess.getInstanceUrl() + contactUrl;
        final ContactResponse contactResponse = restTemplate.exchange( reqUrl, HttpMethod.POST,
                buildRequest( createHttpHeader( crmAccess.getAccessToken() ), crmContact ), ContactResponse.class )
                .getBody();
        LOGGER.debug( "contact created " + contactResponse.getId() );
        return contactResponse;
    }

    /**
     * Update contact.
     *
     * @param contactRequest
     *            the contact request
     * @param contactId
     *            the contact id
     * @return the contact response
     */
    @Override
    public ContactResponse updateContact( final CRMContactRequest contactRequest, final String contactId ) {
        final CRMAccess crmAccess = crmAuthenticator.authenticate();
        final String reqUrl = crmAccess.getInstanceUrl() + contactUrl + contactId;
        LOGGER.debug( "sending update contact request to CRM." );
        return restTemplate.exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( RestUtil.createHttpHeader( crmAccess.getAccessToken() ), contactRequest ),
                ContactResponse.class ).getBody();
    }

    /**
     * Find contact by email.
     *
     * @param contactEmail
     *            the contact email
     * @return the map
     */
    @Override
    public Map< String, Object > findContactByEmail( final String contactEmail ) {
        final QueryParams params = new QueryParams();
        params.add( EMAIL, contactEmail );
        return crmQueryService.findOne( GET_CONTACT_DETAILS_BY_EMAIL_ID, params );
    }

    /**
     * Gets the contact by opportunity id.
     *
     * @param opportunityId
     *            the opportunity id
     * @return the contact by opportunity id
     */
    @Override
    public com.owners.gravitas.domain.Contact getContactByOpportunityId( final String opportunityId ) {
        final Search search = searchDao.searchByOpportunityId( opportunityId );
        return agentContactDao.getContactByOpportunityId( search.getContactId(), search.getAgentId() );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ContactService#findContactIdByOpportunityId(
     * java.lang.String)
     */
    @Override
    public String findContactIdByOpportunityId( final String crmOpportunityId ) {
        LOGGER.info( "getting contact id for opportunity " + crmOpportunityId );
        final QueryParams params = new QueryParams();
        params.add( ID, String.valueOf( crmOpportunityId ) );
        final Map< String, Object > response = crmQueryService.findOne( GET_CONTACT_ID_BY_OPPORTUNITY_ID, params );
        final Object contactId = response.get( QUERY_PARAM_PRIMARY_CONTACT );
        if (contactId == null) {
            final String msg = "Contact not found for given opportunity id +" + crmOpportunityId;
            LOGGER.debug( msg );
            throw new ApplicationException( msg, RESULT_NOT_FOUND );
        }
        return contactId.toString();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ContactService#patchContact(java.util.Map,
     * java.lang.String)
     */
    @Override
    public void patchContact( final Map< String, Object > patchRequest, final String contactId ) {
        LOGGER.debug( "sending patch contact request to CRM." );
        final CRMAccess crmAccess = crmAuthenticator.authenticate();
        final String reqUrl = crmAccess.getInstanceUrl() + contactUrl + contactId;
        restTemplate.exchange( reqUrl, HttpMethod.PATCH,
                buildRequest( RestUtil.createHttpHeader( crmAccess.getAccessToken() ), patchRequest ),
                ContactResponse.class ).getBody();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.ContactService#findByOpportunities(java.util.
     * Set)
     */
    @Override
    public com.owners.gravitas.domain.entity.Contact findByOpportunities(
            final Collection< Opportunity > opportunities ) {
        return contactRepository.findByOpportunities( new HashSet<>( opportunities ) );
    }

	@Override
	public String findEmailByCrmId(String crmId) {
		Contact contact = contactRepository.findByCrmId(crmId);
		if(contact != null){
			return contact.getEmail();
		}
		return null;
	}
    
    
}
