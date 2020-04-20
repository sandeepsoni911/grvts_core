package com.owners.gravitas.controller;

import static com.owners.gravitas.constants.UserPermission.CREATE_GROUP;
import static com.owners.gravitas.constants.UserPermission.DELETE_GROUP;
import static com.owners.gravitas.constants.UserPermission.UPDATE_GROUP;
import static com.owners.gravitas.constants.UserPermission.VIEW_AGENT_GROUP;
import static com.owners.gravitas.constants.UserPermission.VIEW_GROUP;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.owners.gravitas.annotation.PerformanceLog;
import com.owners.gravitas.annotation.ReadArgs;
import com.owners.gravitas.business.GroupManagementBusinessService;
import com.owners.gravitas.dto.request.UserGroupRequest;
import com.owners.gravitas.dto.response.GroupResponse;
import com.owners.gravitas.dto.response.UserGroupResponse;

/**
 * The Class GroupManagementController.
 *
 * @author raviz
 */
@RestController
public class GroupManagementController extends BaseWebController {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( GroupManagementController.class );

    /** The group management business service. */
    @Autowired
    private GroupManagementBusinessService groupManagementBusinessService;

    /**
     * Creates the user group.
     *
     * @param userGroupRequest
     *            the user group request
     * @return the group response
     */
    @CrossOrigin
    @RequestMapping( value = "/groups", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( CREATE_GROUP )
    public GroupResponse createUserGroup( @RequestBody @Valid final UserGroupRequest userGroupRequest ) {
        LOGGER.info( "Creating group with name : " + userGroupRequest.getGroupName() );
        return groupManagementBusinessService.createUserGroup( userGroupRequest );
    }

    /**
     * Gets the groups.
     *
     * @param name
     *            the name
     * @param deleted
     *            the deleted
     * @return the groups
     */
    @CrossOrigin
    @RequestMapping( value = "/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { VIEW_GROUP, VIEW_AGENT_GROUP } )
    public UserGroupResponse getGroups( @RequestParam( required = false ) final String name,
            @RequestParam( required = false ) final String deleted ) {
        LOGGER.info( "Fetching group with name : " + name + " deleted : " + deleted );
        return groupManagementBusinessService.getGroups( name, deleted );
    }

    /**
     * Gets the group by id.
     *
     * @param groupId
     *            the group id
     * @return the group by id
     */
    @CrossOrigin
    @RequestMapping( value = "/groups/{groupId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( VIEW_GROUP )
    public UserGroupResponse getGroupById( @PathVariable final String groupId ) {
        LOGGER.info( "Fetching group with id : " + groupId );
        return groupManagementBusinessService.getGroupById( groupId );
    }

    /**
     * Update group.
     *
     * @param groupId
     *            the group id
     * @param userGroupRequest
     *            the user group request
     * @return the user group response
     */
    @CrossOrigin
    @RequestMapping( value = "/groups/{groupId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( UPDATE_GROUP )
    public UserGroupResponse updateGroup( @PathVariable final String groupId,
            @RequestBody @Valid final UserGroupRequest userGroupRequest ) {
        LOGGER.info( "Updating group with id : " + groupId );
        groupManagementBusinessService.updateGroup( groupId, userGroupRequest );
        return groupManagementBusinessService.getGroupById( groupId );
    }

    /**
     * Archive group.
     *
     * @param groupId
     *            the group id
     * @return the group response
     */
    @CrossOrigin
    @RequestMapping( value = "/groups/{groupId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( DELETE_GROUP )
    public GroupResponse archiveGroup( @PathVariable final String groupId ) {
        LOGGER.info( "Archiving group with id : " + groupId );
        return groupManagementBusinessService.archiveGroup( groupId );
    }

    /**
     * Gets the user groups by group id and email id.
     *
     * @param groupId
     *            the group id
     * @param emailId
     *            the email id
     * @return the user groups by group id and email id
     */
    @CrossOrigin
    @RequestMapping( value = "part-of/group/{groupId}/email/{emailId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { VIEW_AGENT_GROUP } )
    public GroupResponse getUserGroupsByGroupIdAndEmailId( @PathVariable final String groupId,
            @PathVariable final String emailId ) {
        LOGGER.info( "Fetching UserGroups for agent {} in group {} : ", emailId, groupId );
        return groupManagementBusinessService.getUserGroupsByGroupIdAndEmailId( groupId, emailId );
    }

    /**
     * Gets the user groups by group name and email id.
     *
     * @param groupName
     *            the group name
     * @param emailId
     *            the email id
     * @return the user groups by group name and email id
     */
    @CrossOrigin
    @RequestMapping( value = "part-of/group-name/{groupName}/email/{emailId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ReadArgs
    @PerformanceLog
    @Secured( { VIEW_AGENT_GROUP } )
    public GroupResponse getUserGroupsByGroupNameAndEmailId( @PathVariable final String groupName,
            @PathVariable final String emailId ) {
        LOGGER.info( "Fetching UserGroups for agent {} in group {} : ", emailId, groupName );
        return groupManagementBusinessService.getUserGroupsByGroupNameAndEmailId( groupName, emailId );
    }

}
