package com.owners.gravitas.delegate;

import static org.mockito.Mockito.verify;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.BpmnError;
import org.flowable.engine.delegate.DelegateExecution;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.LeadFollowupBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.constants.Constants;

/**
 * The Class LeadFollowupServiceTaskTest.
 *
 * @author vishwanathm
 */
public class LeadFollowupServiceTaskTest extends AbstractBaseMockitoTest {

    /** The lead status check service task. */
    @InjectMocks
    private LeadFollowupServiceTask leadFollowupServiceTask;

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
     */
    @Test
    public void testExecute() {
        final LeadSource leadSource = new LeadSource();
        leadSource.setId( "crmLeadId" );
        final String executionId = "testId";
        Mockito.when( execution.getId() ).thenReturn( executionId );
        Mockito.when( runtimeService.getVariable( executionId, Constants.LEAD ) ).thenReturn( leadSource );
        Mockito.doNothing().when( leadFollowupBusinessService ).sendLeadFollowupEmails( executionId, leadSource );
        leadFollowupServiceTask.execute( execution );

        verify( leadFollowupBusinessService ).sendLeadFollowupEmails( Mockito.anyString(),
                Mockito.any( LeadSource.class ) );
    }

    /**
     * Test execute exception.
     */
    @Test( expectedExceptions = BpmnError.class )
    public void testExecuteException() {
        final String executionId = "testId";
        Mockito.when( execution.getId() ).thenReturn( "" );
        Mockito.when( runtimeService.getVariable( executionId, Constants.LEAD ) ).thenReturn( "" );
        Mockito.doThrow( new RuntimeException() ).when( leadFollowupBusinessService )
                .sendLeadFollowupEmails( Mockito.anyString(), Mockito.any( LeadSource.class ) );
        leadFollowupServiceTask.execute( execution );
    }
}
