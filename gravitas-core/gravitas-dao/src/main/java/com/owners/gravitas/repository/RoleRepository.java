package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owners.gravitas.domain.entity.Role;

/**
 * The Interface RoleRepository.
 * 
 * @author ankusht
 */
public interface RoleRepository extends JpaRepository< Role, String > {

    /** The find roles by user email id. */
    String FIND_ROLES_BY_USER_EMAIL_ID = "select role from GR_ROLE role inner join role.roleMember roleMember inner join roleMember.user user where user.email=:email";

    /**
     * Gets the roles by user email.
     *
     * @param email
     *            the email
     * @return the roles by user email
     */
    @Query( value = FIND_ROLES_BY_USER_EMAIL_ID )
    public List< Role > getRolesByUserEmailId( @Param( "email" ) String email );

    /**
     * Find by id.
     *
     * @param id
     *            the id
     * @return the role
     */
    public Role findById( String id );

    /**
     * Find by name.
     *
     * @param name
     *            the name
     * @return the role
     */
    Role findByName( String name );
}
