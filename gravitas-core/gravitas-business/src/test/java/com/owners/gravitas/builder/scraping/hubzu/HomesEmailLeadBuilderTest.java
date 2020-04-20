package com.owners.gravitas.builder.scraping.hubzu;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.scraping.hubzu.HomesEmailLeadBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.LeadRequest;

/**
 * The Class HomesEmailLeadBuilderTest.
 *
 * @author vishwanathm
 */
public class HomesEmailLeadBuilderTest extends AbstractBaseMockitoTest {

    /** The homes email lead builder. */
    @InjectMocks
    private HomesEmailLeadBuilder homesEmailLeadBuilder;

    /** The message text. */
    final String messageText = "First Name: test Last Name: test Phone: test Email: test@tes.com Question/Comments: test Property Info: test Questions test (Listed by REALHome Services and Solutions) test $";

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        LeadRequest leadRequest = homesEmailLeadBuilder.convertTo( messageText );
        Assert.assertNotNull( leadRequest );
    }
}
