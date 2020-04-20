package com.owners.gravitas.builder.scraping.hubzu;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.scraping.hubzu.HubzuEmailLeadBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.LeadRequest;

/**
 * The Class HubzuEmailLeadBuilderTest.
 *
 * @author vishwanathm
 */
public class HubzuEmailLeadBuilderTest extends AbstractBaseMockitoTest {

    /** The hubzu email lead builder. */
    @InjectMocks
    private HubzuEmailLeadBuilder hubzuEmailLeadBuilder;

    /** The message text. */
    final String messageText = "Buyer's Message test  Buyer's Details Name: : test test Buyer Contact Number: : test Buyer Email: : test@test.com Property Details Property ID : test Property Address : test Please";

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        LeadRequest leadRequest = hubzuEmailLeadBuilder.convertTo( messageText );
        Assert.assertNotNull( leadRequest );
    }
}
