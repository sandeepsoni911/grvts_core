package com.owners.gravitas.dto.response;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class UserGroupResponse.
 *
 * @author raviz
 */
public class UserGroupResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3853510547809129031L;

    /** The user group details. */
    private List< UserGroupDetail > userGroupDetails = new ArrayList<>();

    /**
     * Instantiates a new user group response.
     */
    public UserGroupResponse() {

    }

    /**
     * Instantiates a new user group response.
     *
     * @param userGroupDetails
     *            the user group details
     */
    public UserGroupResponse( final List< UserGroupDetail > userGroupDetails ) {
        super();
        this.userGroupDetails = userGroupDetails;
    }

    /**
     * Gets the user group details.
     *
     * @return the user group details
     */
    public List< UserGroupDetail > getUserGroupDetails() {
        return userGroupDetails;
    }

    /**
     * Sets the user group details.
     *
     * @param userGroupDetails
     *            the new user group details
     */
    public void setUserGroupDetails( final List< UserGroupDetail > userGroupDetails ) {
        this.userGroupDetails = userGroupDetails;
    }

    /**
     * Adds the user group details.
     *
     * @param userGroupDetail
     *            the user group detail
     */
    public void addUserGroupDetails( final UserGroupDetail userGroupDetail ) {
        userGroupDetails.add( userGroupDetail );
    }

}
