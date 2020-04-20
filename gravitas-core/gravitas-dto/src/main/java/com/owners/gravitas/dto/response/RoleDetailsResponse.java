package com.owners.gravitas.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.owners.gravitas.dto.Role;

/**
 * The Class RoleDetailsResponse.
 * 
 * @author pabhishek
 */
public class RoleDetailsResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6242393417944656482L;

    /** The roles. */
    private List< Role > roles = new ArrayList< Role >();

    /**
     * Gets the roles.
     *
     * @return the roles
     */
    public List< Role > getRoles() {
        return roles;
    }

    /**
     * Sets the roles.
     *
     * @param roles
     *            the new roles
     */
    public void setRoles( List< Role > roles ) {
        this.roles = roles;
    }

}
