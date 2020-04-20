package com.owners.gravitas.handler;

import static com.owners.gravitas.dto.response.BaseResponse.Status.SUCCESS;
import static com.owners.gravitas.enums.RecordType.BOTH;
import static com.owners.gravitas.enums.RecordType.BUYER;
import static com.owners.gravitas.enums.RecordType.SELLER;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.amqp.AgentSource;
import com.owners.gravitas.amqp.OpportunitySource;
import com.owners.gravitas.business.AgentBusinessService;
import com.owners.gravitas.business.AgentRatingBusinessService;
import com.owners.gravitas.business.OpportunityBusinessService;
import com.owners.gravitas.business.builder.AgentSourceBuilder;
import com.owners.gravitas.business.builder.ClientFollowUpEmailNotificationBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.ClientFollowupConfig;
import com.owners.gravitas.config.FeedbackEmailJmxConfig;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.domain.entity.Opportunity;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.response.NotificationResponse;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.service.AccountService;
import com.owners.gravitas.service.AgentService;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.OpportunityService;
import com.owners.gravitas.service.SearchService;
import com.owners.gravitas.service.StageLogService;

/**
 * The Class SoldStageChangeHandlerTest.
 *
 * @author raviz
 */
public class SoldStageChangeHandlerTest extends AbstractBaseMockitoTest {

    /** The sold stage change handler. */
    @InjectMocks
    private SoldStageChangeHandler soldStageChangeHandler;

    /** The MailService. */
    @Mock
    private MailService mailService;

    /** The client follow up email notification builder. */
    @Mock
    private ClientFollowUpEmailNotificationBuilder clientFollowUpEmailNotificationBuilder;

    /** The search service. */
    @Mock
    private SearchService searchService;

    /** The opportunity business service. */
    @Mock
    private OpportunityBusinessService opportunityBusinessService;

    /** The client followup config. */
    @Mock
    private ClientFollowupConfig clientFollowupConfig;

    /** The agent service. */
    @Mock
    private AgentService agentService;

    /** The opportunity service. */
    @Mock
    private OpportunityService opportunityService;

    /** The account service. */
    @Mock
    private AccountService accountService;

    /** The agent business service. */
    @Mock
    private AgentBusinessService agentBusinessService;

    /** The agent source builder. */
    @Mock
    private AgentSourceBuilder agentSourceBuilder;

    /** The feedback email jmx config. */
    @Mock
    private FeedbackEmailJmxConfig feedbackEmailJmxConfig;

    /** The agent rating business service. */
    @Mock
    private AgentRatingBusinessService agentRatingBusinessService;

    /** The stage log service. */
    @Mock
    protected StageLogService stageLogService;

