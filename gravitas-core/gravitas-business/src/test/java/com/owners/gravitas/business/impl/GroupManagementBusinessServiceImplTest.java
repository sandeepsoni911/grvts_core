package com.owners.gravitas.business.impl;

import static com.owners.gravitas.dto.response.BaseResponse.Status.SUCCESS;
import static com.owners.gravitas.enums.RefCode.AGENT_GROUP;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.GroupBuilder;
import com.owners.gravitas.business.builder.UserGroupResponseBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.Group;
import com.owners.gravitas.domain.entity.GroupOkr;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.domain.entity.UserGroup;
import com.owners.gravitas.dto.ApiUser;
import com.owners.gravitas.dto.request.UserGroupRequest;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.GroupDetailResponse;
import com.owners.gravitas.dto.response.GroupResponse;
import com.owners.gravitas.dto.response.UserGroupDetail;
import com.owners.gravitas.dto.response.UserGroupResponse;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.GroupService;
import com.owners.gravitas.service.RefCodeService;
import com.owners.gravitas.service.UserGroupService;
import com.owners.gravitas.service.UserService;
import com.owners.gravitas.util.GravitasWebUtil;

/**
 * The Class GroupManagementBusinessServiceImplTest.
 *
 * @author raviz
 */
public class GroupManagementBusinessServiceImplTest extends AbstractBaseMockitoTest {

    /** The business service impl. */
    @InjectMocks
    private GroupManagementBusinessServiceImpl businessServiceImpl;

    /** The user service. */
    @Mock
    private UserService userService;

    /** The ref type code service. */
    @Mock
    private RefCodeService refTypeCodeService;

    /** The group service. */
    @Mock
    private GroupService groupService;

    /** The user group service. */
    @Mock
    private UserGroupService userGroupService;

    /** The user group response builder. */
    @Mock
    private UserGroupResponseBuilder userGroupResponseBuilder;

    /** The gravitas web util. */
    @Mock
    private GravitasWebUtil gravitasWebUtil;

    /** The group builder. */
    @Mock
    private GroupBuilder groupBuilder;

    /**
     * Test create user group should create group.
     */
    @Test( dataProvider = "getUserGroupRequest" )
    public void testCreateUserGroupShouldCreateGroup( final UserGroupRequest request ) {
        final com.owners.gravitas.domain.entity.RefCode refCode = new com.owners.gravitas.domain.entity.RefCode();
        final Group group = new Group();
        final Group savedGroup = new Group();
        savedGroup.setId( "groupId" );
        final List< User > users = new ArrayList<>();
        users.add( new User() );
        final List< UserGroup > userGroups = new ArrayList<>();

        when( groupService.findByName( request.getGroupName() ) ).thenReturn( null );
        when( refTypeCodeService.findByCode( AGENT_GROUP.getCode() ) ).thenReturn( refCode );
        when( groupBuilder.convertTo( request ) ).thenReturn( group );
        when( groupService.save( group ) ).thenReturn( savedGroup );
        when( userService.getUsersByEmail( request.getUserEmails() ) ).thenReturn( users );
        when( userGroupService.save( Mockito.anyListOf( UserGroup.class ) ) ).thenReturn( userGroups );

        final GroupResponse response = businessServiceImpl.createUserGroup( request );

        assertEquals( response.getId(), savedGroup.getId() );
        assertEquals( response.getStatus(), SUCCESS );
        verify( groupService ).findByName( request.getGroupName() );
        verify( refTypeCodeService ).findByCode( AGENT_GROUP.getCode() );
        verify( groupBuilder ).convertTo( request );
        verify( groupService ).save( group );
        verify( userService ).getUsersByEmail( request.getUserEmails() );
        verify( userGroupService ).save( Mockito.anyListOf( UserGroup.class ) );
    }

    /**
     * Test create user group should throw exception when group already exist.
     *
     * @param request
     *            the request
     */
    @Test( dataProvider = "getUserGroupRequest", expectedExceptions = ApplicationException.class )
    public void testCreateUserGroupShouldThrowExceptionWhenGroupAlreadyExist( final UserGroupRequest request ) {
        when( groupService.findByName( request.getGroupName() ) ).thenReturn( new Group() );
        businessServiceImpl.createUserGroup( request );
    }

