package com.owners.gravitas.service;

import java.util.List;

import com.google.api.services.calendar.model.Event;

/**
 * The Interface CalendarService.
 */
public interface CalendarService {

    /**
     * Sync event with google calendar.
     *
     * @param agentEmail
     *            the agent email
     * @param googleCalendarEvent
     *            the google calendar event
     * @return the event
     */
    Event createEvent( final String agentEmail, Event googleCalendarEvent );

    /**
     * 
     * @param agentEmail
     * @param timeMin
     * @param timeMax
     * @return
     */
    List< Event > getEvents( final String email, final Long timeMin, final Long timeMax );
}
