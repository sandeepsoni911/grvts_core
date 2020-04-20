package com.owners.gravitas.business.impl;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.business.GroupManagementBusinessService;
import com.owners.gravitas.business.builder.GroupBuilder;
import com.owners.gravitas.business.builder.UserGroupResponseBuilder;
import com.owners.gravitas.domain.entity.Group;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.domain.entity.UserGroup;
import com.owners.gravitas.dto.ApiUser;
import com.owners.gravitas.dto.request.UserGroupRequest;
import com.owners.gravitas.dto.response.BaseResponse;
import com.owners.gravitas.dto.response.BaseResponse.Status;
import com.owners.gravitas.dto.response.GroupDetailResponse;
import com.owners.gravitas.dto.response.GroupResponse;
import com.owners.gravitas.dto.response.UserGroupResponse;
import com.owners.gravitas.enums.ErrorCode;
import com.owners.gravitas.enums.RefCode;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.GroupService;
import com.owners.gravitas.service.RefCodeService;
import com.owners.gravitas.service.UserGroupService;
import com.owners.gravitas.service.UserService;
import com.owners.gravitas.util.GravitasWebUtil;

/**
 * The Class GroupManagementBusinessServiceImpl.
 *
 * @author raviz
 */
@Service( "groupManagementBusinessServiceImpl" )
public class GroupManagementBusinessServiceImpl implements GroupManagementBusinessService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( GroupManagementBusinessServiceImpl.class );

    /** The user service. */
    @Autowired
    private UserService userService;

    /** The ref type code service. */
    @Autowired
    private RefCodeService refTypeCodeService;

    /** The group service. */
    @Autowired
    private GroupService groupService;

    /** The user group service. */
    @Autowired
    private UserGroupService userGroupService;

    /** The user group response builder. */
    @Autowired
    private UserGroupResponseBuilder userGroupResponseBuilder;

    /** The gravitas web util. */
    @Autowired
    private GravitasWebUtil gravitasWebUtil;

    /** The group builder. */
    @Autowired
    private GroupBuilder groupBuilder;

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.GroupManagementBusinessService#
     * createUserGroup(com.owners.gravitas.dto.request.UserGroupRequest)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public GroupResponse createUserGroup( final UserGroupRequest userGroupRequest ) {
        checkGroupExists( userGroupRequest.getGroupName() );
        final com.owners.gravitas.domain.entity.RefCode refCode = refTypeCodeService
                .findByCode( RefCode.AGENT_GROUP.getCode() );
        Group group = groupBuilder.convertTo( userGroupRequest );
        group.setRefCode( refCode );
        group = groupService.save( group );
        userGroupService.save( buildUserGroups( userGroupRequest.getUserEmails(), group ) );
        return new GroupResponse( group.getId() );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.GroupManagementBusinessService#getGroups(
     * java.lang.String, java.lang.String)
     */
    @Override
    @Transactional( readOnly = true )
    public UserGroupResponse getGroups( final String name, final String isDeleted ) {
        UserGroupResponse response = null;
        final List< Group > groups = getGroupsList( name, isDeleted );
        if (CollectionUtils.isNotEmpty( groups )) {
            response = userGroupResponseBuilder.convertTo( groups );
        } else {
            response = new UserGroupResponse();
        }
        return response;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.GroupManagementBusinessService#getGroupById(
     * java.lang.String)
     */
    @Override
    @Transactional( readOnly = true )
    public UserGroupResponse getGroupById( final String id ) {
        UserGroupResponse response = null;
        final List< Group > groups = new ArrayList<>();
        groups.add( getGroup( id ) );
        response = userGroupResponseBuilder.convertTo( groups );
        return response;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.GroupManagementBusinessService#updateGroup(
     * java.lang.String, com.owners.gravitas.dto.request.UserGroupRequest)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public void updateGroup( final String groupId, final UserGroupRequest userGroupRequest ) {
        Group group = getGroup( groupId );
        validateUser( group.getCreatedBy() );
        if (!group.getName().equals( userGroupRequest.getGroupName() )) {
            checkGroupExists( userGroupRequest.getGroupName() );
        }
        group = groupBuilder.convertTo( userGroupRequest, group );
        updateLastModifiedDates( group );
        group = groupService.save( group );
        userGroupService.deleteByGroupAndFlush( group );
        final Set< UserGroup > userGroups = buildUserGroups( userGroupRequest.getUserEmails(), group );
        userGroupService.save( userGroups );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.GroupManagementBusinessService#archiveGroup(
     * java.lang.String)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public GroupResponse archiveGroup( final String groupId ) {
        final Group group = getGroup( groupId );
        validateUser( group.getCreatedBy() );
        group.setDeleted( true );
        updateLastModifiedDates( group );
        groupService.save( group );
        return new GroupResponse( groupId, Status.SUCCESS, "Bucket archived successfully" );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.GroupManagementBusinessService#
     * getGroupsByAgent(java.lang.String)
     */
    @Override
    @Transactional( readOnly = true )
    public GroupDetailResponse getGroupsByAgent( final String email ) {
        final List< com.owners.gravitas.dto.Group > groups = new ArrayList<>();
        final User user = userService.findByEmail( email );
        final Set< UserGroup > userGroups = userGroupService.findByUser( user );
        for ( final UserGroup userGroup : userGroups ) {
            final Group group = userGroup.getGroup();
            if (!group.isDeleted()) {
                final List< Group > groupList = new ArrayList<>();
                groupList.add( group );
                final UserGroupResponse userGroupDetail = userGroupResponseBuilder.convertTo( groupList );
                groups.add( userGroupDetail.getUserGroupDetails().get( 0 ) );
            }
        }
        return new GroupDetailResponse( groups );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.GroupManagementBusinessService#
     * updateAgentsGroups(java.lang.String, java.util.List)
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRES_NEW )
    public BaseResponse updateAgentsGroups( final String email, final List< String > groupNames ) {
        final String appUserEmail = gravitasWebUtil.getAppUser().getEmail();
        final User user = userService.getUserByEmail( email );
        if (user == null) {
            throw new ApplicationException( "Invalid user : " + email, ErrorCode.USER_NOT_FOUND );
        }
        final Set< UserGroup > userGroups = userGroupService.findByUser( user );
        final List< Group > groups = getAgentGroups( appUserEmail, groupNames );
        deleteByUserAndGroup( appUserEmail, user, userGroups );
        saveUserGroups( user, groups );
        return new BaseResponse();
    }

    /**
     * Update last modified dates.
     *
     * @param group
     *            the group
     */
    private void updateLastModifiedDates( final Group group ) {
        final DateTime lastModifiedDate = new DateTime();
        group.setLastModifiedDate( lastModifiedDate );
        group.getGroupOkr().setLastModifiedDate( lastModifiedDate );
    }

    /**
     * Gets the agent groups.
     *
     * @param appUserEmail
     *            the app user email
     * @param groupNames
     *            the group names
     * @return the agent groups
     */
    private List< Group > getAgentGroups( final String appUserEmail, final List< String > groupNames ) {
        final List< Group > groups = new ArrayList<>();
        if (isNotEmpty( groupNames )) {
            for ( final String groupName : groupNames ) {
                final Group group = groupService.findGroupByNameAndDeleted( groupName, false );
                if (group != null) {
                    if (appUserEmail.equalsIgnoreCase( group.getCreatedBy() )) {
                        groups.add( group );
                    }
                }
            }
        }
        return groups;
    }

    /**
     * Delete by user and group.
     *
     * @param appUserEmail
     *            the app user email
     * @param user
     *            the user
     * @param userGroups
     *            the user groups
     */
    private void deleteByUserAndGroup( final String appUserEmail, final User user, final Set< UserGroup > userGroups ) {
        for ( final UserGroup userGroup : userGroups ) {
            final Group group = userGroup.getGroup();
            if (appUserEmail.equalsIgnoreCase( group.getCreatedBy() )) {
                userGroupService.deleteByUserAndGroup( user, group );
            }
        }
    }

    /**
     * Save user groups.
     *
     * @param user
     *            the user
     * @param groups
     *            the groups
     */
    private void saveUserGroups( final User user, final List< Group > groups ) {
        for ( final Group group : groups ) {
            final UserGroup existingUserGroup = userGroupService.findByUserAndGroup( user, group );
            if (existingUserGroup == null) {
                final UserGroup userGroup = new UserGroup();
                userGroup.setGroup( group );
                userGroup.setUser( user );
                userGroupService.save( userGroup );
            }
            updateLastModifiedDates( group );
            groupService.save( group );
        }
    }

    /**
     * Validate user.
     *
     * @param email
     *            the email
     */
    private void validateUser( final String email ) {
        final ApiUser appUser = gravitasWebUtil.getAppUser();
        if (!email.equals( appUser.getEmail() )) {
            throw new ApplicationException( "Invalid user access to group", ErrorCode.ACCESS_DENIED_ERROR );
        }
    }

    /**
     * Check group exists.
     *
     * @param name
     *            the name
     */
    private void checkGroupExists( final String name ) {
        final Group group = groupService.findByName( name );
        if (group != null) {
            LOGGER.info( "Group already exists with name " + name );
            throw new ApplicationException( "Group already exists with name " + name,
                    ErrorCode.GROUP_NAME_ALREADY_EXISTS );
        }
    }

    /**
     * Builds the user groups.
     *
     * @param emails
     *            the emails
     * @param group
     *            the group
     * @return the sets the
     */
    private Set< UserGroup > buildUserGroups( final List< String > emails, final Group group ) {
        final List< User > users = userService.getUsersByEmail( emails );
        final Set< UserGroup > userGroups = new HashSet<>();
        for ( final User user : users ) {
            final UserGroup userGroup = new UserGroup();
            userGroup.setUser( user );
            userGroup.setGroup( group );
            userGroups.add( userGroup );
        }
        return userGroups;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.GroupManagementBusinessService#getGroupsList
     * (java.lang.String, java.lang.String)
     */
    @Override
    public List< Group > getGroupsList( final String name, final String isDeleted ) {
        List< Group > groups = new ArrayList<>();
        if (isNotBlank( name )) {
            Group group = null;
            if (isNotBlank( isDeleted )) {
                group = groupService.findGroupByNameAndDeleted( name, Boolean.valueOf( isDeleted ) );
            } else {
                group = groupService.findByName( name );
            }
            if (group != null) {
                groups.add( group );
            }
        } else if (isNotBlank( isDeleted )) {
            groups = groupService.findByDeleted( Boolean.valueOf( isDeleted ) );
        } else {
            groups = groupService.findAll();
        }
        return groups;
    }

    /**
     * Gets the group.
     *
     * @param id
     *            the id
     * @return the group
     */
    private Group getGroup( final String id ) {
        final Group group = groupService.findGroupById( id );
        if (group == null) {
            LOGGER.info( "Group not found with id as " + id );
            throw new ApplicationException( "Group not found with given id :" + id, ErrorCode.GROUP_NOT_FOUND );
        } else if (group.isDeleted()) {
            LOGGER.info( "Group found with id as " + id + " and deleted to true" );
            throw new ApplicationException( "Group is deleted with given id :" + id, ErrorCode.GROUP_DELETED );
        }
        return group;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.GroupManagementBusinessService#
     * getUserGroupsByGroupIdAndEmailId(java.lang.String, java.lang.String)
     */
    @Override
    public GroupResponse getUserGroupsByGroupIdAndEmailId( final String groupId, final String emailId ) {

        final User user = userService.getUserByEmail( emailId );
        final Group group = groupService.findGroupById( groupId );
        if (user == null) {
            throw new ApplicationException( "Invalid user email id : " + emailId, ErrorCode.USER_NOT_FOUND );
        }
        if (group == null) {
            LOGGER.info( "Group Id not found with id as " + groupId );
            throw new ApplicationException( "Group Id not found with given id :" + groupId, ErrorCode.GROUP_NOT_FOUND );
        }
        final UserGroup userGroup = userGroupService.findByUserAndGroup( user, group );

        final GroupResponse groupResponse = new GroupResponse( groupId );
        groupResponse.setIsAgentPartOfGroup( null != userGroup ? true : false );
        return groupResponse;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.business.GroupManagementBusinessService#
     * getUserGroupsByGroupNameAndEmailId(java.lang.String, java.lang.String)
     */
    @Override
    public GroupResponse getUserGroupsByGroupNameAndEmailId( final String groupName, final String emailId ) {
        final User user = userService.getUserByEmail( emailId );
        final Group group = groupService.findByName( groupName );
        if (user == null) {
            throw new ApplicationException( "Invalid user email id : " + emailId, ErrorCode.USER_NOT_FOUND );
        }
        if (group == null) {
            LOGGER.info( "Group Name not found with id as " + groupName );
            throw new ApplicationException( "Group Name not found with given id :" + groupName,
                    ErrorCode.GROUP_NOT_FOUND );
        }
        final UserGroup userGroup = userGroupService.findByUserAndGroup( user, group );

        final GroupResponse groupResponse = new GroupResponse( groupName );
        groupResponse.setIsAgentPartOfGroup( null != userGroup ? true : false );
        return groupResponse;
    }

}
