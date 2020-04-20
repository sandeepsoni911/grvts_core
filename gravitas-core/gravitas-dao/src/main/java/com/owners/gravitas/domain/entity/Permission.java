package com.owners.gravitas.domain.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * The Class Permission.
 * 
 * @author pabhishek
 */
@Entity( name = "GR_PERMISSION" )
public class Permission extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8153698359739735225L;

    /** The role permission. */
    @OneToMany( mappedBy = "permission" )
    private List< RolePermission > rolePermission;

    /** The name. */
    @Column( name = "NAME",unique = true, nullable = true )
    private String name;

    /** The description. */
    @Column( name = "DESCRIPTION", nullable = true )
    private String description;

    /**
     * Instantiates a new permission.
     */
    public Permission() {

    }

    /**
     * Instantiates a new permission.
     *
     * @param rolePermission
     *            the role permission
     * @param name
     *            the name
     * @param description
     *            the description
     */
    public Permission( List< RolePermission > rolePermission, String name, String description ) {
        super();
        this.rolePermission = rolePermission;
        this.name = name;
        this.description = description;
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

    /**
     * Gets the role permission.
     *
     * @return the role permission
     */
  
    public List< RolePermission > getRolePermission() {
        return rolePermission;
    }

    /**
     * Sets the role permission.
     *
     * @param rolePermission
     *            the new role permission
     */
    public void setRolePermission( List< RolePermission > rolePermission ) {
        this.rolePermission = rolePermission;
    }

}
