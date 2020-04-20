package com.owners.gravitas.repository;

import org.testng.annotations.Test;

import com.owners.gravitas.config.RepositoryTestFactory;

/**
 * The Class GroupRepositoryTest.
 *
 * @author raviz
 */
public class GroupRepositoryTest {

    /** The group repository. */
    private final GroupRepository groupRepository = RepositoryTestFactory.test( GroupRepository.class );

    /**
     * Test find by name.
     */
    @Test
    public void testFindByName() {
        groupRepository.findByName( "name" );
    }

    /**
     * Test find by name and deleted.
     */
    @Test
    public void testFindByNameAndDeleted() {
        groupRepository.findByNameAndDeleted( "name", false );
    }

    /**
     * Test find by id.
     */
    @Test
    public void testFindById() {
        groupRepository.findById( "id" );
    }

    /**
     * Test find by deleted.
     */
    @Test
    public void testFindByDeleted() {
        groupRepository.findByDeleted( true );
    }

}
