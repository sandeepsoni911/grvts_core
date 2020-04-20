package com.owners.gravitas.audit;

import static com.owners.gravitas.constants.Constants.GRAVITAS;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.dto.ApiUser;

/**
 * The Class AuditorTest.
 */
public class AuditorTest extends AbstractBaseMockitoTest {

    /** The auditor. */
    @InjectMocks
    private Auditor auditor;

    /**
     * Test get current auditor.
     */
    @Test
    private void testGetCurrentAuditor() {
        final List< GrantedAuthority > authorities = new ArrayList< GrantedAuthority >();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority( "ROLE1" );
        authorities.add( grantedAuthority );
        ApiUser user = new ApiUser( "test", "test@test.com" );
        Authentication auth = new UsernamePasswordAuthenticationToken( user, null );
        SecurityContextHolder.getContext().setAuthentication( auth );
        Assert.assertEquals( auditor.getCurrentAuditor(), "test@test.com" );
    }

    /**
     * Test get current auditor when user not assignable form.
     */
    @Test
    private void testGetCurrentAuditorWhenUserNotAssignableForm() {
        final List< GrantedAuthority > authorities = new ArrayList< GrantedAuthority >();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority( "ROLE1" );
        authorities.add( grantedAuthority );
        User user = new User( "test", "***", authorities );
        Authentication auth = new UsernamePasswordAuthenticationToken( user, null );
        SecurityContextHolder.getContext().setAuthentication( auth );
        Assert.assertNotNull( auditor.getCurrentAuditor() );
    }

    /**
     * Test get current auditor when user not assignable form.
     */
    @Test
    private void testGetCurrentAuditorWhenUserNotAssignableForm1() {
        final List< GrantedAuthority > authorities = new ArrayList< GrantedAuthority >();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority( "ROLE1" );
        authorities.add( grantedAuthority );
        Authentication auth = new UsernamePasswordAuthenticationToken( new Object(), null );
        SecurityContextHolder.getContext().setAuthentication( auth );
        Assert.assertNotNull( auditor.getCurrentAuditor() );
    }

    /**
     * Test get current auditor for anonymous user.
     */
    @Test
    private void testGetCurrentAuditorForANONYMOUSUser() {
        SecurityContextHolder.getContext().setAuthentication( null );
        RequestContextHolder.setRequestAttributes( null );
        Assert.assertEquals( auditor.getCurrentAuditor(), GRAVITAS );
    }
}
