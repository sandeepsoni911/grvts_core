package com.owners.gravitas.handler;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.AgentBusinessService;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.business.builder.AgentSourceBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.domain.entity.Opportunity;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.crm.response.CRMAgentResponse;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.exception.ResultNotFoundException;
import com.owners.gravitas.service.AccountService;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.SearchService;
import com.owners.gravitas.service.StageLogService;

/**
 * The Class PendingSaleStageChangeHandlerTest.
 *
 * @author shivamm
 */
public class PendingSaleStageChangeHandlerTest extends AbstractBaseMockitoTest {

    /** The pending sale stage change handler. */
    @InjectMocks
    private PendingSaleStageChangeHandler pendingSaleStageChangeHandler;

    /** The agent business service. */
    @Mock
    private AgentBusinessService agentBusinessService;

    /** The agent source builder. */
    @Mock
    private AgentSourceBuilder agentSourceBuilder;

    /** The agent service. */
    @Mock
    private AgentService agentService;

    /** The opportunity service. */
    @Mock
    private OpportunityService opportunityService;

    /** The account service. */
    @Mock
    private AccountService accountService;

    /** The opportunity business service. */
    @Mock
    private OpportunityBusinessService opportunityBusinessService;

    /** The search service. */
    @Mock
    private SearchService searchService;

    /** The stage log service. */
    @Mock
    private StageLogService stageLogService;

    /**
     * Test handle change should throw exception if agent source not found.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testHandleChangeShouldThrowExceptionIfAgentSourceNotFound() {
        final String agentId = "agentId";
        final String opportunityId = "fbID";
        final Contact contact = new Contact();
        final Opportunity opportunity = new Opportunity();
        final String crmId = "crmId";
        contact.setCrmId( crmId );
        final String accountId = "accId";
        final Search search = new Search();
        final String agentEmail = "agentEmail";
        search.setAgentEmail( agentEmail );
        final com.owners.gravitas.domain.entity.Contact contactEntity = new com.owners.gravitas.domain.entity.Contact();
        contactEntity.setCrmId( crmId );
        opportunity.setContact( contactEntity );
        when( opportunityService.getOpportunityByFbId( opportunityId, Boolean.FALSE ) ).thenReturn( opportunity );
        when( opportunityService.getTitleClosingCompanyByOpportunityId( crmId ) ).thenReturn( accountId );
        when( accountService.findAccountNameById( accountId ) ).thenThrow( ResultNotFoundException.class );
        when( searchService.searchByAgentId( agentId ) ).thenReturn( search );
        when( agentBusinessService.getCRMAgentByEmail( search.getAgentEmail() ) )
                .thenThrow( ResultNotFoundException.class );

        pendingSaleStageChangeHandler.handleChange( agentId, opportunityId, contact );
    }

    /**
     * Test handle change should throw exception if opportunity source not
     * found.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testHandleChangeShouldThrowExceptionIfOpportunitySourceNotFound() {
        final String agentId = "agentId";
        final String opportunityId = "fbID";
        final Contact contact = new Contact();
        final Opportunity opportunity = new Opportunity();
        final String crmId = "crmId";
        contact.setCrmId( crmId );
        final String accountId = "accId";
        final Search search = new Search();
        final String agentEmail = "agentEmail";
        search.setAgentEmail( agentEmail );
        final AgentSource agentSource = new AgentSource();
        final com.owners.gravitas.domain.entity.Contact contactEntity = new com.owners.gravitas.domain.entity.Contact();
        contactEntity.setCrmId( crmId );
        opportunity.setContact( contactEntity );
        when( opportunityService.getOpportunityByFbId( opportunityId, Boolean.FALSE ) ).thenReturn( opportunity );
        when( opportunityService.getTitleClosingCompanyByOpportunityId( crmId ) ).thenReturn( accountId );
        when( accountService.findAccountNameById( accountId ) ).thenThrow( ResultNotFoundException.class );
        when( searchService.searchByAgentId( agentId ) ).thenReturn( search );
        when( agentBusinessService.getCRMAgentByEmail( search.getAgentEmail() ) ).thenReturn( agentSource );
        when( opportunityBusinessService.getOpportunity( crmId ) ).thenThrow( ResultNotFoundException.class );

        pendingSaleStageChangeHandler.handleChange( agentId, opportunityId, contact );
    }

    /**
     * Test handle change.
     */
    @Test
    public void testHandleChangeWithPtsFalseAndAccountIdNotNull() {
        final String agentId = "agentId";
        final Agent agent = new Agent();
        final AgentInfo info = new AgentInfo();
        final String email = "a@a.com";
        info.setEmail( email );
        agent.setInfo( info );
        final AgentSource agentSource = new AgentSource();
        final CRMAgentResponse agentResponse = new CRMAgentResponse();
        agentResponse.setEmail( "test@test.com" );

        final String opportunityId = "fbID";
        final Contact contact = new Contact();
        final OpportunitySource opportunitySource = new OpportunitySource();
        final Opportunity opportunity = new Opportunity();
        final String crmId = "crmId";
        contact.setCrmId( crmId );
        final String accountID = "test";
        final String accountName = "PTS";
        final Search search = new Search();
        search.setAgentEmail( email );
        final com.owners.gravitas.domain.entity.Contact contactEntity = new com.owners.gravitas.domain.entity.Contact();
        contactEntity.setCrmId( crmId );
        opportunity.setContact( contactEntity );

        when( searchService.searchByAgentId( agentId ) ).thenReturn( search );
        when( agentBusinessService.getCRMAgentByEmail( search.getAgentEmail() ) ).thenReturn( agentSource );
        when( opportunityService.getOpportunityByFbId( opportunityId, Boolean.FALSE ) ).thenReturn( opportunity );
        // when( agentService.getAgentDetails( email ) ).thenReturn( response );
        when( agentSourceBuilder.convertTo( agentResponse ) ).thenReturn( agentSource );
        when( opportunityService.getTitleClosingCompanyByOpportunityId( crmId ) ).thenReturn( accountID );
        when( accountService.findAccountNameById( accountID ) ).thenReturn( accountName );
        when( opportunityBusinessService.getOpportunity( crmId ) ).thenReturn( opportunitySource );
        final PostResponse postResponse = pendingSaleStageChangeHandler.handleChange( agentId, opportunityId, contact );

        verify( opportunityService ).getOpportunityByFbId( opportunityId, Boolean.FALSE );
        verify( agentBusinessService ).getCRMAgentByEmail( search.getAgentEmail() );
        verify( opportunityService ).getTitleClosingCompanyByOpportunityId( crmId );
        verify( agentBusinessService ).sendPendingSalePTSEmailNotification( true, agentSource, opportunitySource );
        verify( accountService ).findAccountNameById( accountID );
        verify( opportunityBusinessService ).getOpportunity( crmId );
        assertNull( postResponse );
    }

