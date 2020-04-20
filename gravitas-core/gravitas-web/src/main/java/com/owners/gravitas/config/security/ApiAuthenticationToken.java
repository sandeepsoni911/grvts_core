package com.owners.gravitas.config.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import com.owners.gravitas.dto.ApiUser;

/**
 * The Class ApiAuthenticationToken.
 */
public class ApiAuthenticationToken extends AbstractAuthenticationToken {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3583354466437433906L;

    /** The authenticated user. */
    private ApiUser user;

    /**
     * Instantiates a new authorization token.
     *
     * @param principalToken
     *            the principal token
     */
    public ApiAuthenticationToken() {
        super( Arrays.asList() );
    }

    /**
     * Instantiates a new authentication token.
     *
     * @param principalToken
     *            the principal token
     * @param user
     *            the user
     */
    public ApiAuthenticationToken( ApiUser user, Collection<? extends GrantedAuthority> authorities ) {
        super( authorities );
        this.user = user;
        this.setAuthenticated( true );
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.core.Authentication#getCredentials()
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.core.Authentication#getPrincipal()
     */
    @Override
    public Object getPrincipal() {
        return user;
    }

}
