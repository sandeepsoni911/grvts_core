package com.owners.gravitas.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.api.client.util.ArrayMap;
import com.google.api.services.admin.directory.model.User;
import com.google.api.services.admin.directory.model.UserName;
import com.owners.gravitas.business.builder.AgentDtoBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.dto.Agent;
import com.owners.gravitas.service.AgentDetailsService;
import com.owners.gravitas.service.UserService;

public class AgentDtoBuilderTest extends AbstractBaseMockitoTest {

	/** The agent details builder. */
	@InjectMocks
	private AgentDtoBuilder agentDtoBuilder;

	/** The user service. */
	@Mock
	private UserService userService;

	/** The agent details service. */
	@Mock
	private AgentDetailsService agentDetailsService;

	@Test
	public void testConvertTo() {
		final User googleUser = new User();
		final UserName name = new UserName();
		name.setFullName("Test User");
		googleUser.setName(name);
		final AgentDetails agentDetails = new AgentDetails();
		final List<Map<String, Object>> list = new ArrayList();
		final Map<String, Object> phones = new ArrayMap();
		phones.put("type", "work");
		phones.put("value", "65465421");
		list.add(phones);
		googleUser.setPhones(list);
		final com.owners.gravitas.domain.entity.User user = new com.owners.gravitas.domain.entity.User();
		user.setStatus("Active");
		agentDetails.setUser(user);
		Mockito.when(userService.getUser(Mockito.anyString())).thenReturn(googleUser);
		Mockito.when(agentDetailsService.findAgentByEmail(Mockito.anyString())).thenReturn(agentDetails);
		final Agent agent = agentDtoBuilder.convertTo("a@a.com");
		Assert.assertNotNull(agent);
		Assert.assertEquals(agent.getStatus(), user.getStatus());

	}

	/**
	 * Test convert from witout source.
	 */
	@Test(expectedExceptions = UnsupportedOperationException.class)
	public void testConvertFrom() {
		agentDtoBuilder.convertFrom(new Agent());
	}
}
