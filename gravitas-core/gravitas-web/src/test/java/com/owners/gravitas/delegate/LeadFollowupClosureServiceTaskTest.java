package com.owners.gravitas.delegate;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.business.LeadFollowupBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.constants.Constants;

/**
 * The Class LeadFollowupClosureServiceTaskTest.
 *
 * @author vishwanathm
 */
public class LeadFollowupClosureServiceTaskTest extends AbstractBaseMockitoTest {
    /** The lead status check service task. */
    @InjectMocks
    private LeadFollowupClosureServiceTask leadFollowupLogCleanupServiceTask;

    /** The marketing email business service. */
    @Mock
    private LeadFollowupBusinessService leadFollowupBusinessService;

    /** The execution. */
    @Mock
    private DelegateExecution execution;

    /** The runtime service. */
    @Mock
    private RuntimeService runtimeService;

    /** The lead business service. */
    @Mock
    private LeadBusinessService leadBusinessService;

    /**
     * Test execute.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testExecute() throws Exception {
        final LeadSource leadSource = new LeadSource();
        leadSource.setId( "crmLeadId" );
        final String executionId = "testId";
        Mockito.when( execution.getId() ).thenReturn( executionId );
        Mockito.when( runtimeService.getVariable( executionId, Constants.LEAD ) ).thenReturn( leadSource );
        Mockito.when( runtimeService.getVariable( executionId, Constants.LOST_STATUS ) ).thenReturn( Boolean.FALSE );
        Mockito.doNothing().when( leadFollowupBusinessService ).cleanLeadFollowupLog( Mockito.anyString() );
        leadFollowupLogCleanupServiceTask.execute( execution );
        Mockito.verify( leadFollowupBusinessService ).cleanLeadFollowupLog( Mockito.anyString() );
    }

    /**
     * Test execute error.
     *
     * @throws Exception
     *             the exception
     */
    @Test( expectedExceptions = Exception.class )
    public void testExecuteError() throws Exception {
        final String executionId = "testId";
        Mockito.when( execution.getId() ).thenReturn( executionId );
        Mockito.when( runtimeService.getVariable( executionId, Constants.LEAD ) ).thenReturn( null );
        Mockito.when( runtimeService.getVariable( executionId, Constants.LOST_STATUS ) ).thenReturn( Boolean.FALSE );
        Mockito.doNothing().when( leadFollowupBusinessService ).cleanLeadFollowupLog( Mockito.anyString() );
        leadFollowupLogCleanupServiceTask.execute( execution );
    }
}