    /**
     * Test get groups should return response when group name and deleted
     * provided.
     */
    @Test
    public void testGetGroupsShouldReturnResponseWhenGroupNameAndDeletedProvided() {
        final String name = "groupName";
        final String isDeleted = "false";
        final Group group = new Group();
        final UserGroupResponse expectedResponse = new UserGroupResponse();

        when( groupService.findGroupByNameAndDeleted( name, false ) ).thenReturn( group );
        when( userGroupResponseBuilder.convertTo( Mockito.anyListOf( Group.class ) ) ).thenReturn( expectedResponse );
        final UserGroupResponse actualResponse = businessServiceImpl.getGroups( name, isDeleted );

        assertEquals( actualResponse, expectedResponse );
        verify( groupService ).findGroupByNameAndDeleted( name, false );
        verify( groupService, times( 0 ) ).findByName( name );
        verify( groupService, times( 0 ) ).findByDeleted( false );
        verify( groupService, times( 0 ) ).findAll();
    }

    /**
     * Test get groups should return empty response when group name and deleted
     * provided.
     */
    @Test
    public void testGetGroupsShouldReturnEmptyResponseWhenGroupNameAndDeletedProvided() {
        final String name = "groupName";
        final String isDeleted = "false";

        when( groupService.findGroupByNameAndDeleted( name, false ) ).thenReturn( null );
        final UserGroupResponse actualResponse = businessServiceImpl.getGroups( name, isDeleted );

        assertNotNull( actualResponse );
        assertEquals( actualResponse.getStatus(), SUCCESS );
        assertEquals( actualResponse.getUserGroupDetails().size(), 0 );
        verify( groupService ).findGroupByNameAndDeleted( name, false );
        verify( groupService, times( 0 ) ).findByName( name );
        verify( groupService, times( 0 ) ).findByDeleted( false );
        verify( groupService, times( 0 ) ).findAll();
        verifyZeroInteractions( userGroupResponseBuilder );
    }

    /**
     * Test get groups should return response when group name provided and
     * deleted is empty.
     */
    @Test
    public void testGetGroupsShouldReturnResponseWhenGroupNameProvidedAndDeletedIsEmpty() {
        final String name = "groupName";
        final String isDeleted = "";
        final Group group = new Group();
        final UserGroupResponse expectedResponse = new UserGroupResponse();

        when( groupService.findByName( name ) ).thenReturn( group );
        when( userGroupResponseBuilder.convertTo( Mockito.anyListOf( Group.class ) ) ).thenReturn( expectedResponse );
        final UserGroupResponse actualResponse = businessServiceImpl.getGroups( name, isDeleted );

        assertEquals( actualResponse, expectedResponse );
        verify( groupService ).findByName( name );
        verify( groupService, times( 0 ) ).findGroupByNameAndDeleted( anyString(), anyBoolean() );
        verify( groupService, times( 0 ) ).findByDeleted( false );
        verify( groupService, times( 0 ) ).findAll();
    }

    /**
     * Test get groups should return response when group name is empty and
     * deleted provided.
     */
    @Test
    public void testGetGroupsShouldReturnResponseWhenGroupNameIsEmptyAndDeletedProvided() {
        final String name = "";
        final String isDeleted = "false";
        final List< Group > groups = new ArrayList<>();
        groups.add( new Group() );
        final UserGroupResponse expectedResponse = new UserGroupResponse();

        when( groupService.findByDeleted( false ) ).thenReturn( groups );
        when( userGroupResponseBuilder.convertTo( Mockito.anyListOf( Group.class ) ) ).thenReturn( expectedResponse );
        final UserGroupResponse actualResponse = businessServiceImpl.getGroups( name, isDeleted );

        assertEquals( actualResponse, expectedResponse );
        verify( groupService ).findByDeleted( false );
        verify( groupService, times( 0 ) ).findGroupByNameAndDeleted( anyString(), anyBoolean() );
        verify( groupService, times( 0 ) ).findByName( name );
        verify( groupService, times( 0 ) ).findAll();
    }

