package com.owners.gravitas.handler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
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
import com.owners.gravitas.config.FeedbackEmailJmxConfig;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.enums.TaskType;
import com.owners.gravitas.service.StageLogService;

/**
 * Test class for NewStageChangeHandler.
 *
 * @author Khanujal
 *
 */
public class WritingOfferStageChangeHandlerTest extends AbstractBaseMockitoTest {

    /** NewStageChangeHandler. */
    @InjectMocks
    private WritingOfferStageChangeHandler writingOfferStageChangeHandler;

    /** The agent notification business service. */
    @Mock
    private AgentNotificationBusinessService agentNotificationBusinessService;

    /** The agent task business service. */
    @Mock
    private AgentTaskBusinessService agentTaskBusinessService;

    /** The feedback email jmx config. */
    @Mock
    protected FeedbackEmailJmxConfig feedbackEmailJmxConfig;

    /** The agent rating business service. */
    @Mock
    private AgentRatingBusinessService agentRatingBusinessService;

    /** The stage log service. */
    @Mock
    protected StageLogService stageLogService;

    /**
     * Before test.
     */
    @BeforeMethod
    public void beforeTest() {
        ReflectionTestUtils.setField( writingOfferStageChangeHandler, "taskTitle", "test" );
        Mockito.reset( agentTaskBusinessService );
    }

    /**
     * test method for handleChange().
     */
    @Test
    public void testHandleChange() {
        final Contact contact = new Contact();
        PostResponse response = new PostResponse();
        when( agentTaskBusinessService.saveTaskifNotExists( anyString(), anyString(), any( TaskType.class ),
                anyString(), anyString() ) ).thenReturn( response );
        when( agentNotificationBusinessService.sendPushNotification( anyString(), any( NotificationRequest.class ) ) )
                .thenReturn( null );
        PostResponse postResponse = writingOfferStageChangeHandler.handleChange( "agentId", "opportunityId", contact );
        Mockito.verify( agentNotificationBusinessService ).sendPushNotification( Mockito.any(), Mockito.any() );
        verify( agentTaskBusinessService ).saveTaskifNotExists( anyString(), anyString(), any( TaskType.class ),
                anyString(), anyString() );
        assertEquals( response, postResponse );
    }

    /**
     * Should not save task if contact is null.
     */
    @Test
    public void shouldNotSaveTaskIfContactIsNull() {
        PostResponse postResponse = writingOfferStageChangeHandler.handleChange( "agentId", "opportunityId", null );
        assertEquals( null, postResponse );
        verifyNoMoreInteractions( agentNotificationBusinessService );
        verifyNoMoreInteractions( agentTaskBusinessService );
    }

    /**
     * Should create task for valid inputs.
     */
    @Test
    public void shouldCreateTaskForValidInputs() {
        String title = "test";
        String agentId = "dummy agent id";
        String opportunityId = "dummy opportunity id";
        Contact contact = new Contact();
        String firstName = "firstName";
        String lastName = "lastName";
        contact.setFirstName( firstName );
        contact.setLastName( lastName );
        final String titleTxt = String.format( title, firstName + lastName );

        Task task = writingOfferStageChangeHandler.buildTask( agentId, opportunityId, contact );
        assertEquals( titleTxt, task.getTitle() );
        assertEquals( opportunityId, task.getOpportunityId() );
        assertEquals( agentId, task.getCreatedBy() );
        assertEquals( TaskType.ASK_PREMIUM_TITLE.getType(), task.getTaskType() );
    }

    /**
     * test method for getTask().
     */
    @Test
    public void testGetTask() {
        final Contact contact = new Contact();
        writingOfferStageChangeHandler.buildTask( "agentId", "opportunityId", contact );
    }

    /**
     * Test send feedback email.
     */
    @Test
    public void testSendFeedbackEmail() {
        final String agentId = "test";
        final String crmOpportunityId = "test id";
        Contact contact = new Contact();
        String firstName = "firstName";
        String lastName = "lastName";
        contact.setFirstName( firstName );
        contact.setLastName( lastName );
        when( feedbackEmailJmxConfig.getWritingOfferStgFeedbackEnabled() ).thenReturn( true );
        writingOfferStageChangeHandler.sendFeedbackEmail( agentId, crmOpportunityId, contact );
        verify( feedbackEmailJmxConfig ).getWritingOfferStgFeedbackEnabled();
        verify( agentRatingBusinessService ).sendEmail( "agent-interaction-docProcessing", agentId, crmOpportunityId,
                contact );
    }

}