    /**
     * Test handle change should send mail when opp type is buyer and buyer flag
     * is enabled with pts true and account id not null.
     */
    @Test
    public void testHandleChangeShouldSendMailWhenOppTypeIsBuyerAndBuyerFlagIsEnabledWithPtsTrueAndAccountIdNotNull() {
        final Contact contact = new Contact();
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setOpportunityType( BUYER.getType() );
        final EmailNotification emailNotification = new EmailNotification();
        final NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setStatus( SUCCESS.toString() );
        final Search search = new Search();
        search.setCrmOpportunityId( "id" );
        final String agentId = "agentId";
        final Agent agent = new Agent();
        final AgentInfo info = new AgentInfo();
        final String email = "a@a.com";
        info.setEmail( email );
        agent.setInfo( info );
        final Map< String, Object > response = new HashMap< String, Object >();
        final AgentSource agentSource = new AgentSource();
        final String opportunityId = "fbID";
        final Opportunity opportunity = new Opportunity();
        // opportunity.setCrmId( "crmID" );
        final String accountID = "test";
        final String accountName = "PTS";
        Search search1 = new Search();
        search.setAgentEmail( email );
        final com.owners.gravitas.domain.entity.Contact contactEntity = new com.owners.gravitas.domain.entity.Contact();
        contactEntity.setCrmId( "crmID" );
        opportunity.setContact( contactEntity );
        when( opportunityService.getOpportunityByFbId( opportunityId, Boolean.FALSE ) ).thenReturn( opportunity );
        when( searchService.searchByAgentId( agentId ) ).thenReturn( search1 );
        when( clientFollowupConfig.isEnableClientFollowUpEmailForBuyer() ).thenReturn( TRUE );
        when( clientFollowupConfig.isEnableClientFollowUpEmailForSeller() ).thenReturn( FALSE );
        when( clientFollowUpEmailNotificationBuilder.convertTo( opportunitySource ) ).thenReturn( emailNotification );
        when( mailService.send( emailNotification ) ).thenReturn( notificationResponse );

        when( agentService.getAgentById( agentId ) ).thenReturn( agent );
        when( agentService.getAgentDetails( email ) ).thenReturn( response );
        // when( agentSourceBuilder.convertTo( response ) ).thenReturn(
        // agentSource );
        when( opportunityService.getTitleClosingCompanyByOpportunityId( "crmID" ) ).thenReturn( accountID );
        when( accountService.findAccountNameById( accountID ) ).thenReturn( accountName );
        when( opportunityBusinessService.getOpportunity( "crmID" ) ).thenReturn( opportunitySource );
        when( searchService.searchByOpportunityId( opportunityId ) ).thenReturn( search );
        when( opportunityBusinessService.getOpportunityCreateDetails( "id" ) ).thenReturn( opportunitySource );

        final PostResponse postResponse = soldStageChangeHandler.handleChange( agentId, opportunityId, contact );

        assertNull( postResponse );
        verify( clientFollowupConfig ).isEnableClientFollowUpEmailForBuyer();
        verify( clientFollowupConfig, times( 0 ) ).isEnableClientFollowUpEmailForSeller();
        verify( clientFollowUpEmailNotificationBuilder ).convertTo( opportunitySource );
        verify( mailService ).send( emailNotification );
    }

    /**
     * Test handle change should not send mail when opp type is buyer and buyer
     * flag is disabled with pts false and account id null.
     */
    @Test
    public void testHandleChangeShouldNotSendMailWhenOppTypeIsBuyerAndBuyerFlagIsDisabledWithPtsFalseAndAccountIdNull() {
        final Contact contact = new Contact();
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setOpportunityType( BUYER.getType() );
        final EmailNotification emailNotification = new EmailNotification();
        final NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setStatus( SUCCESS.toString() );
        final Search search = new Search();
        search.setCrmOpportunityId( "id" );
        final String agentId = "agentId";
        final Agent agent = new Agent();
        final AgentInfo info = new AgentInfo();
        final String email = "a@a.com";
        info.setEmail( email );
        agent.setInfo( info );
        final Map< String, Object > response = new HashMap< String, Object >();
        final AgentSource agentSource = new AgentSource();
        final String opportunityId = "fbID";
        final Opportunity opportunity = new Opportunity();
        // opportunity.setCrmId( "crmID" );
        Search search1 = new Search();
        search.setAgentEmail( email );
        final com.owners.gravitas.domain.entity.Contact contactEntity = new com.owners.gravitas.domain.entity.Contact();
        contactEntity.setCrmId( "crmID" );
        opportunity.setContact( contactEntity );
        when( opportunityService.getOpportunityByFbId( opportunityId, Boolean.FALSE ) ).thenReturn( opportunity );
        when( searchService.searchByAgentId( agentId ) ).thenReturn( search1 );
        when( clientFollowupConfig.isEnableClientFollowUpEmailForBuyer() ).thenReturn( FALSE );
        when( clientFollowupConfig.isEnableClientFollowUpEmailForSeller() ).thenReturn( FALSE );
        when( clientFollowUpEmailNotificationBuilder.convertTo( opportunitySource ) ).thenReturn( emailNotification );
        when( mailService.send( emailNotification ) ).thenReturn( notificationResponse );

        when( agentService.getAgentById( agentId ) ).thenReturn( agent );
        when( agentService.getAgentDetails( email ) ).thenReturn( response );
        // when( agentSourceBuilder.convertTo( response ) ).thenReturn(
        // agentSource );
        when( opportunityService.getTitleClosingCompanyByOpportunityId( "crmID" ) ).thenReturn( null );
        when( opportunityBusinessService.getOpportunity( "crmID" ) ).thenReturn( opportunitySource );
        when( searchService.searchByOpportunityId( opportunityId ) ).thenReturn( search );
        when( opportunityBusinessService.getOpportunityCreateDetails( "id" ) ).thenReturn( opportunitySource );

        final PostResponse postResponse = soldStageChangeHandler.handleChange( agentId, opportunityId, contact );

        assertNull( postResponse );
        verify( clientFollowupConfig ).isEnableClientFollowUpEmailForBuyer();
        verify( clientFollowupConfig, times( 0 ) ).isEnableClientFollowUpEmailForSeller();
        verifyZeroInteractions( clientFollowUpEmailNotificationBuilder );
        verifyZeroInteractions( mailService );
    }

