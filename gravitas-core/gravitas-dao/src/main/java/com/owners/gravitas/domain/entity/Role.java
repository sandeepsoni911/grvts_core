package com.owners.gravitas.domain.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * The Class Role.
 *
 * @author pabhishek
 */
@Entity( name = "GR_ROLE" )
public class Role extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4536581647120899528L;

    /** The role member. */
    @OneToMany( mappedBy = "role" )
    private List< RoleMember > roleMember;

    /** The role permission. */
    @OneToMany( mappedBy = "role" )
    private List< RolePermission > rolePermissions;

    /** The name. */
    @Column( name = "NAME", unique = true, nullable = false )
    private String name;

    /** The description. */
    @Column( name = "DESCRIPTION", nullable = true )
    private String description;

    /**
     * Instantiates a new role.
     */
    public Role() {

    }

    /**
     * Instantiates a new role.
     *
     * @param roleMember
     *            the role member
     * @param rolePermissions
     *            the role permission
     * @param name
     *            the name
     * @param description
     *            the description
     */
    public Role( List< RoleMember > roleMember, List< RolePermission > rolePermission, String name,
            String description ) {
        super();
        this.roleMember = roleMember;
        this.rolePermissions = rolePermission;
        this.name = name;
        this.description = description;
    }

    /**
     * Gets the role member.
     *
     * @return the role member
     */

    public List< RoleMember > getRoleMember() {
        return roleMember;
    }

    /**
     * Sets the role member.
     *
     * @param roleMember
     *            the new role member
     */
    public void setRoleMember( List< RoleMember > roleMember ) {
        this.roleMember = roleMember;
    }

    /**
     * Gets the role permission.
     *
     * @return the role permission
     */

    public List< RolePermission > getRolePermissions() {
        return rolePermissions;
    }

    /**
     * Sets the role permission.
     *
     * @param rolePermissions
     *            the new role permission
     */
    public void setRolePermissions( List< RolePermission > rolePermission ) {
        this.rolePermissions = rolePermission;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName( String name ) {
        this.name = name;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description
     *            the new description
     */
    public void setDescription( String description ) {
        this.description = description;
    }

}
