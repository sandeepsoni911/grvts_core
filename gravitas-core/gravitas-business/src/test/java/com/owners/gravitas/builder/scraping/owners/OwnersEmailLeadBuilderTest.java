package com.owners.gravitas.builder.scraping.owners;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.scraping.owners.OwnersEmailLeadBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.LeadRequest;

/**
 * The Class RealtorEmailLeadBuilderTest.
 *
 * @author vishwanathm
 */
public class OwnersEmailLeadBuilderTest extends AbstractBaseMockitoTest {

	/** The realtor email lead builder. */
	@InjectMocks
	private OwnersEmailLeadBuilder ownersEmailLeadBuilder;

	/**
	 * Test convert to.
	 */
	@Test
	public void testConvertTo() {
		final String messageText = "Source: Zillows First Name: Johnss Last Name: Doe Email: johnsdose@gmail.com Phone: 999-999-9999 Message: I would like more information regarding the property at 1702 Toplea Dr in Euless. Property Address: 1702 Toplea Dr, Euless, TX 76040 URL: http://www.zillow.com/homedetails/1702-Toplea-Dr-Euless-TX-76040/29157463_zpid/ Property State: TX Property Zip: 76040 Property/MLS ID: #13326384 Price: $88,500 Notes: Status Sale Pending/No Show; Days on market 123 days; Listing agent Michael Sweat; Broker Realhome Services And Solution.";
		LeadRequest leadRequest = ownersEmailLeadBuilder.convertTo( messageText );
		Assert.assertNotNull( leadRequest );
	}
	
	/**
	 * Test convert to.
	 */
	@Test
	public void testConvertToForBlankLeadSourceUrl() {
		final String messageText = "Source: Zillows First Name: Johnss Last Name: Doe Email: johnsdose@gmail.com Phone: 999-999-9999 Message: I would like more information regarding the property at 1702 Toplea Dr in Euless. Property Address: 1702 Toplea Dr, Euless, TX 76040 URL: Property State: TX Property Zip: 76040 Property/MLS ID: #13326384 Price: $88,500 Notes: Status Sale Pending/No Show; Days on market 123 days; Listing agent Michael Sweat; Broker Realhome Services And Solution.";
		LeadRequest leadRequest = ownersEmailLeadBuilder.convertTo( messageText );
		Assert.assertNotNull( leadRequest );
	}
	
}
