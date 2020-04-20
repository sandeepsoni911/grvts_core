package com.owners.gravitas.delegate.buyerFarming;

import static com.owners.gravitas.constants.Constants.LEAD;
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
import com.owners.gravitas.business.ProcessBusinessService;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.BuyerFarmingConfig;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.enums.GravitasProcess;
import com.owners.gravitas.exception.ActivitiException;
import com.owners.gravitas.service.ContactEntityService;

/**
 * The Class CheckForFarmingEligibilityServiceTaskTest.
 *
 * @author amits
 */
public class CheckForFarmingEligibilityServiceTaskTest extends AbstractBaseMockitoTest {

	/** The check for buyer farming eligibility service task. */
	@InjectMocks
	private CheckForFarmingEligibilityServiceTask checkForFarmingEligibilityServiceTask;

	/** The buyer farming business service. */
	@Mock
	private BuyerFarmingBusinessService buyerFarmingBusinessService;

	/** The contact service v1. */
	@Mock
	private ContactEntityService contactServiceV1;

	/** The process management business service. */
	@Mock
	protected ProcessBusinessService processBusinessService;

	/** The buyer registration config. */
	@Mock
	private BuyerFarmingConfig buyerFarmingConfig;

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
		final Contact contact = new Contact();
		contact.setOwnersComId("test");
		when(runtimeService.getVariable(execution.getId(), LEAD)).thenReturn(leadSource);
		when(buyerFarmingConfig.isBuyerAutoRegistrationEnabled()).thenReturn(true);
		when(buyerFarmingBusinessService.isBuyerAutoRegistrationEmail(Mockito.anyString())).thenReturn(true);
	    when(buyerFarmingBusinessService.isFarmLongTermState(Mockito.anyString())).thenReturn(true);
		when(contactServiceV1.getContact(Mockito.anyString(), Mockito.anyString())).thenReturn(contact);
		//checkForFarmingEligibilityServiceTask.execute(execution);
		//verify(runtimeService).getVariable(execution.getId(), LEAD);
		//verify(runtimeService).setVariable(execution.getId(), "isFarmingRequired", true);
	}

	/**
	 * Test execute when exception is thrown.
	 */
	@Test(expectedExceptions = ActivitiException.class)
	public void testExecuteWhenExceptionIsThrown() {
		final LeadSource leadSource = new LeadSource();
		final Contact contact = new Contact();
		contact.setOwnersComId("test");
		when(runtimeService.getVariable(execution.getId(), LEAD)).thenReturn(leadSource);
		when(buyerFarmingConfig.isBuyerAutoRegistrationEnabled()).thenReturn(true);
		when(buyerFarmingBusinessService.isBuyerAutoRegistrationEmail(Mockito.anyString())).thenReturn(true);
        when(buyerFarmingBusinessService.isFarmLongTermState(Mockito.anyString())).thenReturn(true);
		when(contactServiceV1.getContact(Mockito.anyString(), Mockito.anyString())).thenReturn(contact);
		doThrow(Exception.class).when(processBusinessService).deActivateAndSignal(Mockito.anyString(),
				Mockito.any(GravitasProcess.class), Mockito.anyMap());
		checkForFarmingEligibilityServiceTask.execute(execution);
	}
}
