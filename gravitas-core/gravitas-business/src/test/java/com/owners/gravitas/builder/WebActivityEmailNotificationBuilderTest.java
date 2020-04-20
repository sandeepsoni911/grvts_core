package com.owners.gravitas.builder;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.testng.annotations.Test;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.business.builder.WebActivityEmailNotificationBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * The Class WebActivityEmailNotificationBuilderTest.
 *
 * @author amits
 */
public class WebActivityEmailNotificationBuilderTest extends AbstractBaseMockitoTest {

    /** The web activity email notification builder. */
    @InjectMocks
    private WebActivityEmailNotificationBuilder webActivityEmailNotificationBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        final Map< String, Object > map = new HashMap<>();
        map.put( "fromEmail", "test" );
        map.put( "name", "test" );
        map.put( "addressLine", "test" );
        map.put( "toEmail", "test" );
        webActivityEmailNotificationBuilder.convertTo( map );
    }

    /**
     * Testconvert from.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertFrom() {
        webActivityEmailNotificationBuilder.convertFrom( new EmailNotification() );
    }
}
