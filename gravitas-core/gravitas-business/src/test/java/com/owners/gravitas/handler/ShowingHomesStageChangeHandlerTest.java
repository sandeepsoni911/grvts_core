package com.owners.gravitas.handler;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
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
 * The Class ShowingHomesStageChangeHandlerTest.
 *
 * @author harshads
 */
public class ShowingHomesStageChangeHandlerTest extends AbstractBaseMockitoTest {

    /** The showing homes stage change handler. */
    @InjectMocks
    private ShowingHomesStageChangeHandler showingHomesStageChangeHandler;

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
    protected StageLogService stageLogService;

    /**
     * Before test.
     */
    @BeforeMethod
    public void beforeTest() {
        Mockito.reset( agentTaskBusinessService );
    }

    /**
     * test method for handleChange().
     */
    @Test
    public void testHandleChange() {
        final Contact contact = new Contact();
        showingHomesStageChangeHandler.handleChange( "agentId", "opportunityId", contact );
        Mockito.verify( agentTaskBusinessService ).closeTaskByType( Mockito.anyString(), Mockito.anyString(),
                Mockito.any( TaskType.class ) );
    }

    /**
     * test method for getTask().
     */
    @Test
    public void testGetTask() {
        Assert.assertNull( showingHomesStageChangeHandler.buildTask( "agentId", "opportunityId", new Contact() ) );
    }

    /**
     * Test send feedback email.
     */
    @Test
    public void testSendFeedbackEmail() {
        final String agentId = "test";
        final String crmOpportunityId = "test_id";
        final Contact contact = new Contact();
        when( feedbackEmailJmxConfig.getShowingHomesStgFeedbackEnabled() ).thenReturn( true );
        doNothing().when( agentRatingBusinessService ).sendEmail( "agent-interaction-homeVisit", agentId,
                crmOpportunityId, contact );
        showingHomesStageChangeHandler.sendFeedbackEmail( agentId, crmOpportunityId, contact );
        verify( feedbackEmailJmxConfig ).getShowingHomesStgFeedbackEnabled();
        verify( agentRatingBusinessService ).sendEmail( "agent-interaction-homeVisit", agentId, crmOpportunityId,
                contact );
    }
}
