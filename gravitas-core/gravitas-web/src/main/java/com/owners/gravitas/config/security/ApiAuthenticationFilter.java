/*
 *
 */
package com.owners.gravitas.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * This http filter stand before basic authenticationfilter in spring security
 * filter chain to validate the API requests.
 */
public class ApiAuthenticationFilter extends OncePerRequestFilter {

    /** The Constant TOKEN_KEY. */
    private static final String TOKEN_KEY = "Token ";

    /** The authentication manager. */
    private AuthenticationManager authenticationManager;

    /**
     * Instantiates a new api authentication filter.
     *
     * @param authenticationManager
     *            the authentication manager
     */
    public ApiAuthenticationFilter( AuthenticationManager authenticationManager ) {
        this.authenticationManager = authenticationManager;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(
     * javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
     */
    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain )
            throws ServletException, IOException {
        try {
            String authToken = request.getHeader( HttpHeaders.AUTHORIZATION );
            processTokenAuthentication( authToken );
            filterChain.doFilter( request, response );
        } catch ( AuthenticationException authenticationException ) {
            SecurityContextHolder.clearContext();
            response.sendError( HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage() );
        }
    }

    /**
     * Process token authentication.
     *
     * @param authToken
     *            the auth token
     */
    private void processTokenAuthentication( String authToken ) {
        if (authToken == null || !authToken.startsWith( TOKEN_KEY )) {
            throw new InsufficientAuthenticationException( "Invalid token: " + authToken );
        }
        String tokenValue = authToken.substring( authToken.indexOf( TOKEN_KEY ) + TOKEN_KEY.length() );
        PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken( tokenValue,
                null );
        Authentication responseAuthentication = authenticationManager.authenticate( requestAuthentication );
        if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
            throw new BadCredentialsException( "Unable to authenticate Domain User for provided token" );
        }
        logger.debug( "User successfully authenticated" );
        SecurityContextHolder.getContext().setAuthentication( responseAuthentication );
    }

}
