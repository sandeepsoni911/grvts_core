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
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.helper.FirebaseRestAuthenticator;
import com.owners.gravitas.domain.Note;
import com.owners.gravitas.dto.FirebaseAccess;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Class AgentNotesDaoImplTest.
 *
 * @author harshads
 */
public class AgentNotesDaoImplTest extends AbstractBaseMockitoTest {

    /** The agent dao impl. */
    @InjectMocks
    AgentNotesDaoImpl agentNotesDaoImpl;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /** The authenticator. */
    @Mock
    private FirebaseRestAuthenticator authenticator;

    /**
     * Save agent notes test.
     */
    @Test
    public void testSaveAgentNotes() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Note.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new PostResponse(), HttpStatus.OK ) );
        Assert.assertNotNull( agentNotesDaoImpl.saveAgentNote( new Note(), "" ), "Response should not be null" );
    }

    /**
     * Test update agent notes.
     */
    @Test
    public void testUpdateAgentNotes() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Note.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new PostResponse(), HttpStatus.OK ) );
        Assert.assertNotNull( agentNotesDaoImpl.updateAgentNote( new Note(), "", "" ), "Response should not be null" );
    }

    @Test
    public void testGetAgentNotes() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        final Map< String, Object > map = new HashMap<>();

        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( null, HttpStatus.OK ) );

        Note note = agentNotesDaoImpl.getAgentNote( "", "" );
        Assert.assertNull( note, "Response should be null" );

        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( map, HttpStatus.OK ) );

        note = agentNotesDaoImpl.getAgentNote( "", "" );
        Assert.assertNull( note, "Response should be null" );

        map.put( "opportunityId", "opportunityId" );
        map.put( "deleted", true );
        map.put( "details", "details" );
        map.put( "createdDtm", 10L );

        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( map, HttpStatus.OK ) );

        note = agentNotesDaoImpl.getAgentNote( "", "" );
        Assert.assertNotNull( note, "Response should not be null" );

    }
}
