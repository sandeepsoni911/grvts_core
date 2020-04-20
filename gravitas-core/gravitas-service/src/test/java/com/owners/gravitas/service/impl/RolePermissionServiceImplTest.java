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
import com.owners.gravitas.repository.RolePermissionRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class RolePermissionServiceImplTest.
 *
 * @author shivamm
 */
public class RolePermissionServiceImplTest extends AbstractBaseMockitoTest {

    /** The role permission service impl. */
    @InjectMocks
    private RolePermissionServiceImpl rolePermissionServiceImpl;

    /** The role permission repository. */
    @Mock
    private RolePermissionRepository rolePermissionRepository;

    /**
     * Test save agent task.
     */
    @Test
    public void testGetPermissionsByUserEmailId() {
        rolePermissionServiceImpl.getPermissionsByUserEmailId( "test" );
        when( rolePermissionRepository.getPermissionsByUserEmailId( "test" ) ).thenReturn( new ArrayList< String >() );
        verify( rolePermissionRepository ).getPermissionsByUserEmailId( "test" );
    }

    /**
     * Test get permissions by roles.
     */
    @Test
    public void testGetPermissionsByRoles() {
        rolePermissionServiceImpl.getPermissionsByRoles( new ArrayList< String >() );
        when( rolePermissionRepository.getPermissionsByUserEmailId( "test" ) ).thenReturn( new ArrayList< String >() );
        verify( rolePermissionRepository ).getPermissionsByRoles( new ArrayList< String >() );
    }

    /**
     * Test get roles by user email id.
     */
    @Test
    public void testGetRolesByUserEmailId() {
        final String email = "test@test.com";
        final List< String > expectedRoles = new ArrayList<>();
        when( rolePermissionRepository.getRolesyUserEmailId( email ) ).thenReturn( expectedRoles );
        final List< String > actualRoles = rolePermissionServiceImpl.getRolesByUserEmailId( email );
        assertEquals( actualRoles, expectedRoles );
        verify( rolePermissionRepository ).getRolesyUserEmailId( email );
    }

}
