package com.owners.gravitas.business;

import java.util.List;

import com.owners.gravitas.domain.entity.Group;
import com.owners.gravitas.dto.request.UserGroupRequest;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.GroupDetailResponse;
import com.owners.gravitas.dto.response.GroupResponse;
import com.owners.gravitas.dto.response.UserGroupResponse;

/**
 * The Interface GroupManagementBusinessServiceImpl.
 *
 * @author raviz
 */
public interface GroupManagementBusinessService {

    /**
     * Creates the user group.
     *
     * @param userGroupRequest
     *            the user group request
     * @return the group response
     */
    GroupResponse createUserGroup( UserGroupRequest userGroupRequest );

    /**
     * Gets the groups.
     *
     * @param name
     *            the name
     * @param isDeleted
     *            the is deleted
     * @return the groups
     */
    UserGroupResponse getGroups( String name, String isDeleted );

    /**
     * Gets the group by id.
     *
     * @param id
     *            the id
     * @return the group by id
     */
    UserGroupResponse getGroupById( String id );

    /**
     * Update group.
     *
     * @param groupId
     *            the group id
     * @param userGroupRequest
     *            the user group request
     */
    void updateGroup( String groupId, UserGroupRequest userGroupRequest );

    /**
     * Archive group.
     *
     * @param groupId
     *            the group id
     * @return the group response
     */
    GroupResponse archiveGroup( String groupId );

    /**
     * Gets the groups by agent.
     *
     * @param email
     *            the email
     * @return the groups by agent
     */
    GroupDetailResponse getGroupsByAgent( String email );

    /**
     * Update agents groups.
     *
     * @param email
     *            the email
     * @param groupNames
     *            the group names
     * @return the base response
     */
    BaseResponse updateAgentsGroups( String email, List< String > groupNames );

    /**
     * Gets the groups list.
     *
     * @param name
     *            the name
     * @param isDeleted
     *            the is deleted
     * @return the groups list
     */
    List< Group > getGroupsList( String name, String isDeleted );
    
    /**
     * Gets the user groups by group id and email id.
     *
     * @param groupId
     *            the group id
     * @param emailId
     *            the email id
     * @return the user groups by group id and email id
     */
    GroupResponse getUserGroupsByGroupIdAndEmailId( String groupId, String emailId );
    
    /**
     * Gets the user groups by group name and email id.
     *
     * @param groupName
     *            the group name
     * @param emailId
     *            the email id
     * @return the user groups by group name and email id
     */
    GroupResponse getUserGroupsByGroupNameAndEmailId( String groupName, String emailId );
    
}
