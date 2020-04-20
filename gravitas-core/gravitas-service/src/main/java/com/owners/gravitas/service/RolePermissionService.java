package com.owners.gravitas.service;

import java.util.List;

/**
 * The Interface RolePermissionService.
 *
 * @author pabhishek
 */
public interface RolePermissionService {

    /**
     * Gets the permissions by user email id.
     *
     * @param email
     *            the email
     * @return the permissions by user email id
     */
    List< String > getPermissionsByUserEmailId( String email );

    /**
     * Gets the permissions by roles.
     *
     * @param roleNames
     *            the role names
     * @return the permissions by roles
     */
    List< String > getPermissionsByRoles( List< String > roleNames );

    /**
     * Gets the roles by user email id.
     *
     * @param email
     *            the email
     * @return the roles by user email id
     */
    List< String > getRolesByUserEmailId( String email );

}
