/**
 *
 */
package com.owners.gravitas.dao.impl;

import java.util.HashMap;

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

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.helper.FirebaseRestAuthenticator;
import com.owners.gravitas.domain.Contact;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.dto.FirebaseAccess;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Class ContactDaoImplTest.
 *
 * @author harshads
 */
public class ContactDaoImplTest extends AbstractBaseMockitoTest {

    /** The contact dao impl. */
    @InjectMocks
    AgentContactDaoImpl contactDaoImpl;

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
        ReflectionTestUtils.setField( contactDaoImpl, "firebaseHost", "testvalue" );
    }

    /**
     * Save contact test.
     */
    @Test
    public void saveContactTest() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Opportunity.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new PostResponse(), HttpStatus.OK ) );
        Assert.assertEquals( contactDaoImpl.saveContact( "", new Contact() ).getMessage(), "Operation was successful",
                "opportunity save" );
    }

    /**
     * Update contact test.
     */
    @Test
    public void updateContactTest() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Opportunity.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new PostResponse(), HttpStatus.OK ) );
        contactDaoImpl.updateContact( "", "", new Contact() );

        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Opportunity.class.getClass() ) );

    }

    /**
     * Update contact patch test.
     */
    @Test
    public void updateContactPatchTest() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Opportunity.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new PostResponse(), HttpStatus.OK ) );
        contactDaoImpl.patchContact( "", "", new HashMap< String, Object >() );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Opportunity.class.getClass() ) );
    }

    /**
     * Gets the contact by id test.
     *
     * @return the contact by id test
     */
    @Test
    public void getContactByIdTest() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Opportunity.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new Contact(), HttpStatus.OK ) );
        Contact contact = contactDaoImpl.getContactById( "test", "test" );

        Assert.assertNotEquals( contact, null );
    }
}
