/**
 *
 */
package com.owners.gravitas.dao.impl;

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
import com.owners.gravitas.domain.AgentInfo;
import com.owners.gravitas.dto.FirebaseAccess;

/**
 * The Class AgentInfoDaoImplTest.
 *
 * @author harshads
 */
public class AgentInfoDaoImplTest extends AbstractBaseMockitoTest {

    /** The agent dao impl. */
    @InjectMocks
    AgentInfoDaoImpl agentInfoDaoImpl;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /** The authenticator. */
    @Mock
    private FirebaseRestAuthenticator authenticator;

    /**
     * Patch agent info test.
     */
    @Test
    public void patchAgentInfoTest() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( AgentInfo.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new AgentInfo(), HttpStatus.OK ) );
        agentInfoDaoImpl.patchAgentInfo( "test", new AgentInfo() );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( AgentInfo.class.getClass() ) );
    }

    /**
     * Gets the agent info test.
     *
     * @return the agent info test
     */
    @Test
    public void getAgentInfoTest() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new AgentInfo(), HttpStatus.OK ) );
        agentInfoDaoImpl.getAgentInfo( "test" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) );
    }

    /**
     * Gets the agent info test.
     *
     * @return the agent info test
     */
    @Test
    public void testPatchAgentInfoPhone() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( "test", HttpStatus.OK ) );

        agentInfoDaoImpl.patchAgentInfoPhone( "test", "test" );

        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Object.class.getClass() ) );
    }

}
