package com.owners.gravitas.builder.scraping.hubzu;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.scraping.hubzu.TruliaEmailLeadBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.LeadRequest;

/**
 * The Class TruliaEmailLeadBuilderTest.
 *
 * @author vishwanathm
 */
public class TruliaEmailLeadBuilderTest extends AbstractBaseMockitoTest {

    /** The trulia email lead builder. */
    @InjectMocks
    private TruliaEmailLeadBuilder truliaEmailLeadBuilder;

    /** The message text. */
    final String messageText = "you have a new lead! From: test test Email: test@test.com Phone: 414124 Message: test View all your leads test This lead was sent from: test-test,test\" MLS ID: 122134 $ 234234 Need";

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        LeadRequest leadRequest = truliaEmailLeadBuilder.convertTo( messageText );
        Assert.assertNotNull( leadRequest );
    }
}
