package com.owners.gravitas.builder.scraping.hubzu;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.scraping.hubzu.RealtorEmailLeadBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.LeadRequest;

/**
 * The Class RealtorEmailLeadBuilderTest.
 *
 * @author vishwanathm
 */
public class RealtorEmailLeadBuilderTest extends AbstractBaseMockitoTest {

    /** The realtor email lead builder. */
    @InjectMocks
    private RealtorEmailLeadBuilder realtorEmailLeadBuilder;

    /** The message text. */
    final String messageText = "New lead from realtor.com August 23, 2016 12:03 am ggggggggggggggggggggggggg gtest Name test Verified phone 12221 possible 12122121 test@test.com Property Details etwtwetwet Recent Search Activity MLS ID #12345678 test $";

    /** The message text1. */
    final String messageText1 = "New lead from realtor.com August 23, 2016 12:03 pm ggggggggggggggggggggggggg gtest Name test Verified phone 12221 possible 12122121 test@test.com Property Details etwtwetwet Recent Search Activity MLS ID #12345678 test $";

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        LeadRequest leadRequest = realtorEmailLeadBuilder.convertTo( messageText );
        Assert.assertNotNull( leadRequest );

        leadRequest = realtorEmailLeadBuilder.convertTo( messageText1 );
        Assert.assertNotNull( leadRequest );
    }
}
