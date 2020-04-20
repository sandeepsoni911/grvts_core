package com.owners.gravitas.builder.scraping.hubzu;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.scraping.hubzu.HomeBidzEmailLeadBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.LeadRequest;

/**
 * The Class HomeBidzEmailLeadBuilderTest.
 *
 * @author vishwanathm
 */
public class HomeBidzEmailLeadBuilderTest extends AbstractBaseMockitoTest {

    /** The home bidz email lead builder. */
    @InjectMocks
    private HomeBidzEmailLeadBuilder homeBidzEmailLeadBuilder;

    /** The message text. */
    final String messageText = "Info: test test test test@test.com Message: test - Property listings by homebidz.co";

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        LeadRequest leadRequest = homeBidzEmailLeadBuilder.convertTo( messageText );
        Assert.assertNotNull( leadRequest );
    }
}
