package com.owners.gravitas.delegate;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.BpmnError;
import org.flowable.engine.delegate.DelegateExecution;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.business.ActionFlowBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.domain.entity.OpportunityAction;
import com.owners.gravitas.enums.PushNotificationType;

/**
 * The Class LeadFollowupServiceTaskTest.
 *
 * @author amits
 */
public class ScriptedFlowCallTaskTest extends AbstractBaseMockitoTest {

	/** The scripted flow call task. */
	@InjectMocks
	private ScriptedFlowCallTask scriptedFlowCallTask;

	/** The execution. */
	@Mock
	private DelegateExecution execution;

	/** The runtime service. */
	@Mock
	private RuntimeService runtimeService;

	/** The action flow business service. */
	@Mock
	private ActionFlowBusinessService actionFlowBusinessService;

	/**
	 * Test execute.
	 */
	@Test
	public void testExecuteHappyFlow() {
		final String executionId = "testId";
		OpportunityAction oppAct = new OpportunityAction();
		oppAct.setCompleted(false);
		oppAct.setDeleted(false);
		Mockito.when(execution.getId()).thenReturn(executionId);
		Mockito.when(actionFlowBusinessService.getOpportunityAction(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(oppAct);
		scriptedFlowCallTask.execute(execution);

		Mockito.verify(actionFlowBusinessService, Mockito.times(1))
				.sendOpportunityPushNotifications(Mockito.anyString(), Mockito.any(PushNotificationType.class));
	}

	/**
	 * Test execute deleted true.
	 */
	@Test
	public void testExecuteDeletedTrue() {
		final String executionId = "testId";
		OpportunityAction oppAct = new OpportunityAction();
		oppAct.setCompleted(false);
		oppAct.setDeleted(true);
		Mockito.when(execution.getId()).thenReturn(executionId);
		Mockito.when(actionFlowBusinessService.getOpportunityAction(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(oppAct);
		scriptedFlowCallTask.execute(execution);

		Mockito.verify(actionFlowBusinessService, Mockito.times(0))
				.sendOpportunityPushNotifications(Mockito.anyString(), Mockito.any(PushNotificationType.class));
	}

	/**
	 * Test execute task completed true.
	 */
	@Test
	public void testExecuteTaskCompletedTrue() {
		final String executionId = "testId";
		OpportunityAction oppAct = new OpportunityAction();
		oppAct.setCompleted(true);
		oppAct.setDeleted(false);
		Mockito.when(execution.getId()).thenReturn(executionId);
		Mockito.when(actionFlowBusinessService.getOpportunityAction(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(oppAct);
		scriptedFlowCallTask.execute(execution);

		Mockito.verify(actionFlowBusinessService, Mockito.times(0))
				.sendOpportunityPushNotifications(Mockito.anyString(), Mockito.any(PushNotificationType.class));
	}

	/**
	 * Test execute exception.
	 */
	@Test(expectedExceptions = BpmnError.class)
	public void testExecuteException() {
		final String executionId = "testId";
		Mockito.when(execution.getId()).thenReturn("");
		Mockito.when(runtimeService.getVariable(executionId, Constants.LEAD)).thenReturn("");
		Mockito.doThrow(new RuntimeException()).when(actionFlowBusinessService)
				.getOpportunityAction(Mockito.anyString(), Mockito.anyString());
		scriptedFlowCallTask.execute(execution);
	}
}
