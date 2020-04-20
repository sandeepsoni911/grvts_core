package com.owners.gravitas.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.owners.gravitas.business.GroupManagementBusinessService;
import com.owners.gravitas.config.BaseControllerTest;
import com.owners.gravitas.dto.request.UserGroupRequest;
import com.owners.gravitas.dto.response.GroupResponse;
import com.owners.gravitas.dto.response.UserGroupResponse;

/**
 * The Class GroupManagementControllerTest.
 *
 * @author raviz
 */
public class GroupManagementControllerTest extends BaseControllerTest {

    /** The controller. */
    @InjectMocks
    private GroupManagementController controller;

    /** The group management business service. */
    @Mock
    private GroupManagementBusinessService groupManagementBusinessService;

    /**
     * Sets the up base.
     *
     * @throws Exception
     *             the exception
     */
    @BeforeClass
    public void setUpBase() throws Exception {
        super.setUpMockMVC( controller );
    }

    /**
     * Test create user group.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUserGroup() throws Exception {
        final GroupResponse groupResponse = new GroupResponse( "id" );
        when( groupManagementBusinessService.createUserGroup( any( UserGroupRequest.class ) ) )
                .thenReturn( groupResponse );
        this.mockMvc.perform( post( "/webapi/groups" ).content(
                "{\"groupName\":\"groupName1\", \"testStartDate\":\"2001-01-01\",  \"testEndDate\":\"2001-01-10\",  \"relatedOkr\":\"relatedOkr1\",  \"userEmails\":[\"michael.dpenha@ownerstest.com\"]}" )
                .contentType( MediaType.APPLICATION_JSON_UTF8 ) ).andExpect( status().isOk() );
        verify( groupManagementBusinessService ).createUserGroup( any( UserGroupRequest.class ) );
    }

    /**
     * Test get groups.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetGroups() throws Exception {
        final String name = "groupName";
        final String deleted = "false";
        final String url = "/webapi/groups";
        when( groupManagementBusinessService.getGroups( name, deleted ) ).thenReturn( new UserGroupResponse() );
        this.mockMvc
                .perform( get( url ).param( "name", name ).param( "deleted", deleted )
                        .contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
        verify( groupManagementBusinessService ).getGroups( name, deleted );
    }

    /**
     * Test get group by id.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetGroupById() throws Exception {
        final String groupId = "groupId";
        final String url = "/webapi/groups/" + groupId;
        when( groupManagementBusinessService.getGroupById( groupId ) ).thenReturn( new UserGroupResponse() );
        this.mockMvc.perform(
                get( url ).contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
        verify( groupManagementBusinessService ).getGroupById( groupId );
    }

    /**
     * Test update group.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testUpdateGroup() throws Exception {
        final String groupId = "groupId";
        final String url = "/webapi/groups/" + groupId;
        Mockito.doNothing().when( groupManagementBusinessService ).updateGroup( Mockito.anyString(),
                any( UserGroupRequest.class ) );
        this.mockMvc.perform( put( url ).content(
                "{\"groupName\":\"groupName1\", \"testStartDate\":\"2001-01-01\",  \"testEndDate\":\"2001-01-10\",  \"relatedOkr\":\"relatedOkr1\",  \"userEmails\":[\"michael.dpenha@ownerstest.com\"]}" )
                .contentType( MediaType.APPLICATION_JSON_UTF8 ) ).andExpect( status().isOk() );
        verify( groupManagementBusinessService ).updateGroup( Mockito.anyString(), any( UserGroupRequest.class ) );
        verify( groupManagementBusinessService ).getGroupById( groupId );
    }

    /**
     * Test archive group.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testArchiveGroup() throws Exception {
        final String groupId = "groupId";
        final String url = "/webapi/groups/" + groupId;
        when( groupManagementBusinessService.archiveGroup( groupId ) ).thenReturn( new GroupResponse( groupId ) );
        this.mockMvc.perform(
                delete( url ).contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
        verify( groupManagementBusinessService ).archiveGroup( groupId );
    }

    /**
     * Test get user groups by group id and email id.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testGetUserGroupsByGroupIdAndEmailId() throws Exception {
        final String groupId = "groupId";
        final String emailId = "someone@owners";
        final String url = "/webapi/part-of/group/" + groupId + "/email/" + emailId;
        when( groupManagementBusinessService.getUserGroupsByGroupIdAndEmailId( groupId, emailId ) )
                .thenReturn( new GroupResponse( groupId ) );
        this.mockMvc.perform(
                get( url ).contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
        verify( groupManagementBusinessService ).getUserGroupsByGroupIdAndEmailId( groupId, emailId );
    }

    @Test
    public void testGetUserGroupsByGroupNameAndEmailId() throws Exception {
        final String groupName = "groupName";
        final String emailId = "someone@owners";
        final String url = "/webapi/part-of/group-name/" + groupName + "/email/" + emailId;
        when( groupManagementBusinessService.getUserGroupsByGroupNameAndEmailId( groupName, emailId ) )
                .thenReturn( new GroupResponse( groupName ) );
        this.mockMvc.perform(
                get( url ).contentType( MediaType.APPLICATION_JSON ).header( "principal", "uid:test@test.com" ) )
                .andExpect( status().isOk() );
        verify( groupManagementBusinessService ).getUserGroupsByGroupNameAndEmailId( groupName, emailId );
    }

}
