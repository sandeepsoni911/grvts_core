package com.owners.gravitas.builder;

import org.joda.time.DateTime;
import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.amqp.LeadSource;
import com.owners.gravitas.business.builder.MortgageLeadNotificationBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * Test class for MortgageLeadNotificationBuilder
 * 
 * @author sandeepsoni
 *
 */
public class MortgageLeadNotificationBuilderTest extends AbstractBaseMockitoTest {

	/** The email notification builder. */
	@InjectMocks
	private MortgageLeadNotificationBuilder mortgageLeadNotificationBuilder;

	/**
	 * Test convert to.
	 */
	@Test
	public void testConvertTo() {
		LeadSource leadRequest = new LeadSource();
		leadRequest.setLastName("test");
		EmailNotification request = mortgageLeadNotificationBuilder.convertTo(leadRequest);
		Assert.assertNotNull(request);

		request = mortgageLeadNotificationBuilder.convertTo(leadRequest, new EmailNotification());
		Assert.assertNotNull(request);
	}

	/**
	 * Test convert to source null.
	 */
	@Test
	public void testConvertToSourceNull() {
		final EmailNotification request = mortgageLeadNotificationBuilder.convertTo(null);
		Assert.assertNull(request);
	}

	/**
	 * Test convert to destination null.
	 */
	@Test
	public void testConvertToDestinationNull() {
		LeadSource leadRequest = new LeadSource();
		leadRequest.setLastName("test");
		EmailNotification request = mortgageLeadNotificationBuilder.convertTo(leadRequest, null);
		Assert.assertNotNull(request);

		request = mortgageLeadNotificationBuilder.convertTo(null, null);
		Assert.assertNull(request);
	}

	/**
	 * Testconvert from.
	 */
	@Test(expectedExceptions = UnsupportedOperationException.class)
	public void testconvertFrom() {
		mortgageLeadNotificationBuilder.convertFrom(new EmailNotification());
	}

	/**
	 * Test convert to should format name if first name is not blank.
	 */
	@Test
	public void testConvertTo_shouldFormatNameIfFirstNameIsNotBlank() {
		LeadSource leadRequest = new LeadSource();
		leadRequest.setLastName("last");
		leadRequest.setFirstName("first");
		EmailNotification request = mortgageLeadNotificationBuilder.convertTo(leadRequest);
		Assert.assertNotNull(request);
		request = mortgageLeadNotificationBuilder.convertTo(leadRequest, new EmailNotification());
		Assert.assertNotNull(request);
		Assert.assertEquals(request.getEmail().getParameterMap().get("LEAD_FIRST_NAME"), "First");
		Assert.assertEquals(request.getEmail().getParameterMap().get("LEAD_LAST_NAME"), "Last");
	}
	
	
	/**
	 * Test convert to should extract zip from property address.
	 */
	@Test
	public void testConvertTo_getPropertyZipFromAddress() {
		LeadSource leadRequest = new LeadSource();
		leadRequest.setPropertyAddress("123 south west Avenue, Los Angeles CA 90012");
		EmailNotification request = mortgageLeadNotificationBuilder.convertTo(leadRequest);
		Assert.assertNotNull(request);
		request = mortgageLeadNotificationBuilder.convertTo(leadRequest, new EmailNotification());
		Assert.assertNotNull(request);
		Assert.assertEquals(request.getEmail().getParameterMap().get("PROPERTY_ZIP"), "90012");
	}
	
	/**
	 * Test convert to should extract zip from property address.
	 */
	@Test
	public void testConvertTo_getPropertyZipFromAddress_withoutZip() {
		LeadSource leadRequest = new LeadSource();
		leadRequest.setPropertyAddress("123 south west Avenue, Los Angeles CA");
		EmailNotification request = mortgageLeadNotificationBuilder.convertTo(leadRequest);
		Assert.assertNotNull(request);
		Assert.assertEquals(request.getEmail().getParameterMap().get("PROPERTY_ZIP"), "Not Available");
	}
	
	/**
	 * Test convert to should extract zip from property address.
	 */
	@Test
	public void testConvertTo_getPropertyZipFromAddress_withoutPropAddress() {
		LeadSource leadRequest = new LeadSource();
		leadRequest.setPropertyAddress("");
		EmailNotification request = mortgageLeadNotificationBuilder.convertTo(leadRequest);
		Assert.assertNotNull(request);
		Assert.assertNotNull(request);
		Assert.assertEquals(request.getEmail().getParameterMap().get("PROPERTY_ZIP"), "Not Available");
	}
	
	
	/**
	 * Test convert to format .
	 */
	@Test
	public void testConvertTo_formatDate() {
		LeadSource leadRequest = new LeadSource();
		leadRequest.setPropertyAddress("");
		leadRequest.setCreatedDateTime(new DateTime(2018, 2, 14, 12, 5, 0, 0));
		EmailNotification request = mortgageLeadNotificationBuilder.convertTo(leadRequest);
		Assert.assertNotNull(request);
		Assert.assertNotNull(request);
		Assert.assertEquals(request.getEmail().getParameterMap().get("CREATED_DATE"), "02/14/2018 12:05:00");
	}

}
