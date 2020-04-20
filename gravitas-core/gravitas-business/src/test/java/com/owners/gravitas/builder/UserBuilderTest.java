package com.owners.gravitas.builder;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.owners.gravitas.business.builder.UserBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.Role;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.dto.request.AgentOnboardRequest;
import com.owners.gravitas.service.RoleMemberService;
import com.owners.gravitas.service.UserService;

/**
 * The Class UserBuilderTest.
 *
 * @author pabhishek
 */
public class UserBuilderTest extends AbstractBaseMockitoTest {

    /** The user builder. */
    @InjectMocks
    private UserBuilder userBuilder;

    /** The role member service. */
    @Mock
    RoleMemberService roleMemberService;

    /** The user service. */
    @Mock
    UserService userService;

    /**
     * Test convert from.
     */
    @Test
    public void testConvertFrom() {
        Mockito.when( roleMemberService.findByName( Mockito.anyString() ) ).thenReturn( new Role() );
        Mockito.when( roleMemberService.save( new Role() ) ).thenReturn( new Role() );
        Mockito.when( userService.save( Mockito.any( User.class ) ) ).thenReturn( new User() );
        final AgentOnboardRequest request = new AgentOnboardRequest();
        request.setEmail( "test1.user1@gmail.com" );
        request.setRoleId( "test2.user2@gmail.com" );
        request.setStatus( "testStatus" );
        User user = userBuilder.convertFrom( request );
        Assert.assertNotNull( user );
    }

    /**
     * Test convert from source as null.
     */
    @Test
    public void testConvertFromSourceAsNull() {
        User user = userBuilder.convertFrom( null, new User() );
        Assert.assertNotNull( user );
    }

    /**
     * Test convert from dest as null.
     */
    @Test
    public void testConvertFromDestAsNull() {
        Mockito.when( roleMemberService.findByName( Mockito.anyString() ) ).thenReturn( new Role() );
        Mockito.when( roleMemberService.save( new Role() ) ).thenReturn( new Role() );
        Mockito.when( userService.save( Mockito.any( User.class ) ) ).thenReturn( new User() );
        final AgentOnboardRequest request = new AgentOnboardRequest();
        request.setEmail( "test1.user1@gmail.com" );
        request.setRoleId( "test2.user2@gmail.com" );
        request.setStatus( "testStatus" );
        User user = userBuilder.convertFrom( request, null );
        Assert.assertNotNull( user );
    }

    /**
     * Testconvert to.
     */
    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void testconvertTo() {
        userBuilder.convertTo( new User() );
    }
}
