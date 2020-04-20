package com.owners.gravitas.handler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.owners.gravitas.business.AgentNotificationBusinessService;
import com.owners.gravitas.business.AgentRatingBusinessService;
import com.owners.gravitas.business.AgentTaskBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.BadgeCounterJmxConfig;
import com.owners.gravitas.config.FeedbackEmailJmxConfig;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.enums.TaskType;
import com.owners.gravitas.service.AgentOpportunityService;
import com.owners.gravitas.service.StageLogService;

/**
 * Test class for ClaimedStageChangeHandler.
 *
 * @author Khanujal
 *
 */
public class ClaimedStageChangeHandlerTest extends AbstractBaseMockitoTest {

    /** ClaimedStageChangeHandler. */
    @InjectMocks
    private ClaimedStageChangeHandler claimedStageChangeHandler;

    /** AgentTaskBusinessService. */
    @Mock
    private AgentTaskBusinessService agentTaskBusinessService;

    /** The feedback email jmx config. */
    @Mock
    private FeedbackEmailJmxConfig feedbackEmailJmxConfig;

    /** The agent rating business service. */
    @Mock
    private AgentRatingBusinessService agentRatingBusinessService;

    /** The stage log service. */
    @Mock
    private StageLogService stageLogService;

    /** The badge counter jmx config. */
    @Mock
    private BadgeCounterJmxConfig badgeCounterJmxConfig;

    /** The agent opportunity service. */
    @Mock
    private AgentOpportunityService agentOpportunityService;

    /** The agent notification business service. */
    @Mock
    private AgentNotificationBusinessService agentNotificationBusinessService;

    /**
     * Before test.
     */
    @BeforeMethod
    public void beforeTest() {
        ReflectionTestUtils.setField( claimedStageChangeHandler, "taskTitle", "test" );
        Mockito.reset( agentTaskBusinessService );
    }

    /**
     * test method for handleChange().
     */
    @Test
    public void testHandleChange() {
        final Contact contact = new Contact();
        claimedStageChangeHandler.handleChange( "agentId", "opportunityId", contact );
        Mockito.verify( agentTaskBusinessService ).closeTaskByType( Mockito.anyString(), Mockito.anyString(),
                Mockito.any( TaskType.class ) );
        Mockito.verify( agentTaskBusinessService ).saveTaskifNotExists( Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any() );
    }

    /**
     * test method for getTask().
     */
    @Test
    public void testGetTask() {
        final Contact contact = new Contact();
        claimedStageChangeHandler.buildTask( "agentId", "opportunityId", contact );
    }

    /**
     * Test send feedback email.
     */
    @Test
    public void testSendFeedbackEmail() {
        final String agentId = "test";
        final String crmOpportunityId = "test_id";
        final Contact contact = new Contact();
        when( feedbackEmailJmxConfig.getClaimedStgFeedbackEnabled() ).thenReturn( true );
        doNothing().when( agentRatingBusinessService ).sendEmail( "agent-interaction-1", agentId, crmOpportunityId,
                contact );
        claimedStageChangeHandler.sendFeedbackEmail( agentId, crmOpportunityId, contact );
        verify( feedbackEmailJmxConfig ).getClaimedStgFeedbackEnabled();
        verify( agentRatingBusinessService ).sendEmail( "agent-interaction-1", agentId, crmOpportunityId, contact );
    }

    /**
     * Test handle badge counter change.
     */
    @Test
    public void testHandleBadgeCounterChange() {
        final String agentId = "agentId";
        when( badgeCounterJmxConfig.isBadgeCountEnabled() ).thenReturn( true );
        when( agentOpportunityService.getAgentNewOpportunitiesCount( agentId ) ).thenReturn( 2 );
        claimedStageChangeHandler.handleBadgeCounterChange( agentId );
        verify( agentNotificationBusinessService ).sendPushNotification( anyString(),
                any( NotificationRequest.class ) );
    }

    /**
     * Test handle badge counter change when badge count is disabled.
     */
    @Test
    public void testHandleBadgeCounterChangeWhenBadgeCountIsDisabled() {
        final String agentId = "agentId";
        when( badgeCounterJmxConfig.isBadgeCountEnabled() ).thenReturn( false );
        claimedStageChangeHandler.handleBadgeCounterChange( agentId );
        verifyZeroInteractions( agentOpportunityService, agentNotificationBusinessService );
    }
}
