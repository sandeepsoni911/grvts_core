/**
 *
 */
package com.owners.gravitas.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.AgentHolder;
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.domain.Contact;
import com.owners.gravitas.domain.LastViewed;
import com.owners.gravitas.domain.Note;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.dto.FirebaseAccess;
import com.owners.gravitas.dto.QueryParams;

// TODO: Auto-generated Javadoc
/**
 * The Class AgentDaoImplTest.
 *
 * @author harshads
 */
public class AgentDaoImplTest extends AbstractBaseMockitoTest {

    /** The agent dao impl. */
    @InjectMocks
    private AgentDaoImpl agentDaoImpl;

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
        ReflectionTestUtils.setField( agentDaoImpl, "firebaseHost", "testvalue" );
    }

    /**
     * Save agent test.
     */
    @Test
    public void saveAgentTest() {

        Agent agent = new Agent();
        agent.setInfo( new AgentInfo() );

        Note note = new Note();
        Map< String, Note > agentNotes = new HashMap<>();
        agentNotes.put( "agentNotes", note );
        agent.setAgentNotes( agentNotes );

        Contact contact = new Contact();
        Map< String, Contact > contacts = new HashMap<>();
        contacts.put( "contact", contact );
        agent.setContacts( contacts );

        Opportunity opportunity = new Opportunity();
        Map< String, Opportunity > opportunities = new HashMap<>();
        opportunities.put( "opportunites", opportunity );
        agent.setOpportunities( opportunities );

        Request request = new Request();
        Map< String, Request > requests = new HashMap<>();
        requests.put( "requests", request );
        agent.setRequests( requests );

        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Agent.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( agent, HttpStatus.OK ) );
        agentDaoImpl.saveAgent( new AgentHolder() );

        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Agent.class.getClass() ) );
    }

    /**
     * Delete agent test.
     */
    @Test
    public void deleteAgentTest() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new Agent(), HttpStatus.OK ) );
        agentDaoImpl.deleteAgent( "test" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) );
    }

    /**
     * Update last viewed test.
     */
    @Test
    public void updateLastViewedTest() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( LastViewed.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new LastViewed(), HttpStatus.OK ) );
        agentDaoImpl.updateLastViewed( "test", "test", "test" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( LastViewed.class.getClass() ) );
    }

    /**
     * Gets the all agent ids test.
     *
     * @return the all agent ids test
     */
    @Test
    public void getAllAgentIdsTest() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );

        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( String.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new HashMap< String, Boolean >(), HttpStatus.OK ) );

        agentDaoImpl.getAllAgentIds();
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( String.class.getClass() ) );
    }

    /**
     * Test is agent exist test for valid agent id.
     */
    @Test
    public void testIsAgentExistTestForValidAgentId() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        QueryParams queryParams = new QueryParams();
        queryParams.add( "test", "test" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Class.class ), Mockito.anyMap() ) )
                .thenReturn( new ResponseEntity( new HashMap< String, Boolean >(), HttpStatus.OK ) );

        boolean value = agentDaoImpl.isAgentExist( "test" );
        assertTrue( value );
    }

    /**
     * Test is agent exist test for in valid agent id.
     */
    @Test
    public void testIsAgentExistTestForInValidAgentId() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        QueryParams queryParams = new QueryParams();
        queryParams.add( "test", "test" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Class.class ), Mockito.anyMap() ) )
                .thenReturn( new ResponseEntity( null, HttpStatus.OK ) );

        boolean value = agentDaoImpl.isAgentExist( "test" );
        assertFalse( value );
    }

    /**
     * Test get agent by id for valid agent id.
     */
    @Test
    public void testGetAgentByIdForValidAgentId() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        String agentId = "dummyId";
        Agent expected = new Agent();
        ResponseEntity< Agent > responseEntity = new ResponseEntity< Agent >( expected, HttpStatus.OK );

        when( authenticator.authenticate() ).thenReturn( fbAcc );
        when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Class.class ), Mockito.anyMap() ) )
                        .thenReturn( responseEntity );

        Agent actual = agentDaoImpl.getAgentById( agentId );
        assertEquals( expected, actual );
    }

    /**
     * Test get agent email by id.
     */
    @Test
    public void testGetAgentEmailById() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        String agentId = "dummyId";
        ResponseEntity< String > responseEntity = new ResponseEntity< String >( agentId, HttpStatus.OK );

        when( authenticator.authenticate() ).thenReturn( fbAcc );
        when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Class.class ), Mockito.anyMap() ) )
                        .thenReturn( responseEntity );

        String actual = agentDaoImpl.getAgentEmailById( agentId );
        assertEquals( "dummyId", actual );
    }
}
