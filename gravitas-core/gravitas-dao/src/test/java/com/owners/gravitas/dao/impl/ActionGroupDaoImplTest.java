package com.owners.gravitas.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.owners.gravitas.domain.Action;
import com.owners.gravitas.domain.ActionGroup;
import com.owners.gravitas.dto.FirebaseAccess;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.repository.ActionGroupRepository;

/**
 * The Class ActionGroupDaoImplTest.
 */
public class ActionGroupDaoImplTest extends AbstractBaseMockitoTest {

    /** The action group dao impl. */
    @InjectMocks
    private ActionGroupDaoImpl actionGroupDaoImpl;

    /** The rest template. */
    @Mock
    private RestTemplate restTemplate;

    /** The authenticator. */
    @Mock
    private FirebaseRestAuthenticator authenticator;

    /** The action group repository. */
    @Mock
    private ActionGroupRepository actionGroupRepository;

    /**
     * Test get action group.
     */
    @Test
    public void testGetActionGroup() {
        List< com.owners.gravitas.domain.entity.ActionGroup > list = new ArrayList<>();
        list.add( new com.owners.gravitas.domain.entity.ActionGroup() );
        when( actionGroupRepository.findAll() ).thenReturn( list );
        actionGroupDaoImpl.getActionGroup();
        verify( actionGroupRepository ).findAll();
    }

    /**
     * Test find all.
     */
    @Test
    public void testFindAll() {
        List< com.owners.gravitas.domain.entity.ActionGroup > list = new ArrayList<>();
        when( actionGroupRepository.findAll() ).thenReturn( list );
        List actual = actionGroupDaoImpl.findAll();
        assertEquals( actual, list );
        verify( actionGroupRepository ).findAll();
    }

    /**
     * Test create action group.
     */
    @Test
    public void testCreateActionGroup() {
        ActionGroup actionGroup = new ActionGroup();
        FirebaseAccess fbAce = new FirebaseAccess();
        fbAce.setAccessToken( "accessToken" );
        when( authenticator.authenticate() ).thenReturn( fbAce );
        when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Action.class.getClass() ) ) )
                        .thenReturn( new ResponseEntity( new PostResponse(), HttpStatus.OK ) );

        actionGroupDaoImpl.createActionGroup( "test", actionGroup );
        verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Action.class.getClass() ) );

    }

    /**
     * Testget action.
     */
    @Test
    public void testgetAction() {
        FirebaseAccess fbAce = new FirebaseAccess();
        fbAce.setAccessToken( "accessToken" );
        when( authenticator.authenticate() ).thenReturn( fbAce );
        when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Action.class.getClass() ) ) )
                        .thenReturn( new ResponseEntity( new Action(), HttpStatus.OK ) );
        actionGroupDaoImpl.getAction( "test", "test", "test" );
        verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Action.class.getClass() ) );

    }

    /**
     * Test patch action.
     */
    @Test
    public void testPatchAction() {
        Map< String, Object > action = new HashMap<>();
        String agentId = "test";
        String actionFlowId = "test";
        String actionId = "test";
        FirebaseAccess fbAce = new FirebaseAccess();
        fbAce.setAccessToken( "accessToken" );
        when( authenticator.authenticate() ).thenReturn( fbAce );
        when( restTemplate.exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Action.class.getClass() ) ) )
                        .thenReturn( new ResponseEntity( new Action(), HttpStatus.OK ) );
        actionGroupDaoImpl.patchAction( agentId, actionFlowId, actionId, action );
        verify( restTemplate ).exchange( Mockito.anyString(), Mockito.any( HttpMethod.class ),
                Mockito.any( HttpEntity.class ), Mockito.any( Action.class.getClass() ) );
    }

}
