package com.owners.gravitas.builder.scraping.hubzu;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.scraping.hubzu.UltraForcesEmailLeadBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.LeadRequest;

/**
 * The Class UltraForcesEmailLeadBuilderTest.
 *
 * @author vishwanathm
 */
public class UltraForcesEmailLeadBuilderTest extends AbstractBaseMockitoTest {

    /** The ultra forces email lead builder. */
    @InjectMocks
    private UltraForcesEmailLeadBuilder ultraForcesEmailLeadBuilder;

    /** The message text. */
    final String messageText = "Customer Name: test test Customer Email Address: test@test.com Customer Phone: 565465456 Message: test This email";

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        LeadRequest leadRequest = ultraForcesEmailLeadBuilder.convertTo( messageText );
        Assert.assertNotNull( leadRequest );
    }
}
