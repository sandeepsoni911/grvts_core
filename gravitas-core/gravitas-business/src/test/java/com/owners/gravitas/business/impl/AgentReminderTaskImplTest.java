package com.owners.gravitas.business.impl;

import static com.owners.gravitas.enums.PushNotificationType.SCRIPTED_FLOW_INCOMPLETE;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.business.ActionFlowBusinessService;
import com.owners.gravitas.business.AgentNotificationBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.service.AgentOpportunityService;
import com.owners.gravitas.service.SearchService;

/**
 * The Class AgentReminderTaskImplTest.
 */
public class AgentReminderTaskImplTest extends AbstractBaseMockitoTest {

    /** The agent reminder task. */
    @InjectMocks
    private AgentReminderTaskImpl agentReminderTask;

    /** The agent notification business service */
    @Mock
    private AgentNotificationBusinessService agentNotificationBusinessService;

    /** The agent opportunity service. */
    @Mock
    private AgentOpportunityService agentOpportunityService;

    /** The action flow business service. */
    @Mock
    private ActionFlowBusinessService actionFlowBusinessService;

    /** The search service. */
    @Mock
    private SearchService searchService;

    /**
     * Test send notification.
     */
    @Test
    public void testSendNotification() {
        final String agentId = "test id";
        final Search agentSearch = new Search();
        when( searchService.searchByAgentId( agentId ) ).thenReturn( agentSearch );
        when( actionFlowBusinessService.isEligibleForScriptedCall( agentSearch.getAgentEmail() ) ).thenReturn( false );
        Mockito.when( agentOpportunityService.getAgentNewOpportunitiesCount( Mockito.any() ) ).thenReturn( 1 );
        agentReminderTask.sendNewOppNotification( agentId );
        verify( agentNotificationBusinessService ).sendPushNotification( Mockito.any(), Mockito.any() );
        verify( searchService ).searchByAgentId( agentId );
        verify( actionFlowBusinessService ).isEligibleForScriptedCall( agentSearch.getAgentEmail() );
        verify( agentOpportunityService ).getAgentNewOpportunitiesCount( anyString() );
    }

    /**
     * Should not send new opp notification if new opportunities count is zero.
     */
    @Test
    public void shouldNotSendNewOppNotificationIfNewOpportunitiesCountISZero() {
        final String agentId = "test id";
        final Search agentSearch = new Search();
        when( searchService.searchByAgentId( agentId ) ).thenReturn( agentSearch );
        when( actionFlowBusinessService.isEligibleForScriptedCall( agentSearch.getAgentEmail() ) ).thenReturn( false );
        Mockito.when( agentOpportunityService.getAgentNewOpportunitiesCount( Mockito.any() ) ).thenReturn( 0 );
        agentReminderTask.sendNewOppNotification( agentId );
        verifyNoMoreInteractions( agentNotificationBusinessService );
        verify( agentOpportunityService ).getAgentNewOpportunitiesCount( anyString() );
        verify( searchService ).searchByAgentId( agentId );
        verify( actionFlowBusinessService ).isEligibleForScriptedCall( agentSearch.getAgentEmail() );
    }

    /**
     * Should not send new opp notification when agent is eligible for scripted
     * call.
     */
    @Test
    public void shouldNotSendNewOppNotificationWhenAgentIsEligibleForScriptedCall() {
        final String agentId = "test id";
        final Search agentSearch = new Search();
        when( searchService.searchByAgentId( agentId ) ).thenReturn( agentSearch );
        when( actionFlowBusinessService.isEligibleForScriptedCall( agentSearch.getAgentEmail() ) ).thenReturn( true );
        Mockito.when( agentOpportunityService.getAgentNewOpportunitiesCount( Mockito.any() ) ).thenReturn( 0 );
        agentReminderTask.sendNewOppNotification( agentId );
        verify( searchService ).searchByAgentId( agentId );
        verify( actionFlowBusinessService ).isEligibleForScriptedCall( agentSearch.getAgentEmail() );
        verifyZeroInteractions( agentNotificationBusinessService );
        verifyZeroInteractions( agentOpportunityService );
    }

    /**
     * Should send claimed opp notification for valid input.
     */
    @Test
    public void shouldSendClaimedOppNotificationForValidInput() {
        final String agentId = "test id";
        final Search agentSearch = new Search();
        when( searchService.searchByAgentId( agentId ) ).thenReturn( agentSearch );
        when( actionFlowBusinessService.isEligibleForScriptedCall( agentSearch.getAgentEmail() ) ).thenReturn( false );
        when( agentOpportunityService.hasAgentClaimedOpportunityWithOpenTasks( anyString() ) ).thenReturn( true );
        agentReminderTask.sendClaimedOppNotification( agentId );
        verify( agentNotificationBusinessService ).sendPushNotification( anyString(),
                any( NotificationRequest.class ) );
        verify( searchService ).searchByAgentId( agentId );
        verify( actionFlowBusinessService ).isEligibleForScriptedCall( agentSearch.getAgentEmail() );
    }

