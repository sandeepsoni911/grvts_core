package com.owners.gravitas.business.impl;

import static com.owners.gravitas.enums.ProspectAttributeType.NOTES;
import static com.owners.gravitas.enums.UserStatusType.INACTIVE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.api.client.util.ArrayMap;
import com.owners.gravitas.business.builder.AgentDetailsBuilder;
import com.owners.gravitas.business.builder.UserBuilder;
import com.owners.gravitas.business.builder.domain.GoogleUserBuilder;
import com.owners.gravitas.business.builder.request.CRMAgentRequestBuilder;
import com.owners.gravitas.business.builder.response.AgentDetailsResponseBuilder;
import com.owners.gravitas.business.builder.response.AgentsResponseBuilder;
import com.owners.gravitas.business.builder.response.RoleDetailsResponseBuilder;
import com.owners.gravitas.business.builder.response.UserDetailsResponseBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.ActivityProperty;
import com.owners.gravitas.domain.entity.AgentDetails;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.domain.entity.ContactActivity;
import com.owners.gravitas.domain.entity.ObjectAttributeConfig;
import com.owners.gravitas.domain.entity.RefCode;
import com.owners.gravitas.domain.entity.Role;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.domain.entity.UserLoginLog;
import com.owners.gravitas.dto.Agent;
import com.owners.gravitas.dto.UserDetails;
import com.owners.gravitas.dto.crm.request.UserLoginLogRequest;
import com.owners.gravitas.dto.request.UserNotificationConfigDetails;
import com.owners.gravitas.dto.response.AgentDetailsResponse;
import com.owners.gravitas.dto.response.AgentsResponse;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.RoleDetailsResponse;
import com.owners.gravitas.dto.response.UserActivityResponse;
import com.owners.gravitas.dto.response.UserDetailsResponse;
import com.owners.gravitas.dto.response.UserNotificationConfigResponse;
import com.owners.gravitas.dto.response.UserNotificationConfigResult;
import com.owners.gravitas.service.AgentDetailsService;
import com.owners.gravitas.service.BuyerService;
import com.owners.gravitas.service.ContactActivityService;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.MailDetailStatusService;
import com.owners.gravitas.service.ObjectAttributeConfigService;
import com.owners.gravitas.service.RoleMemberService;
import com.owners.gravitas.service.UserService;

/**
 * The Class UserBusinessServiceImplTest.
 *
 * @author amits
 */