    /**
     * Test get groups should return response when group name and deleted are
     * empty.
     */
    @Test
    public void testGetGroupsShouldReturnResponseWhenGroupNameAndDeletedAreEmpty() {
        final String name = "";
        final String isDeleted = "";
        final List< Group > groups = new ArrayList<>();
        groups.add( new Group() );
        final UserGroupResponse expectedResponse = new UserGroupResponse();

        when( groupService.findAll() ).thenReturn( groups );
        when( userGroupResponseBuilder.convertTo( Mockito.anyListOf( Group.class ) ) ).thenReturn( expectedResponse );
        final UserGroupResponse actualResponse = businessServiceImpl.getGroups( name, isDeleted );

        assertEquals( actualResponse, expectedResponse );
        verify( groupService ).findAll();
        verify( groupService, times( 0 ) ).findByDeleted( false );
        verify( groupService, times( 0 ) ).findGroupByNameAndDeleted( anyString(), anyBoolean() );
        verify( groupService, times( 0 ) ).findByName( name );

    }

    /**
     * Test get group by id shoudl return response.
     */
    @Test
    public void testGetGroupByIdShoudlReturnResponse() {
        final String id = "id";
        final Group group = new Group();
        final UserGroupResponse expectedResponse = new UserGroupResponse();
        when( groupService.findGroupById( id ) ).thenReturn( group );
        when( userGroupResponseBuilder.convertTo( Mockito.anyListOf( Group.class ) ) ).thenReturn( expectedResponse );
        final UserGroupResponse actualResponse = businessServiceImpl.getGroupById( id );
        assertEquals( actualResponse, expectedResponse );
        verify( groupService ).findGroupById( id );
        verify( userGroupResponseBuilder ).convertTo( Mockito.anyListOf( Group.class ) );
    }

    /**
     * Test get group by id should throw exception when invalid id is provided.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testGetGroupByIdShouldThrowExceptionWhenInvalidIdIsProvided() {
        final String id = "id";
        when( groupService.findGroupById( id ) ).thenReturn( null );
        businessServiceImpl.getGroupById( id );
    }

    /**
     * Test get group by id should throw exception when group is deleted.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testGetGroupByIdShouldThrowExceptionWhenGroupIsDeleted() {
        final String id = "id";
        final Group group = new Group();
        group.setDeleted( true );
        when( groupService.findGroupById( id ) ).thenReturn( group );
        businessServiceImpl.getGroupById( id );
    }

    /**
     * Test update group should return response.
     *
     * @param request
     *            the request
     */
    @Test( dataProvider = "getUserGroupRequest" )
    public void testUpdateGroupShouldReturnResponse( final UserGroupRequest request ) {
        final String groupId = "id";
        final Group group = new Group();
        final GroupOkr grpOkr = new GroupOkr();
        group.setGroupOkr( grpOkr );
        group.setName( "existingGroupname" );
        group.setCreatedBy( "test@test.com" );
        final ApiUser appUser = new ApiUser();
        appUser.setEmail( "test@test.com" );
        final List< User > users = new ArrayList<>();
        users.add( new User() );

        when( groupService.findGroupById( groupId ) ).thenReturn( group );
        when( gravitasWebUtil.getAppUser() ).thenReturn( appUser );
        when( groupService.findByName( request.getGroupName() ) ).thenReturn( null );
        when( groupBuilder.convertTo( request, group ) ).thenReturn( group );
        when( groupService.save( group ) ).thenReturn( group );
        doNothing().when( userGroupService ).deleteByGroupAndFlush( group );
        when( userService.getUsersByEmail( request.getUserEmails() ) ).thenReturn( users );
        when( userGroupService.save( Mockito.anySetOf( UserGroup.class ) ) ).thenReturn( new ArrayList<>() );

        businessServiceImpl.updateGroup( groupId, request );

        verify( groupService ).findGroupById( groupId );
        verify( gravitasWebUtil ).getAppUser();
        verify( groupService ).findByName( request.getGroupName() );
        verify( groupBuilder ).convertTo( request, group );
        verify( groupService ).save( group );
        verify( userGroupService ).deleteByGroupAndFlush( group );
        verify( userService ).getUsersByEmail( request.getUserEmails() );
        verify( userGroupService ).save( Mockito.anySetOf( UserGroup.class ) );
    }

