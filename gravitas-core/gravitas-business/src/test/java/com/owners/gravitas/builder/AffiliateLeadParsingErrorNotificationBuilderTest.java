package com.owners.gravitas.builder;

import static com.owners.gravitas.constants.Constants.NOTIFICATION_CLIENT_ID;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.business.builder.AffiliateErrorNotificationBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.AffiliateEmailAttribute;

/**
 * The Class AffiliateLeadParsingErrorNotificationBuilderTest.
 *
 * @author amits
 */
public class AffiliateLeadParsingErrorNotificationBuilderTest extends AbstractBaseMockitoTest {

    /** The affiliate lead parsing error notification builder. */
    @InjectMocks
    private AffiliateErrorNotificationBuilder affiliateErrorNotificationBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        final EmailNotification notification = affiliateErrorNotificationBuilder
                .convertTo( new AffiliateEmailAttribute(), new EmailNotification() );
        Assert.assertNotNull( notification );
        Assert.assertEquals( notification.getClientId(), NOTIFICATION_CLIENT_ID );
    }

    /**
     * Test convert to source as null.
     */
    @Test
    public void testConvertToSourceAsNull() {
        final EmailNotification notification = affiliateErrorNotificationBuilder.convertTo( null,
                new EmailNotification() );
        Assert.assertNotNull( notification );
    }

    /**
     * Test convert to email notification as null.
     */
    @Test
    public void testConvertToEmailNotificationAsNull() {
        final EmailNotification notification = affiliateErrorNotificationBuilder
                .convertTo( new AffiliateEmailAttribute(), null );
        Assert.assertNotNull( notification );
    }

    /**
     * Test convert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFrom() {
        affiliateErrorNotificationBuilder.convertFrom( new EmailNotification(), new AffiliateEmailAttribute() );
    }
}
