/**
 * 
 */
package com.owners.gravitas.dao.impl;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.helper.FirebaseRestAuthenticator;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.dto.FirebaseAccess;

/**
 * The Class RequestDaoImplTest.
 *
 * @author harshads
 */
public class RequestDaoImplTest extends AbstractBaseMockitoTest {

	/** The request dao impl. */
	@InjectMocks
	AgentRequestDaoImpl requestDaoImpl;

	/** The rest template. */
	@Mock
	private RestTemplate restTemplate;

	/** The authenticator. */
	@Mock
	private FirebaseRestAuthenticator authenticator;

	/**
	 * Save request test.
	 */
	@Test
	public void saveRequestTest() {
		Search search = new Search();
		search.setAgentEmail("test");
		search.setContactEmail("test");
		FirebaseAccess fbAcc = new FirebaseAccess();
		fbAcc.setAccessToken("accessToken");
		Mockito.when(authenticator.authenticate()).thenReturn(fbAcc);
		Object response = Mockito
				.when(restTemplate.exchange(Mockito.anyString(), Mockito.any(HttpMethod.class),
						Mockito.any(HttpEntity.class), Mockito.any(String.class.getClass())))
				.thenReturn(new ResponseEntity(null, HttpStatus.OK));
		requestDaoImpl.saveAgentRequest(new Request(), "");
		Assert.assertNotNull(response, "Response should not be null");
	}

}
