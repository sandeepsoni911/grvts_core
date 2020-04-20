package com.owners.gravitas.business.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.OpportunityContact;
import com.owners.gravitas.business.ActionLogBusinessService;
import com.owners.gravitas.business.AgentNotificationBusinessService;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.business.builder.ActionLogBuilder;
import com.owners.gravitas.business.builder.CRMContactBuilder;
import com.owners.gravitas.business.builder.ContactBuilder;
import com.owners.gravitas.business.builder.OpportunityContactBuilder;
import com.owners.gravitas.business.builder.OpportunitySourceBuilder;
import com.owners.gravitas.business.builder.PushNotificationBuilder;
import com.owners.gravitas.business.builder.domain.AgentBuilder;
import com.owners.gravitas.business.builder.domain.OpportunityBuilder;
import com.owners.gravitas.business.builder.domain.RequestBuilder;
import com.owners.gravitas.business.builder.domain.SearchBuilder;
import com.owners.gravitas.business.builder.request.ErrorSlackRequestBuilder;
import com.owners.gravitas.business.builder.response.ActionLogResponseBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.crm.request.CRMContactRequest;
import com.owners.gravitas.dto.response.AgentResponse;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.handler.OpportunityChangeHandlerFactory;
import com.owners.gravitas.service.AgentContactService;
import com.owners.gravitas.service.AgentInfoService;
import com.owners.gravitas.service.AgentNoteService;
import com.owners.gravitas.service.AgentOpportunityService;
import com.owners.gravitas.service.AgentRequestService;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.service.AgentTaskService;
import com.owners.gravitas.service.ContactService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.PushNotificationService;
import com.owners.gravitas.service.SearchService;
import com.owners.gravitas.service.SlackService;
import com.owners.gravitas.util.GravitasWebUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class AgentBusinessServiceImplTest.
 *
 * @author vishwanathm
 */
public class AgentContactBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The agent business service impl. */
    @InjectMocks
    private AgentContactBusinessServiceImpl agentBusinessServiceImpl;

    /** The agent service. */
    @Mock
    private AgentService agentService;

    /** The agent note service. */
    @Mock
    private AgentNoteService agentNoteService;

    /** The opportunity service. */
    @Mock
    private OpportunityService opportunityService;

    /** The opportunity business service. */
    @Mock
    private OpportunityBusinessService opportunityBusinessService;

    /** The agent info service. */
    @Mock
    private AgentInfoService agentInfoService;

    /** The agent builder. */
    @Mock
    private AgentBuilder agentBuilder;

    /** The search holder builder. */
    @Mock
    private SearchBuilder contactSearchBuilder;

    /** The search service. */
    @Mock
    private SearchService searchService;

    /** The agent opportunity service. */
    @Mock
    private AgentOpportunityService agentOpportunityService;

    /** The opportunity builder. */
    @Mock
    private OpportunityBuilder opportunityBuilder;

    /** The request builder. */
    @Mock
    private RequestBuilder requestBuilder;

    /** The agent contact service. */
    @Mock
    private AgentContactService agentContactService;

    /** The request service. */
    @Mock
    private AgentRequestService requestService;

    /** The push notification builder. */
    @Mock
    private PushNotificationBuilder pushNotificationBuilder;

    /** The push notification service. */
    @Mock
    private PushNotificationService pushNotificationService;
    /** The opportunity source builder. */
    @Mock
    private OpportunitySourceBuilder opportunitySourceBuilder;

    /** The opportunity contact builder. */
    @Mock
    private OpportunityContactBuilder opportunityContactBuilder;

    /** The opportunity change handler factory. */
    @Mock
    private OpportunityChangeHandlerFactory opportunityChangeHandlerFactory;

    /** The contact service. */
    @Mock
    private ContactService contactService;

    /** The crm contact builder. */
    @Mock
    private CRMContactBuilder crmContactBuilder;

    /** The agent push notification business service. */
    @Mock
    private AgentNotificationBusinessService agentPushNotificationBusinessService;

    /** The agent task service. */
    @Mock
    private AgentTaskService agentTaskService;

    /** The agent opportunity business service impl. */
    @Mock
    private AgentOpportunityBusinessServiceImpl agentOpportunityBusinessServiceImpl;

    /** The action log builder. */
    @Mock
    private ActionLogBuilder actionLogBuilder;

    /** The action log business service. */
    @Mock
    private ActionLogBusinessService actionLogBusinessService;

    /** The gravitas web util. */
    @Mock
    private GravitasWebUtil gravitasWebUtil;

    /** The action log response builder. */
    @Mock
    private ActionLogResponseBuilder actionLogResponseBuilder;

    /** The error slack request builder. */
    @Mock
    private ErrorSlackRequestBuilder errorSlackRequestBuilder;

    /** the slack service. */
    @Mock
    private SlackService slackService;

    /** The contact builder. */
    @Mock
    private ContactBuilder contactBuilder;

    /**
     * Test update contacts.
     */
    @Test
    public void testUpdateContacts() {
        final Map< String, String > request = new HashMap() {
            {
                put( "phone", "56456445" );
            }
        };
        Mockito.when( agentContactService.getContactById( Mockito.any(), Mockito.any() ) )
                .thenReturn( new com.owners.gravitas.domain.Contact() );
        Mockito.when( searchService.searchByContactId( Mockito.any() ) ).thenReturn( new Search() );
        Mockito.when( contactService.findContactByEmail( Mockito.any() ) ).thenReturn( new HashMap<>() );
        Mockito.when( crmContactBuilder.convertTo( Mockito.any() ) ).thenReturn( new CRMContactRequest() );
        String contactId = "testContactId";
        final AgentResponse response = agentBusinessServiceImpl.updateContact( "test", contactId, request );
        Assert.assertNotNull( response );
        assertEquals( contactId, response.getId() );
    }

    /**
     * Should throw exception if contact is not found.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void shouldThrowExceptionIfContactNotFound() {
        final Map< String, String > request = new HashMap() {
            {
                put( "phone", "56456445" );
            }
        };
        Mockito.when( searchService.searchByContactId( Mockito.any() ) ).thenReturn( null );
        agentBusinessServiceImpl.updateContact( "test", "test", request );
    }

    /**
     * Test handle opportunity contact change.
     */
    @Test
    public void testHandleOpportunityContactChange() {
        OpportunityContact opportunityContact = new OpportunityContact();
        opportunityContact.setAgentEmail( "test@test.com" );
        Contact contact = new Contact();
        contact.addEmail( "test@test.com" );
        opportunityContact.setPrimaryContact( contact );
        Search search = new Search();
        search.setAgentId( "test" );
        Mockito.when( searchService.searchByContactEmail( Mockito.anyString() ) ).thenReturn( search );
        agentBusinessServiceImpl.handleOpportunityContactChange( opportunityContact );
    }

    /**
     * Should not update contact if update request is empty.
     */
    @Test
    public void shouldNotUpdateContactIfRequestIsEmpty() {
        final Map< String, String > request = new HashMap<>();
        String contactId = "testContactId";
        final AgentResponse response = agentBusinessServiceImpl.updateContact( "test", contactId, request );
        Assert.assertNotNull( response );
        assertEquals( contactId, response.getId() );
        verifyZeroInteractions( searchService );
        verifyZeroInteractions( agentContactService );
        verifyZeroInteractions( actionLogBusinessService );
        verifyZeroInteractions( contactService );
        verifyZeroInteractions( crmContactBuilder );
    }

}