    /**
     * Test update group should return response when group name is same.
     *
     * @param request
     *            the request
     */
    @Test( dataProvider = "getUserGroupRequest" )
    public void testUpdateGroupShouldReturnResponseWhenGroupNameIsSame( final UserGroupRequest request ) {
        final String groupId = "id";
        final Group group = new Group();
        final GroupOkr grpOkr = new GroupOkr();
        group.setGroupOkr( grpOkr );
        group.setName( "groupName" );
        group.setCreatedBy( "test@test.com" );
        final ApiUser appUser = new ApiUser();
        appUser.setEmail( "test@test.com" );
        final List< User > users = new ArrayList<>();
        users.add( new User() );

        when( groupService.findGroupById( groupId ) ).thenReturn( group );
        when( gravitasWebUtil.getAppUser() ).thenReturn( appUser );
        when( groupService.findByName( request.getGroupName() ) ).thenReturn( null );
        when( groupBuilder.convertTo( request, group ) ).thenReturn( group );
        when( groupService.save( group ) ).thenReturn( group );
        doNothing().when( userGroupService ).deleteByGroupAndFlush( group );
        when( userService.getUsersByEmail( request.getUserEmails() ) ).thenReturn( users );
        when( userGroupService.save( Mockito.anySetOf( UserGroup.class ) ) ).thenReturn( new ArrayList<>() );

        businessServiceImpl.updateGroup( groupId, request );

        verify( groupService ).findGroupById( groupId );
        verify( gravitasWebUtil ).getAppUser();
        verify( groupService, times( 0 ) ).findByName( anyString() );
        verify( groupBuilder ).convertTo( request, group );
        verify( groupService ).save( group );
        verify( userGroupService ).deleteByGroupAndFlush( group );
        verify( userService ).getUsersByEmail( request.getUserEmails() );
        verify( userGroupService ).save( Mockito.anySetOf( UserGroup.class ) );
    }

    /**
     * Test update group should throw exception when invalid user update group.
     *
     * @param request
     *            the request
     */
    @Test( dataProvider = "getUserGroupRequest", expectedExceptions = ApplicationException.class )
    public void testUpdateGroupShouldThrowExceptionWhenInvalidUserUpdateGroup( final UserGroupRequest request ) {
        final String groupId = "id";
        final Group group = new Group();
        group.setName( "groupName" );
        group.setCreatedBy( "test@test.com" );
        final ApiUser appUser = new ApiUser();
        appUser.setEmail( "invalid@test.com" );

        when( groupService.findGroupById( groupId ) ).thenReturn( group );
        when( gravitasWebUtil.getAppUser() ).thenReturn( appUser );
        businessServiceImpl.updateGroup( groupId, request );
    }

    /**
     * Test update group should throw exception when invalid id is provided.
     *
     * @param request
     *            the request
     */
    @Test( dataProvider = "getUserGroupRequest", expectedExceptions = ApplicationException.class )
    public void testUpdateGroupShouldThrowExceptionWhenInvalidIdIsProvided( final UserGroupRequest request ) {
        final String id = "id";
        when( groupService.findGroupById( id ) ).thenReturn( null );
        businessServiceImpl.updateGroup( id, request );
    }

    /**
     * Test update group should throw exception when group is deleted.
     *
     * @param request
     *            the request
     */
    @Test( dataProvider = "getUserGroupRequest", expectedExceptions = ApplicationException.class )
    public void testUpdateGroupShouldThrowExceptionWhenGroupIsDeleted( final UserGroupRequest request ) {
        final String id = "id";
        final Group group = new Group();
        group.setDeleted( true );
        when( groupService.findGroupById( id ) ).thenReturn( group );
        businessServiceImpl.updateGroup( id, request );
    }

    /**
     * Test archive group should return response.
     */
    @Test
    public void testArchiveGroupShouldReturnResponse() {
        final String groupId = "id";
        final Group group = new Group();
        group.setCreatedBy( "test@test.com" );
        final GroupOkr grpOkr = new GroupOkr();
        group.setGroupOkr( grpOkr );
        final ApiUser appUser = new ApiUser();
        appUser.setEmail( "test@test.com" );

        when( groupService.findGroupById( groupId ) ).thenReturn( group );
        when( gravitasWebUtil.getAppUser() ).thenReturn( appUser );
        when( groupService.save( group ) ).thenReturn( group );

        final GroupResponse response = businessServiceImpl.archiveGroup( groupId );

        assertEquals( response.getStatus(), SUCCESS );
        assertEquals( response.getMessage(), "Bucket archived successfully" );
        verify( groupService ).findGroupById( groupId );
        verify( gravitasWebUtil ).getAppUser();
        verify( groupService ).save( group );
    }

