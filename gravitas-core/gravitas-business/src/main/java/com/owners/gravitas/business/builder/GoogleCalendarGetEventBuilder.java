package com.owners.gravitas.business.builder;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.owners.gravitas.dto.CalendarEvent;

@Component
public class GoogleCalendarGetEventBuilder extends AbstractBuilder< Event, CalendarEvent > {
    
    private static final String SOURCE = "GOOGLE_CALENDAR";

    @Override
    public CalendarEvent convertTo( final Event source, final CalendarEvent destination ) {
        CalendarEvent calendarEvent = destination;
        if (source != null) {
            if (calendarEvent == null) {
                calendarEvent = new CalendarEvent();
            }
            calendarEvent.setDescription( source.getDescription() );
            calendarEvent.setSummary( source.getSummary() );
            DateTime start = source.getStart().getDateTime();
            if (start == null) {
                start = source.getStart().getDate();
            }
            calendarEvent.setStartTime( start.getValue() );
            DateTime end = source.getEnd().getDateTime();
            if (end == null) {
                end = source.getEnd().getDate();
            }
            calendarEvent.setEndTime( end.getValue() );
            calendarEvent.setLocation( source.getLocation() );
            calendarEvent.setSource( SOURCE );
            if (!CollectionUtils.isEmpty( source.getAttendees() )) {
                List< String > attendeeNameList = new ArrayList<>();
                source.getAttendees().stream().filter( attendee -> StringUtils.isNotEmpty( attendee.getDisplayName() ) )
                        .forEach( attendee -> attendeeNameList.add( attendee.getDisplayName() ) );
                calendarEvent.setAttendeesName( attendeeNameList );
            }
        }
        return calendarEvent;
    }

    @Override
    public Event convertFrom( CalendarEvent source, Event destination ) {
        throw new UnsupportedOperationException();
    }

}
