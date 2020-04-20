package com.owners.gravitas.builder.scraping.hubzu;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.scraping.hubzu.ZillowEmailLeadBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.LeadRequest;

/**
 * The Class ZillowEmailLeadBuilderTest.
 *
 * @author vishwanathm
 */
public class ZillowEmailLeadBuilderTest extends AbstractBaseMockitoTest {

    /** The zillow email lead builder. */
    @InjectMocks
    private ZillowEmailLeadBuilder zillowEmailLeadBuilder;

    /** The message text. */
    final String messageText = "New Contact from Zillow test test says: test Respond within 10 minutes for best results. test@test.com 23525 Manage or forward this contact on Zillow. test Listing information test Listed by <a href=\"https://click.mail.zillow.com none\">test </span></a>";

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        LeadRequest leadRequest = zillowEmailLeadBuilder.convertTo( messageText );
        Assert.assertNotNull( leadRequest );
    }
}
