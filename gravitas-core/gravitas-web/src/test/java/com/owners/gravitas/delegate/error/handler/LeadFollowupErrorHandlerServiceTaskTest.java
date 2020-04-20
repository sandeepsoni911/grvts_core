package com.owners.gravitas.delegate.error.handler;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.LeadFollowupBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.delegate.error.handler.LeadFollowupErrorHandlerServiceTask;

/**
 * The Class LeadFollowupErrorHandlerServiceTaskTest.
 *
 * @author vishwanathm
 */
public class LeadFollowupErrorHandlerServiceTaskTest extends AbstractBaseMockitoTest {
    /** The lead status check service task. */
    @InjectMocks
    private LeadFollowupErrorHandlerServiceTask leadFollowupErrorHandlerServiceTask;

    /** The execution. */
    @Mock
    private DelegateExecution execution;

    /** The runtime service. */
    @Mock
    private RuntimeService runtimeService;

    /** The marketing email business service. */
    @Mock
    private LeadFollowupBusinessService leadFollowupBusinessService;

    /**
     * Test execute.
     *
     * @throws Exception
     */
    @Test
    public void testExecute() throws Exception {
        final LeadSource leadSource = new LeadSource();
        leadSource.setId( "crmLeadId" );

        final String executionId = "testId";
        Mockito.when( execution.getId() ).thenReturn( executionId );
        Mockito.when( runtimeService.getVariable( executionId, "errorLog" ) ).thenReturn( new Exception() );
        Mockito.when( runtimeService.getVariable( executionId, Constants.LEAD ) ).thenReturn( leadSource );
        Mockito.when( runtimeService.getVariable( executionId, Constants.LOST_STATUS ) ).thenReturn( Boolean.FALSE );
        Mockito.doNothing().when( leadFollowupBusinessService ).cleanLeadFollowupLog( Mockito.anyString() );
        leadFollowupErrorHandlerServiceTask.execute( execution );
        Mockito.verify( leadFollowupBusinessService ).cleanLeadFollowupLog( Mockito.anyString() );
    }

    @Test
    public void testExecuteError() throws Exception {
        final String executionId = "testId";
        Mockito.when( execution.getId() ).thenReturn( executionId );
        Mockito.when( runtimeService.getVariable( executionId, "errorLog" ) ).thenReturn( null );
        Mockito.when( runtimeService.getVariable( executionId, Constants.LEAD ) ).thenReturn( "lead_id_test" );
        Mockito.when( runtimeService.getVariable( executionId, Constants.LOST_STATUS ) ).thenReturn( Boolean.FALSE );
        Mockito.doNothing().when( leadFollowupBusinessService ).cleanLeadFollowupLog( Mockito.anyString() );
        leadFollowupErrorHandlerServiceTask.execute( execution );
    }
}