    /**
     * Test handle change.
     */
    @Test
    public void testHandleChangeWithPtsFalseAndAccountIdNull() {
        final String agentId = "agentId";
        final Agent agent = new Agent();
        final AgentInfo info = new AgentInfo();
        final String email = "a@a.com";
        info.setEmail( email );
        agent.setInfo( info );
        final AgentSource agentSource = new AgentSource();
        final String opportunityId = "fbID";
        final Contact contact = new Contact();
        final OpportunitySource opportunitySource = new OpportunitySource();
        final Opportunity opportunity = new Opportunity();
        final String crmId = "crmId";
        contact.setCrmId( crmId );
        final Search search = new Search();
        search.setAgentEmail( email );
        final CRMAgentResponse agentResponse = new CRMAgentResponse();
        agentResponse.setEmail( "test@test.com" );
        final com.owners.gravitas.domain.entity.Contact contactEntity = new com.owners.gravitas.domain.entity.Contact();
        contactEntity.setCrmId( crmId );
        opportunity.setContact( contactEntity );
        when( opportunityService.getOpportunityByFbId( opportunityId, Boolean.FALSE ) ).thenReturn( opportunity );
        when( searchService.searchByAgentId( agentId ) ).thenReturn( search );
        when( agentBusinessService.getCRMAgentByEmail( search.getAgentEmail() ) ).thenReturn( agentSource );
        // when( agentService.getAgentDetails( email ) ).thenReturn( response );
        when( agentSourceBuilder.convertTo( agentResponse ) ).thenReturn( agentSource );
        when( opportunityService.getTitleClosingCompanyByOpportunityId( crmId ) ).thenReturn( null );
        when( opportunityBusinessService.getOpportunity( crmId ) ).thenReturn( opportunitySource );
        final PostResponse postResponse = pendingSaleStageChangeHandler.handleChange( agentId, opportunityId, contact );

        verify( opportunityService ).getOpportunityByFbId( opportunityId, Boolean.FALSE );
        verify( agentBusinessService ).getCRMAgentByEmail( search.getAgentEmail() );
        verify( opportunityService ).getTitleClosingCompanyByOpportunityId( crmId );
        verify( agentBusinessService ).sendPendingSalePTSEmailNotification( false, agentSource, opportunitySource );
        verify( opportunityBusinessService ).getOpportunity( crmId );
        assertNull( postResponse );
    }

    /**
     * Test build task.
     */
    @Test
    public void testBuildTask() {
        final String agentId = "agentId";
        final String opportunityId = "opId";
        final Contact contact = new Contact();
        final Task buildTask = pendingSaleStageChangeHandler.buildTask( agentId, opportunityId, contact );
        assertNull( buildTask );
    }

    /**
     * Test send feedback email.
     */
    @Test
    public void testSendFeedbackEmail() {
        pendingSaleStageChangeHandler.sendFeedbackEmail( "test", "", null );
    }
}
