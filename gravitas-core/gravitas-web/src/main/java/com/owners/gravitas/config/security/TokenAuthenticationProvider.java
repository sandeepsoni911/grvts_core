package com.owners.gravitas.config.security;

import static com.owners.gravitas.constants.Constants.FIREBASE_ACCESS_FILE;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.tasks.Task;
import com.google.firebase.tasks.Tasks;
import com.owners.gravitas.dto.ApiUser;
import com.owners.gravitas.enums.ErrorCode;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.RolePermissionService;
import com.owners.gravitas.service.UserService;

/**
 * The Class TokenAuthenticationProvider.
 */
public class TokenAuthenticationProvider implements AuthenticationProvider {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( TokenAuthenticationProvider.class );

    /** The Constant ROLE_PREFIX. */
    private static final String ROLE_PREFIX = "ROLE_";

    /** The user service. */
    private UserService userService;

    private RolePermissionService rolePermissionService;

    /**
     * Instantiates a new token authentication provider.
     *
     * @param userService
     *            the user service
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public TokenAuthenticationProvider( final UserService userService,
            final RolePermissionService rolePermissionService ) throws IOException {
        this.userService = userService;
        this.rolePermissionService = rolePermissionService;
        final FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount( new FileInputStream( FIREBASE_ACCESS_FILE ) ).build();
        FirebaseApp.initializeApp( options );
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.authentication.AuthenticationProvider#
     * authenticate(org.springframework.security.core.Authentication)
     */
    @Override
    public Authentication authenticate( final Authentication authentication ) throws AuthenticationException {
        LOGGER.debug( "User API authentication started." );
        try {
            final String token = ( String ) authentication.getPrincipal();
            final Task< FirebaseToken > authTask = FirebaseAuth.getInstance().verifyIdToken( token );

            Tasks.await( authTask );

            if (authTask.isSuccessful()) {
                final FirebaseToken decodedToken = authTask.getResult();
                final ApiUser user = new ApiUser( decodedToken.getUid(), decodedToken.getEmail() );
                // get roles from firebase users
                final Set< String > roles = userService.getRoles( user.getUid(), user.getEmail() );
                final Set< String > dbRoles = new HashSet< String >(
                        rolePermissionService.getRolesByUserEmailId( user.getEmail() ) );
                roles.addAll( dbRoles );
                final List< String > rolePermissions = new ArrayList< String >();
                if (isNotEmpty( roles )) {
                    rolePermissions
                            .addAll( rolePermissionService.getPermissionsByRoles( new ArrayList< String >( roles ) ) );
                }
                rolePermissions.addAll( rolePermissionService.getPermissionsByUserEmailId( user.getEmail() ) );
                final Set< String > permissions = new HashSet< String >( rolePermissions );
                user.setRoles( roles );
                permissions.addAll( roles );
                LOGGER.info( "User: " + user.getEmail() + " Roles: " + roles + " Permissions: " + permissions );
                return new ApiAuthenticationToken( user, createAuthorityList( permissions ) );
            } else if (authTask.getException() != null) {
                LOGGER.error( "Authentication failed: " + authTask.getException().getLocalizedMessage(),
                        authTask.getException() );
            }
            throw new BadCredentialsException( "Invalid token or token expired" );
        } catch ( final Exception ex ) {
            LOGGER.error( "Something went wrong here: " );
            throw new ApplicationException( "Problem in user authentication", ex, ErrorCode.USER_AUTHENTICATION_ERROR );
        }
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.security.authentication.AuthenticationProvider#
     * supports(java.lang.Class)
     */
    @Override
    public boolean supports( final Class< ? > authentication ) {
        return authentication.equals( PreAuthenticatedAuthenticationToken.class );
    }

    /**
     * Creates the authority list.
     *
     * @param roles
     *            the roles
     * @return the list
     */
    private List< GrantedAuthority > createAuthorityList( final Set< String > roles ) {
        final List< GrantedAuthority > authorities = new ArrayList< GrantedAuthority >( roles.size() );
        String computedRole = null;
        for ( final String role : roles ) {
            computedRole = role.toUpperCase();
            if (!role.startsWith( ROLE_PREFIX )) {
                computedRole = ROLE_PREFIX + computedRole;
            }
            authorities.add( new SimpleGrantedAuthority( computedRole ) );
        }
        return authorities;
    }

}