    /**
     * Should not send claimed opp notification if agent hasnt claimed
     * opportunity with open tasks.
     */
    @Test
    public void shouldNotSendClaimedOppNotificationIfAgentHasntClaimedOpportunityWithOpenTasks() {
        final String agentId = "test id";
        final Search agentSearch = new Search();
        when( searchService.searchByAgentId( agentId ) ).thenReturn( agentSearch );
        when( actionFlowBusinessService.isEligibleForScriptedCall( agentSearch.getAgentEmail() ) ).thenReturn( false );
        when( agentOpportunityService.hasAgentClaimedOpportunityWithOpenTasks( anyString() ) ).thenReturn( false );
        agentReminderTask.sendClaimedOppNotification( agentId );
        verify( searchService ).searchByAgentId( agentId );
        verify( actionFlowBusinessService ).isEligibleForScriptedCall( agentSearch.getAgentEmail() );
        verifyZeroInteractions( agentNotificationBusinessService );
    }

    /**
     * Should not send claimed opp notification when agent is eligible for
     * scripted call.
     */
    @Test
    public void shouldNotSendClaimedOppNotificationWhenAgentIsEligibleForScriptedCall() {
        final String agentId = "test id";
        final Search agentSearch = new Search();
        when( searchService.searchByAgentId( agentId ) ).thenReturn( agentSearch );
        when( actionFlowBusinessService.isEligibleForScriptedCall( agentSearch.getAgentEmail() ) ).thenReturn( true );
        when( agentOpportunityService.hasAgentClaimedOpportunityWithOpenTasks( anyString() ) ).thenReturn( false );
        agentReminderTask.sendClaimedOppNotification( agentId );
        verify( searchService ).searchByAgentId( agentId );
        verify( actionFlowBusinessService ).isEligibleForScriptedCall( agentSearch.getAgentEmail() );
        verifyZeroInteractions( agentOpportunityService );
        verifyZeroInteractions( agentNotificationBusinessService );
    }

    /**
     * Test send action flow incomplete reminder should send notification.
     */
    @Test
    public void testSendActionFlowIncompleteReminderShouldSendNotification() {
        final String agentId = "id";
        final String agentEmail = "test@test.com";
        final Search search = new Search();
        search.setAgentEmail( agentEmail );
        when( searchService.searchByAgentId( agentId ) ).thenReturn( search );
        when( actionFlowBusinessService.isEligibleForScriptedCall( agentEmail ) ).thenReturn( true );
        when( agentOpportunityService.hasIncompleteActionFlow( agentEmail ) ).thenReturn( true );
        doNothing().when( actionFlowBusinessService ).sendOpportunityPushNotifications( agentId,
                SCRIPTED_FLOW_INCOMPLETE );

        agentReminderTask.sendActionFlowIncompleteReminder( agentId );

        verify( searchService ).searchByAgentId( agentId );
        verify( actionFlowBusinessService ).isEligibleForScriptedCall( agentEmail );
        verify( agentOpportunityService ).hasIncompleteActionFlow( agentEmail );
        verify( actionFlowBusinessService ).sendOpportunityPushNotifications( agentId, SCRIPTED_FLOW_INCOMPLETE );
    }

    /**
     * Test send action flow incomplete reminder should not send notification
     * when not eligible for scripted call.
     */
    @Test
    public void testSendActionFlowIncompleteReminderShouldNotSendNotificationWhenNotEligibleForScriptedCall() {
        final String agentId = "id";
        final String agentEmail = "test@test.com";
        final Search search = new Search();
        search.setAgentEmail( agentEmail );
        when( searchService.searchByAgentId( agentId ) ).thenReturn( search );
        when( actionFlowBusinessService.isEligibleForScriptedCall( agentEmail ) ).thenReturn( false );

        agentReminderTask.sendActionFlowIncompleteReminder( agentId );

        verify( searchService ).searchByAgentId( agentId );
        verify( actionFlowBusinessService ).isEligibleForScriptedCall( agentEmail );
        verifyZeroInteractions( agentOpportunityService );
        verifyZeroInteractions( actionFlowBusinessService );
    }

    /**
     * Test send action flow incomplete reminder should not send notification
     * when dont have incomplete actions.
     */
    @Test
    public void testSendActionFlowIncompleteReminderShouldNotSendNotificationWhenDontHaveIncompleteActions() {
        final String agentId = "id";
        final String agentEmail = "test@test.com";
        final Search search = new Search();
        search.setAgentEmail( agentEmail );
        when( searchService.searchByAgentId( agentId ) ).thenReturn( search );
        when( actionFlowBusinessService.isEligibleForScriptedCall( agentEmail ) ).thenReturn( true );
        when( agentOpportunityService.hasIncompleteActionFlow( agentEmail ) ).thenReturn( false );

        agentReminderTask.sendActionFlowIncompleteReminder( agentId );

        verify( searchService ).searchByAgentId( agentId );
        verify( actionFlowBusinessService ).isEligibleForScriptedCall( agentEmail );
        verify( agentOpportunityService ).hasIncompleteActionFlow( agentEmail );
        verifyZeroInteractions( actionFlowBusinessService );
    }

}
