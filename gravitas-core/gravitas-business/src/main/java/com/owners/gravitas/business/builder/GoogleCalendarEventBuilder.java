package com.owners.gravitas.business.builder;

import static java.lang.Boolean.FALSE;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.owners.gravitas.domain.Task;

/**
 * The Class GoogleCalendarEventBuilder.
 *
 * @author shivamm
 */
@Component
public class GoogleCalendarEventBuilder extends AbstractBuilder< Task, Event > {

    /** The Constant POP_UP. */
    public static final String POP_UP = "popup";

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public Event convertTo( final Task source, final Event destination ) {
        Event googleCalendarEvent = destination;
        if (source != null) {
            if (googleCalendarEvent == null) {
                googleCalendarEvent = new Event();
            }
            final DateTime startDateTime = new DateTime( source.getDueDtm() );
            final EventDateTime start = new EventDateTime().setDateTime( startDateTime );

            org.joda.time.DateTime jodaEndTime = new org.joda.time.DateTime( source.getDueDtm() );
            jodaEndTime = jodaEndTime.plusMinutes( 60 );

            final DateTime endDateTime = new DateTime( jodaEndTime.getMillis() );
            final EventDateTime end = new EventDateTime().setDateTime( endDateTime );

            final EventReminder[] reminderOverrides = new EventReminder[] {
                    new EventReminder().setMethod( POP_UP ).setMinutes( 30 ), };
            final Event.Reminders reminders = new Event.Reminders().setUseDefault( FALSE )
                    .setOverrides( Arrays.asList( reminderOverrides ) );

            googleCalendarEvent.setLocation( source.getLocation() );
            googleCalendarEvent.setDescription( source.getDescription() );
            googleCalendarEvent.setStart( start );
            googleCalendarEvent.setEnd( end );
            googleCalendarEvent.setReminders( reminders );
        }
        return googleCalendarEvent;
    }

    /** Method not supported. */
    @Override
    public Task convertFrom( final Event source, final Task destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }
}
