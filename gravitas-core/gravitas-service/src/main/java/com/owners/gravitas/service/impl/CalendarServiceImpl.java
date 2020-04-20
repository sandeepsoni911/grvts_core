package com.owners.gravitas.service.impl;

import static com.google.api.services.calendar.CalendarScopes.CALENDAR;
import static com.owners.gravitas.constants.Constants.GRAVITAS;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.CalendarService;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class CalendarServiceImpl.
 *
 * @author shivamm
 */
@Service
public class CalendarServiceImpl extends BaseGoogleService implements CalendarService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( CalendarServiceImpl.class );

    /** The Constant SCOPES. */
    private static final List< String > CALENDAR_SCOPES = Arrays.asList( CALENDAR );

    /** The Constant CALENDAR_ID. */
    public static final String CALENDAR_ID = "primary";

    /**
     * Creates the event.
     *
     * @param agentEmail
     *            the agent email
     * @param event
     *            the event
     * @return the event
     */
    @Override
    public Event createEvent( final String agentEmail, final Event event ) {
        final Calendar calendarService = getCalendarService( agentEmail );
        Event savedEvent = null;
        try {
            savedEvent = calendarService.events().insert( CALENDAR_ID, event ).execute();
        } catch ( IOException exp ) {
            LOGGER.error( "Problem in adding event in google calendar: " + exp.getLocalizedMessage(), exp );
            throw new ApplicationException( "Problem in adding event in google calendar: " + exp.getLocalizedMessage(),
                    exp );
        }
        return savedEvent;
    }

    /**
     * Gets the calendar service.
     *
     * @param agentEmail
     *            the agent email
     * @return the calendar service
     */
    private Calendar getCalendarService( final String agentEmail ) {
        try {
            final HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            return new Calendar.Builder( HTTP_TRANSPORT, JSON_FACTORY,
                    getCredential( agentEmail, HTTP_TRANSPORT, CALENDAR_SCOPES ) ).setApplicationName( GRAVITAS )
                            .build();
        } catch ( GeneralSecurityException | IOException exp ) {
            LOGGER.error( "Problem in getting Calendar service: " + exp.getLocalizedMessage(), exp );
            throw new ApplicationException( "Problem in getting Calendar service: " + exp.getLocalizedMessage(), exp );
        }
    }

    /**
     * 
     * @param agentEmail
     * @param timeMin
     * @param timeMax
     * @return
     */
    @Override
    public List< Event > getEvents( final String email, final Long timeMin, final Long timeMax ) {
        LOGGER.info( "getEvents for emailId : {} timeMin and timeMax : {}", email, timeMin, timeMax );
        final Calendar calendarService = getCalendarService( email );
        Events events = null;
        List< Event > calendarEvents = new ArrayList<>();
        DateTime startDateTime = timeMin != null ? new DateTime( timeMin ) : null;
        DateTime endDateTime = timeMax != null ? new DateTime( timeMax ) : null;
        try {
            String pageToken = null;
            do {
                events = calendarService.events().list( CALENDAR_ID ).setPageToken( pageToken )
                        .setTimeMin( startDateTime ).setTimeMax( endDateTime ).setOrderBy( "startTime" )
                        .setSingleEvents( true ).execute();
                calendarEvents = events.getItems();
                pageToken = events.getNextPageToken();
            } while ( pageToken != null );
        } catch ( IOException exp ) {
            LOGGER.error( "Problem in getting events from google calendar: " + exp.getLocalizedMessage(), exp );
            throw new ApplicationException(
                    "Problem in getting events from google calendar: " + exp.getLocalizedMessage(), exp );
        }
        LOGGER.info( "getEvents for emailId : {} timeMin : {} timeMax : {} and events : {}", email, timeMin,
                timeMax, JsonUtil.toJson( calendarEvents ) );
        return calendarEvents;
    }
}
