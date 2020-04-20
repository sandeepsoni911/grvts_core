package com.owners.gravitas.util;

import static com.owners.gravitas.constants.Constants.GRAVITAS;

import org.mockito.InjectMocks;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.ApiUser;
import com.owners.gravitas.exception.UserNotLoggedInException;

/**
 * Test class for AdminWebUtil
 *
 * @author suyogn
 *
 */
public class GravitasWebUtilTest extends AbstractBaseMockitoTest {

    @InjectMocks
    private GravitasWebUtil webUtil;

    /**
     * Test get app user.
     */
    @Test
    public void testGetAppUser() {
        ApiUser user = new ApiUser( "test", "test@test.com" );
        Authentication auth = new UsernamePasswordAuthenticationToken( user, null );
        SecurityContextHolder.getContext().setAuthentication( auth );
        Assert.assertEquals( "test", webUtil.getAppUser().getUid() );
    }

    /**
     * Test get app user when user not assignable form.
     */
    @Test( expectedExceptions = UserNotLoggedInException.class )
    public void testGetAppUserWithException() {
        Authentication auth = new UsernamePasswordAuthenticationToken( null, null );
        SecurityContextHolder.getContext().setAuthentication( auth );
        webUtil.getAppUser();
    }
    
    @Test( expectedExceptions = UserNotLoggedInException.class )
    public void testGetAppUserWithException2() {
        Authentication auth = new UsernamePasswordAuthenticationToken( new String(), null );
        SecurityContextHolder.getContext().setAuthentication( auth );
        webUtil.getAppUser();
    }

    /**
     * Test get app user email.
     */
    @Test
    public void testGetAppUserEmail() {
        ApiUser user = new ApiUser( "test", "test@test.com" );
        Authentication auth = new UsernamePasswordAuthenticationToken( user, null );
        SecurityContextHolder.getContext().setAuthentication( auth );
        Assert.assertEquals( "test@test.com", webUtil.getAppUserEmail() );
    }
    
    /**
     * Test get app user email.
     */
    @Test
    public void testGetAppUserEmail2() {
        Authentication auth = new UsernamePasswordAuthenticationToken( null, null );
        SecurityContextHolder.getContext().setAuthentication( auth );
        Assert.assertEquals( GRAVITAS, webUtil.getAppUserEmail() );
    }

}