    /**
     * Test handle change should send mail when opp type is seller and seller
     * flag is enabled with pts false and account id null.
     */
    @Test
    public void testHandleChangeShouldSendMailWhenOppTypeIsSellerAndSellerFlagIsEnabledWithPtsFalseAndAccountIdNull() {
        final Contact contact = new Contact();
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setOpportunityType( SELLER.getType() );
        final EmailNotification emailNotification = new EmailNotification();
        final NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setStatus( SUCCESS.toString() );
        final Search search = new Search();
        search.setCrmOpportunityId( "id" );
        final String agentId = "agentId";
        final Agent agent = new Agent();
        final AgentInfo info = new AgentInfo();
        final String email = "a@a.com";
        info.setEmail( email );
        agent.setInfo( info );
        final Map< String, Object > response = new HashMap< String, Object >();
        final AgentSource agentSource = new AgentSource();
        final String opportunityId = "fbID";
        final Opportunity opportunity = new Opportunity();
        // opportunity.setCrmId( "crmID" );
        Search search1 = new Search();
        search.setAgentEmail( email );
        final com.owners.gravitas.domain.entity.Contact contactEntity = new com.owners.gravitas.domain.entity.Contact();
        contactEntity.setCrmId( "crmID" );
        opportunity.setContact( contactEntity );
        when( opportunityService.getOpportunityByFbId( opportunityId, Boolean.FALSE ) ).thenReturn( opportunity );
        when( searchService.searchByAgentId( agentId ) ).thenReturn( search1 );
        when( clientFollowupConfig.isEnableClientFollowUpEmailForBuyer() ).thenReturn( FALSE );
        when( clientFollowupConfig.isEnableClientFollowUpEmailForSeller() ).thenReturn( TRUE );
        when( clientFollowUpEmailNotificationBuilder.convertTo( opportunitySource ) ).thenReturn( emailNotification );
        when( mailService.send( emailNotification ) ).thenReturn( notificationResponse );

        when( agentService.getAgentById( agentId ) ).thenReturn( agent );
        when( agentService.getAgentDetails( email ) ).thenReturn( response );
        // when( agentSourceBuilder.convertTo( response ) ).thenReturn(
        // agentSource );
        when( opportunityService.getTitleClosingCompanyByOpportunityId( "crmID" ) ).thenReturn( null );
        when( opportunityBusinessService.getOpportunity( "crmID" ) ).thenReturn( opportunitySource );
        when( searchService.searchByOpportunityId( opportunityId ) ).thenReturn( search );
        when( opportunityBusinessService.getOpportunityCreateDetails( "id" ) ).thenReturn( opportunitySource );

        final PostResponse postResponse = soldStageChangeHandler.handleChange( agentId, opportunityId, contact );

        assertNull( postResponse );
        verify( clientFollowupConfig, times( 0 ) ).isEnableClientFollowUpEmailForBuyer();
        verify( clientFollowupConfig ).isEnableClientFollowUpEmailForSeller();
        verify( clientFollowUpEmailNotificationBuilder ).convertTo( opportunitySource );
        verify( mailService ).send( emailNotification );
    }

