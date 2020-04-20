/**
 *
 */
package com.owners.gravitas.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.owners.gravitas.dao.AgentTaskDao;
import com.owners.gravitas.dao.helper.BasicRestAuthenticator;
import com.owners.gravitas.dao.helper.FirebaseRestAuthenticator;
import com.owners.gravitas.domain.Agent;
import com.owners.gravitas.domain.AgentHolder;
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.domain.Note;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.FirebaseAccess;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Class AgentDaoImplTest.
 *
 */
public class AgentOpportunityDaoImplTest extends AbstractBaseMockitoTest {

    /** The agent dao impl. */
    @InjectMocks
    private AgentOpportunityDaoImpl agentOpportunityDaoImpl;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /** The authenticator. */
    @Mock
    private FirebaseRestAuthenticator authenticator;

    /** The mapper. */
    @Mock
    private ObjectMapper mapper;

    @Mock
    private AgentTaskDao agentTaskDao;

    /** The basic authenticator. */
    @Mock
    protected BasicRestAuthenticator basicAuthenticator;

    /**
     * Inits the x.
     */
    @BeforeMethod
    public void initX() {
        ReflectionTestUtils.setField( agentOpportunityDaoImpl, "firebaseHost", "testvalue" );
    }

    /**
     * Gets the all agent ids test.
     *
     * @return the all agent ids test
     */
    @Test
    public void testgetOpportunityById() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );

        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( String.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new Opportunity(), HttpStatus.OK ) );

        agentOpportunityDaoImpl.getOpportunityById( "test", "test" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( String.class.getClass() ) );
    }

    /**
     * Gets the all agent ids test.
     *
     * @return the all agent ids test
     */
    @Test
    public void testsaveOpportunity() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );

        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( String.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new PostResponse(), HttpStatus.OK ) );

        agentOpportunityDaoImpl.saveOpportunity( "test", new Opportunity() );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( String.class.getClass() ) );
    }

    @Test
    public void testupdateOpportunity() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );

        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( String.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( null, HttpStatus.OK ) );

        agentOpportunityDaoImpl.updateOpportunity( "test", "test", new Opportunity() );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( String.class.getClass() ) );
    }

    @Test
    public void testpatchOpportunity() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );

        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( String.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new Opportunity(), HttpStatus.OK ) );

        agentOpportunityDaoImpl.patchOpportunity( "test", "test", new HashMap() );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( String.class.getClass() ) );
    }

    @Test
    public void testgetAgentOpportunites() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );

        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( String.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new HashMap(), HttpStatus.OK ) );

        agentOpportunityDaoImpl.getAgentOpportunites( "test" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( String.class.getClass() ) );
    }
}
