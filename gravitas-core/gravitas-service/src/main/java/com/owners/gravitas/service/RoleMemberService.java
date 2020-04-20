package com.owners.gravitas.service;

import java.util.List;
import java.util.Map;

import com.owners.gravitas.domain.entity.Role;

/**
 * The Interface RoleMemberService.
 * 
 * @author pabhishek
 */
public interface RoleMemberService {

    /**
     * Save.
     *
     * @param role
     *            the role
     * @return the role
     */
    Role save( Role role );

    /**
     * Gets the roles by user email id.
     *
     * @param email
     *            the email
     * @return the roles by user email id
     */
    List< Role > getRolesByUserEmailId( String email );

    /**
     * Find by id.
     *
     * @param id
     *            the id
     * @return the role
     */
    Role findById( String id );

    /**
     * Find by name.
     *
     * @param name
     *            the name
     * @return the role
     */
    Role findByName( String name );
    
    /**
     * Map of Role Name and Role Id
     *
     * @return the map
     */
    Map<String,String> getRolesNameMap();
    
    /**
     * Map of Role Id and Role Name
     *
     * @return the map
     */
    Map<String,String> getRolesIdMap();

}
