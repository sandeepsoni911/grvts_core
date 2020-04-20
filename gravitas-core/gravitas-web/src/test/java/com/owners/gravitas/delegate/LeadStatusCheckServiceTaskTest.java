package com.owners.gravitas.delegate;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.LeadBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.dto.crm.response.CRMLeadResponse;
import com.owners.gravitas.enums.LeadStatus;

/**
 * The Class LeadStatusCheckServiceTaskTest.
 *
 * @author vishwanathm
 */
public class LeadStatusCheckServiceTaskTest extends AbstractBaseMockitoTest {

    /** The lead status check service task. */
    @InjectMocks
    private LeadStatusCheckServiceTask leadStatusCheckServiceTask;

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
     */
    @Test
    public void testExecuteOutboundAttempt() {
        final String executionId = "testId";
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setEmail( "email@email.com" );
        leadResponse.setFirstName( "firstName" );
        leadResponse.setLeadStatus( LeadStatus.OUTBOUND_ATTEMPT.getStatus() );
        final LeadSource leadSource = new LeadSource();
        leadSource.setId( "crmLeadId" );
        Mockito.when( execution.getId() ).thenReturn( executionId );
        Mockito.when( runtimeService.getVariable( executionId, Constants.LEAD ) ).thenReturn( leadSource );
        Mockito.when( leadBusinessService.getCRMLead( Mockito.anyString() ) ).thenReturn( leadResponse );
        leadStatusCheckServiceTask.execute( execution );
        Mockito.verify( runtimeService, Mockito.times( 2 ) ).setVariable( Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString() );
    }

    /**
     * Test execute new status.
     */
    @Test
    public void testExecuteNewStatus() {
        final String executionId = "testId";
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setEmail( "email@email.com" );
        leadResponse.setFirstName( "firstName" );
        leadResponse.setLeadStatus( LeadStatus.NEW.getStatus() );
        final LeadSource leadSource = new LeadSource();
        leadSource.setId( "crmLeadId" );
        Mockito.when( execution.getId() ).thenReturn( executionId );
        Mockito.when( runtimeService.getVariable( executionId, Constants.LEAD ) ).thenReturn( leadSource );
        Mockito.when( leadBusinessService.getCRMLead( Mockito.anyString() ) ).thenReturn( leadResponse );
        leadStatusCheckServiceTask.execute( execution );
        Mockito.verify( runtimeService, Mockito.times( 2 ) ).setVariable( Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString() );
    }

    /**
     * Test execute exception.
     */
    @Test( expectedExceptions = Exception.class )
    public void testExecuteException() {
        final String executionId = "testId";
        final CRMLeadResponse leadResponse = new CRMLeadResponse();
        leadResponse.setEmail( "email@email.com" );
        leadResponse.setFirstName( "firstName" );
        leadResponse.setLeadStatus( LeadStatus.NEW.getStatus() );
        Mockito.when( execution.getId() ).thenReturn( executionId );
        Mockito.when( runtimeService.getVariable( executionId, Constants.LEAD ) ).thenReturn( null );
        Mockito.when( leadBusinessService.getCRMLead( "lead_id_test" ) ).thenReturn( leadResponse );
        leadStatusCheckServiceTask.execute( execution );
        Mockito.verify( runtimeService, Mockito.times( 3 ) ).setVariable( Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString() );
    }

}
