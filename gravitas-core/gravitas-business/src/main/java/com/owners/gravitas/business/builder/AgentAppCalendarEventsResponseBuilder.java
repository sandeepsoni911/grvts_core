package com.owners.gravitas.business.builder;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.owners.gravitas.domain.entity.AgentTask;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.dto.CalendarEvent;
import com.owners.gravitas.service.ContactEntityService;

@Component
public class AgentAppCalendarEventsResponseBuilder {

    private static final String SOURCE = "AGENT_APP";

    @Value( "${agent.meeting.duration}" )
    private int meetingDuration;
    
    /** The contact service V 1. */
    @Autowired
    private ContactEntityService contactServiceV1;

    public CalendarEvent convertFrom( AgentTask source, Contact contact ) {
        CalendarEvent calendarEvent = null;

        if (null != source && null != contact) {
            
            calendarEvent = new CalendarEvent();
            
            if (source.getScheduledDtm() != null) {
                calendarEvent.setStartTime( new DateTime( source.getScheduledDtm() ).getMillis() );
                calendarEvent.setEndTime(
                        new DateTime( source.getScheduledDtm() ).plusMinutes( meetingDuration ).getMillis() );
            }

            if (isNotBlank( source.getTitle() )) {
                calendarEvent.setSummary( source.getTitle() );
            }

            if (isNotBlank( source.getDescription() )) {
                calendarEvent.setDescription( source.getDescription() );
            }

            if (isNotBlank( source.getLocation() )) {
                calendarEvent.setLocation( source.getLocation() );
            }

            if (isNotBlank( source.getType() )) {
                calendarEvent.setTaskType( source.getType() );
            }

            List< String > attendeesName = new ArrayList<>();
            attendeesName.add( contactServiceV1.getUserName( contact ) );
            calendarEvent.setAttendeesName( attendeesName );
            
            calendarEvent.setTaskStatus( source.getStatus() );
            calendarEvent.setSource( SOURCE );
        }

        return calendarEvent;
    }
}
