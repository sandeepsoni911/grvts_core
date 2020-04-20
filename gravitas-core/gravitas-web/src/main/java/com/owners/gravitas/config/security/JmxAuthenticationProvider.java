package com.owners.gravitas.config.security;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.owners.gravitas.service.GenericDbService;
import com.owners.gravitas.util.EncryptDecryptUtil;

/**
 * The Class JmxAuthenticationProvider.
 * 
 * @author ankusht
 */
public class JmxAuthenticationProvider implements AuthenticationProvider {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( JmxAuthenticationProvider.class );

    /** The Constant JMX_USER_QUERY. */
    private static final String JMX_USER_QUERY = "select username, password, iv from gr_jmx_user where username=:username";

    /** The generic db service. */
    @Autowired
    private GenericDbService genericDbService;

    /*
     * (non-Javadoc)
     * @see org.springframework.security.authentication.AuthenticationProvider#
     * authenticate(org.springframework.security.core.Authentication)
     */
    @Override
    public Authentication authenticate( final Authentication auth ) throws AuthenticationException {
        final String username = auth.getName();
        final Map< String, String > params = new HashMap<>();
        params.put( "username", username );
        final List< Object[] > userDetails = genericDbService.executeQuery( JMX_USER_QUERY, params );
        if (CollectionUtils.isNotEmpty( userDetails )) {
            final String password = auth.getCredentials().toString();
            final String dbPassword = getDecryptedPasswordFromDB( userDetails );
            if (password.equals( dbPassword )) {
                LOGGER.info( "User '{}' successfully logged into JMX ", username );
                return new UsernamePasswordAuthenticationToken( username, password, Collections.emptyList() );
            } else {
                LOGGER.info( "Incorrect password provided by user '{}'", username );
                throw new BadCredentialsException( "Authentication failed" );
            }
        } else {
            LOGGER.info( "User '{}' tried logging into JMX", username );
            throw new BadCredentialsException( "Invalid username" );
        }
    }

    /**
     * Gets the decrypted password from DB.
     *
     * @param userDetails
     *            the user details
     * @return the decrypted password from DB
     */
    private String getDecryptedPasswordFromDB( final List< Object[] > userDetails ) {
        final String dbPassword = userDetails.get( 0 )[1].toString();
        final String dbIv = userDetails.get( 0 )[2].toString();
        final Map< String, String > decryptedValues = EncryptDecryptUtil.decrypt( dbPassword, dbIv );
        return decryptedValues.values().iterator().next();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.authentication.AuthenticationProvider#
     * supports(java.lang.Class)
     */
    @Override
    public boolean supports( final Class< ? > auth ) {
        return auth.equals( UsernamePasswordAuthenticationToken.class );
    }
}
