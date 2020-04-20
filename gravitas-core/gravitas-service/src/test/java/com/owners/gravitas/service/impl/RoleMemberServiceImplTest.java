package com.owners.gravitas.service.impl;

import java.util.ArrayList;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.Role;
import com.owners.gravitas.repository.RoleRepository;

/**
 * The Class RoleMemberServiceImplTest.
 *
 * @author shivamm
 */
public class RoleMemberServiceImplTest extends AbstractBaseMockitoTest {

    /** The role member service impl. */
    @InjectMocks
    private RoleMemberServiceImpl roleMemberServiceImpl;

    /** The role repository. */
    @Mock
    private RoleRepository roleRepository;

    /**
     * Test get agent coverage areas.
     */
    @Test
    public void testGetAgentCoverageAreas() {
        final Role role = new Role();
        roleMemberServiceImpl.save( role );
        Mockito.when( roleRepository.save( role ) ).thenReturn( new Role() );
        Mockito.verify( roleRepository ).save( role );
    }

    /**
     * Test get roles by user email id.
     */
    @Test
    public void testGetRolesByUserEmailId() {
        roleMemberServiceImpl.getRolesByUserEmailId( "test" );
        Mockito.when( roleRepository.getRolesByUserEmailId( "test" ) ).thenReturn( new ArrayList< Role >() );
        Mockito.verify( roleRepository ).getRolesByUserEmailId( "test" );
    }

    /**
     * Test find by id.
     */
    @Test
    public void testFindById() {
        roleMemberServiceImpl.findById( "test" );
        Mockito.when( roleRepository.findById( "test" ) ).thenReturn( new Role() );
        Mockito.verify( roleRepository ).findById( "test" );
    }

    /**
     * Test find by name.
     */
    @Test
    public void testFindByName() {
        roleMemberServiceImpl.findByName( "test" );
        Mockito.when( roleRepository.findByName( "test" ) ).thenReturn( new Role() );
        Mockito.verify( roleRepository ).findByName( "test" );
    }
}
