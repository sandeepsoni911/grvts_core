package com.owners.gravitas.business.builder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.response.UserDetailsResponseBuilder;
import com.owners.gravitas.domain.entity.Group;
import com.owners.gravitas.domain.entity.GroupOkr;
import com.owners.gravitas.domain.entity.UserGroup;
import com.owners.gravitas.dto.response.UserDetailsResponse;
import com.owners.gravitas.dto.response.UserGroupDetail;
import com.owners.gravitas.dto.response.UserGroupResponse;
import com.owners.gravitas.service.UserGroupService;
import com.owners.gravitas.service.UserService;

/**
 * The Class UserGroupResponseBuilder.
 *
 * @author raviz
 */
@Component( "userGroupResponseBuilder" )
public class UserGroupResponseBuilder extends AbstractBuilder< List< Group >, UserGroupResponse > {

    /** The user service. */
    @Autowired
    private UserService userService;

    /** The user group service. */
    @Autowired
    private UserGroupService userGroupService;

    /** The user details response builder. */
    @Autowired
    private UserDetailsResponseBuilder userDetailsResponseBuilder;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public UserGroupResponse convertTo( final List< Group > source, final UserGroupResponse destination ) {
        UserGroupResponse response = destination;
        if (source != null) {
            if (response == null) {
                response = new UserGroupResponse();
            }
            for ( final Group group : source ) {
                if (group != null) {
                    final UserDetailsResponse usersDetails = getUserDetailsResponse( group );
                    if (usersDetails != null) {
                        final UserGroupDetail userGroupDetail = buildUserGroupDetail( group );
                        userGroupDetail.setUsers( usersDetails.getUsers() );
                        response.addUserGroupDetails( userGroupDetail );
                    }
                }
            }
        }
        return response;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public List< Group > convertFrom( final UserGroupResponse source, final List< Group > destination ) {
        throw new UnsupportedOperationException();
    }

    /**
     * Builds the user group detail.
     *
     * @param group
     *            the group
     * @return the user group detail
     */
    private UserGroupDetail buildUserGroupDetail( final Group group ) {
        final UserGroupDetail userGroupDetail = new UserGroupDetail();
//        UserName userName = userService.getUserDetails( group.getCreatedBy() ).getName();
//        userGroupDetail.setCreatedFirstName( userName.getGivenName() );
//        userGroupDetail.setCreatedLastName( userName.getFamilyName() );
//        userGroupDetail.setModifiedFirstName( userName.getGivenName() );
//        userGroupDetail.setModifiedLastName( userName.getFamilyName() );

        final GroupOkr groupOkr = group.getGroupOkr();
        userGroupDetail.setGroupId( group.getId() );
        userGroupDetail.setGroupName( group.getName() );
        userGroupDetail.setTestStartDate( groupOkr.getTestStartDate().toDate() );
        userGroupDetail.setTestEndDate( groupOkr.getTestEndDate().toDate() );
        userGroupDetail.setRelatedOkr( groupOkr.getRelatedOkr() );
        userGroupDetail.setCreatedBy( group.getCreatedBy() );
        userGroupDetail.setCreatedOn( group.getCreatedDate() );
        userGroupDetail.setLastModifiedBy( group.getLastModifiedBy() );
        userGroupDetail.setLastModifiedOn( groupOkr.getLastModifiedDate() );
        return userGroupDetail;
    }

    /**
     * Gets the user details response.
     *
     * @param group
     *            the group
     * @return the user details response
     */
    private UserDetailsResponse getUserDetailsResponse( final Group group ) {
        UserDetailsResponse usersDetails = null;
        final Set< UserGroup > userGroups = userGroupService.findByGroup( group );
        if (userGroups != null) {
            final List< String > emails = userGroups.stream().map( s -> s.getUser().getEmail() )
                    .collect( Collectors.toList() );
            usersDetails = userDetailsResponseBuilder.convertTo( userService.getUsersByEmails( emails ) );
        }
        return usersDetails;
    }

}
