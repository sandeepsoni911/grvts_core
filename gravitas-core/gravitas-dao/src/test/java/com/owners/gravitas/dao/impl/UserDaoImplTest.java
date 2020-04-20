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
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.helper.FirebaseRestAuthenticator;
import com.owners.gravitas.dto.FirebaseAccess;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchDaoImplTest.
 */
public class UserDaoImplTest extends AbstractBaseMockitoTest {

    /** The user dao impl. */
    @InjectMocks
    UserDaoImpl userDaoImpl;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /** The authenticator. */
    @Mock
    private FirebaseRestAuthenticator authenticator;

    /**
     * Search by agent id test.
     */
    @Test
    public void testGetRoles() {
        ReflectionTestUtils.setField( userDaoImpl, "replaceString", "test" );
        Map< String, Boolean > search = new HashMap<>();
        search.put( "test", false );
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.anyMap().getClass() ) )
                .thenReturn( new ResponseEntity( search, HttpStatus.OK ) );
        userDaoImpl.getRoles( "test" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.anyMap().getClass() );
    }

    /**
     * Test get roles_ response as null.
     */
    @Test
    public void testGetRoles_ResponseAsNull() {
        ReflectionTestUtils.setField( userDaoImpl, "replaceString", "test" );
        Map< String, Boolean > search = new HashMap<>();
        search.put( "test", false );
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.anyMap().getClass() ) )
                .thenReturn( new ResponseEntity( null, HttpStatus.OK ) );
        userDaoImpl.getRoles( "test" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.anyMap().getClass() );
    }
}
