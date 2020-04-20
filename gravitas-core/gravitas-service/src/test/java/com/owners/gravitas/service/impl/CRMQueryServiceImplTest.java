package com.owners.gravitas.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.QueryParams;
import com.owners.gravitas.dto.crm.response.CRMAccess;
import com.owners.gravitas.dto.crm.response.CRMResponse;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.exception.ResultNotFoundException;
import com.owners.gravitas.service.CRMAuthenticatorService;

/**
 * The Class CRMQueryServiceImplTest.
 *
 * @author vishwanathm
 */
public class CRMQueryServiceImplTest extends AbstractBaseMockitoTest {

    /** The crm query service impl. */
    @InjectMocks
    private CRMQueryServiceImpl crmQueryServiceImpl;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /** The crm authenticator. */
    @Mock
    private CRMAuthenticatorService crmAuthenticator;

    @BeforeMethod
    public void resetMocks() {
        Mockito.reset( restTemplate );
        Mockito.reset( crmAuthenticator );
    }

    /**
     * Test find one.
     */
    @Test
    public void testFindOne() {
        CRMAccess access = new CRMAccess();
        access.setAccessToken( "test" );
        access.setInstanceUrl( "test" );
        final String respStr = "{\"totalSize\":1,\"done\":true,\"records\":[{\"test\" : \"test\"}]}";
        Mockito.when( crmAuthenticator.authenticate() ).thenReturn( access );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Class.class ), Mockito.anyMap() ) )
                .thenReturn( new ResponseEntity( respStr, HttpStatus.OK ) );
        Map< String, Object > map = crmQueryServiceImpl.findOne( "test query", new QueryParams() );
        Assert.assertNotNull( map );
        Assert.assertEquals( map.size(), 1 );
        assertEquals( "test", map.entrySet().iterator().next().getKey() );
        assertEquals( "test", map.entrySet().iterator().next().getValue() );
        Mockito.verify( crmAuthenticator ).authenticate();
    }

    /**
     * Test find one for exception.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testFindOneForException() {
        CRMAccess access = new CRMAccess();
        access.setAccessToken( "test" );
        access.setInstanceUrl( "test" );
        Mockito.when( crmAuthenticator.authenticate() ).thenReturn( access );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ), Mockito.anyMap() ) )
                .thenReturn( new ResponseEntity( "", HttpStatus.OK ) );
        crmQueryServiceImpl.findOne( "test query", new QueryParams() );
    }

    /**
     * Test find one for result not found exception.
     */
    @Test( expectedExceptions = ResultNotFoundException.class )
    public void testFindOneForResultNotFoundException() {
        CRMAccess access = new CRMAccess();
        access.setAccessToken( "test" );
        access.setInstanceUrl( "test" );
        final String respStr = "{\"totalSize\":1,\"done\":true,\"records\":[]}";
        Mockito.when( crmAuthenticator.authenticate() ).thenReturn( access );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ), Mockito.anyMap() ) )
                .thenReturn( new ResponseEntity( respStr, HttpStatus.OK ) );
        crmQueryServiceImpl.findOne( "test query", new QueryParams() );
    }

    /**
     * Test find all for less than SF fetch limit.
     *
     * @throws JsonProcessingException
     *             the json processing exception
     */
    @Test
    public void testFindAllForLessThanSFFetchLimit() throws JsonProcessingException {
        Map< String, Object > response = new HashMap<>();
        response.put( "totalSize", 1 );
        response.put( "done", true );
        List< Map< String, Object > > records = new ArrayList<>();
        Map< String, Object > map = new HashMap<>();
        map.put( "1", "2" );
        records.add( map );
        response.put( "records", records );
        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString( response );
        String query = "dummyQuery";
        QueryParams params = new QueryParams();
        CRMAccess access = new CRMAccess();
        access.setInstanceUrl( "test" );
        when( crmAuthenticator.authenticate() ).thenReturn( access );
        ResponseEntity< String > responseEntity = new ResponseEntity< String >( body, HttpStatus.OK );
        when( restTemplate.exchange( anyString(), any( HttpMethod.class ), any( HttpEntity.class ), any( Class.class ),
                anyMap() ) ).thenReturn( responseEntity );
        CRMResponse crmResponse = crmQueryServiceImpl.findAll( query, params );
        assertNotNull( crmResponse );
        assertEquals( 1, crmResponse.getRecords().size() );
        assertEquals( "1", crmResponse.getRecords().iterator().next().entrySet().iterator().next().getKey() );
        assertEquals( "2", crmResponse.getRecords().iterator().next().entrySet().iterator().next().getValue() );
    }

    @Test
    public void testFindAllForMoreThanSFFetchLimit() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Map< String, Object > response = new HashMap<>();
        int limit = ( Integer ) ReflectionTestUtils.getField( crmQueryServiceImpl, "SF_FETCH_LIMIT" );
        response.put( "totalSize", limit + 1 );
        response.put( "done", true );
        List< Map< String, Object > > records = new ArrayList<>();
        for ( int c = 0; c < limit; c++ ) {
            Map< String, Object > map = new HashMap<>();
            records.add( map );
        }
        response.put( "records", records );
        String body = mapper.writeValueAsString( response );

        Map< String, Object > response2 = new HashMap<>();
        response2.put( "totalSize", 1 );
        response2.put( "done", true );
        List< Map< String, Object > > records2 = new ArrayList<>();
        records2.add( new HashMap<>() );
        response2.put( "records", records2 );
        String body2 = mapper.writeValueAsString( response2 );

        String query = "dummyQuery";
        QueryParams params = new QueryParams();
        CRMAccess access = new CRMAccess();
        access.setInstanceUrl( "test" );
        when( crmAuthenticator.authenticate() ).thenReturn( access );
        ResponseEntity< String > responseEntity = new ResponseEntity< String >( body, HttpStatus.OK );
        ResponseEntity< String > responseEntity2 = new ResponseEntity< String >( body2, HttpStatus.OK );
        when( restTemplate.exchange( anyString(), any( HttpMethod.class ), any( HttpEntity.class ), any( Class.class ),
                anyMap() ) ).thenReturn( responseEntity ).thenReturn( responseEntity2 );
        CRMResponse crmResponse = crmQueryServiceImpl.findAll( query, params );
        assertNotNull( crmResponse );
        assertEquals( limit + 1, crmResponse.getRecords().size() );
    }
}
