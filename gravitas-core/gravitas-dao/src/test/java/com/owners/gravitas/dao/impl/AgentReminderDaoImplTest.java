/**
 *
 */
package com.owners.gravitas.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.helper.FirebaseRestAuthenticator;
import com.owners.gravitas.domain.Reminder;
import com.owners.gravitas.dto.FirebaseAccess;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Class AgentReminderDaoImplTest.
 *
 * @author harshads
 */
public class AgentReminderDaoImplTest extends AbstractBaseMockitoTest {

    /** The agent dao impl. */
    @InjectMocks
    AgentReminderDaoImpl agentReminderDaoImpl;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /** The authenticator. */
    @Mock
    private FirebaseRestAuthenticator authenticator;

    /** The mapper. */
    @Mock
    private ObjectMapper mapper;

    /**
     * Inits the x.
     */
    @BeforeMethod
    public void initX() {
        ReflectionTestUtils.setField( agentReminderDaoImpl, "firebaseHost", "testvalue" );
    }

    /**
     * Delete agent test.
     */
    @Test
    public void agentReminderTest() {
        final FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( PostResponse.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new PostResponse(), HttpStatus.OK ) );
        agentReminderDaoImpl.setReminder( "test", "test", new Reminder() );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( PostResponse.class.getClass() ) );
    }

    /**
     * Delete agent test.
     */
    @Test
    public void testGetReminder() {
        final FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Reminder.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new Reminder(), HttpStatus.OK ) );
        agentReminderDaoImpl.getReminder( "test", "test", "test" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Reminder.class.getClass() ) );
    }

    /**
     * Test get reminder.
     */
    @Test
    public void testPatchReminder() {
        final Map< String, Object > reminder = new HashMap<>();
        final FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Map.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new PostResponse(), HttpStatus.OK ) );
        agentReminderDaoImpl.patchReminder( "test", "test", "test", reminder );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Map.class.getClass() ) );
    }

    /**
     * Test get reminder.
     */
    @Test
    public void testGetTaskReminder() {
        final Map< String, Reminder > reminders = new HashMap<>();
        reminders.put( "test", new Reminder() );
        final FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Map.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( reminders, HttpStatus.OK ) );
        agentReminderDaoImpl.getTaskReminders( "test", "test" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Map.class.getClass() ) );
    }

    /**
     * Test delete reminder.
     */
    @Test
    public void testDeleteReminder() {
        final FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Reminder.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new Reminder(), HttpStatus.OK ) );
        final Reminder reminder = agentReminderDaoImpl.deleteReminder( "agent", "task", "reminder" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Reminder.class.getClass() ) );
    }

}
