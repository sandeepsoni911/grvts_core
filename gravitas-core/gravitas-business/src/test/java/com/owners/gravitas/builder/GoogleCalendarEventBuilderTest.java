package com.owners.gravitas.builder;

import org.mockito.InjectMocks;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.api.services.calendar.model.Event;
import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.business.builder.GoogleCalendarEventBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.Task;

/**
 * The Class EmailNotificationBuilderTest.
 *
 * @author vishwanathm
 */
public class GoogleCalendarEventBuilderTest extends AbstractBaseMockitoTest {

    /** The google calendar event builder. */
    @InjectMocks
    private GoogleCalendarEventBuilder googleCalendarEventBuilder;

    /**
     * Test convert to.
     */
    @Test
    public void testConvertTo() {
        Task task = new Task();
        task.setDueDtm( 1000l );
        Event request = googleCalendarEventBuilder.convertTo( task );
        Assert.assertNotNull( request );
    }

    /**
     * Test convert to source null.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testConvertFrom() {
        final Task request = googleCalendarEventBuilder.convertFrom( new Event() );
        Assert.assertNull( request );
    }
}
