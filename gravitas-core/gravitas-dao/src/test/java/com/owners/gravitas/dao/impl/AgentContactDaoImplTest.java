package com.owners.gravitas.dao.impl;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

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
import com.owners.gravitas.domain.Contact;
import com.owners.gravitas.domain.Task;
import com.owners.gravitas.dto.FirebaseAccess;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Class AgentContactDaoImplTest.
 *
 * @author shivamm
 */
public class AgentContactDaoImplTest extends AbstractBaseMockitoTest {

    /** The agent contact dao impl. */
    @InjectMocks
    private AgentContactDaoImpl agentContactDaoImpl;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /** The authenticator. */
    @Mock
    private FirebaseRestAuthenticator authenticator;

    /**
     * Test save contact.
     */
    @Test
    public void testSaveContact() {
        Contact con = new Contact();
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Task.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new PostResponse(), HttpStatus.OK ) );
        final PostResponse response = agentContactDaoImpl.saveContact( "test", con );
        Assert.assertNotNull( response );
        Assert.assertEquals( response.getStatus(), Status.SUCCESS );
    }

    /**
     * Test patch contact.
     */
    @Test
    public void testPatchContact() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Task.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new Task(), HttpStatus.OK ) );
        agentContactDaoImpl.patchContact( "test", "test", new HashMap< String, Object >() );

        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Task.class.getClass() ) );
    }

    /**
     * Test update contact.
     */
    @Test
    public void testUpdateContact() {
        Contact con = new Contact();
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( HashMap.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new PostResponse(), HttpStatus.OK ) );
        agentContactDaoImpl.updateContact( "test", "test", con );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Task.class.getClass() ) );
    }

    /**
     * Test get contact by opportunity id.
     */
    @Test
    public void testGetContactById() {
        Contact contact = new Contact();
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Contact.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( contact, HttpStatus.OK ) );

        Contact con = agentContactDaoImpl.getContactById( "test", "test" );
        assertNotNull( con );
    }

    /**
     * Test get contact by opportunity id.
     */
    @Test
    public void testGetContactByOpportunityId() {
        Contact contact = new Contact();
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Contact.class.getClass() ), Mockito.anyMap() ) )
                .thenReturn( new ResponseEntity( contact, HttpStatus.OK ) );

        Contact con = agentContactDaoImpl.getContactByOpportunityId( "test", "test" );
        assertNotNull( con );
    }
}