    /**
     * Test handle change should not send mail when opp type is seller and
     * seller flag is disabled with pts false and account id null.
     */
    @Test
    public void testHandleChangeShouldNotSendMailWhenOppTypeIsSellerAndSellerFlagIsDisabledWithPtsFalseAndAccountIdNull() {
        final Contact contact = new Contact();
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setOpportunityType( SELLER.getType() );
        final EmailNotification emailNotification = new EmailNotification();
        final NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setStatus( SUCCESS.toString() );
        final Search search = new Search();
        search.setCrmOpportunityId( "id" );
        final String agentId = "agentId";
        final Agent agent = new Agent();
        final AgentInfo info = new AgentInfo();
        final String email = "a@a.com";
        info.setEmail( email );
        agent.setInfo( info );
        final Map< String, Object > response = new HashMap< String, Object >();
        final AgentSource agentSource = new AgentSource();
        final String opportunityId = "fbID";
        final Opportunity opportunity = new Opportunity();
        // opportunity.setCrmId( "crmID" );
        Search search1 = new Search();
        search.setAgentEmail( email );
        final com.owners.gravitas.domain.entity.Contact contactEntity = new com.owners.gravitas.domain.entity.Contact();
        contactEntity.setCrmId( "crmID" );
        opportunity.setContact( contactEntity );
        when( opportunityService.getOpportunityByFbId( opportunityId, Boolean.FALSE ) ).thenReturn( opportunity );
        when( searchService.searchByAgentId( agentId ) ).thenReturn( search1 );
        when( clientFollowupConfig.isEnableClientFollowUpEmailForBuyer() ).thenReturn( FALSE );
        when( clientFollowupConfig.isEnableClientFollowUpEmailForSeller() ).thenReturn( FALSE );
        when( clientFollowUpEmailNotificationBuilder.convertTo( opportunitySource ) ).thenReturn( emailNotification );
        when( mailService.send( emailNotification ) ).thenReturn( notificationResponse );

        when( agentService.getAgentById( agentId ) ).thenReturn( agent );
        when( agentService.getAgentDetails( email ) ).thenReturn( response );
        // when( agentSourceBuilder.convertTo( response ) ).thenReturn(
        // agentSource );
        when( opportunityService.getTitleClosingCompanyByOpportunityId( "crmID" ) ).thenReturn( null );
        when( opportunityBusinessService.getOpportunity( "crmID" ) ).thenReturn( opportunitySource );
        when( searchService.searchByOpportunityId( opportunityId ) ).thenReturn( search );
        when( opportunityBusinessService.getOpportunityCreateDetails( "id" ) ).thenReturn( opportunitySource );

        final PostResponse postResponse = soldStageChangeHandler.handleChange( agentId, opportunityId, contact );

        assertNull( postResponse );
        verify( clientFollowupConfig, times( 0 ) ).isEnableClientFollowUpEmailForBuyer();
        verify( clientFollowupConfig ).isEnableClientFollowUpEmailForSeller();
        verifyZeroInteractions( clientFollowUpEmailNotificationBuilder );
        verifyZeroInteractions( mailService );

    }