    /**
     * Test archive group should throw exception when group not exist with given
     * id.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testArchiveGroupShouldThrowExceptionWhenGroupNotExistWithGivenId() {
        final String groupId = "id";
        final Group group = new Group();
        group.setCreatedBy( "test@test.com" );
        final ApiUser appUser = new ApiUser();
        appUser.setEmail( "invalid@test.com" );

        when( groupService.findGroupById( groupId ) ).thenReturn( group );
        when( gravitasWebUtil.getAppUser() ).thenReturn( appUser );
        businessServiceImpl.archiveGroup( groupId );
    }

    /**
     * Test archive group should throw exception when group is already deleted.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testArchiveGroupShouldThrowExceptionWhenGroupIsAlreadyDeleted() {
        final String groupId = "id";
        final Group group = new Group();
        group.setDeleted( true );
        when( groupService.findGroupById( groupId ) ).thenReturn( group );
        businessServiceImpl.archiveGroup( groupId );
    }

    /**
     * Test archive group should throw exception when invalid user delete group.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testArchiveGroupShouldThrowExceptionWhenInvalidUserDeleteGroup() {
        final String groupId = "id";
        final Group group = new Group();
        group.setDeleted( true );
        when( groupService.findGroupById( groupId ) ).thenReturn( group );
        businessServiceImpl.archiveGroup( groupId );
    }

    /**
     * Test get groups by agent should return response.
     */
    @Test
    public void testGetGroupsByAgentShouldReturnResponse() {
        final String email = "test@test.com";
        final User user = new User();
        final Set< UserGroup > userGroups = new HashSet<>();
        final UserGroup userGroup = new UserGroup();
        userGroup.setGroup( new Group() );
        userGroups.add( userGroup );
        final UserGroupResponse userGroupResponse = new UserGroupResponse();
        userGroupResponse.getUserGroupDetails().add( new UserGroupDetail() );

        when( userService.findByEmail( email ) ).thenReturn( user );
        when( userGroupService.findByUser( user ) ).thenReturn( userGroups );
        when( userGroupResponseBuilder.convertTo( Mockito.anyListOf( Group.class ) ) ).thenReturn( userGroupResponse );
        final GroupDetailResponse response = businessServiceImpl.getGroupsByAgent( email );

        assertEquals( response.getStatus(), SUCCESS );
        assertEquals( response.getGroups().size(), 1 );
        verify( userService ).findByEmail( email );
        verify( userGroupService ).findByUser( user );
        verify( userGroupResponseBuilder ).convertTo( Mockito.anyListOf( Group.class ) );
    }

    /**
     * Test get groups by agent should return response when group is deleted.
     */
    @Test
    public void testGetGroupsByAgentShouldReturnResponseWhenGroupIsDeleted() {
        final String email = "test@test.com";
        final User user = new User();
        final Set< UserGroup > userGroups = new HashSet<>();
        final UserGroup userGroup = new UserGroup();
        final Group group = new Group();
        group.setDeleted( true );
        userGroup.setGroup( group );
        userGroups.add( userGroup );
        final UserGroupResponse userGroupResponse = new UserGroupResponse();
        userGroupResponse.getUserGroupDetails().add( new UserGroupDetail() );

        when( userService.findByEmail( email ) ).thenReturn( user );
        when( userGroupService.findByUser( user ) ).thenReturn( userGroups );
        final GroupDetailResponse response = businessServiceImpl.getGroupsByAgent( email );

        assertEquals( response.getStatus(), SUCCESS );
        assertEquals( response.getGroups().size(), 0 );
        verify( userService ).findByEmail( email );
        verify( userGroupService ).findByUser( user );
        verify( userGroupResponseBuilder, times( 0 ) ).convertTo( Mockito.anyListOf( Group.class ) );
    }

    /**
     * Test update agents groups should return response.
     */
    @Test
    public void testUpdateAgentsGroupsShouldReturnResponse() {
        final String email = "test@test.com";
        final List< String > groupNames = new ArrayList<>();
        groupNames.add( "testGroup" );
        final ApiUser appUser = new ApiUser();
        appUser.setEmail( email );
        final User user = new User();
        final Group group = new Group();
        group.setCreatedBy( email );
        final GroupOkr grpOkr = new GroupOkr();
        group.setGroupOkr( grpOkr );

        when( groupService.findGroupByNameAndDeleted( anyString(), anyBoolean() ) ).thenReturn( group );
        when( gravitasWebUtil.getAppUser() ).thenReturn( appUser );
        when( userService.getUserByEmail( email ) ).thenReturn( user );
        doNothing().when( userGroupService ).deleteByUserAndFlush( user );
        when( userGroupService.save( Mockito.anySetOf( UserGroup.class ) ) ).thenReturn( new ArrayList<>() );
        final BaseResponse response = businessServiceImpl.updateAgentsGroups( email, groupNames );

        assertNotNull( response );
        assertEquals( response.getStatus(), SUCCESS );
        verify( groupService ).findGroupByNameAndDeleted( anyString(), anyBoolean() );
        verify( gravitasWebUtil ).getAppUser();
        verify( userService ).getUserByEmail( email );
    }

