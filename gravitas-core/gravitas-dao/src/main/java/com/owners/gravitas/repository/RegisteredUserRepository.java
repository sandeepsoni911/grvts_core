package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.owners.gravitas.domain.entity.RegisteredUser;

/**
 * The Interface RegisteredUserRepository.
 * 
 * @author bhardrah
 */
public interface RegisteredUserRepository extends JpaRepository< RegisteredUser, String > {

    /**
     * Find by email.
     *
     * @param email
     *            the email
     * @return the registeredUser
     */
    RegisteredUser findByEmail( final String email );
    
    /**
     * Find by userId.
     *
     * @param userId
     *            the user Id
     * @return the registeredUser
     */
    RegisteredUser findByUserId( final String userId );

}
