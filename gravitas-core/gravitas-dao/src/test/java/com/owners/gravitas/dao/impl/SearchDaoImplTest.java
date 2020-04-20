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

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.helper.FirebaseRestAuthenticator;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.dto.FirebaseAccess;

/**
 * The Class SearchDaoImplTest.
 */
public class SearchDaoImplTest extends AbstractBaseMockitoTest {

    /** The search dao impl. */
    @InjectMocks
    SearchDaoImpl searchDaoImpl;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /** The authenticator. */
    @Mock
    private FirebaseRestAuthenticator authenticator;

    /**
     * Inits the x.
     */
    @BeforeMethod
    public void initX() {
        ReflectionTestUtils.setField( searchDaoImpl, "firebaseHost", "testvalue" );
    }

    /**
     * Search by agent id test.
     */
    @Test
    public void searchByAgentIdTest() {
        Map< String, String > search = new HashMap<>();
        search.put( "test", "value" );
        Map< String, Map< String, String > > searchMap = new HashMap<>();
        searchMap.put( "test", search );
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.anyMap().getClass(), Mockito.anyMap() ) )
                .thenReturn( new ResponseEntity( searchMap, HttpStatus.OK ) );
        searchDaoImpl.searchByAgentId( "test" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.anyMap().getClass(), Mockito.anyMap() );
    }

    /**
     * Search by crm opportunity id test.
     */
    @Test
    public void searchByCrmOpportunityIdTest() {
        Map< String, String > search = new HashMap<>();
        search.put( "test", "value" );
        Map< String, Map< String, String > > searchMap = new HashMap<>();
        searchMap.put( "test", search );
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.anyMap().getClass(), Mockito.anyMap() ) )
                .thenReturn( new ResponseEntity( searchMap, HttpStatus.OK ) );
        searchDaoImpl.searchByCrmOpportunityId( "test" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.anyMap().getClass(), Mockito.anyMap() );
    }

    /**
     * Search by agent email test.
     */
    @Test
    public void searchByAgentEmailTest() {
        Map< String, String > search = new HashMap<>();
        search.put( "test", "value" );
        Map< String, Map< String, String > > searchMap = new HashMap<>();
        searchMap.put( "test", search );
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.anyMap().getClass(), Mockito.anyMap() ) )
                .thenReturn( new ResponseEntity( searchMap, HttpStatus.OK ) );
        searchDaoImpl.searchByAgentEmail( "test" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.anyMap().getClass(), Mockito.anyMap() );
    }

    /**
     * Search by contact email test.
     */
    @Test
    public void searchByContactEmailTest() {
        Map< String, String > search = new HashMap<>();
        search.put( "test", "value" );
        Map< String, Map< String, String > > searchMap = new HashMap<>();
        searchMap.put( "test", search );
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.anyMap().getClass(), Mockito.anyMap() ) )
                .thenReturn( new ResponseEntity( searchMap, HttpStatus.OK ) );
        searchDaoImpl.searchByContactEmail( "test" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.anyMap().getClass(), Mockito.anyMap() );
    }

    /**
     * Save search test.
     */
    @Test
    public void saveSearchTest() {
        Search search = new Search();
        search.setAgentEmail( "test" );
        search.setContactEmail( "test" );
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Object response = Mockito
                .when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                        Mockito.any( HttpEntity.class ), Mockito.any( Search.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( null, HttpStatus.OK ) );
        searchDaoImpl.saveSearch( search );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Search.class.getClass() ) );
    }

    /**
     * Save searches test.
     */
    @Test
    public void saveSearchesTest() {
        Map< String, Search > searchMap = new HashMap<>();
        Search search = new Search();
        search.setAgentEmail( "test" );
        search.setContactEmail( "test" );
        searchMap.put( "one", search );
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Object response = Mockito
                .when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                        Mockito.any( HttpEntity.class ), Mockito.any( Map.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( null, HttpStatus.OK ) );
        searchDaoImpl.saveSearches( searchMap );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Map.class.getClass() ) );
    }

    /**
     * Search by opportunity id test.
     */
    @Test
    public void searchByOpportunityIdTest() {
        Map< String, String > search = new HashMap<>();
        search.put( "test", "value" );
        Map< String, Map< String, String > > searchMap = new HashMap<>();
        searchMap.put( "test", search );
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.anyMap().getClass(), Mockito.anyMap() ) )
                .thenReturn( new ResponseEntity( searchMap, HttpStatus.OK ) );
        searchDaoImpl.searchByOpportunityId( "test" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.anyMap().getClass(), Mockito.anyMap() );
    }

    /**
     * Search by contact id test.
     */
    @Test
    public void searchByContactIdTest() {
        Map< String, String > search = new HashMap<>();
        search.put( "test", "value" );
        Map< String, Map< String, String > > searchMap = new HashMap<>();
        searchMap.put( "test", search );
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.anyMap().getClass(), Mockito.anyMap() ) )
                .thenReturn( new ResponseEntity( searchMap, HttpStatus.OK ) );
        searchDaoImpl.searchByContactId( "test" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.anyMap().getClass(), Mockito.anyMap() );
    }

    /**
     * Update search test.
     */
    @Test
    public void updateSearchTest() {
        Search search = new Search();
        search.setAgentEmail( "test" );
        search.setContactEmail( "test" );
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Object response = Mockito
                .when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                        Mockito.any( HttpEntity.class ), Mockito.any( Search.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( null, HttpStatus.OK ) );
        searchDaoImpl.updateSearch( search );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Search.class.getClass() ) );
    }

    /**
     * Delete search test.
     */
    @Test
    public void deleteSearchTest() {
        Map< String, String > search = new HashMap<>();
        search.put( "test", "value" );
        Map< String, Map< String, String > > searchMap = new HashMap<>();
        searchMap.put( "test", search );
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( searchMap, HttpStatus.OK ) );
        searchDaoImpl.deleteSearch( "test" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) );
    }
}
