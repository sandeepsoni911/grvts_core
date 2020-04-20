package com.owners.gravitas.delegate.buyerFarming;

import static com.owners.gravitas.constants.Constants.LEAD;
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
import com.owners.gravitas.business.BuyerFarmingBusinessService;
import com.owners.gravitas.business.ProcessBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.exception.ActivitiException;

/**
 * The Class PrepareForInsideSaleProcessServiceTaskTest.
 *
 * @author amits
 */
public class PrepareForInsideSaleProcessServiceTaskTest extends AbstractBaseMockitoTest {

	@InjectMocks
	private PrepareForInsideSaleProcessServiceTask prepareForInsideSaleProcessServiceTask;

	/** The buyer farming business service. */
	@Mock
	private BuyerFarmingBusinessService buyerFarmingBusinessService;

	/** The process management business service. */
	@Mock
	protected ProcessBusinessService processBusinessService;

	/** The runtime service. */
	@Mock
	protected RuntimeService runtimeService;

	/** The execution. */
	@Mock
	private DelegateExecution execution;

	/**
	 * Test execute.
	 */
	@Test
	public void testExecute() {
		final LeadSource leadSource = new LeadSource();
		when(runtimeService.getVariable(execution.getId(), LEAD)).thenReturn(leadSource);
		prepareForInsideSaleProcessServiceTask.execute(execution);
		verify(runtimeService).getVariable(execution.getId(), LEAD);
	}

	/**
	 * Test execute when exception is thrown.
	 */
	@Test(expectedExceptions = ActivitiException.class)
	public void testExecuteWhenExceptionIsThrown() {
		final LeadSource leadSource = new LeadSource();
		when(runtimeService.getVariable(execution.getId(), LEAD)).thenReturn(leadSource);
		doThrow(Exception.class).when(processBusinessService).createProcess(anyString(), anyString(), anyString(),
				any());
		prepareForInsideSaleProcessServiceTask.execute(execution);
	}
}