    /**
     * Test update agents groups should return response if group names are not
     * passed.
     */
    @Test
    public void testUpdateAgentsGroupsShouldReturnResponseIfGroupNamesAreNotPassed() {
        final String email = "test@test.com";
        final List< String > groupNames = null;
        final ApiUser apiUser = new ApiUser();
        apiUser.setEmail( email );
        final User user = new User();
        user.setEmail( email );
        when( gravitasWebUtil.getAppUser() ).thenReturn( apiUser );
        when( userService.getUserByEmail( email ) ).thenReturn( user );
        final BaseResponse response = businessServiceImpl.updateAgentsGroups( email, groupNames );
        assertNotNull( response );
        assertEquals( response.getStatus(), SUCCESS );

        verifyZeroInteractions( groupService );
    }

    /**
     * Test update agents groups should return response when group is null.
     */
    @Test
    public void testUpdateAgentsGroupsShouldReturnResponseWhenGroupIsNull() {
        final String email = "test@test.com";
        final List< String > groupNames = new ArrayList<>();
        groupNames.add( "testGroup" );
        final ApiUser appUser = new ApiUser();
        appUser.setEmail( email );
        final User user = new User();
        final Group group = new Group();
        group.setCreatedBy( email );

        when( groupService.findGroupByNameAndDeleted( anyString(), anyBoolean() ) ).thenReturn( null );
        when( gravitasWebUtil.getAppUser() ).thenReturn( appUser );
        when( userService.getUserByEmail( email ) ).thenReturn( user );
        doNothing().when( userGroupService ).deleteByUserAndFlush( user );
        when( userGroupService.save( Mockito.anySetOf( UserGroup.class ) ) ).thenReturn( new ArrayList<>() );
        final BaseResponse response = businessServiceImpl.updateAgentsGroups( email, groupNames );

        assertNotNull( response );
        assertEquals( response.getStatus(), SUCCESS );
        verify( groupService, times( 1 ) ).findGroupByNameAndDeleted( anyString(), anyBoolean() );
        verify( userService ).getUserByEmail( email );
    }

    /**
     * Test update agents groups should throw exception when user not found.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testUpdateAgentsGroupsShouldThrowExceptionWhenUserNotFound() {
        final String email = "test@test.com";
        final List< String > groupNames = new ArrayList<>();
        groupNames.add( "testGroup" );
        final ApiUser appUser = new ApiUser();
        appUser.setEmail( email );
        final Group group = new Group();
        group.setCreatedBy( email );
        when( groupService.findGroupByNameAndDeleted( anyString(), anyBoolean() ) ).thenReturn( null );
        when( gravitasWebUtil.getAppUser() ).thenReturn( appUser );
        when( userService.getUserByEmail( email ) ).thenReturn( null );
        businessServiceImpl.updateAgentsGroups( email, groupNames );
    }

    /**
     * Test update agents groups should return response1.
     */
    @Test
    public void testUpdateAgentsGroupsShouldReturnResponse1() {
        final String email = "test@test.com";
        final List< String > groupNames = new ArrayList<>();
        groupNames.add( "testGroup" );
        final ApiUser appUser = new ApiUser();
        appUser.setEmail( email );
        final User user = new User();
        final Group group = new Group();
        group.setCreatedBy( email );
        final GroupOkr grpOkr = new GroupOkr();
        group.setGroupOkr( grpOkr );
        final Set< UserGroup > userGroups = new HashSet<>();
        final UserGroup userGroup = new UserGroup();
        userGroup.setUser( user );
        userGroup.setGroup( group );
        userGroups.add( userGroup );

        when( groupService.findGroupByNameAndDeleted( anyString(), anyBoolean() ) ).thenReturn( group );
        when( gravitasWebUtil.getAppUser() ).thenReturn( appUser );
        when( userService.getUserByEmail( email ) ).thenReturn( user );
        doNothing().when( userGroupService ).deleteByUserAndFlush( user );
        when( userGroupService.save( Mockito.anySetOf( UserGroup.class ) ) ).thenReturn( new ArrayList<>() );
        when( userGroupService.findByUser( user ) ).thenReturn( userGroups );
        final BaseResponse response = businessServiceImpl.updateAgentsGroups( email, groupNames );

        assertNotNull( response );
        assertEquals( response.getStatus(), SUCCESS );
        verify( groupService ).findGroupByNameAndDeleted( anyString(), anyBoolean() );
        verify( gravitasWebUtil ).getAppUser();
        verify( userService ).getUserByEmail( email );
    }

