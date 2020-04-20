package com.owners.gravitas.handler;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.owners.gravitas.business.AgentTaskBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.service.StageLogService;

/**
 * Test class for NewStageChangeHandler.
 *
 * @author Khanujal
 *
 */
public class NewStageChangeHandlerTest extends AbstractBaseMockitoTest {

    /** NewStageChangeHandler. */
    @InjectMocks
    private NewStageChangeHandler newStageChangeHandler;

    /** AgentTaskBusinessService. */
    @Mock
    private AgentTaskBusinessService agentTaskBusinessService;

    /** The stage log service. */
    @Mock
    private StageLogService stageLogService;

    /**
     * Before test.
     */
    @BeforeMethod
    public void beforeTest() {
        ReflectionTestUtils.setField( newStageChangeHandler, "taskTitle", "test" );
        Mockito.reset( agentTaskBusinessService );
    }

    /**
     * test method for handleChange().
     */
    @Test
    public void testHandleChange() {
        final Contact contact = new Contact();
        newStageChangeHandler.handleChange( "agentId", "opportunityId", contact );

        Mockito.verify( agentTaskBusinessService ).saveTaskifNotExists( Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any() );
    }

    /**
     * test method for getTask().
     */
    @Test
    public void testGetTask() {
        final Contact contact = new Contact();
        newStageChangeHandler.buildTask( "agentId", "opportunityId", contact );
    }

    /**
     * Test send feedback email.
     */
    @Test
    public void testSendFeedbackEmail() {
        newStageChangeHandler.sendFeedbackEmail( "agentId", "crmOpportunityId", new Contact() );
    }
}
