package com.owners.gravitas.builder;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.AgentAssignmentLogBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.HappyAgentsConfig;
import com.owners.gravitas.domain.entity.AgentAssignmentLog;
import com.owners.gravitas.dto.AgentAssignmentLogDto;

/**
 * The Class AgentAssignmentLogBuilderTest.
 */
public class AgentAssignmentLogBuilderTest extends AbstractBaseMockitoTest {

	/** The action log audit builder. */
	@InjectMocks
	private AgentAssignmentLogBuilder agentAssignmentLogBuilder;
	/** The happy agents config. */
	@Mock
	private HappyAgentsConfig happyAgentsConfig;

	/**
	 * Test convert to for average agent type.
	 */
	@Test
	public void testConvertToForAverageAgentType() {
		Mockito.when(happyAgentsConfig.getAgentTypeAverageLowThresholdScore()).thenReturn(20d);
		Mockito.when(happyAgentsConfig.getAgentTypeAverageHighThresholdScore()).thenReturn(40d);
		Mockito.when(happyAgentsConfig.getAgentTypeNewThresholdScore()).thenReturn(100d);
		Mockito.when(happyAgentsConfig.getAgentTypeGoodLowThresholdScore()).thenReturn(40d);
		Mockito.when(happyAgentsConfig.getAgentTypeGoodHighThresholdScore()).thenReturn(100d);
		AgentAssignmentLog log = agentAssignmentLogBuilder
				.convertTo(new AgentAssignmentLogDto(null, 21, null, null, null));
		Assert.assertNotNull(log);
		Assert.assertEquals(log.getAgentType(), "average");
	}

	/**
	 * Test convert to for good agent type.
	 */
	@Test
	public void testConvertToForGoodAgentType() {
		Mockito.when(happyAgentsConfig.getAgentTypeAverageLowThresholdScore()).thenReturn(20d);
		Mockito.when(happyAgentsConfig.getAgentTypeAverageHighThresholdScore()).thenReturn(40d);
		Mockito.when(happyAgentsConfig.getAgentTypeNewThresholdScore()).thenReturn(100d);
		Mockito.when(happyAgentsConfig.getAgentTypeGoodLowThresholdScore()).thenReturn(40d);
		Mockito.when(happyAgentsConfig.getAgentTypeGoodHighThresholdScore()).thenReturn(100d);
		AgentAssignmentLog log = agentAssignmentLogBuilder
				.convertTo(new AgentAssignmentLogDto(null, 51, null, null, null));
		Assert.assertNotNull(log);
		Assert.assertEquals(log.getAgentType(), "good");
	}

	/**
	 * Test convert to for new agent type.
	 */
	@Test
	public void testConvertToForNewAgentType() {
		Mockito.when(happyAgentsConfig.getAgentTypeAverageLowThresholdScore()).thenReturn(20d);
		Mockito.when(happyAgentsConfig.getAgentTypeAverageHighThresholdScore()).thenReturn(40d);
		Mockito.when(happyAgentsConfig.getAgentTypeNewThresholdScore()).thenReturn(100d);
		Mockito.when(happyAgentsConfig.getAgentTypeGoodLowThresholdScore()).thenReturn(40d);
		Mockito.when(happyAgentsConfig.getAgentTypeGoodHighThresholdScore()).thenReturn(100d);
		AgentAssignmentLog log = agentAssignmentLogBuilder
				.convertTo(new AgentAssignmentLogDto(null, 100, null, null, null));
		Assert.assertNotNull(log);
		Assert.assertEquals(log.getAgentType(), "new");
	}

	/**
	 * Test convert from witout source.
	 */
	@Test(expectedExceptions = UnsupportedOperationException.class)
	public void testConvertFrom() {
		AgentAssignmentLogDto log = agentAssignmentLogBuilder.convertFrom(new AgentAssignmentLog());
	}

}
