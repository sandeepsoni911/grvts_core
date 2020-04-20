/**
 *
 */
package com.owners.gravitas.repository;

import java.util.ArrayList;

import org.testng.annotations.Test;

import com.owners.gravitas.config.RepositoryTestFactory;

/**
 * The Class UserRepositoryTest.
 *
 * @author harshads
 */
public class UserRepositoryTest {

    /** The user repository. */
    private final UserRepository userRepository = RepositoryTestFactory.test( UserRepository.class );

    /**
     * Find by email test.
     */
    @Test
    public void findByEmailTest() {
        userRepository.findByEmail( null );
    }

    /**
     * Find all test.
     */
    //@Test
    public void findAllTest() {
        userRepository.findAll();
    }

    /**
     * Test find by email in.
     */
    @Test
    public void testFindByEmailIn() {
        userRepository.findByEmailIn( new ArrayList< String >() );
    }
}
