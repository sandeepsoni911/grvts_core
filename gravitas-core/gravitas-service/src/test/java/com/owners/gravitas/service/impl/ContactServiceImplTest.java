/**
 *
 */
package com.owners.gravitas.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.AgentContactDao;
import com.owners.gravitas.dao.SearchDao;
import com.owners.gravitas.domain.Contact;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.crm.request.CRMContactRequest;
import com.owners.gravitas.dto.crm.response.CRMAccess;
import com.owners.gravitas.dto.crm.response.ContactResponse;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.CRMAuthenticatorService;
import com.owners.gravitas.service.CRMQueryService;

/**
 * The Class ContactServiceImplTest.
 *
 * @author harshads
 */
public class ContactServiceImplTest extends AbstractBaseMockitoTest {

    /** The account service. */
    @InjectMocks
    ContactServiceImpl contactServiceImpl;

    /** restTemplate for making rest call to owners. */
    @Mock
    private CRMAuthenticatorService crmAuthenticator;

    /** restTemplate for making rest call to owners. */
    @Mock
    private RestTemplate restTemplate;

    /** The crm query service. */
    @Mock
    private CRMQueryService crmQueryService;

    /** The search dao. */
    @Mock
    private SearchDao searchDao;

    /** The agent contact dao. */
    @Mock
    private AgentContactDao agentContactDao;

    /**
     * Authenticate test.
     */
    @Test
    public void authenticateTest() {
        final ContactResponse contactResponse = new ContactResponse();
        contactResponse.setId( "112" );

        Mockito.when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( contactResponse, HttpStatus.OK ) );

        final ContactResponse contactResponse1 = contactServiceImpl.createContact( new CRMContactRequest() );

        Assert.assertEquals( contactResponse1.getId(), "112" );
    }

    /**
     * Test find contact by id.
     */
    @Test
    public void testFindContactById() {
        Mockito.when( crmQueryService.findOne( Mockito.anyString(), Mockito.any( QueryParams.class ) ) )
                .thenReturn( new HashMap< String, Object >() );
        final Map< String, Object > map = contactServiceImpl.findContactById( "id", "email" );
        Assert.assertNotNull( map );
    }

    /**
     * Test update contact.
     */
    @Test
    public void testUpdateContact() {
        final ContactResponse contactResponse = new ContactResponse();
        contactResponse.setId( "112" );
        Mockito.when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( contactResponse, HttpStatus.OK ) );
        final ContactResponse contactResponse1 = contactServiceImpl.updateContact( new CRMContactRequest(),
                "contactId" );

        Assert.assertEquals( contactResponse1.getId(), "112" );
    }

    /**
     * Test find contact by email.
     */
    @Test
    public void testFindContactByEmail() {
        Mockito.when( crmQueryService.findOne( Mockito.anyString(), Mockito.any( QueryParams.class ) ) )
                .thenReturn( new HashMap< String, Object >() );
        final Map< String, Object > map = contactServiceImpl.findContactByEmail( "email@email.com" );
        Assert.assertNotNull( map );
    }

    /**
     * Test get contact by opportunity id.
     */
    @Test
    public void testGetContactByOpportunityId() {
        final String opportunityId = "testOppId";
        final Search search = new Search();
        search.setAgentId( "testAgentId" );
        search.setOpportunityId( opportunityId );
        search.setContactId( "testContactId" );

        Mockito.when( searchDao.searchByOpportunityId( opportunityId ) ).thenReturn( search );
        Mockito.when( agentContactDao.getContactByOpportunityId( search.getContactId(), search.getAgentId() ) )
                .thenReturn( new Contact() );

        final Contact contact = contactServiceImpl.getContactByOpportunityId( opportunityId );
        Assert.assertNotNull( contact );
        Mockito.verify( searchDao ).searchByOpportunityId( opportunityId );
        Mockito.verify( agentContactDao ).getContactByOpportunityId( search.getContactId(), search.getAgentId() );
    }

    /**
     * Test get contact id by opportunity id.
     */
    @Test
    public void testFindContactIdByOpportunityId() {
        final String opportunityId = "testOppId";
        final Map< String, Object > map = new HashMap< String, Object >();
        map.put( "Primary_Contact__c", "test" );
        final QueryParams param = new QueryParams();
        param.add( "abc", "abc" );
        Mockito.when( crmQueryService.findOne( Mockito.anyString(), Mockito.any( QueryParams.class ) ) )
                .thenReturn( map );

        final String contactId = contactServiceImpl.findContactIdByOpportunityId( opportunityId );
        Assert.assertEquals( contactId, "test" );
        Mockito.verify( crmQueryService ).findOne( Mockito.anyString(), Mockito.any( QueryParams.class ) );
    }

    /**
     * Test patch contact
     */
    @Test
    public void testPatchContact() {
        final ContactResponse contactResponse = new ContactResponse();
        contactResponse.setId( "110" );
        Mockito.when( crmAuthenticator.authenticate() ).thenReturn( new CRMAccess() );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( contactResponse, HttpStatus.OK ) );
        contactServiceImpl.patchContact( new HashMap< String, Object >(), "test" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) );

    }

    /**
     * Test find contact id by opportunity id should throw exception.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testFindContactIdByOpportunityIdShouldThrowException() {
        final String crmOpportunityId = "oppId";
        when( crmQueryService.findOne( Mockito.anyString(), any( QueryParams.class ) ) ).thenReturn( new HashMap<>() );
        contactServiceImpl.findContactIdByOpportunityId( crmOpportunityId );
    }

}
