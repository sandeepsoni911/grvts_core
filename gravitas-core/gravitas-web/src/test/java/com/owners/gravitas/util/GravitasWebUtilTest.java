package com.owners.gravitas.util;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.ApiUser;
import com.owners.gravitas.exception.UserNotLoggedInException;

/**
 * The Class GravitasWebUtilTest.
 *
 * @author vishwanathm
 */
public class GravitasWebUtilTest extends AbstractBaseMockitoTest {

    /** The gravitas web util. */
    @InjectMocks
    private GravitasWebUtil gravitasWebUtil;

    /**
     * Test get app user.
     */
    @Test
    public void testGetAppUser() {
        final List< GrantedAuthority > authorities = new ArrayList< GrantedAuthority >();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority( "ROLE1" );
        authorities.add( grantedAuthority );
        ApiUser user = new ApiUser( "test", "" );
        Authentication auth = new UsernamePasswordAuthenticationToken( user, null );
        SecurityContextHolder.getContext().setAuthentication( auth );
        ApiUser user1 = gravitasWebUtil.getAppUser();
        Assert.assertEquals( "test", user1.getUid() );
    }

    /**
     * Test get app user when user not login.
     */
    @Test( expectedExceptions = UserNotLoggedInException.class )
    public void testGetAppUserWhenUserNotLogin() {
        Authentication auth = new UsernamePasswordAuthenticationToken( null, null );
        SecurityContextHolder.getContext().setAuthentication( auth );
        gravitasWebUtil.getAppUser();
    }

    /**
     * Test get app user when user not assignablr form.
     */
    @Test( expectedExceptions = UserNotLoggedInException.class )
    public void testGetAppUserWhenUserNotAssignablrForm() {
        final List< GrantedAuthority > authorities = new ArrayList< GrantedAuthority >();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority( "ROLE1" );
        authorities.add( grantedAuthority );
        User user = new User( "test", "****", authorities );
        Authentication auth = new UsernamePasswordAuthenticationToken( user, null );
        SecurityContextHolder.getContext().setAuthentication( auth );
        gravitasWebUtil.getAppUser();

    }
}
