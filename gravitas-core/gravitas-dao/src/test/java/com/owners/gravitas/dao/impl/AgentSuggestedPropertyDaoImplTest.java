package com.owners.gravitas.dao.impl;

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
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.FirebaseAccess;
import com.owners.gravitas.dto.request.AgentSuggestedPropertyRequest;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Class AgentSuggestedPropertyDaoImplTest.
 *
 * @author javeedsy
 */
public class AgentSuggestedPropertyDaoImplTest extends AbstractBaseMockitoTest {

    /** The agent suggest property dao impl. */
    @InjectMocks
    AgentSuggestedPropertyDaoImpl agentSuggestedPropertyDaoImpl;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /** The authenticator. */
    @Mock
    private FirebaseRestAuthenticator authenticator;

    /**
     * Test save agent suggested property.
     */
    @Test
    public void testSaveAgentSuggestedProperty() {
        final FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Task.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new PostResponse(), HttpStatus.OK ) );
        final PostResponse response = agentSuggestedPropertyDaoImpl
                .saveAgentSuggestedProperty( new AgentSuggestedPropertyRequest(), "test" );
        Assert.assertNotNull( response );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );
    }

}
