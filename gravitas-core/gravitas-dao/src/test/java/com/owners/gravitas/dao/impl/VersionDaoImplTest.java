/**
 *
 */
package com.owners.gravitas.dao.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;

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
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.helper.FirebaseRestAuthenticator;
import com.owners.gravitas.domain.ClientVersion;
import com.owners.gravitas.domain.Version;
import com.owners.gravitas.dto.FirebaseAccess;
import com.owners.gravitas.dto.request.ClientVersionRequest;

/**
 * The Class VersionDaoImplTest.
 *
 * @author nishak
 */
public class VersionDaoImplTest extends AbstractBaseMockitoTest {

    /** The agent task dao impl. */
    @InjectMocks
    VersionDaoImpl versionDaoImpl;

    /** The rest template. */
    @Mock
    RestTemplate restTemplate;

    /** The authenticator. */
    @Mock
    FirebaseRestAuthenticator authenticator;

    /**
     * Save version test.
     */
    @Test
    public void saveVersionTestWithIf() {

        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        final Map< String, Map< String, ClientVersion > > requestMap = new HashMap<>();
        final Map< String, ClientVersion > clientVersionMap = new HashMap<>();
        ClientVersion clientVersion = new ClientVersion();
        clientVersionMap.put( "versionInfo", clientVersion );
        requestMap.put( "ClientUI", clientVersionMap );

        final Map< String, Version > versionMap = new HashMap<>();
        Version version = new Version();
        versionMap.put( "versionInfo", version );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), any( Class.class ) ) )
                .thenReturn( new ResponseEntity( new ClientVersion(), HttpStatus.OK ) );

        versionDaoImpl.saveVersion( requestMap, versionMap );

        Mockito.verify( restTemplate, times( 2 ) ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), any( Class.class ) );
    }

    /**
     * Save version test.
     */
    @Test
    public void saveVersionTestWithoutIf() {

        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        final Map< String, Map< String, ClientVersion > > requestMap = new HashMap<>();
        final Map< String, ClientVersion > clientVersionMap = new HashMap<>();
        clientVersionMap.put( "versionInfo", new ClientVersion() );
        requestMap.put( "ClientUI", clientVersionMap );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), any( Class.class ) ) )
                .thenReturn( new ResponseEntity( new ClientVersionRequest(), HttpStatus.OK ) );

        versionDaoImpl.saveVersion( requestMap, null );

        Mockito.verify( restTemplate, times( 1 ) ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), any( Class.class ) );
    }
}
