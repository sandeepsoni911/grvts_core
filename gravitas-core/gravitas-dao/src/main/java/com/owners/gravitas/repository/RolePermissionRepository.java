package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owners.gravitas.domain.entity.Permission;

/**
 * The Interface PermissionRepository.
 *
 * @author pabhishek
 */
public interface RolePermissionRepository extends JpaRepository< Permission, String > {

    /** The find permissions by user email id. */
    String FIND_PERMISSIONS_BY_USER_EMAIL_ID = "select distinct permission.name from GR_ROLE_MEMBER roleMember inner join  roleMember.user user inner join  roleMember.role.rolePermissions rolePermissions inner join rolePermissions.permission permission where user.email=:email";

    /** The find permissions by roles. */
    String FIND_PERMISSIONS_BY_ROLES = "select distinct permission.name from GR_ROLE_PERMISSION rolePermission inner join  rolePermission.role role inner join  rolePermission.permission permission where role.name IN (:roleNames)";

    /** The find roles by user email id. */
    String FIND_ROLES_BY_USER_EMAIL_ID = "select distinct role.name from GR_ROLE_MEMBER roleMember inner join  roleMember.user user inner join  roleMember.role.rolePermissions rolePermissions inner join rolePermissions.role role where user.email=:email";

    /**
     * Gets the permissions by user email id.
     *
     * @param email
     *            the email
     * @return the permissions by user email id
     */
    @Query( value = FIND_PERMISSIONS_BY_USER_EMAIL_ID )
    List< String > getPermissionsByUserEmailId( @Param( "email" ) String email);

    /**
     * Gets the permissions by roles.
     *
     * @param roleNames
     *            the role names
     * @return the permissions by roles
     */
    @Query( value = FIND_PERMISSIONS_BY_ROLES )
    List< String > getPermissionsByRoles( @Param( "roleNames" ) List< String > roleNames);

    /**
     * Gets the rolesy user email id.
     *
     * @param email
     *            the email
     * @return the rolesy user email id
     */
    @Query( value = FIND_ROLES_BY_USER_EMAIL_ID )
    List< String > getRolesyUserEmailId( @Param( "email" ) String email);
}
