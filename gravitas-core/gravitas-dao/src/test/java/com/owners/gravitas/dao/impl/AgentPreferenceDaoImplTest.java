/**
 *
 */
package com.owners.gravitas.dao.impl;

import static org.junit.Assert.assertNotNull;

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
import com.owners.gravitas.dto.FirebaseAccess;

// TODO: Auto-generated Javadoc
/**
 * The Class AgentDaoImplTest.
 *
 * @author javeedsy
 */
public class AgentPreferenceDaoImplTest extends AbstractBaseMockitoTest {

    /** The agent dao impl. */
    @InjectMocks
    private AgentPreferenceDaoImpl agentPreferenceDaoImpl;

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
        ReflectionTestUtils.setField( agentPreferenceDaoImpl, "firebaseHost", "testvalue" );
    }

    /**
     * Test save agent preferences data.
     */
    @Test
    public void testSaveAgentPreferencesData() {

        final String fbPath = "data/config/email";
        final Map< String, Object > agentPreference = new HashMap< String, Object >();
        final Object object = new Object();

        final FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( object, HttpStatus.OK ) );

        final Object objectResponse = agentPreferenceDaoImpl.saveAgentPreferencesData( fbPath, agentPreference );

        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) );
        assertNotNull( objectResponse );
    }
}
