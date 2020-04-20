package com.owners.gravitas.dao.impl;

import static org.junit.Assert.assertEquals;
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
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.helper.FirebaseRestAuthenticator;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.dto.FirebaseAccess;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Class AgentTaskDaoImplTest.
 *
 * @author amits
 */
public class AgentTaskDaoImplTest extends AbstractBaseMockitoTest {

    /** The agent task dao impl. */
    @InjectMocks
    AgentTaskDaoImpl agentTaskDaoImpl;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /** The authenticator. */
    @Mock
    private FirebaseRestAuthenticator authenticator;

    /** The agent contact dao impl. */
    @InjectMocks
    private AgentContactDaoImpl agentContactDaoImpl;

    /**
     * Test save agent task.
     */
    @Test
    public void testSaveAgentTask() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Task.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new PostResponse(), HttpStatus.OK ) );
        final PostResponse response = agentTaskDaoImpl.saveAgentTask( new Task(), "test" );
        Assert.assertNotNull( response );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );
    }

    /**
     * Test patch agent task.
     */
    @Test
    public void testPatchAgentTask() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Task.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new Task(), HttpStatus.OK ) );
        Task task = agentTaskDaoImpl.patchAgentTask( "test", "test", new HashMap< String, Object >() );
        Assert.assertNotNull( task );
    }

    /**
     * Test save agent tasks.
     */
    @Test
    public void testSaveAgentTasks() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( HashMap.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new PostResponse(), HttpStatus.OK ) );
        final PostResponse response = agentTaskDaoImpl.saveAgentTasks( new HashMap< String, Task >(), "test" );
        Assert.assertNotNull( response );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );
    }

    /**
     * Test task by opportunity id
     */
    @Test
    public void getTasksByOpportunityIdTest() {
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

        Map< String, Task > requestsByOpportunityId = agentTaskDaoImpl.getTasksByOpportunityId( "test", "test" );

        final ObjectMapper mapper = new ObjectMapper();
        final Task request = mapper.convertValue( value, Task.class );
        Map< String, Task > expected = new HashMap<>();
        expected.put( "dummy key", request );

        assertEquals( expected.getClass(), requestsByOpportunityId.getClass() );

        // Mockito.verify( restTemplate ).exchange( Mockito.anyString(),
        // Mockito.any( HttpMethod.class ),
        // Mockito.any( HttpEntity.class ), Mockito.any( Map.class.getClass() ),
        // Mockito.anyMap() );
    }

    /**
     * Test save agent tasks.
     */
    @Test
    public void testGetTaskById() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );

        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Task.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new Task(), HttpStatus.OK ) );

        final Task task = agentTaskDaoImpl.getTaskById( "test", "test" );

        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Task.class.getClass() ) );

    }

    /**
     * Test Task by type.
     */
    @Test
    public void testGetTaskByType() {
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

        agentTaskDaoImpl.getTaskByType( "test", "test", "test" );
    }

    /**
     * Test Open opportunity type tasks.
     */
    @Test
    public void testGetOpenContactOpportunityTypeTasks() {
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

        agentTaskDaoImpl.getOpenContactOpportunityTypeTasks( "test", "test" );
    }

}
