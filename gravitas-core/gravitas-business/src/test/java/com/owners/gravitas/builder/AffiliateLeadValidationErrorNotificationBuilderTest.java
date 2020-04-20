package com.owners.gravitas.builder;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.business.builder.AffiliateErrorNotificationBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.AffiliateEmailAttribute;

/**
 * The Class AffiliateLeadValidationErrorNotificationBuilderTest.
 *
 * @author amits
 */
public class AffiliateLeadValidationErrorNotificationBuilderTest extends AbstractBaseMockitoTest {

    /** The affiliate lead validation error notification builder. */
    @InjectMocks
    private AffiliateErrorNotificationBuilder affiliateErrorNotificationBuilder;

    /** The source object. */
    @Mock
    private EmailNotification sourceObject;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        affiliateErrorNotificationBuilder.convertTo( new AffiliateEmailAttribute(), sourceObject );
    }

    /**
     * Test convert to source as null.
     */
    @Test
    public void testConvertToSourceAsNull() {
        affiliateErrorNotificationBuilder.convertTo( null, sourceObject );
    }

    /**
     * Test convert to email notification as null.
     */
    @Test
    public void testConvertToEmailNotificationAsNull() {
        affiliateErrorNotificationBuilder.convertTo( new AffiliateEmailAttribute(), null );
    }

    /**
     * Test convert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFrom() {
        affiliateErrorNotificationBuilder.convertFrom( sourceObject, new AffiliateEmailAttribute() );
    }
}
