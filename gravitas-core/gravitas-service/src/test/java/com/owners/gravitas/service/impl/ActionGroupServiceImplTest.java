package com.owners.gravitas.service.impl;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dao.ActionGroupDao;
import com.owners.gravitas.domain.ActionGroup;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.entity.OpportunityAction;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.repository.OpportunityActionRepository;
import com.owners.gravitas.service.builder.OpportunityActionBuilder;

/**
 * The Class ActionGroupServiceImplTest.
 *
 * @author shivamm
 */
public class ActionGroupServiceImplTest extends AbstractBaseMockitoTest {

    /** The action group service impl. */
    @InjectMocks
    private ActionGroupServiceImpl actionGroupServiceImpl;

    /** The action group dao. */
    @Mock
    private ActionGroupDao actionGroupDao;

    /** The opportunity action builder. */
    @Mock
    private OpportunityActionBuilder opportunityActionBuilder;

    /** The opportunity action repository. */
    @Mock
    private OpportunityActionRepository opportunityActionRepository;

    /**
     * Test create action group.
     */
    @Test
    public void testCreateActionGroup() {
        final String agentId = "test";
        final ActionGroup actionGroup = new ActionGroup();
        final String opportunityId = "test";
        final Opportunity opportunity = new Opportunity();
        final PostResponse expectedResponse = new PostResponse();
        final List< OpportunityAction > actions = new ArrayList<>();

        when( actionGroupDao.createActionGroup( agentId, actionGroup ) ).thenReturn( expectedResponse );
        when( opportunityActionBuilder.convertTo( actionGroup ) ).thenReturn( actions );
        when( opportunityActionRepository.save( actions ) ).thenReturn( new ArrayList<>() );

        final PostResponse actualResponse = actionGroupServiceImpl.createActionGroup( agentId, actionGroup,
                opportunityId, opportunity );
        Assert.assertEquals( actualResponse, expectedResponse );
        verify( actionGroupDao ).createActionGroup( agentId, actionGroup );
        verify( opportunityActionBuilder ).convertTo( actionGroup );
        verify( opportunityActionRepository ).save( actions );
    }

    /**
     * Test get action info.
     */
    @Test
    public void testGetActionInfo() {
        final String agentId = "test";
        final String actionGroupId = "test";
        final String actionId = "test";
        actionGroupServiceImpl.getActionInfo( agentId, actionGroupId, actionId );
        Mockito.verify( actionGroupDao ).getAction( agentId, actionGroupId, actionId );
    }

    /**
     * Test patch agent action info.
     */
    @Test
    public void testPatchAgentActionInfo() {
        final String agentId = "test";
        final ActionGroup actionGroup = new ActionGroup();
        final String opportunityId = "test";
        final Opportunity opportunity = new Opportunity();
        final String actionFlowId = "test";
        final String actionId = "test";
        final Map< String, Object > action = new HashMap<>();
        actionGroupServiceImpl.patchAgentActionInfo( agentId, actionFlowId, actionId, action );
        Mockito.verify( actionGroupDao ).patchAction( agentId, actionFlowId, actionId, action );
    }

    /**
     * Test get action group.
     */
    @Test
    public void testGetActionGroup() {
        final String agentId = "test";
        final String actionGroupId = "test";
        final ActionGroup expectedActionGroup = new ActionGroup();
        when( actionGroupDao.getActionGroup( agentId, actionGroupId ) ).thenReturn( expectedActionGroup );
        final ActionGroup actualActionGroup = actionGroupServiceImpl.getActionGroup( agentId, actionGroupId );
        assertEquals( actualActionGroup, expectedActionGroup );
        verify( actionGroupDao ).getActionGroup( agentId, actionGroupId );
    }

    /**
     * Test get start time.
     */
    @Test
    public void testGetStartTime() {
        final String actionGroupId = "test";
        final List< OpportunityAction > expectedActions = new ArrayList<>();
        when( opportunityActionRepository.findByActionFlowId( actionGroupId ) ).thenReturn( expectedActions );
        final List< OpportunityAction > actualActions = actionGroupServiceImpl.getStartTime( actionGroupId );
        assertEquals( actualActions, expectedActions );
        verify( opportunityActionRepository ).findByActionFlowId( actionGroupId );
    }

    /**
     * Test save action group.
     */
    @Test
    public void testSaveActionGroup() {
        final List< OpportunityAction > expectedActionFlows = new ArrayList<>();
        when( opportunityActionRepository.save( expectedActionFlows ) ).thenReturn( expectedActionFlows );
        final List< OpportunityAction > actualActions = actionGroupServiceImpl.saveActionGroup( expectedActionFlows );
        assertEquals( actualActions, expectedActionFlows );
        verify( opportunityActionRepository ).save( expectedActionFlows );
    }

    /**
     * Test save action.
     */
    @Test
    public void testSaveAction() {
        final OpportunityAction expectedAction = new OpportunityAction();
        when( opportunityActionRepository.save( expectedAction ) ).thenReturn( expectedAction );
        final OpportunityAction actualAction = actionGroupServiceImpl.saveAction( expectedAction );
        assertEquals( actualAction, expectedAction );
        verify( opportunityActionRepository ).save( expectedAction );
    }

    /**
     * Test patch action group.
     */
    @Test
    public void testPatchActionGroup() {
        final String agentId = "test";
        final String actionGroupId = "test";
        final Map< String, Object > actionFlowData = new HashMap<>();
        final String agentEmail = "test";
        doNothing().when( actionGroupDao ).patchActionGroup( agentId, actionGroupId, actionFlowData );
        actionGroupServiceImpl.patchActionGroup( agentId, actionGroupId, agentEmail, actionFlowData );
        verify( actionGroupDao ).patchActionGroup( agentId, actionGroupId, actionFlowData );
    }

    /**
     * Test get opportunity action.
     */
    @Test
    public void testGetOpportunityAction() {
        final String actionGroupId = "test";
        final String actionId = "test";
        final OpportunityAction expectedOpportunityAction = new OpportunityAction();
        when( opportunityActionRepository.findByActionFlowIdAndActionId( actionGroupId, actionId ) )
                .thenReturn( expectedOpportunityAction );
        final OpportunityAction actualOpportunityAction = actionGroupServiceImpl.getOpportunityAction( actionGroupId,
                actionId );
        assertEquals( actualOpportunityAction, expectedOpportunityAction );
        verify( opportunityActionRepository ).findByActionFlowIdAndActionId( actionGroupId, actionId );
    }

    @Test
    public void testSaveActionGroupShouldReturnList() {
        final String actionFlowId = "test";
        final ActionGroup actionGroup = new ActionGroup();
        final List< OpportunityAction > expectedOpportuityAction = new ArrayList<>();
        final OpportunityAction opportunityAction = new OpportunityAction();
        opportunityAction.setActionFlowId( actionFlowId );
        expectedOpportuityAction.add( opportunityAction );
        when( opportunityActionBuilder.convertTo( actionGroup ) ).thenReturn( expectedOpportuityAction );
        when( opportunityActionRepository.save( expectedOpportuityAction ) ).thenReturn( expectedOpportuityAction );
        final List< OpportunityAction > actualActionGroup = actionGroupServiceImpl.saveActionGroup( actionFlowId,
                actionGroup );
        assertEquals( actualActionGroup, expectedOpportuityAction );
        assertEquals( actualActionGroup.get( 0 ).getActionFlowId(),
                expectedOpportuityAction.get( 0 ).getActionFlowId() );
        verify( opportunityActionBuilder ).convertTo( actionGroup );
        verify( opportunityActionRepository ).save( expectedOpportuityAction );
    }
}
