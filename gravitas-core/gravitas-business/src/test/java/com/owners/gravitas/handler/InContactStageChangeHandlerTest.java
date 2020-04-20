package com.owners.gravitas.handler;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.owners.gravitas.business.AgentRatingBusinessService;
import com.owners.gravitas.business.AgentTaskBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.FeedbackEmailJmxConfig;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.enums.TaskType;
import com.owners.gravitas.service.StageLogService;

/**
 * Test class for ClaimedStageChangeHandler.
 *
 * @author vishwanathm
 *
 */
public class InContactStageChangeHandlerTest extends AbstractBaseMockitoTest {

    /** ClaimedStageChangeHandler. */
    @InjectMocks
    private InContactStageChangeHandler inContactStageChangeHandler;

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

    /**
     * Before test.
     */
    @BeforeMethod
    public void beforeTest() {
        ReflectionTestUtils.setField( inContactStageChangeHandler, "taskTitle", "test" );
        Mockito.reset( agentTaskBusinessService );
    }

    /**
     * test method for handleChange().
     */
    @Test
    public void testHandleChange() {
        final Contact contact = new Contact();
        inContactStageChangeHandler.handleChange( "agentId", "opportunityId", contact );

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
        inContactStageChangeHandler.buildTask( "agentId", "opportunityId", contact );
    }

    /**
     * Test send feedback email.
     */
    @Test
    public void testSendFeedbackEmail() {
        final String agentId = "test";
        final String crmOpportunityId = "test_id";
        final Contact contact = new Contact();
        when( feedbackEmailJmxConfig.getInContactStgFeedbackEnabled() ).thenReturn( true );
        doNothing().when( agentRatingBusinessService ).sendEmail( "agent-interaction-1", agentId, crmOpportunityId,
                contact );
        inContactStageChangeHandler.sendFeedbackEmail( agentId, crmOpportunityId, contact );
        verify( feedbackEmailJmxConfig ).getInContactStgFeedbackEnabled();
        verify( agentRatingBusinessService ).sendEmail( "agent-interaction-1", agentId, crmOpportunityId, contact );
    }
}
