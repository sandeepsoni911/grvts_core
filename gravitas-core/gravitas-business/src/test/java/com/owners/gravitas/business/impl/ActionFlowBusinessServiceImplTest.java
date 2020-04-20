package com.owners.gravitas.business.impl;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.flowable.engine.RuntimeService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.business.AgentNotificationBusinessService;
import com.owners.gravitas.business.builder.domain.ActionGroupBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.ActionFlowConfig;
import com.owners.gravitas.domain.Action;
import com.owners.gravitas.domain.ActionGroup;
import com.owners.gravitas.domain.Opportunity;
import com.owners.gravitas.domain.Search;
import com.owners.gravitas.domain.entity.Group;
import com.owners.gravitas.domain.entity.OpportunityAction;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.domain.entity.UserGroup;
import com.owners.gravitas.dto.request.NotificationRequest;
import com.owners.gravitas.dto.response.PostResponse;
import com.owners.gravitas.service.ActionGroupService;
import com.owners.gravitas.service.AgentOpportunityService;
import com.owners.gravitas.service.GroupService;
import com.owners.gravitas.service.UserGroupService;

/**
 * The Class ActionFlowBusinessServiceImplTest.
 *
 * @author raviz
 */
public class ActionFlowBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The action flow business service impl. */
    @InjectMocks
    private ActionFlowBusinessServiceImpl actionFlowBusinessServiceImpl;

    /** The agent push notification business service. */
    @Mock
    private AgentNotificationBusinessService agentNotificationBusinessService;

    /** The action group builder. */
    @Mock
    private ActionGroupBuilder actionGroupBuilder;

    /** The action group service. */
    @Mock
    private ActionGroupService actionGroupService;

    /** The agent opportunity service. */
    @Mock
    private AgentOpportunityService agentOpportunityService;

    /** The action flow config. */
    @Mock
    private ActionFlowConfig actionFlowConfig;

    /** The group service. */
    @Mock
    private GroupService groupService;

    /** The user group service. */
    @Mock
    private UserGroupService userGroupService;

    /** The runtime service. */
    @Mock
    protected RuntimeService runtimeService;

    /**
     * Test create action group should create action group.
     */
    @Test
    public void testCreateActionGroupShouldCreateActionGroup() {
        final String opportunityId = "opportunityId";
        final String agentId = "agentId";
        final Opportunity opportunity = new Opportunity();
        final Search agentSearch = new Search();
        final ActionGroup actionGroup = new ActionGroup();
        final PostResponse postReponse = new PostResponse();

        when( actionGroupBuilder.convertTo( opportunityId ) ).thenReturn( actionGroup );
        when( actionGroupService.createActionGroup( agentId, actionGroup, opportunityId, opportunity ) )
                .thenReturn( postReponse );
        doNothing().when( agentOpportunityService ).patchOpportunity( anyString(), anyString(), anyString(), anyMap() );
        when( runtimeService.startProcessInstanceByKey( anyString(), anyMap() ) ).thenReturn( null );
        when( agentNotificationBusinessService.sendPushNotification( anyString(), any( NotificationRequest.class ) ) )
                .thenReturn( new ArrayList< String >() );

        actionFlowBusinessServiceImpl.createActionGroup( opportunityId, agentId, opportunity, agentSearch );

        verify( actionGroupBuilder ).convertTo( opportunityId );
        verify( actionGroupService ).createActionGroup( agentId, actionGroup, opportunityId, opportunity );
        verify( agentOpportunityService ).patchOpportunity( anyString(), anyString(), anyString(), anyMap() );
        verify( runtimeService ).startProcessInstanceByKey( anyString(), anyMap() );
        verify( agentNotificationBusinessService ).sendPushNotification( anyString(),
                any( NotificationRequest.class ) );
    }

    /**
     * Test create action group should not create action group.
     */
    @Test
    public void testCreateActionGroupShouldNotCreateActionGroup() {
        final String opportunityId = "opportunityId";
        final String agentId = "agentId";
        final Opportunity opportunity = new Opportunity();
        final Search agentSearch = new Search();

        when( actionGroupBuilder.convertTo( opportunityId ) ).thenReturn( null );

        actionFlowBusinessServiceImpl.createActionGroup( opportunityId, agentId, opportunity, agentSearch );

        verify( actionGroupBuilder ).convertTo( opportunityId );
        verifyZeroInteractions( actionGroupService );
        verifyZeroInteractions( agentOpportunityService );
        verifyZeroInteractions( runtimeService );
        verifyZeroInteractions( agentNotificationBusinessService );
    }

    /**
     * Test get action info.
     */
    @Test
    public void testGetActionInfo() {
        final String agentId = "agentId";
        final String actionFlowId = "actionFlowId";
        final String action = "action";
        final Action expectedReponse = new Action();
        when( actionGroupService.getActionInfo( agentId, actionFlowId, action ) ).thenReturn( expectedReponse );
        final Action actualResponse = actionFlowBusinessServiceImpl.getActionInfo( agentId, actionFlowId, action );
        assertEquals( actualResponse, expectedReponse );
        verify( actionGroupService ).getActionInfo( agentId, actionFlowId, action );
    }

    /**
     * Test is eligible for scripted call should return true when agent is part
     * of bucket.
     */
    @Test
    public void testIsEligibleForScriptedCallShouldReturnTrueWhenAgentIsPartOfBucket() {
        final String opportunityType = "Buyer";
        final String agentEmail = "test@test.com";
        final String bucket = "bucket";
        final Group group = new Group();
        final User user = new User();
        user.setEmail( agentEmail );
        final UserGroup userGroup = new UserGroup();
        userGroup.setUser( user );
        final Set< UserGroup > userGroups = new HashSet<>();
        userGroups.add( userGroup );

        when( actionFlowConfig.isScriptedCallsEnabled() ).thenReturn( true );
        when( actionFlowConfig.getScriptedCallsBucket() ).thenReturn( bucket );
        when( groupService.findGroupByNameAndDeleted( bucket, false ) ).thenReturn( group );
        when( userGroupService.findByGroup( group ) ).thenReturn( userGroups );

        final boolean result = actionFlowBusinessServiceImpl.isEligibleForScriptedCall( opportunityType, agentEmail );
        assertTrue( result );
        verify( actionFlowConfig ).isScriptedCallsEnabled();
        verify( actionFlowConfig ).getScriptedCallsBucket();
        verify( groupService ).findGroupByNameAndDeleted( bucket, false );
        verify( userGroupService ).findByGroup( group );
    }

    /**
     * Test is eligible for scripted call should return false when agent is part
     * of bucket.
     */
    @Test
    public void testIsEligibleForScriptedCallShouldReturnFalseWhenAgentIsNotPartOfBucket() {
        final String opportunityType = "Buyer";
        final String agentEmail = "test@test.com";
        final String bucket = "bucket";
        final Group group = new Group();
        final User user = new User();
        user.setEmail( "differentEmail" );
        final UserGroup userGroup = new UserGroup();
        userGroup.setUser( user );
        final Set< UserGroup > userGroups = new HashSet<>();
        userGroups.add( userGroup );

        when( actionFlowConfig.isScriptedCallsEnabled() ).thenReturn( true );
        when( actionFlowConfig.getScriptedCallsBucket() ).thenReturn( bucket );
        when( groupService.findGroupByNameAndDeleted( bucket, false ) ).thenReturn( group );
        when( userGroupService.findByGroup( group ) ).thenReturn( userGroups );

        final boolean result = actionFlowBusinessServiceImpl.isEligibleForScriptedCall( opportunityType, agentEmail );
        assertFalse( result );
        verify( actionFlowConfig ).isScriptedCallsEnabled();
        verify( actionFlowConfig ).getScriptedCallsBucket();
        verify( groupService ).findGroupByNameAndDeleted( bucket, false );
        verify( userGroupService ).findByGroup( group );
    }

    /**
     * Test is eligible for scripted call should return false when bucket not
     * found.
     */
    @Test
    public void testIsEligibleForScriptedCallShouldReturnFalseWhenBucketNotFound() {
        final String opportunityType = "Buyer";
        final String agentEmail = "test@test.com";
        final String bucket = "bucket";

        when( actionFlowConfig.isScriptedCallsEnabled() ).thenReturn( true );
        when( actionFlowConfig.getScriptedCallsBucket() ).thenReturn( bucket );
        when( groupService.findGroupByNameAndDeleted( bucket, false ) ).thenReturn( null );

        final boolean result = actionFlowBusinessServiceImpl.isEligibleForScriptedCall( opportunityType, agentEmail );
        assertFalse( result );
        verify( actionFlowConfig ).isScriptedCallsEnabled();
        verify( actionFlowConfig ).getScriptedCallsBucket();
        verify( groupService ).findGroupByNameAndDeleted( bucket, false );
        verifyZeroInteractions( userGroupService );
    }

    /**
     * The test is eligible for scripted call should return true when bucket is
     * null
     */
    @Test
    public void testIsEligibleForScriptedCallShouldReturnTrueWhenBucketIsNull() {
        final String opportunityType = "Buyer";
        final String agentEmail = "test@test.com";

        when( actionFlowConfig.isScriptedCallsEnabled() ).thenReturn( true );
        when( actionFlowConfig.getScriptedCallsBucket() ).thenReturn( null );

        final boolean result = actionFlowBusinessServiceImpl.isEligibleForScriptedCall( opportunityType, agentEmail );
        assertTrue( result );
        verify( actionFlowConfig ).isScriptedCallsEnabled();
        verify( actionFlowConfig ).getScriptedCallsBucket();
        verifyZeroInteractions( groupService );
        verifyZeroInteractions( userGroupService );
    }

    /**
     * The test is eligible for scripted call should return true when bucket is
     * empty
     */
    @Test
    public void testIsEligibleForScriptedCallShouldReturnTrueWhenBucketIsEmpty() {
        final String opportunityType = "Buyer";
        final String agentEmail = "test@test.com";

        when( actionFlowConfig.isScriptedCallsEnabled() ).thenReturn( true );
        when( actionFlowConfig.getScriptedCallsBucket() ).thenReturn( "  " );

        final boolean result = actionFlowBusinessServiceImpl.isEligibleForScriptedCall( opportunityType, agentEmail );
        assertTrue( result );
        verify( actionFlowConfig ).isScriptedCallsEnabled();
        verify( actionFlowConfig ).getScriptedCallsBucket();
        verifyZeroInteractions( groupService );
        verifyZeroInteractions( userGroupService );
    }

    /**
     * Test is eligible for scripted call should return false when scripted call
     * disabled.
     */
    @Test
    public void testIsEligibleForScriptedCallShouldReturnFalseWhenScriptedCallDisabled() {
        final String opportunityType = "Buyer";
        final String agentEmail = "test@test.com";

        when( actionFlowConfig.isScriptedCallsEnabled() ).thenReturn( false );

        final boolean result = actionFlowBusinessServiceImpl.isEligibleForScriptedCall( opportunityType, agentEmail );
        assertFalse( result );
        verify( actionFlowConfig ).isScriptedCallsEnabled();
        verify( actionFlowConfig, Mockito.times( 0 ) ).getScriptedCallsBucket();
        verifyZeroInteractions( groupService );
        verifyZeroInteractions( userGroupService );
    }

    /**
     * Test is eligible for scripted call should return false when opportunity
     * type is not buyer.
     */
    @Test
    public void testIsEligibleForScriptedCallShouldReturnFalseWhenOpportunityTypeIsNotBuyer() {
        final String opportunityType = "Seller";
        final String agentEmail = "test@test.com";

        when( actionFlowConfig.isScriptedCallsEnabled() ).thenReturn( false );

        final boolean result = actionFlowBusinessServiceImpl.isEligibleForScriptedCall( opportunityType, agentEmail );
        assertFalse( result );
        verifyZeroInteractions( actionFlowConfig );
        verifyZeroInteractions( groupService );
        verifyZeroInteractions( userGroupService );
    }

    /**
     * Test agent is eligible for scripted call should return true when agent is
     * part of bucket.
     */
    @Test
    public void testAgentIsEligibleForScriptedCallShouldReturnTrueWhenAgentIsPartOfBucket() {
        final String agentEmail = "test@test.com";
        final String bucket = "bucket";
        final Group group = new Group();
        final User user = new User();
        user.setEmail( agentEmail );
        final UserGroup userGroup = new UserGroup();
        userGroup.setUser( user );
        final Set< UserGroup > userGroups = new HashSet<>();
        userGroups.add( userGroup );

        when( actionFlowConfig.isScriptedCallsEnabled() ).thenReturn( true );
        when( actionFlowConfig.getScriptedCallsBucket() ).thenReturn( bucket );
        when( groupService.findGroupByNameAndDeleted( bucket, false ) ).thenReturn( group );
        when( userGroupService.findByGroup( group ) ).thenReturn( userGroups );

        final boolean result = actionFlowBusinessServiceImpl.isEligibleForScriptedCall( agentEmail );
        assertTrue( result );
        verify( actionFlowConfig ).isScriptedCallsEnabled();
        verify( actionFlowConfig ).getScriptedCallsBucket();
        verify( groupService ).findGroupByNameAndDeleted( bucket, false );
        verify( userGroupService ).findByGroup( group );
    }

    @Test
    public void testAgentIsEligibleForScriptedCallShouldReturnFalseWhenAgentIsNotPartOfBucket() {
        final String agentEmail = "test@test.com";
        final String bucket = "bucket";
        final Group group = new Group();
        final User user = new User();
        user.setEmail( "differentEmail" );
        final UserGroup userGroup = new UserGroup();
        userGroup.setUser( user );
        final Set< UserGroup > userGroups = new HashSet<>();
        userGroups.add( userGroup );

        when( actionFlowConfig.isScriptedCallsEnabled() ).thenReturn( true );
        when( actionFlowConfig.getScriptedCallsBucket() ).thenReturn( bucket );
        when( groupService.findGroupByNameAndDeleted( bucket, false ) ).thenReturn( group );
        when( userGroupService.findByGroup( group ) ).thenReturn( userGroups );

        final boolean result = actionFlowBusinessServiceImpl.isEligibleForScriptedCall( agentEmail );
        assertFalse( result );
        verify( actionFlowConfig ).isScriptedCallsEnabled();
        verify( actionFlowConfig ).getScriptedCallsBucket();
        verify( groupService ).findGroupByNameAndDeleted( bucket, false );
        verify( userGroupService ).findByGroup( group );
    }

    /**
     * Test agent is eligible for scripted call should return false when bucket
     * not found.
     */
    @Test
    public void testAgentIsEligibleForScriptedCallShouldReturnFalseWhenBucketNotFound() {
        final String agentEmail = "test@test.com";
        final String bucket = "bucket";

        when( actionFlowConfig.isScriptedCallsEnabled() ).thenReturn( true );
        when( actionFlowConfig.getScriptedCallsBucket() ).thenReturn( bucket );
        when( groupService.findGroupByNameAndDeleted( bucket, false ) ).thenReturn( null );

        final boolean result = actionFlowBusinessServiceImpl.isEligibleForScriptedCall( agentEmail );
        assertFalse( result );
        verify( actionFlowConfig ).isScriptedCallsEnabled();
        verify( actionFlowConfig ).getScriptedCallsBucket();
        verify( groupService ).findGroupByNameAndDeleted( bucket, false );
        verifyZeroInteractions( userGroupService );
    }

    /**
     * The test agent is eligible for scripted call should return true when
     * bucket is
     * null
     */
    @Test
    public void testAgentIsEligibleForScriptedCallShouldReturnTrueWhenBucketIsNull() {
        final String agentEmail = "test@test.com";

        when( actionFlowConfig.isScriptedCallsEnabled() ).thenReturn( true );
        when( actionFlowConfig.getScriptedCallsBucket() ).thenReturn( null );

        final boolean result = actionFlowBusinessServiceImpl.isEligibleForScriptedCall( agentEmail );
        assertTrue( result );
        verify( actionFlowConfig ).isScriptedCallsEnabled();
        verify( actionFlowConfig ).getScriptedCallsBucket();
        verifyZeroInteractions( groupService );
        verifyZeroInteractions( userGroupService );
    }

    /**
     * The test agent is eligible for scripted call should return true when
     * bucket is
     * empty
     */
    @Test
    public void testAgentIsEligibleForScriptedCallShouldReturnTrueWhenBucketIsEmpty() {
        final String agentEmail = "test@test.com";

        when( actionFlowConfig.isScriptedCallsEnabled() ).thenReturn( true );
        when( actionFlowConfig.getScriptedCallsBucket() ).thenReturn( "  " );

        final boolean result = actionFlowBusinessServiceImpl.isEligibleForScriptedCall( agentEmail );
        assertTrue( result );
        verify( actionFlowConfig ).isScriptedCallsEnabled();
        verify( actionFlowConfig ).getScriptedCallsBucket();
        verifyZeroInteractions( groupService );
        verifyZeroInteractions( userGroupService );
    }

    /**
     * The test agent is eligible for scripted call should return false when
     * scripted call
     * disabled.
     */
    @Test
    public void testAgentIsEligibleForScriptedCallShouldReturnFalseWhenScriptedCallDisabled() {
        final String agentEmail = "test@test.com";

        when( actionFlowConfig.isScriptedCallsEnabled() ).thenReturn( false );

        final boolean result = actionFlowBusinessServiceImpl.isEligibleForScriptedCall( agentEmail );
        assertFalse( result );
        verify( actionFlowConfig ).isScriptedCallsEnabled();
        verify( actionFlowConfig, Mockito.times( 0 ) ).getScriptedCallsBucket();
        verifyZeroInteractions( groupService );
        verifyZeroInteractions( userGroupService );
    }

    /**
     * Test get opportunity action.
     */
    @Test
    public void testGetOpportunityAction() {
        final String actionGroupId = "actionGroupId";
        final String actionId = "actionId";
        final OpportunityAction expectedValue = new OpportunityAction();
        when( actionGroupService.getOpportunityAction( actionGroupId, actionId ) ).thenReturn( expectedValue );
        final OpportunityAction ActualValue = actionFlowBusinessServiceImpl.getOpportunityAction( actionGroupId,
                actionId );
        assertEquals( ActualValue, expectedValue );
        verify( actionGroupService ).getOpportunityAction( actionGroupId, actionId );
    }

}
