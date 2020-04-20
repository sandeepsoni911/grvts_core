package com.owners.gravitas.delegate.error.handler;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.ProcessBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.enums.GravitasProcess;
import com.owners.gravitas.exception.ActivitiException;

public class BuyerFarmingErrorHandlerTest extends AbstractBaseMockitoTest {
	@InjectMocks
	private BuyerFarmingErrorHandler buyerFarmingErrorHandler;

	/** The execution. */
	@Mock
	private DelegateExecution execution;

	/** The runtime service. */
	@Mock
	private RuntimeService runtimeService;

	/** The marketing email business service. */
	@Mock
	private ProcessBusinessService processBusinessService;

	/**
	 * Test execute.
	 *
	 * @throws Exception
	 */
	@Test
	public void testExecute() throws Exception {
		final LeadSource leadSource = new LeadSource();
		leadSource.setId("crmLeadId");

		final String executionId = "testId";
		Mockito.when(execution.getId()).thenReturn(executionId);
		Mockito.when(runtimeService.getVariable(executionId, Constants.ACTIVITI_EXCEPTION))
				.thenReturn(new ActivitiException("test", "test", GravitasProcess.LEAD_MANAGEMENT_PROCESS));
		Mockito.when(runtimeService.getVariable(executionId, Constants.LEAD)).thenReturn(leadSource);
		Mockito.when(runtimeService.getVariable(executionId, Constants.LOST_STATUS)).thenReturn(Boolean.FALSE);
		buyerFarmingErrorHandler.execute(execution);
		Mockito.verify(processBusinessService).deActivateProcess(Mockito.anyString(),
				Mockito.any(GravitasProcess.class));
	}

	@Test
	public void testExecuteError() throws Exception {
		final String executionId = "testId";
		Mockito.when(execution.getId()).thenReturn(executionId);
		Mockito.when(runtimeService.getVariable(executionId, Constants.ACTIVITI_EXCEPTION)).thenReturn(null);
		Mockito.when(runtimeService.getVariable(executionId, Constants.LEAD)).thenReturn("lead_id_test");
		Mockito.when(runtimeService.getVariable(executionId, Constants.LOST_STATUS)).thenReturn(Boolean.FALSE);
		buyerFarmingErrorHandler.execute(execution);
	}
}
