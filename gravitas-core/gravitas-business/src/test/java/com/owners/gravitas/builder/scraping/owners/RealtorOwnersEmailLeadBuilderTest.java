package com.owners.gravitas.builder.scraping.owners;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.scraping.owners.RealtorOwnersEmailLeadBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.LeadRequest;

/**
 * The Class RealtorEmailLeadBuilderTest.
 *
 * @author amits
 */
public class RealtorOwnersEmailLeadBuilderTest extends AbstractBaseMockitoTest {

    /** The realtor email lead builder. */
    @InjectMocks
    private RealtorOwnersEmailLeadBuilder realtorOwnersEmailLeadBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
    	final String messageText = "This is an automated inquiry sent by a REALTOR.com® consumer.  Please do not reply to this email.  Please contact Rebecca Rodriguez directly using the information below. First Name: Rebecca Last Name: Rodriguez Email Address: reva1973@msn.com Phone Number: 916-512-9236 I need: Tell me more about this property Comment: I'm interested in 8228 Aztec Ln Best way to reach me is through email or text. This consumer inquired about: Property Address: 8228 Aztec Ln Sacramento, CA 95828 MLSID # 16058954   Email target: Broker Basic Property Attributes: $69,000 Bed: 3 Bath: 2 Property Type: Mfd/Mobile Home View this listing on REALTOR.com®: http://www.realtor.com/realestateandhomes-detail/M21737-78664 Courtesy of: The Realtor.com® Marketing System";
        LeadRequest leadRequest = realtorOwnersEmailLeadBuilder.convertTo( messageText );
        Assert.assertNotNull( leadRequest );
    }
    
    /**
     * Test convert to with blank property address.
     */
    @Test
    public void testConvertToWithBlankPropertyAddress() {
    	final String messageText = "This is an automated inquiry sent by a REALTOR.com® consumer.  Please do not reply to this email.  Please contact Rebecca Rodriguez directly using the information below. First Name: Rebecca Last Name: Rodriguez Email Address: reva1973@msn.com Phone Number: 916-512-9236 I need: Tell me more about this property Comment: I'm interested in 8228 Aztec Ln Best way to reach me is through email or text. This consumer inquired about: Property Address: MLSID # 16058954   Email target: Broker Basic Property Attributes: $69,000 Bed: 3 Bath: 2 Property Type: Mfd/Mobile Home View this listing on REALTOR.com®: http://www.realtor.com/realestateandhomes-detail/M21737-78664 Courtesy of: The Realtor.com® Marketing System";
        LeadRequest leadRequest = realtorOwnersEmailLeadBuilder.convertTo( messageText );
        Assert.assertNotNull( leadRequest );
    }
    
}
