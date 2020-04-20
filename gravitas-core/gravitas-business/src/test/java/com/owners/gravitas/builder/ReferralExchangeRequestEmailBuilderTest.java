package com.owners.gravitas.builder;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.business.builder.request.ReferralExchangeRequestEmailBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The Class ReferralExchangeRequestEmailBuilderTest.
 *
 * @author Madhav
 */
public class ReferralExchangeRequestEmailBuilderTest extends AbstractBaseMockitoTest {

    /** The email notification builder. */
    @InjectMocks
    private ReferralExchangeRequestEmailBuilder emailNotificationBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        List< String > emailList = new ArrayList< String >();
        emailList.add( "test@test.com" );
        String fullName = "test";
        EmailNotification notification = emailNotificationBuilder.convertTo( fullName, emailList );
        Assert.assertNotNull( notification );
        Assert.assertEquals( fullName, notification.getEmail().getParameterMap().get( "NAME" ) );
    }

    /**
     * Test convert to expect exception.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertToExpectException() {
        emailNotificationBuilder.convertTo( "test", new EmailNotification() );
    }

    /**
     * Test convert from expect exception.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFromExpectException() {
        emailNotificationBuilder.convertFrom( new EmailNotification(), "test" );
    }
}