    /**
     * Test handle change should not send mail when opp type is invalid and both
     * flag are enabled with pts false and account id null.
     */
    @Test
    public void testHandleChangeShouldNotSendMailWhenOppTypeIsInvalidAndBothFlagAreEnabledWithPtsFalseAndAccountIdNull() {
        final Contact contact = new Contact();
        final OpportunitySource opportunitySource = new OpportunitySource();
        opportunitySource.setOpportunityType( BOTH.getType() );
        final EmailNotification emailNotification = new EmailNotification();
        final NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setStatus( SUCCESS.toString() );
        final Search search = new Search();
        search.setCrmOpportunityId( "id" );
        final String agentId = "agentId";
        final Agent agent = new Agent();
        final AgentInfo info = new AgentInfo();
        final String email = "a@a.com";
        info.setEmail( email );
        agent.setInfo( info );
        final Map< String, Object > response = new HashMap< String, Object >();
        final AgentSource agentSource = new AgentSource();
        final String opportunityId = "fbID";
        final Opportunity opportunity = new Opportunity();
        // opportunity.setCrmId( "crmID" );
        Search search1 = new Search();
        search.setAgentEmail( email );
        final com.owners.gravitas.domain.entity.Contact contactEntity = new com.owners.gravitas.domain.entity.Contact();
        contactEntity.setCrmId( "crmID" );
        opportunity.setContact( contactEntity );
        when( opportunityService.getOpportunityByFbId( opportunityId, Boolean.FALSE ) ).thenReturn( opportunity );
        when( searchService.searchByAgentId( agentId ) ).thenReturn( search1 );
        when( clientFollowupConfig.isEnableClientFollowUpEmailForBuyer() ).thenReturn( TRUE );
        when( clientFollowupConfig.isEnableClientFollowUpEmailForSeller() ).thenReturn( TRUE );
        when( clientFollowUpEmailNotificationBuilder.convertTo( opportunitySource ) ).thenReturn( emailNotification );
        when( mailService.send( emailNotification ) ).thenReturn( notificationResponse );

        when( agentService.getAgentById( agentId ) ).thenReturn( agent );
        when( agentService.getAgentDetails( email ) ).thenReturn( response );
        // when( agentSourceBuilder.convertTo( response ) ).thenReturn(
        // agentSource );
        when( opportunityService.getTitleClosingCompanyByOpportunityId( "crmID" ) ).thenReturn( null );
        when( opportunityBusinessService.getOpportunity( "crmID" ) ).thenReturn( opportunitySource );
        when( searchService.searchByOpportunityId( opportunityId ) ).thenReturn( search );
        when( opportunityBusinessService.getOpportunityCreateDetails( "id" ) ).thenReturn( opportunitySource );

        final PostResponse postResponse = soldStageChangeHandler.handleChange( agentId, opportunityId, contact );

        assertNull( postResponse );
        verify( clientFollowupConfig, times( 0 ) ).isEnableClientFollowUpEmailForBuyer();
        verify( clientFollowupConfig, times( 0 ) ).isEnableClientFollowUpEmailForSeller();
        verifyZeroInteractions( clientFollowUpEmailNotificationBuilder );
        verifyZeroInteractions( mailService );

    }

    /**
     * Test build task.
     */
    @Test
    public void testBuildTask() {
        final Task task = soldStageChangeHandler.buildTask( "test", "test", new Contact() );
        assertNull( task );
    }

    /**
     * Test send feedback email should send email when feedback flag is enabled.
     */
    @Test
    public void testSendFeedbackEmailShouldSendEmailWhenFeedbackFlagIsEnabled() {
        final String agentId = "testAgentId";
        final String crmOpportunityId = "testCrmOpportunityId";
        final Contact contact = new Contact();
        final String emailTemplate = ( String ) ReflectionTestUtils.getField( soldStageChangeHandler,
                "FEEDBACK_EMAIL_TEMPLATE" );
        when( feedbackEmailJmxConfig.getSoldStgFeedbackEnabled() ).thenReturn( TRUE );
        doNothing().when( agentRatingBusinessService ).sendEmail( emailTemplate, agentId, crmOpportunityId, contact );
        soldStageChangeHandler.sendFeedbackEmail( agentId, crmOpportunityId, contact );
        verify( feedbackEmailJmxConfig ).getSoldStgFeedbackEnabled();
        verify( agentRatingBusinessService ).sendEmail( emailTemplate, agentId, crmOpportunityId, contact );
    }

    /**
     * Test send feedback email should not send email when feedback flag is
     * disabled.
     */
    @Test
    public void testSendFeedbackEmailShouldNotSendEmailWhenFeedbackFlagIsDisabled() {
        final String agentId = "testAgentId";
        final String crmOpportunityId = "testCrmOpportunityId";
        final Contact contact = new Contact();
        when( feedbackEmailJmxConfig.getSoldStgFeedbackEnabled() ).thenReturn( FALSE );
        soldStageChangeHandler.sendFeedbackEmail( agentId, crmOpportunityId, contact );
        verify( feedbackEmailJmxConfig ).getSoldStgFeedbackEnabled();
        verifyZeroInteractions( agentRatingBusinessService );
    }
}
