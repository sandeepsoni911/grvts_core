package com.owners.gravitas.business.builder;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import com.google.api.services.calendar.model.Event;
import com.owners.gravitas.dto.CalendarEvent;

@Component
public class GoogleCalendarEventListBuilder
        extends AbstractBuilder< List< Event >, List< CalendarEvent > > {

    /** The Google calendar getEvent builder. */
    @Autowired
    private GoogleCalendarGetEventBuilder googleCalendarGetEventBuilder;

    @Override
    public List< CalendarEvent > convertTo( List< Event > source,
            List< CalendarEvent > destination ) {
        List< CalendarEvent > googleCalendarEventList = destination;
        if (!CollectionUtils.isEmpty( source )) {
            if (CollectionUtils.isEmpty( googleCalendarEventList )) {
                googleCalendarEventList = new ArrayList< CalendarEvent >();
            }
            for ( Event event : source ) {
                googleCalendarEventList.add( googleCalendarGetEventBuilder.convertTo( event ) );
            }
        }
        return googleCalendarEventList;
    }

    @Override
    public List< Event > convertFrom( List< CalendarEvent > source, List< Event > destination ) {
        throw new UnsupportedOperationException();
    }

}
