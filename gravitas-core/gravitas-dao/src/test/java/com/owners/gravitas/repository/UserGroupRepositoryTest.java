package com.owners.gravitas.repository;

import org.testng.annotations.Test;

import com.owners.gravitas.config.RepositoryTestFactory;
import com.owners.gravitas.domain.entity.Group;
import com.owners.gravitas.domain.entity.User;

/**
 * The Class UserGroupRepositoryTest.
 *
 * @author raviz
 */
public class UserGroupRepositoryTest {

    /** The user group repository. */
    private final UserGroupRepository userGroupRepository = RepositoryTestFactory.test( UserGroupRepository.class );

    /**
     * Test find by group.
     */
    @Test
    public void testFindByGroup() {
        userGroupRepository.findByGroup( new Group() );
    }

    /**
     * Test find by user.
     */
    @Test
    public void testFindByUser() {
        userGroupRepository.findByUser( new User() );
    }

    /**
     * Test delete by group.
     */
    @Test
    public void testDeleteByGroup() {
        userGroupRepository.deleteByGroup( new Group() );
    }

    /**
     * Test delete by user.
     */
    @Test
    public void testDeleteByUser() {
        userGroupRepository.deleteByUser( new User() );
    }
}
