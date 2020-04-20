/**
 *
 */
package com.owners.gravitas.repository;

import org.testng.annotations.Test;

import com.owners.gravitas.config.RepositoryTestFactory;

/**
 * The Class RoleMemberRepositoryTest.
 *
 * @author harshads
 */
public class RoleMemberRepositoryTest {

    /** The role member repository. */
    private final RoleMemberRepository roleMemberRepository = RepositoryTestFactory.test( RoleMemberRepository.class );

    /**
     * Find by id test.
     */
    @Test
    public void findByIdTest() {
        roleMemberRepository.findById( null );
    }

    /**
     * Find by name test.
     */
    @Test
    public void findByNameTest() {
        roleMemberRepository.findByName( null );
    }

}
