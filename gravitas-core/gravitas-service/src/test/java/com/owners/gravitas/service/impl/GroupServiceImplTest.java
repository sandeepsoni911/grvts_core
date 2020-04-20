package com.owners.gravitas.service.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.Group;
import com.owners.gravitas.repository.GroupRepository;

/**
 * The Class GroupServiceImplTest.
 *
 * @author raviz
 */
public class GroupServiceImplTest extends AbstractBaseMockitoTest {

    /** The group service impl. */
    @InjectMocks
    private GroupServiceImpl groupServiceImpl;

    /** The group repository. */
    @Mock
    private GroupRepository groupRepository;

    /**
     * Test find by name.
     */
    @Test
    public void testFindByName() {
        final String name = "name";
        final Group expectedGroup = new Group();
        when( groupRepository.findByName( name ) ).thenReturn( expectedGroup );
        final Group actualGroup = groupServiceImpl.findByName( name );
        assertEquals( actualGroup, expectedGroup );
        verify( groupRepository ).findByName( name );
    }

    /**
     * Test find group by name and deleted.
     */
    @Test
    public void testFindGroupByNameAndDeleted() {
        final String name = "name";
        final boolean deleted = false;
        final Group expectedGroup = new Group();
        when( groupRepository.findByNameAndDeleted( name, deleted ) ).thenReturn( expectedGroup );
        final Group actualGroup = groupServiceImpl.findGroupByNameAndDeleted( name, deleted );
        assertEquals( actualGroup, expectedGroup );
        verify( groupRepository ).findByNameAndDeleted( name, deleted );
    }

    /**
     * Test find group by id.
     */
    @Test
    public void testFindGroupById() {
        final String id = "id";
        final Group expectedGroup = new Group();
        when( groupRepository.findById( id ) ).thenReturn( expectedGroup );
        final Group actualGroup = groupServiceImpl.findGroupById( id );
        assertEquals( actualGroup, expectedGroup );
        verify( groupRepository ).findById( id );
    }

    /**
     * Test save.
     */
    @Test
    public void testSave() {
        final Group expectedGroup = new Group();
        when( groupRepository.save( expectedGroup ) ).thenReturn( expectedGroup );
        final Group actualGroup = groupServiceImpl.save( expectedGroup );
        assertEquals( actualGroup, expectedGroup );
        verify( groupRepository ).save( expectedGroup );
    }

    /**
     * Test find by deleted.
     */
    @Test
    public void testFindByDeleted() {
        final boolean deleted = false;
        final List< Group > expectedGroups = new ArrayList< Group >();
        when( groupRepository.findByDeleted( deleted ) ).thenReturn( expectedGroups );
        final List< Group > actualGroups = groupServiceImpl.findByDeleted( deleted );
        assertEquals( actualGroups, expectedGroups );
        verify( groupRepository ).findByDeleted( deleted );
    }

    /**
     * Test find all.
     */
    @Test
    public void testFindAll() {
        final List< Group > expectedGroups = new ArrayList< Group >();
        when( groupRepository.findAll() ).thenReturn( expectedGroups );
        final List< Group > actualGroups = groupServiceImpl.findAll();
        assertEquals( actualGroups, expectedGroups );
        verify( groupRepository ).findAll();
    }
}
