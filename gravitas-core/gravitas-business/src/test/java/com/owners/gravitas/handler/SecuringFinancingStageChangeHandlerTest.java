package com.owners.gravitas.handler;

import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertNull;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.owners.gravitas.business.AgentRatingBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.FeedbackEmailJmxConfig;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.service.StageLogService;

/**
 * The Class SecuringFinancingStageChangeHandlerTest.
 *
 * @author vishwanathm
 */
public class SecuringFinancingStageChangeHandlerTest extends AbstractBaseMockitoTest {

    /** The pending sale stage change handler. */
    @InjectMocks
    private SecuringFinancingStageChangeHandler securingFinancingStageChangeHandler;

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
     * Test build task.
     */
    @Test
    public void testBuildTask() {
        String agentId = "agentId";
        String opportunityId = "opId";
        Contact contact = new Contact();
        Task buildTask = securingFinancingStageChangeHandler.buildTask( agentId, opportunityId, contact );
        assertNull( buildTask );
    }

    /**
     * Test handle change.
     */
    @Test
    public void testHandleChange() {
        String agentId = "agentId";
        String opportunityId = "opId";
        Contact contact = new Contact();
        PostResponse resp = securingFinancingStageChangeHandler.handleChange( agentId, opportunityId, contact );
        assertNull( resp );
    }

    /**
     * Test send feedback email_ jmx enabled.
     */
    @Test
    public void testSendFeedbackEmail_JmxEnabled() {
        String agentId = "agentId";
        String opportunityId = "opId";
        Contact contact = new Contact();
        Mockito.when( feedbackEmailJmxConfig.getSecuringFinancingStgFeedbackEnabled() ).thenReturn( true );
        Mockito.doNothing().when( agentRatingBusinessService ).sendEmail( Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.any( Contact.class ) );
        securingFinancingStageChangeHandler.sendFeedbackEmail( agentId, opportunityId, contact );
        Mockito.verify( feedbackEmailJmxConfig ).getSecuringFinancingStgFeedbackEnabled();
        Mockito.verify( agentRatingBusinessService ).sendEmail( Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.any( Contact.class ) );
    }

    /**
     * Test send feedback email_ jmx disabled.
     */
    @Test
    public void testSendFeedbackEmail_JmxDisabled() {
        String agentId = "agentId";
        String opportunityId = "opId";
        Contact contact = new Contact();
        Mockito.when( feedbackEmailJmxConfig.getSecuringFinancingStgFeedbackEnabled() ).thenReturn( false );
        securingFinancingStageChangeHandler.sendFeedbackEmail( agentId, opportunityId, contact );
        Mockito.verify( feedbackEmailJmxConfig ).getSecuringFinancingStgFeedbackEnabled();
        Mockito.verifyZeroInteractions( agentRatingBusinessService );
    }
}
