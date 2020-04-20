/**
 *
 */
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
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.helper.FirebaseRestAuthenticator;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.dto.FirebaseAccess;
import com.owners.gravitas.dto.response.PostResponse;

/**
 * The Class OpportunityDaoImplTest.
 *
 * @author harshads
 */
public class OpportunityDaoImplTest extends AbstractBaseMockitoTest {

    /** The opportunity dao impl. */
    @InjectMocks
    AgentOpportunityDaoImpl opportunityDaoImpl;

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
        ReflectionTestUtils.setField( opportunityDaoImpl, "firebaseHost", "testvalue" );
    }

    /**
     * Gets the opportunity by id test.
     *
     * @return the opportunity by id test
     */
    @Test
    public void getOpportunityByIdTest() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Opportunity.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new Opportunity(), HttpStatus.OK ) );
        Assert.assertNotNull( opportunityDaoImpl.getOpportunityById( "", "" ), "Opportunity should not be null" );
    }

    /**
     * Save opportunity test.
     */
    @Test
    public void saveOpportunityTest() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Opportunity.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new PostResponse(), HttpStatus.OK ) );
        Assert.assertEquals( opportunityDaoImpl.saveOpportunity( "", new Opportunity() ).getMessage(),
                "Operation was successful", "opportunity save" );
    }

    /**
     * Update opportunity test.
     */
    @Test
    public void updateOpportunityTest() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Opportunity.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new PostResponse(), HttpStatus.OK ) );
        opportunityDaoImpl.updateOpportunity( "", "", new Opportunity() );

        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Opportunity.class.getClass() ) );
    }

    /**
     * Patch opportunity test.
     */
    @Test
    public void patchOpportunityTest() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Opportunity.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new Opportunity(), HttpStatus.OK ) );
        opportunityDaoImpl.patchOpportunity( "", "", new HashMap<>() );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Opportunity.class.getClass() ) );
    }

    /**
     * Gets the agent opportunites test.
     *
     * @return the agent opportunites test
     */
    @Test
    public void getAgentOpportunitesTest() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Opportunity.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new HashMap<>(), HttpStatus.OK ) );
        opportunityDaoImpl.getAgentOpportunites( "" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Opportunity.class.getClass() ) );
    }

    /**
     * Test Get Agent New Opportunities Count method.
     */
    @Test
    public void getAgentNewOpportunitiesCountTest() {
        Map< String, Map > reposne = new HashMap<>();
        Map value = new HashMap<>();
        reposne.put( "test", value );
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Map.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( reposne, HttpStatus.OK ) );
        opportunityDaoImpl.getAgentNewOpportunitiesCount( "test" );
        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Map.class.getClass() ) );
    }

    /**
     * Test Agent claimed Opportunities with open tasks method.
     */
    @Test
    public void hasAgentClaimedOpportunityWithOpenTasksTest() {
        FirebaseAccess fbAcc = new FirebaseAccess();
        fbAcc.setAccessToken( "accessToken" );
        Mockito.when( authenticator.authenticate() ).thenReturn( fbAcc );
        Mockito.when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Map.class.getClass() ) ) )
                .thenReturn( new ResponseEntity( new HashMap(), HttpStatus.OK ) );

        opportunityDaoImpl.hasAgentClaimedOpportunityWithOpenTasks( "test" );

        Mockito.verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Map.class.getClass() ) );
    }
}