public class UserBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The user business service impl. */ 
    @InjectMocks
    private UserBusinessServiceImpl userBusinessServiceImpl;

    /** The contact service V 1. */
    @Mock
    private ContactEntityService contactServiceV1;

    /** The contact activity service. */
    @Mock
    private ContactActivityService contactActivityService;

    @Mock
    private MailDetailStatusService mailDetailStatusService;

    /** The agent onboarding service. */
    @Mock
    private UserService userService;
    
    @Mock
    private BuyerService buyerService;

    /** The agent details builder. */
    @Mock
    private AgentDetailsResponseBuilder agentDetailsResponseBuilder;

    /** The user builder. */
    @Mock
    private GoogleUserBuilder googleUserBuilder;

    /** The agent user builder. */
    @Mock
    private UserBuilder userBuilder;

    /** The user details response builder. */
    @Mock
    private UserDetailsResponseBuilder userDetailsResponseBuilder;

    /** The crm agent request builder. */
    @Mock
    private CRMAgentRequestBuilder crmAgentRequestBuilder;

    /** The agent details service. */
    @Mock
    private AgentDetailsService agentDetailsService;

    /** The agent details builder. */
    @Mock
    private AgentDetailsBuilder agentDetailsBuilder;

    /** The role member service. */
    @Mock
    private RoleMemberService roleMemberService;

    /** The role details response builder. */
    @Mock
    private RoleDetailsResponseBuilder roleDetailsResponseBuilder;

    /** The agents response builder. */
    @Mock
    private AgentsResponseBuilder agentsResponseBuilder;
    
    @Mock
    private ObjectAttributeConfigService objectAttributeConfigService;

    /**
     * Test get agent details.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    private void testGetAgentDetails() throws Exception {
        
        final AgentDetailsResponse agentDetailsResponse = new AgentDetailsResponse();
        final Agent agent = new Agent();
        agentDetailsResponse.setDetails(agent);
        
        final UserDetails details = new UserDetails();
        details.setUser( new com.google.api.services.admin.directory.model.User() );
        
        Mockito.when( userService.getUserDetails( Mockito.anyObject() ) ).thenReturn( details );
        Mockito.when( agentDetailsResponseBuilder.convertTo( Mockito.any() ) ).thenReturn( agentDetailsResponse );
        final BaseResponse response = userBusinessServiceImpl.getUserDetails( "test" );
        Assert.assertNotNull( response );
    }

    /**
     * Test get users by filters when role is null.
     */
    @Test
    public void testGetUsersByFiltersWhenRoleIsNull() {
        final User user = new User();
        user.setEmail( "test@test.com" );
        final List< com.owners.gravitas.domain.entity.User > users = new ArrayList<>();
        users.add( user );

        final com.google.api.services.admin.directory.model.User googleUser = new com.google.api.services.admin.directory.model.User();
        googleUser.setEmails( "test@test.com" );

        final List< com.google.api.services.admin.directory.model.User > googleUsers = new ArrayList<>();
        googleUsers.add( googleUser );

        final com.owners.gravitas.dto.User dtoUsers = new com.owners.gravitas.dto.User();
        dtoUsers.setFirstName( "test" );
        dtoUsers.setLastName( "test" );
        dtoUsers.setEmail( "test@test.com" );
        dtoUsers.setStatus( "active" );

        final List< com.owners.gravitas.dto.User > dtoUsersList = new ArrayList<>();
        dtoUsersList.add( dtoUsers );

        final UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setUsers( dtoUsersList );
        when( userService.findAll() ).thenReturn( users );
        when( userService.getUsersByEmails( anyList() ) ).thenReturn( googleUsers );
        when( userDetailsResponseBuilder.convertTo( googleUsers ) ).thenReturn( userDetailsResponse );

        final BaseResponse baseResponse = userBusinessServiceImpl.getUsersByFilters( null, "default" );

        assertNotNull( baseResponse );
        verify( userService ).findAll();
        verify( userService ).getUsersByEmails( anyList() );
        verify( userDetailsResponseBuilder ).convertTo( googleUsers );
        verifyZeroInteractions( agentDetailsService );
        verifyZeroInteractions( agentsResponseBuilder );

    }

    /**
     * Test get users by filters when role not field agent.
     */
    @Test
    public void testGetUsersByFiltersWhenRoleNotFieldAgent() {
        final String role = "managing broker";
        final User user = new User();
        user.setEmail( "test@test.com" );
        final List< com.owners.gravitas.domain.entity.User > users = new ArrayList<>();
        users.add( user );

        final com.google.api.services.admin.directory.model.User googleUser = new com.google.api.services.admin.directory.model.User();
        googleUser.setEmails( "test@test.com" );

        final List< com.google.api.services.admin.directory.model.User > googleUsers = new ArrayList<>();
        googleUsers.add( googleUser );

        final com.owners.gravitas.dto.User dtoUsers = new com.owners.gravitas.dto.User();
        dtoUsers.setFirstName( "test" );
        dtoUsers.setLastName( "test" );
        dtoUsers.setEmail( "test@test.com" );
        dtoUsers.setStatus( "active" );

        final List< com.owners.gravitas.dto.User > dtoUsersList = new ArrayList<>();
        dtoUsersList.add( dtoUsers );

        final UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setUsers( dtoUsersList );

        when( userService.getUsersByRole( anyString(), anyList() ) ).thenReturn( users );
        when( userService.getUsersByEmails( anyList() ) ).thenReturn( googleUsers );
        when( userDetailsResponseBuilder.convertTo( googleUsers ) ).thenReturn( userDetailsResponse );

        final BaseResponse baseResponse = userBusinessServiceImpl.getUsersByFilters( role, "default" );

        assertNotNull( baseResponse );
        verify( userService ).getUsersByRole( anyString(), anyList() );
        verify( userService ).getUsersByEmails( anyList() );
        verify( userDetailsResponseBuilder ).convertTo( googleUsers );
        verifyZeroInteractions( agentDetailsService );
        verifyZeroInteractions( agentsResponseBuilder );

    }

    /**
     * Test get users by filters when role is field agent and filter is not
     * default.
     */
    @Test
    public void testGetUsersByFiltersWhenRoleIsFieldAgentAndFilterIsNotDefault() {
        final String role = "1099_AGENT";
        final String filter = "non-default";
        final User user = new User();
        user.setEmail( "test@test.com" );
        user.setStatus( "active" );
        final List< com.owners.gravitas.domain.entity.User > users = new ArrayList<>();
        users.add( user );

        final com.google.api.services.admin.directory.model.User googleUser = new com.google.api.services.admin.directory.model.User();
        googleUser.setEmails( "test@test.com" );
        googleUser.setAddresses( "test address" );

        final List< com.google.api.services.admin.directory.model.User > googleUsers = new ArrayList<>();
        googleUsers.add( googleUser );

        final com.owners.gravitas.dto.User dtoUsers = new com.owners.gravitas.dto.User();
        dtoUsers.setFirstName( "test" );
        dtoUsers.setLastName( "test" );
        dtoUsers.setEmail( "test@test.com" );
        dtoUsers.setStatus( "active" );

        final AgentDetails agents = new AgentDetails();
        agents.setUser( user );
        agents.setScore( 5.2 );

        final List< AgentDetails > agentDetails = new ArrayList<>();
        agentDetails.add( agents );

        final List< com.owners.gravitas.dto.User > dtoUsersList = new ArrayList<>();
        dtoUsersList.add( dtoUsers );

        final Agent agent = new Agent();
        agent.setFirstName( "test" );
        agent.setLastName( "test" );
        agent.setScore( 5.2 );
        agent.setStatus( "active" );
        agent.setEmail( "test@test.com" );

        final List< Agent > agentList = new ArrayList<>();
        agentList.add( agent );
        final AgentsResponse response = new AgentsResponse();
        response.setAgents( agentList );

        final UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setUsers( dtoUsersList );

        when( userService.getUsersByRole( anyString(), anyListOf( String.class ) ) ).thenReturn( users );
        when( userService.getUsersByEmails( anyList() ) ).thenReturn( googleUsers );
        when( agentDetailsService.getAgents( users ) ).thenReturn( agentDetails );
        when( agentsResponseBuilder.convertTo( googleUsers ) ).thenReturn( response );

        final BaseResponse baseResponse = userBusinessServiceImpl.getUsersByFilters( role, filter );

        assertNotNull( baseResponse );
        verify( userService ).getUsersByRole( anyString(), anyList() );
        verify( userService ).getUsersByEmails( anyList() );
        verify( agentDetailsService ).getAgents( users );
        verify( agentsResponseBuilder ).convertTo( googleUsers );
    }

    /**
     * Test get users by filters when role is field agent and filter is default.
     */
    @Test
    public void testGetUsersByFiltersWhenRoleIsFieldAgentAndFilterIsDefault() {
        final String role = "1099_AGENT";
        final String filter = "default";
        final User user = new User();
        user.setEmail( "test@test.com" );
        user.setStatus( "active" );
        final List< com.owners.gravitas.domain.entity.User > users = new ArrayList<>();
        users.add( user );

        final com.google.api.services.admin.directory.model.User googleUser = new com.google.api.services.admin.directory.model.User();
        googleUser.setEmails( "test@test.com" );
        googleUser.setAddresses( "test address" );

        final List< com.google.api.services.admin.directory.model.User > googleUsers = new ArrayList<>();
        googleUsers.add( googleUser );

        final com.owners.gravitas.dto.User dtoUsers = new com.owners.gravitas.dto.User();
        dtoUsers.setFirstName( "test" );
        dtoUsers.setLastName( "test" );
        dtoUsers.setEmail( "test@test.com" );
        dtoUsers.setStatus( "active" );

        final AgentDetails agents = new AgentDetails();
        agents.setUser( user );
        agents.setScore( 5.2 );

        final List< AgentDetails > agentDetails = new ArrayList<>();
        agentDetails.add( agents );

        final List< com.owners.gravitas.dto.User > dtoUsersList = new ArrayList<>();
        dtoUsersList.add( dtoUsers );

        final Agent agent = new Agent();
        agent.setFirstName( "test" );
        agent.setLastName( "test" );
        agent.setScore( 5.2 );
        agent.setStatus( "active" );
        agent.setEmail( "test@test.com" );

        final List< Agent > agentList = new ArrayList<>();
        agentList.add( agent );
        final AgentsResponse response = new AgentsResponse();
        response.setAgents( agentList );

        final UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setUsers( dtoUsersList );
        when( userService.getUsersByRole( anyString(), anyList() ) ).thenReturn( users );
        when( userService.getUsersByEmails( anyList() ) ).thenReturn( googleUsers );
        when( agentDetailsService.getAgents( users ) ).thenReturn( agentDetails );
        when( agentsResponseBuilder.convertTo( googleUsers ) ).thenReturn( response );

        final BaseResponse baseResponse = userBusinessServiceImpl.getUsersByFilters( role, filter );

        assertNotNull( baseResponse );
        verify( userService ).getUsersByRole( anyString(), anyList() );
        verify( userService ).getUsersByEmails( anyList() );
        verify( agentDetailsService ).getAgents( users );
        verify( agentsResponseBuilder ).convertTo( googleUsers );

    }

    /**
     * Test reset password.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    private void testResetPassword() throws IOException {
        Mockito.doNothing().when( userService ).resetPassword( Mockito.anyString() );
        userBusinessServiceImpl.resetPassword( "test" );
        Mockito.verify( userService ).resetPassword( Mockito.anyString() );
    }

    /**
     * Test get agent details by listing id.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    private void testGetAgentDetailsByListingId() throws IOException {
        final String listingId = "test";
        Mockito.when( agentDetailsService.findAgentEmailByListingId( Mockito.anyString() ) ).thenReturn( listingId );
        
        final AgentDetailsResponse agentDetailsResponse = new AgentDetailsResponse();
        final Agent agent = new Agent();
        agentDetailsResponse.setDetails(agent);
        
        final UserDetails details = new UserDetails();
        details.setUser( new com.google.api.services.admin.directory.model.User() );
        Mockito.when( userService.getUserDetails( Mockito.anyObject() ) ).thenReturn( details );
        Mockito.when( agentDetailsResponseBuilder.convertTo( Mockito.anyObject() ) ).thenReturn( agentDetailsResponse );
        userBusinessServiceImpl.getAgentDetails( listingId );
        Mockito.verify( agentDetailsService ).findAgentEmailByListingId( Mockito.anyString() );
    }

    /**
     * Test is google user exists.
     */
    @Test
    private void testIsGoogleUserExists() {
        Mockito.when( userService.isGoogleUserExists( Mockito.anyString() ) ).thenReturn( true );
        userBusinessServiceImpl.isGoogleUserExists( Mockito.anyString() );
        Mockito.verify( userService ).isGoogleUserExists( Mockito.anyString() );
    }

    /**
     * Testget user phones.
     */
    @Test
    private void testgetUserPhones() {
        final com.google.api.services.admin.directory.model.User user = new com.google.api.services.admin.directory.model.User();
        final List< ArrayMap< String, Object > > phones = new ArrayList< ArrayMap< String, Object > >();
        final ArrayMap< String, Object > map = new ArrayMap< String, Object >();
        map.add( "testKey", "testValue" );
        phones.add( map );
        user.setPhones( phones );
        Mockito.when( userService.getUser( Mockito.anyString() ) ).thenReturn( user );
        final List< ArrayMap< String, Object > > result = userBusinessServiceImpl.getUserPhones( Mockito.anyString() );
        Assert.assertEquals( phones.get( 0 ).get( "testKey" ), result.get( 0 ).get( "testKey" ) );
    }

    /**
     * Testget user roles by email.
     */
    @Test
    private void testGetUserRolesByEmail() {
        final Set< String > roles = new HashSet<>();
        roles.add( "ADMIN" );
        roles.add( "1099_AGENT" );
        Mockito.when( userService.getRoles( null, "test@user.com" ) ).thenReturn( roles );
        final List< Role > dbRoles = new ArrayList<>();
        final Role role = new Role();
        role.setName( "ADMIN" );
        role.setDescription( "testing" );
        dbRoles.add( role );
        Mockito.when( roleMemberService.getRolesByUserEmailId( "test@user.com" ) ).thenReturn( dbRoles );
        final RoleDetailsResponse response = new RoleDetailsResponse();
        final List< com.owners.gravitas.dto.Role > userRoles = new ArrayList<>();
        final com.owners.gravitas.dto.Role role1 = new com.owners.gravitas.dto.Role();
        role1.setName( "ADMIN" );

        role1.setDescription( "testing" );
        userRoles.add( role1 );
        response.setRoles( userRoles );
        Mockito.when( roleDetailsResponseBuilder.convertTo( Mockito.anyList() ) ).thenReturn( response );
        final RoleDetailsResponse actualResponse = ( RoleDetailsResponse ) userBusinessServiceImpl
                .getUserRolesByEmail( "test@user.com" );
        Assert.assertEquals( actualResponse.getRoles().get( 0 ).getName(), role.getName() );

    }

    /**
     * Test save user login log.
     */
    @Test
    public void testSaveUserLoginLog() {
        BaseResponse response = new BaseResponse();
        final UserLoginLogRequest userLoginLogRequest = new UserLoginLogRequest();
        userLoginLogRequest.setId( "id" );
        userLoginLogRequest.setCreatedDate( new DateTime() );
        userLoginLogRequest.setIpAddress( "test IP Address" );
        response = userBusinessServiceImpl.saveUserLoginLog( userLoginLogRequest );
        assertNotNull( response );
        verify( userService ).saveUserLoginLog( any( UserLoginLog.class ) );
    }

    /**
     * Test get agents by manager for default filter.
     */
    @Test
    public void testGetAgentsByManagerForDefaultFilter() {
        BaseResponse response = new BaseResponse();
        final String email = "test@test.com";
        final String filter = "default";
        final User user = new User();
        user.setEmail( "test@test.com" );
        user.setId( "id" );
        user.setStatus( "ACTIVE" );
        final AgentDetails agentDetails = new AgentDetails();
        agentDetails.setUser( user );
        agentDetails.setScore( 2.5 );
        final List< AgentDetails > agents = new ArrayList<>();
        agents.add( agentDetails );
        final com.google.api.services.admin.directory.model.User googleUser = new com.google.api.services.admin.directory.model.User();
        googleUser.setEmails( "test@test.com" );
        final List< com.google.api.services.admin.directory.model.User > googleUsers = new ArrayList<>();
        googleUsers.add( googleUser );

        final Agent agent = new Agent();
        agent.setEmail( "test@test.com" );
        agent.setScore( 2.5 );
        agent.setStatus( "ACTIVE" );
        final List< Agent > listAgent = new ArrayList<>();
        listAgent.add( agent );
        final AgentsResponse agentResponse = new AgentsResponse();
        agentResponse.setAgents( listAgent );
        when( userService.getUsersByManagingBroker( email ) ).thenReturn( agents );
        when( userService.getUsersByEmails( any( List.class ) ) ).thenReturn( googleUsers );
        when( agentsResponseBuilder.convertTo( any( List.class ) ) ).thenReturn( agentResponse );

        response = userBusinessServiceImpl.getAgentsByManager( email, filter );
        assertNotNull( response );
        verify( userService ).getUsersByManagingBroker( email );
        verify( userService ).getUsersByEmails( any( List.class ) );
        verify( agentsResponseBuilder ).convertTo( any( List.class ) );
    }

    /**
     * Test get agents by manager for non default filter.
     */
    @Test
    public void testGetAgentsByManagerForNonDefaultFilter() {
        BaseResponse response = new BaseResponse();
        final String email = "test@test.com";
        final String filter = "filter";
        final User user = new User();
        user.setEmail( "user@test.com" );
        user.setId( "id" );
        user.setStatus( "ACTIVE" );
        final AgentDetails agentDetails = new AgentDetails();
        agentDetails.setUser( user );
        agentDetails.setScore( 2.5 );
        final List< AgentDetails > agents = new ArrayList<>();
        agents.add( agentDetails );
        final com.google.api.services.admin.directory.model.User googleUser = new com.google.api.services.admin.directory.model.User();
        googleUser.setEmails( "test@test.com" );
        final List< com.google.api.services.admin.directory.model.User > googleUsers = new ArrayList<>();
        googleUsers.add( googleUser );
        final AgentsResponse agentResponse = new AgentsResponse();
        agentResponse.setAgents( new ArrayList<>() );
        when( userService.getUsersByManagingBroker( email ) ).thenReturn( agents );
        when( userService.getUsersByEmails( any( List.class ) ) ).thenReturn( googleUsers );
        when( agentsResponseBuilder.convertTo( any( List.class ) ) ).thenReturn( agentResponse );

        response = userBusinessServiceImpl.getAgentsByManager( email, filter );
        assertNotNull( response );
        verify( userService ).getUsersByManagingBroker( email );
        verify( userService ).getUsersByEmails( any( List.class ) );
        verify( agentsResponseBuilder ).convertTo( any( List.class ) );
    }

    /**
     * Test get agents by manager for user status in active.
     */
    @Test
    public void testGetAgentsByManagerForUserStatusInActive() {
        BaseResponse response = new BaseResponse();
        final String email = "test@test.com";
        final String filter = "default";
        final User user = new User();
        user.setEmail( "user@test.com" );
        user.setId( "id" );
        user.setStatus( INACTIVE.toString() );

        final com.google.api.services.admin.directory.model.User googleUser = new com.google.api.services.admin.directory.model.User();
        googleUser.setEmails( "test@test.com" );

        final AgentDetails agentDetails = new AgentDetails();
        agentDetails.setUser( user );
        agentDetails.setScore( 2.5 );
        final List< AgentDetails > agents = new ArrayList<>();
        agents.add( agentDetails );
        final List< com.google.api.services.admin.directory.model.User > googleUsers = new ArrayList<>();
        googleUsers.add( googleUser );
        final AgentsResponse agentResponse = new AgentsResponse();
        agentResponse.setAgents( new ArrayList<>() );
        when( userService.getUsersByManagingBroker( email ) ).thenReturn( agents );
        when( userService.getUsersByEmails( any( List.class ) ) ).thenReturn( googleUsers );
        when( agentsResponseBuilder.convertTo( any( List.class ) ) ).thenReturn( agentResponse );

        response = userBusinessServiceImpl.getAgentsByManager( email, filter );
        assertNotNull( response );
        verify( userService ).getUsersByManagingBroker( email );
        verify( userService ).getUsersByEmails( any( List.class ) );
        verify( agentsResponseBuilder ).convertTo( any( List.class ) );
    }

    /**
     * Test get agents details by userId and opportunity Type for user status in
     * active.
     */
    @Test
    public void testGetAgentsDetails() {
        BaseResponse response = new BaseResponse();
        
        final UserDetails userDetails = new UserDetails();
        final List< UserDetails > users = new ArrayList<>();
        
        final com.google.api.services.admin.directory.model.User user = new com.google.api.services.admin.directory.model.User();
        userDetails.setUser(user);
        user.setEmails( "test@test.com" );
        
        users.add( userDetails );
        final String email = "test@test.com";
        final String agentOpportunityType = "BUYER";

        when( userService.getAgentsDetails( Mockito.anyString(), Mockito.anyString() ) ).thenReturn( users );
        Mockito.when( agentDetailsResponseBuilder.convertTo( Mockito.any() ) ).thenReturn( new AgentDetailsResponse() );

        response = userBusinessServiceImpl.getAgentsDetails( email, agentOpportunityType );
        assertNotNull( response );
        verify( userService ).getAgentsDetails( Mockito.anyString(), Mockito.anyString() );
    }

    /**
     * Test get user activity details.
     */
    @Test
    public void testGetUserActivityDetails() {
        final String input = "input@a.com";
        final Contact contact = new Contact();
        final String firstName = "f";
        contact.setFirstName( firstName );
        final String lastName = "l";
        contact.setLastName( lastName );
        final String phone = "123456789";
        contact.setPhone( phone );
        final String email = "e";
        contact.setEmail( email );
        contact.setCreatedDate( new DateTime() );
        final List< ContactActivity > contactActivity =  new ArrayList<>();
        final ContactActivity e = new ContactActivity();
        e.setRefCode( new RefCode() );
        e.setCreatedDate( DateTime.now() );
        final List< ActivityProperty > activityProperties = new ArrayList<>();
        final ActivityProperty property = new ActivityProperty();
        activityProperties.add( property );
        e.setActivityProperties( activityProperties );
        contactActivity.add( e );
        final ObjectAttributeConfig config = new ObjectAttributeConfig();

        when( contactServiceV1.getContactByEmail( input ) ).thenReturn( contact );
        when( contactActivityService.getContactActivityByUserId( contact.getOwnersComId() ) )
                .thenReturn( contactActivity );
        when( mailDetailStatusService.getNotificationFeedback( null ) ).thenReturn( null );
        
        when(objectAttributeConfigService.getObjectAttributeConfig(NOTES.getKey(),
				contact.getObjectType())).thenReturn(config);
        
        final BaseResponse actual = userBusinessServiceImpl.getUserActivityDetails( input );

        assertTrue( actual.getClass().isAssignableFrom( UserActivityResponse.class ) );
        final UserActivityResponse response = ( UserActivityResponse ) actual;
        assertEquals( firstName, response.getFirstName() );
        assertEquals( lastName, response.getLastName() );
        assertEquals( phone, response.getPhone() );
        assertEquals( email, response.getEmail() );
        verify( contactServiceV1 ).getContactByEmail( input );
        verify( contactActivityService ).getContactActivityByUserId( contact.getOwnersComId() );
    }

    /**
     * Test get user activity details should return null if contact not found.
     */
    @Test
    public void testGetUserActivityDetails_ShouldReturnNullIfContactNotFound() {
        final String input = "input@a.com";
        final Contact contact = null;
        when( contactServiceV1.getContactByEmail( input ) ).thenReturn( contact );
        final BaseResponse actual = userBusinessServiceImpl.getUserActivityDetails( input );
        assertNull( actual );
        verifyZeroInteractions( contactActivityService );
    }

    /**
     * Test get user activity details for non email input.
     */
    @Test
    public void testGetUserActivityDetails_ForNonEmailInput() {
        final String input = "input";
        final Contact contact = new Contact();
        contact.setCreatedDate( new DateTime() );
        when( contactServiceV1.getContactByOwnersComId( input ) ).thenReturn( contact );
        when( contactActivityService.getContactActivityByUserId( input ) ).thenReturn( null );
        final BaseResponse actual = userBusinessServiceImpl.getUserActivityDetails( input );
        assertNotNull( actual );
    }

    /**
     * Test get agents details should return empty.
     */
    @Test
    public void testGetAgentsDetailsShouldReturnEmpty() {
        final String userId = "uId";
        final String agentOpportunityType = "type";
        when( userService.getAgentsDetails( userId, agentOpportunityType ) ).thenReturn( new ArrayList<>() );
        final BaseResponse response = userBusinessServiceImpl.getAgentsDetails( userId, agentOpportunityType );
        assertEquals( "No Agent available for this client", response.getMessage() );
        verifyZeroInteractions( agentDetailsResponseBuilder );
    }
    
    @Test
    public void testGetUserActivityDetails_ForUserActivityResponseNotNull()  {
    	final String input = "input@gmail.com";
        final Contact contact = new Contact();
        contact.setOwnersComId("abc");
        contact.setCreatedDate( new DateTime() );
        when( contactServiceV1.getContactByEmail( input ) ).thenReturn( contact );
        when( contactActivityService.getContactActivityByUserId( anyString() ) ).thenReturn( null );
        final BaseResponse actual = userBusinessServiceImpl.getUserActivityDetails( input );
        assertNotNull( actual );
    }
    
    /**
     * To get timezone
     */
    @Test
	public void testGetAgentTimeZone() {
		final User user = new User();
		user.setEmail("test@test.com");
		user.setStatus("active");
		final AgentDetails agent = new AgentDetails();
		agent.setUser(user);
		agent.setState("Havana");
		when(agentDetailsService.findAgentByEmail(anyString())).thenReturn(agent);
		TimeZone timeZone = userBusinessServiceImpl.getAgentsTimeZone("someEmailId");
		assertNotNull(timeZone);
	}
    
    @Test
    public void testUpdateNotificationConfigForSuccess() {
        UserNotificationConfigDetails userNotificationConfigDetails = new UserNotificationConfigDetails();
        Contact contact = new Contact();
        when( contactServiceV1.getContactByOwnersComId( userNotificationConfigDetails.getUserId() ) )
                .thenReturn( contact );
        String mappedSubscriptionType = "";
        when( userService.getSubscriptionType( userNotificationConfigDetails.getSubscribeType() ) )
                .thenReturn( mappedSubscriptionType );
        UserNotificationConfigResponse response = new UserNotificationConfigResponse();
        UserNotificationConfigResult configResult = new UserNotificationConfigResult();
        configResult.setStatus( Status.SUCCESS.name() );
        response.setResult( configResult ); 
        when( buyerService.updateNotificationConfig( userNotificationConfigDetails ) ).thenReturn( response );
        BaseResponse baseResponse = userBusinessServiceImpl.updateNotificationConfig( userNotificationConfigDetails );
        assertNotNull( baseResponse );
        assertEquals( Status.SUCCESS, baseResponse.getStatus() );
    }
    
    @Test
    public void testUpdateNotificationConfigForFailure() {
        UserNotificationConfigDetails userNotificationConfigDetails = new UserNotificationConfigDetails();
        Contact contact = new Contact();
        when( contactServiceV1.getContactByOwnersComId( userNotificationConfigDetails.getUserId() ) )
                .thenReturn( contact );
        String mappedSubscriptionType = "";
        when( userService.getSubscriptionType( userNotificationConfigDetails.getSubscribeType() ) )
                .thenReturn( mappedSubscriptionType );
        UserNotificationConfigResponse response = new UserNotificationConfigResponse();
        when( buyerService.updateNotificationConfig( userNotificationConfigDetails ) ).thenReturn( response );
        BaseResponse baseResponse = userBusinessServiceImpl.updateNotificationConfig( userNotificationConfigDetails );
        assertNotNull( baseResponse );
        assertEquals( Status.FAILURE, baseResponse.getStatus() );
    }
}
