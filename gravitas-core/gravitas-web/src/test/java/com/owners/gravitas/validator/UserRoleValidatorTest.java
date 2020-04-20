package com.owners.gravitas.validator;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.ApiUser;
import com.owners.gravitas.exception.AgentInvalidException;
import com.owners.gravitas.util.GravitasWebUtil;

/**
 * Test class UserRoleValidatorTest.
 *
 * @author raviz
 */
public class UserRoleValidatorTest extends AbstractBaseMockitoTest {

    /** The user role validator test. */
    @InjectMocks
    private UserRoleValidator userRoleValidator;

    /** The gravitas web util. */
    @Mock
    private GravitasWebUtil gravitasWebUtil;

    /**
     * Test get valid agent by id should return user on successful validation.
     */
    @Test
    public void testGetValidAgentByIdShouldReturnAgentOnSuccessfulValidation() {
        final Set< String > roles = new HashSet< String >();
        final ApiUser expected = new ApiUser();
        final String uid = "uid";
        expected.setRoles( roles );
        expected.setUid( uid );

        when( gravitasWebUtil.getAppUser() ).thenReturn( expected );

        final ApiUser actual = userRoleValidator.validateByAgentId( uid );
        assertEquals( actual, expected );
        verify( gravitasWebUtil ).getAppUser();
    }

    /**
     * Test get valid user by id should return user on successful validation.
     */
    @Test
    public void testGetValidUserByIdShouldReturnUserOnSuccessfulValidation() {
        final Set< String > roles = new HashSet< String >();
        final ApiUser expected = new ApiUser();
        final String uid = "uid";
        final String role = "role";
        expected.setRoles( roles );
        expected.setUid( uid );

        when( gravitasWebUtil.getAppUser() ).thenReturn( expected );

        ApiUser actual = userRoleValidator.validateByUserId( uid, role );
        assertEquals( actual, expected );
        verify( gravitasWebUtil ).getAppUser();
    }

    /**
     * Test get valid agent by email should return agent.
     */
    @Test
    public void testGetValidAgentByEmailShouldReturnAgent() {
        final Set< String > roles = new HashSet< String >();
        final ApiUser expected = new ApiUser();
        final String email = "test@a.com";
        expected.setRoles( roles );
        expected.setEmail( email );

        when( gravitasWebUtil.getAppUser() ).thenReturn( expected );

        final ApiUser actual = userRoleValidator.validateByAgentEmail( email );
        assertEquals( actual, expected );
        verify( gravitasWebUtil ).getAppUser();
    }

    /**
     * Test get valid user by email should return agent.
     */
    @Test
    public void testGetValidUserByEmailShouldReturnAgent() {
        final Set< String > roles = new HashSet< String >();
        final ApiUser expected = new ApiUser();
        final String email = "test@a.com";
        final String role = "role";
        expected.setRoles( roles );
        expected.setEmail( email );

        when( gravitasWebUtil.getAppUser() ).thenReturn( expected );

        final ApiUser actual = userRoleValidator.validateByUserEmail( email, role );
        assertEquals( actual, expected );
        verify( gravitasWebUtil ).getAppUser();
    }

    /**
     * Test validate user email should validate user by email.
     */
    @Test
    public void testValidateUserEmailShouldValidateUserByEmail() {
        final Set< String > roles = new HashSet< String >();
        final ApiUser expected = new ApiUser();
        final String email = "test@a.com";
        expected.setRoles( roles );
        expected.setEmail( email );

        when( gravitasWebUtil.getAppUser() ).thenReturn( expected );

        userRoleValidator.validateByUserEmail( email );
        verify( gravitasWebUtil ).getAppUser();
    }

    /**
     * Test get valid agent by id should throw exception.
     */
    @Test( expectedExceptions = AgentInvalidException.class )
    public void testGetValidAgentByIdShouldThrowException() {
        final Set< String > roles = new HashSet< String >();
        roles.add( "1099_AGENT" );
        final ApiUser expected = new ApiUser();
        final String appUserUid = "uid";
        final String requestUid = "requestuid";
        expected.setRoles( roles );
        expected.setUid( appUserUid );

        when( gravitasWebUtil.getAppUser() ).thenReturn( expected );

        userRoleValidator.validateByAgentId( requestUid );
        verify( gravitasWebUtil ).getAppUser();
    }

    /**
     * Test get valid agent by email should throw exception.
     */
    @Test( expectedExceptions = AgentInvalidException.class )
    public void testGetValidAgentByEmailShouldThrowException() {
        final Set< String > roles = new HashSet< String >();
        roles.add( "1099_AGENT" );
        final ApiUser expected = new ApiUser();
        final String email = "test@a.com";
        final String requestEmail = "requesttest@a.com";
        expected.setRoles( roles );
        expected.setEmail( email );

        when( gravitasWebUtil.getAppUser() ).thenReturn( expected );

        userRoleValidator.validateByAgentEmail( requestEmail );
        verify( gravitasWebUtil ).getAppUser();
    }

    /**
     * Test get valid agent by email for multiple users.
     */
    @Test
    public void testGetValidAgentByEmailForMultipleUsers() {
        final Set< String > roles = new HashSet< String >();
        roles.add( "1099_AGENT" );
        roles.add( "ADMIN" );
        final ApiUser expected = new ApiUser();
        final String email = "test@a.com";
        expected.setRoles( roles );
        expected.setEmail( email );

        when( gravitasWebUtil.getAppUser() ).thenReturn( expected );

        final ApiUser actual = userRoleValidator.validateByAgentEmail( email );
        assertEquals( actual, expected );
        verify( gravitasWebUtil ).getAppUser();
    }

}
