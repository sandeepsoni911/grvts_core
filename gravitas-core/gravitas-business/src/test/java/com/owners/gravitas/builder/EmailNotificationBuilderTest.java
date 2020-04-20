package com.owners.gravitas.builder;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.business.builder.EmailNotificationBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.request.GenericLeadRequest;

/**
 * The Class EmailNotificationBuilderTest.
 *
 * @author vishwanathm
 */
public class EmailNotificationBuilderTest extends AbstractBaseMockitoTest {

    /** The email notification builder. */
    @InjectMocks
    private EmailNotificationBuilder emailNotificationBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        GenericLeadRequest genReq = new GenericLeadRequest();
        genReq.setLastName( "test" );
        EmailNotification request = emailNotificationBuilder.convertTo( genReq );
        Assert.assertNotNull( request );

        request = emailNotificationBuilder.convertTo(genReq, new EmailNotification() );
        Assert.assertNotNull( request );
    }

    /**
     * Test convert to source null.
     */
    @Test
    public void testConvertToSourceNull() {
        final EmailNotification request = emailNotificationBuilder.convertTo( null );
        Assert.assertNull( request );
    }

    /**
     * Test convert to destination null.
     */
    @Test
    public void testConvertToDestinationNull() {
        GenericLeadRequest genReq = new GenericLeadRequest();
        genReq.setLastName( "test" );
        EmailNotification request = emailNotificationBuilder.convertTo( genReq, null );
        Assert.assertNotNull( request );

        request = emailNotificationBuilder.convertTo( null, null );
        Assert.assertNull( request );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        emailNotificationBuilder.convertFrom( new EmailNotification() );
    }

    /**
     * Test convert to should format name if first name is not blank.
     */
    @Test
    public void testConvertTo_shouldFormatNameIfFirstNameIsNotBlank() {
        final GenericLeadRequest genReq = new GenericLeadRequest();
        genReq.setFirstName( "test" );
        genReq.setLastName( "test" );
        EmailNotification request = emailNotificationBuilder.convertTo( genReq );
        Assert.assertNotNull( request );
        request = emailNotificationBuilder.convertTo( genReq, new EmailNotification() );
        Assert.assertNotNull( request );
    }

}
