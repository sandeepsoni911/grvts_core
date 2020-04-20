package com.owners.gravitas.util;

import static com.owners.gravitas.constants.Constants.GRAVITAS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.owners.gravitas.dto.ApiUser;
import com.owners.gravitas.exception.UserNotLoggedInException;

/**
 * This class has utility methods for web layer.
 */
@Component( "gravitasWebUtil" )
public class GravitasWebUtil {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( GravitasWebUtil.class );

    /**
     * This method provide the logged in user instance.
     *
     * @return instance of {@link AppUser}
     */
    public ApiUser getAppUser() {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null && ApiUser.class.isAssignableFrom( principal.getClass() )) {
            return ( ApiUser ) principal;
        } else {
            throw new UserNotLoggedInException( "User not logged in" );
        }
    }

    /**
     * Gets the app user email.
     *
     * @return the app user email
     */
    public String getAppUserEmail() {
        try {
            return getAppUser().getEmail();
        } catch ( final UserNotLoggedInException unle ) {
            LOGGER.error( "IGNORE: Problem in getting app user ", unle );
            return GRAVITAS;
        }
    }

}
