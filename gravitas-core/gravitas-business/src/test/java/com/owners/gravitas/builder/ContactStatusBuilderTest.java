package com.owners.gravitas.builder;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.request.ContactStatusBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.ContactStatus;
import com.owners.gravitas.dto.request.LeadRequest;

/**
 * Test class for ContactStatusBuilder
 * @author sandeepsoni
 *
 */
public class ContactStatusBuilderTest extends AbstractBaseMockitoTest {
	
	/** The ContactStatusBuilder  */
	@InjectMocks
	ContactStatusBuilder contactStatusBuilder;

	/**
	 * To test convertTo
	 * method
	 */
	@Test
	public void test_convertTo() {
		LeadRequest request = new LeadRequest();
		request.setRequestType("Buyer");
		request.setSource("Source");
		request.setLeadType("BUYER");
		request.setEmail("someEmail@mail.com");
		
		ContactStatus contactStatus = contactStatusBuilder.convertTo(request);
		
		Assert.assertNotNull(contactStatus);
		Assert.assertEquals(contactStatus.getEmail(), "someEmail@mail.com");
	}
	
	
}
