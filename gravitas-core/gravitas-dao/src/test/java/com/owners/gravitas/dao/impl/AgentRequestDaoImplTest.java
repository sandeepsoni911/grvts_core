package com.owners.gravitas.dao.impl;

import static org.junit.Assert.assertEquals;

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
import com.owners.gravitas.domain.Request;
import com.owners.gravitas.dto.FirebaseAccess;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Class AgentRequestDaoImplTest.
 */
public class AgentRequestDaoImplTest extends AbstractBaseMockitoTest {

    /** The dao impl. */
    @InjectMocks
    AgentRequestDaoImpl daoImpl;

    /** The rest template. */
    @Mock
    RestTemplate restTemplate;

    /** The authenticator. */
    @Mock
    FirebaseRestAuthenticator authenticator;

    /**
     * Inits the x.
     */
    @BeforeMethod
    public void initX() {
        ReflectionTestUtils.setField( daoImpl, "firebaseHost", "testvalue" );
    }

    @Test
    public void saveAgentRequestTest() {
        Request request = new Request();
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Request.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new PostResponse(), HttpStatus.OK ) );
        daoImpl.saveAgentRequest( request, "test" );

        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Request.class.getClass() ) );
    }

    @Test
    public void getBuyerRequestTest() {
        Request request = new Request();
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Request.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( request, HttpStatus.OK ) );
        daoImpl.getBuyerRequest( "test", "test" );

        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Request.class.getClass() ) );
    }

    @Test
    public void saveAgentRequestsTest() {
        Map< String, Request > requestMap = new HashMap<>();
        Request request = new Request();
        requestMap.put( "one", request );
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Request.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new PostResponse(), HttpStatus.OK ) );
        daoImpl.saveAgentRequests( requestMap, "test" );

        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( PostResponse.class.getClass() ) );
    }

    @Test
    public void patchAgentRequestTest() {
        Map< String, Object > requestMap = new HashMap<>();
        Request request = new Request();
        requestMap.put( "one", request );
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Request.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( request, HttpStatus.OK ) );
        daoImpl.patchAgentRequest( "test", "test", requestMap );

        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( PostResponse.class.getClass() ) );
    }

    @Test
    public void getRequestsByOpportunityIdTest() {
        Map< String, Map< String, String > > requests = new HashMap<>();
        Map< String, String > value = new HashMap<>();
        value.put( "abc", "zxc" );
        requests.put( "dummy key", value );

        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Map.class.getClass() ), Mockito.anyMap() ) )
                .thenReturn( new ResponseEntity( requests, HttpStatus.OK ) );

        Map< String, Request > requestsByOpportunityId = daoImpl.getRequestsByOpportunityId( "test", "test" );

        final ObjectMapper mapper = new ObjectMapper();
        final Request request = mapper.convertValue( value, Request.class );
        Map< String, Request > expected = new HashMap<>();
        expected.put( "dummy key", request );

        assertEquals( expected.getClass(), requestsByOpportunityId.getClass() );

        // Mockito.verify( restTemplate ).exchange( Mockito.anyString(),
        // Mockito.any( HttpMethod.class ),
        // Mockito.any( HttpEntity.class ), Mockito.any( Map.class.getClass() ),
        // Mockito.anyMap() );
    }
}
