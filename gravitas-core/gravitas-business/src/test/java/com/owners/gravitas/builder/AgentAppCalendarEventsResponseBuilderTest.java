package com.owners.gravitas.builder;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import org.joda.time.DateTime;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.AgentAppCalendarEventsResponseBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.AgentTask;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.dto.CalendarEvent;
import com.owners.gravitas.service.ContactEntityService;

public class AgentAppCalendarEventsResponseBuilderTest extends AbstractBaseMockitoTest {

    @InjectMocks
    private AgentAppCalendarEventsResponseBuilder testAgentAppCalendarEventsResponseBuilder;
    
    @Mock
    private ContactEntityService contactServiceV1;
    
    @Test
    public void testConvertFromWithNullInput() {
        CalendarEvent calendarEvent = testAgentAppCalendarEventsResponseBuilder.convertFrom( null, null );
        assertNull( calendarEvent );
    }
    
    @Test
    public void testConvertFromWithoutNullInput() {
        ReflectionTestUtils.setField( testAgentAppCalendarEventsResponseBuilder, "meetingDuration", 60 );
        AgentTask source = new AgentTask();
        DateTime scheduledDtm = new DateTime();
        source.setScheduledDtm( scheduledDtm );
        source.setTitle( "Calendar Event" );
        source.setDescription( "Event Details" );
        source.setLocation( "blr" );
        source.setType( "SCHEDULE_TOUR" );
        source.setStatus( "COMPLETED" );
        Contact contact = new Contact();
        contact.setFirstName( "Abc" );
        contact.setLastName( "Test" );
        when( contactServiceV1.getUserName( any( Contact.class ) ) ).thenReturn( "Abc Test" );
        CalendarEvent calendarEvent = testAgentAppCalendarEventsResponseBuilder.convertFrom( source, contact );
        assertNotNull( calendarEvent );
        assertEquals( calendarEvent.getAttendeesName().get( 0 ), "Abc Test" );
        assertEquals( calendarEvent.getStartTime(), new Long(scheduledDtm.getMillis()));
        assertEquals( calendarEvent.getEndTime(), new Long(scheduledDtm.plusMinutes( 60 ).getMillis()));
        assertEquals( calendarEvent.getSummary(), "Calendar Event" );
        assertEquals( calendarEvent.getDescription(), "Event Details" );
        assertEquals( calendarEvent.getLocation(), "blr" );
        assertEquals( calendarEvent.getTaskType(), "SCHEDULE_TOUR" );
        assertEquals( calendarEvent.getTaskStatus(), "COMPLETED" );
    }
}
