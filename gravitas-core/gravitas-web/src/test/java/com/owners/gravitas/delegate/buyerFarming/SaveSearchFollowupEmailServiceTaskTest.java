package com.owners.gravitas.delegate.buyerFarming;

import static com.owners.gravitas.constants.Constants.LEAD;
import static com.owners.gravitas.enums.FollowupType.FOLLOW_UP_2;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.ActivitiBusinessService;
import com.owners.gravitas.business.BuyerFarmingBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.exception.ActivitiException;

/**
 * The Class SaveSearchFollowupEmailServiceTaskTest.
 *
 * @author raviz
 */
public class SaveSearchFollowupEmailServiceTaskTest extends AbstractBaseMockitoTest {

    /** The save search followup email service task. */
    @InjectMocks
    private SaveSearchFollowupEmailServiceTask saveSearchFollowupEmailServiceTask;

    /** The buyer farming business service. */
    @Mock
    private BuyerFarmingBusinessService buyerFarmingBusinessService;

    /** The runtime service. */
    @Mock
    protected RuntimeService runtimeService;

    /** The execution. */
    @Mock
    private DelegateExecution execution;

    @Mock
    private ActivitiBusinessService activitiBusinessService;

    /**
     * Test execute.
     */
    //@Test
    public void testExecute() {
        final LeadSource leadSource = new LeadSource();
        final String executionId = "10057";
        when(activitiBusinessService.findLatestExecutionId(execution.getProcessInstanceId())).thenReturn(executionId);
        when( runtimeService.getVariable( executionId, LEAD ) ).thenReturn( leadSource );
        saveSearchFollowupEmailServiceTask.execute( execution );
        verify( runtimeService ).getVariable( executionId, LEAD );
        verify( buyerFarmingBusinessService ).sendFollowupEmail( executionId, leadSource );
        verify( runtimeService ).setVariable( executionId, "FOLLOWUP_TYPE", FOLLOW_UP_2 );
    }

    /**
     * Test execute when exception is thrown.
     */
    @Test( expectedExceptions = ActivitiException.class )
    public void testExecuteWhenExceptionIsThrown() {
        final LeadSource leadSource = new LeadSource();
        when( runtimeService.getVariable( execution.getId(), LEAD ) ).thenReturn( leadSource );
        doThrow( Exception.class ).when( buyerFarmingBusinessService ).sendFollowupEmail( anyString(), any() );
        saveSearchFollowupEmailServiceTask.execute( execution );
    }
}
