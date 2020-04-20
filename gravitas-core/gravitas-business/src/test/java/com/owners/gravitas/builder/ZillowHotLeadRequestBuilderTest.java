package com.owners.gravitas.builder;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.request.ZillowHotLeadRequestBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.GenericLeadRequest;
import com.owners.gravitas.dto.request.LeadRequest;


/**
 * Test class for ZillowHotLeadRequestBuilder
 * @author sandeepsoni
 *
 */
public class ZillowHotLeadRequestBuilderTest extends AbstractBaseMockitoTest {
	
	 /** The web activity email notification builder. */
    @InjectMocks
    private ZillowHotLeadRequestBuilder zillowHotLeadRequestBuilder;
    
    @Test
    public void testConvertTo() {
    	LeadRequest source = new LeadRequest();
    	source.setPhone("98756789");
    	source.setContactId("contactId");
    	GenericLeadRequest genericLeadRequest = zillowHotLeadRequestBuilder.convertTo(source, null);
    	Assert.assertNotNull(genericLeadRequest);
    }
    
    
    @Test
    public void testConvertTo_lastName() {
    	LeadRequest source = new LeadRequest();
    	source.setPhone("98756789");
    	source.setContactId("contactId");
    	GenericLeadRequest genericLeadRequest = zillowHotLeadRequestBuilder.convertTo(source, null);
    	Assert.assertNotNull(genericLeadRequest);
    	Assert.assertNotNull(genericLeadRequest.getEmail());
    	Assert.assertEquals(genericLeadRequest.getLastName(), "contactId_NA");
    }
    
    @Test
    public void testConvertTo_Email() {
    	LeadRequest source = new LeadRequest();
    	source.setPhone("98756789");
    	source.setContactId("contactId");
    	GenericLeadRequest genericLeadRequest = zillowHotLeadRequestBuilder.convertTo(source, null);
    	Assert.assertNotNull(genericLeadRequest);
    	Assert.assertNotNull(genericLeadRequest.getEmail());
    }
    
    @Test
    public void testConvertTo_LeadSourceUrl() {
    	LeadRequest source = new LeadRequest();
    	source.setPhone("98756789");
    	source.setContactId("contactId");
    	GenericLeadRequest genericLeadRequest = zillowHotLeadRequestBuilder.convertTo(source, null);
    	Assert.assertNotNull(genericLeadRequest);
    	Assert.assertEquals(genericLeadRequest.getLeadSourceUrl(), "http://www.zillow.com");
    }
    
    @Test
    public void testConvertTo_LeadSource() {
    	LeadRequest source = new LeadRequest();
    	source.setPhone("98756789");
    	source.setContactId("contactId");
    	GenericLeadRequest genericLeadRequest = zillowHotLeadRequestBuilder.convertTo(source, null);
    	Assert.assertNotNull(genericLeadRequest);
    	Assert.assertEquals(genericLeadRequest.getSource(), "Zillow Hotline");
    }
    
    @Test
    public void testConvertTo_LeadType() {
    	LeadRequest source = new LeadRequest();
    	source.setPhone("98756789");
    	source.setContactId("contactId");
    	GenericLeadRequest genericLeadRequest = zillowHotLeadRequestBuilder.convertTo(source, null);
    	Assert.assertNotNull(genericLeadRequest);
    	Assert.assertEquals(genericLeadRequest.getLeadType(), "BUYER");
    }
    
    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testConvertFrom() {
    	GenericLeadRequest genericLeadRequest = new  GenericLeadRequest();
    	LeadRequest leadRequest = zillowHotLeadRequestBuilder.convertFrom(genericLeadRequest);
    }

}
