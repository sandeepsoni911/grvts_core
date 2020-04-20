package com.owners.gravitas.service.impl;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.Group;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.domain.entity.UserGroup;
import com.owners.gravitas.repository.UserGroupRepository;

/**
 * The Class UserGroupServiceImplTest.
 *
 * @author raviz
 */
public class UserGroupServiceImplTest extends AbstractBaseMockitoTest {

    /** The user group service impl. */
    @InjectMocks
    private UserGroupServiceImpl userGroupServiceImpl;

    /** The user group repository. */
    @Mock
    private UserGroupRepository userGroupRepository;

    /**
     * Test save.
     */
    @Test
    public void testSave() {
        final UserGroup expectedUserGroup = new UserGroup();
        final UserGroup userGroup = new UserGroup();
        when( userGroupRepository.save( userGroup ) ).thenReturn( expectedUserGroup );
        final UserGroup actualUserGroup = userGroupServiceImpl.save( userGroup );
        assertEquals( actualUserGroup, expectedUserGroup );
        verify( userGroupRepository ).save( userGroup );
    }

    /**
     * Test save collection.
     */
    @Test
    public void testSaveCollection() {
        final List< UserGroup > expectedUserGroups = new ArrayList<>();
        final List< UserGroup > userGroups = new ArrayList<>();
        when( userGroupRepository.save( userGroups ) ).thenReturn( expectedUserGroups );
        final List< UserGroup > actualUserGroup = userGroupServiceImpl.save( userGroups );
        assertEquals( actualUserGroup, expectedUserGroups );
        verify( userGroupRepository ).save( userGroups );
    }

    /**
     * Test find by group.
     */
    @Test
    public void testFindByGroup() {
        final Group group = new Group();
        final Set< UserGroup > expectedUserGroup = new HashSet<>();
        when( userGroupRepository.findByGroup( group ) ).thenReturn( expectedUserGroup );
        final Set< UserGroup > actualUserGroup = userGroupServiceImpl.findByGroup( group );
        assertEquals( actualUserGroup, expectedUserGroup );
        verify( userGroupRepository ).findByGroup( group );
    }

    /**
     * Test find by user.
     */
    @Test
    public void testFindByUser() {
        final User user = new User();
        final Set< UserGroup > expectedUserGroup = new HashSet<>();
        when( userGroupRepository.findByUser( user ) ).thenReturn( expectedUserGroup );
        final Set< UserGroup > actualUserGroup = userGroupServiceImpl.findByUser( user );
        assertEquals( actualUserGroup, expectedUserGroup );
        verify( userGroupRepository ).findByUser( user );
    }

    /**
     * Test delete by group and flush.
     */
    @Test
    public void testDeleteByGroupAndFlush() {
        final Group group = new Group();
        doNothing().when( userGroupRepository ).deleteByGroup( group );
        doNothing().when( userGroupRepository ).flush();
        userGroupServiceImpl.deleteByGroupAndFlush( group );
        verify( userGroupRepository ).deleteByGroup( group );
        verify( userGroupRepository ).flush();
    }

    /**
     * Test delete by user and flush.
     */
    @Test
    public void testDeleteByUserAndFlush() {
        final User user = new User();
        doNothing().when( userGroupRepository ).deleteByUser( user );
        doNothing().when( userGroupRepository ).flush();
        userGroupServiceImpl.deleteByUserAndFlush( user );
        verify( userGroupRepository ).deleteByUser( user );
        verify( userGroupRepository ).flush();
    }

    /**
     * Test find by user and group.
     */
    @Test
    public void testFindByUserAndGroup() {
        final User user = new User();
        final Group group = new Group();
        final UserGroup expectedUserGroup = new UserGroup();
        when( userGroupRepository.findByUserAndGroup( user, group ) ).thenReturn( expectedUserGroup );
        final UserGroup actualUserGroup = userGroupServiceImpl.findByUserAndGroup( user, group );
        assertEquals( actualUserGroup, expectedUserGroup );
        verify( userGroupRepository ).findByUserAndGroup( user, group );
    }

    /**
     * Test delete by user and group.
     */
    @Test
    public void testDeleteByUserAndGroup() {
        final User user = new User();
        final Group group = new Group();
        userGroupServiceImpl.deleteByUserAndGroup( user, group );
        verify( userGroupRepository ).deleteByUserAndGroup( user, group );
        verify( userGroupRepository ).flush();
    }
}
