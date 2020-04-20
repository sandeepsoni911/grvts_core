package com.owners.gravitas.delegate.buyerFarming;

import static com.owners.gravitas.constants.Constants.LEAD;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.BuyerFarmingBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.exception.ActivitiException;
import com.owners.gravitas.lock.SyncCacheLockHandler;

/**
 * The Class CreateSaveSearchServiceTaskTest.
 *
 * @author amits
 */
public class CreateSaveSearchServiceTaskTest extends AbstractBaseMockitoTest {

	/** The create save search service task. */
	@InjectMocks
	private CreateSaveSearchServiceTask createSaveSearchServiceTask;

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
    private SyncCacheLockHandler syncCacheLock;

	/**
	 * Test execute.
	 */
	@Test
	public void testExecute() {
		final LeadSource leadSource = new LeadSource();
		leadSource.setEmail("zillow_hotLine_136891799_201806131831390785@zillow.com");
		Mockito.when(syncCacheLock.acquireLockBlocking(Mockito.anyString()))
        .thenReturn(true);
        Mockito.doNothing().when(syncCacheLock).releaseLock(Mockito.anyString());
		when(runtimeService.getVariable(execution.getId(), LEAD)).thenReturn(leadSource);
		createSaveSearchServiceTask.execute(execution);
		verify(runtimeService).getVariable(execution.getId(), LEAD);
	}

	/**
	 * Test execute when exception is thrown.
	 */
	@Test(expectedExceptions = ActivitiException.class)
	public void testExecuteWhenExceptionIsThrown() {
		final LeadSource leadSource = new LeadSource();
		leadSource.setEmail("someEmail@test.com");
		Mockito.when(syncCacheLock.acquireLockBlocking(Mockito.anyString()))
        .thenReturn(true);
        Mockito.doNothing().when(syncCacheLock).releaseLock(Mockito.anyString());
		when(runtimeService.getVariable(execution.getId(), LEAD)).thenReturn(leadSource);
		doThrow(Exception.class).when(buyerFarmingBusinessService).saveSearch(any(LeadSource.class));
		createSaveSearchServiceTask.execute(execution);
	}
}