    /**
     * Test get user groups by group id and email id should return response.
     */
    @Test
    public void testGetUserGroupsByGroupIdAndEmailId_ShouldReturnResponseWithTrue() {
        final String groupId = "groupId123";
        final String email = "test@test.com";
        final User user = new User();
        final Group group = new Group();
        final UserGroup userGroup = new UserGroup();

        final GroupResponse groupResponse = new GroupResponse( groupId );
        groupResponse.setIsAgentPartOfGroup( true );

        when( userService.getUserByEmail( email ) ).thenReturn( user );
        when( groupService.findGroupById( groupId ) ).thenReturn( group );
        when( userGroupService.findByUserAndGroup( user, group ) ).thenReturn( userGroup );
        final GroupResponse response = businessServiceImpl.getUserGroupsByGroupIdAndEmailId( groupId, email );

        assertEquals( response.getStatus(), SUCCESS );
        assertTrue( response.getIsAgentPartOfGroup() );
        verify( userService ).getUserByEmail( email );
        verify( groupService ).findGroupById( groupId );
    }

    @Test
    public void testGetUserGroupsByGroupIdAndEmailId_ShouldReturnResponseWithFalse() {
        final String groupId = "groupId123";
        final String email = "test@test.com";
        final User user = new User();
        final Group group = new Group();
        final UserGroup userGroup = null;

        final GroupResponse groupResponse = new GroupResponse( groupId );
        groupResponse.setIsAgentPartOfGroup( false );

        when( userService.getUserByEmail( email ) ).thenReturn( user );
        when( groupService.findGroupById( groupId ) ).thenReturn( group );
        when( userGroupService.findByUserAndGroup( user, group ) ).thenReturn( userGroup );
        final GroupResponse response = businessServiceImpl.getUserGroupsByGroupIdAndEmailId( groupId, email );

        assertEquals( response.getStatus(), SUCCESS );
        assertFalse( response.getIsAgentPartOfGroup() );
        verify( userService ).getUserByEmail( email );
        verify( groupService ).findGroupById( groupId );
    }

    /**
     * Test get user groups by group id and email id with invalid email id.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testGetUserGroupsByGroupIdAndEmailId_WithInvalidEmailId() {
        final String groupId = "groupId123";
        final String email = "invalid_user@test.com";
        final User user = null;
        final Group group = new Group();
        final UserGroup userGroup = new UserGroup();

        userGroup.setGroup( new Group() );
        userGroup.setUser( user );
        final GroupResponse groupResponse = new GroupResponse( groupId );
        groupResponse.setIsAgentPartOfGroup( false );

        when( userService.getUserByEmail( email ) ).thenReturn( user );
        when( groupService.findGroupById( groupId ) ).thenReturn( group );
        when( userGroupService.findByUserAndGroup( user, group ) ).thenReturn( userGroup );
        final GroupResponse response = businessServiceImpl.getUserGroupsByGroupIdAndEmailId( groupId, email );
    }

    /**
     * Test get user groups by group id and email id with invalid group id.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testGetUserGroupsByGroupIdAndEmailId_WithInvalidGroupId() {
        final String groupId = "invalid_groupId123";
        final String email = "test@test.com";
        final User user = new User();
        final Group group = null;
        final UserGroup userGroup = new UserGroup();

        userGroup.setGroup( new Group() );
        userGroup.setUser( user );
        final GroupResponse groupResponse = new GroupResponse( groupId );
        groupResponse.setIsAgentPartOfGroup( true );

        when( userService.getUserByEmail( email ) ).thenReturn( user );
        when( groupService.findGroupById( groupId ) ).thenReturn( group );
        when( userGroupService.findByUserAndGroup( user, group ) ).thenReturn( userGroup );
        final GroupResponse response = businessServiceImpl.getUserGroupsByGroupIdAndEmailId( groupId, email );
    }

    /**
     * Test get user groups by group name and email id should return response
     * with true.
     */
    @Test
    public void testGetUserGroupsByGroupNameAndEmailId_ShouldReturnResponseWithTrue() {
        final String groupName = "groupId123";
        final String email = "test@test.com";
        final User user = new User();
        final Group group = new Group();
        final UserGroup userGroup = new UserGroup();

        final GroupResponse groupResponse = new GroupResponse( groupName );
        groupResponse.setIsAgentPartOfGroup( true );

        when( userService.getUserByEmail( email ) ).thenReturn( user );
        when( groupService.findByName( groupName ) ).thenReturn( group );
        when( userGroupService.findByUserAndGroup( user, group ) ).thenReturn( userGroup );
        final GroupResponse response = businessServiceImpl.getUserGroupsByGroupNameAndEmailId( groupName, email );

        assertEquals( response.getStatus(), SUCCESS );
        assertTrue( response.getIsAgentPartOfGroup() );
        verify( userService ).getUserByEmail( email );
        verify( groupService ).findByName( groupName );
    }

    /**
     * Test get user groups by group name and email id should return response
     * with false.
     */
    @Test
    public void testGetUserGroupsByGroupNameAndEmailId_ShouldReturnResponseWithFalse() {
        final String groupName = "groupId123";
        final String email = "test@test.com";
        final User user = new User();
        final Group group = new Group();
        final UserGroup userGroup = null;

        final GroupResponse groupResponse = new GroupResponse( groupName );
        groupResponse.setIsAgentPartOfGroup( false );

        when( userService.getUserByEmail( email ) ).thenReturn( user );
        when( groupService.findByName( groupName ) ).thenReturn( group );
        when( userGroupService.findByUserAndGroup( user, group ) ).thenReturn( userGroup );
        final GroupResponse response = businessServiceImpl.getUserGroupsByGroupNameAndEmailId( groupName, email );

        assertEquals( response.getStatus(), SUCCESS );
        assertFalse( response.getIsAgentPartOfGroup() );
        verify( userService ).getUserByEmail( email );
        verify( groupService ).findByName( groupName );
    }

    /**
     * Test get user groups by group name and email id with invalid email id.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testGetUserGroupsByGroupNameAndEmailId_WithInvalidEmailId() {
        final String groupName = "groupId123";
        final String email = "invalid_user@test.com";
        final User user = null;
        final Group group = new Group();
        final UserGroup userGroup = new UserGroup();

        userGroup.setGroup( new Group() );
        userGroup.setUser( user );
        final GroupResponse groupResponse = new GroupResponse( groupName );
        groupResponse.setIsAgentPartOfGroup( false );

        when( userService.getUserByEmail( email ) ).thenReturn( user );
        when( groupService.findGroupById( groupName ) ).thenReturn( group );
        when( userGroupService.findByUserAndGroup( user, group ) ).thenReturn( userGroup );
        final GroupResponse response = businessServiceImpl.getUserGroupsByGroupNameAndEmailId( groupName, email );
    }

    /**
     * Test get user groups by group name and email id with invalid group id.
     */
    @Test( expectedExceptions = ApplicationException.class )
    public void testGetUserGroupsByGroupNameAndEmailId_WithInvalidGroupId() {
        final String groupName = "invalid_groupId123";
        final String email = "test@test.com";
        final User user = new User();
        final Group group = null;
        final UserGroup userGroup = new UserGroup();

        userGroup.setGroup( new Group() );
        userGroup.setUser( user );
        final GroupResponse groupResponse = new GroupResponse( groupName );
        groupResponse.setIsAgentPartOfGroup( true );

        when( userService.getUserByEmail( email ) ).thenReturn( user );
        when( groupService.findGroupById( groupName ) ).thenReturn( group );
        when( userGroupService.findByUserAndGroup( user, group ) ).thenReturn( userGroup );
        final GroupResponse response = businessServiceImpl.getUserGroupsByGroupNameAndEmailId( groupName, email );
    }

    /**
     * Gets the user group request.
     *
     * @return the user group request
     */
    @DataProvider( name = "getUserGroupRequest" )
    private Object[][] getUserGroupRequest() {
        final UserGroupRequest request = new UserGroupRequest();
        request.setGroupName( "groupName" );
        return new Object[][] { { request } };
    }
}
