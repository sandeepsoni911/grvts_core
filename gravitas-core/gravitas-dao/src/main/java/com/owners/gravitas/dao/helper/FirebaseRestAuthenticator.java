package com.owners.gravitas.dao.helper;

import static com.owners.gravitas.constants.Constants.FIREBASE_ACCESS_FILE;
import static com.owners.gravitas.enums.ErrorCode.INTERNAL_SERVER_ERROR;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.owners.gravitas.dto.FirebaseAccess;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class FirebaseRestAuthenticator make connection to Firebase
 * and authenticates the user with provided credential details.
 *
 * @author manishd
 */
@Service( "firebaseRestAuthenticator" )
public class FirebaseRestAuthenticator {
    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( FirebaseRestAuthenticator.class );

    /** The Constant TOKEN_REFRESH_THRESHOLD. */
    @Value( "${firebace.token.refresh.threshold:2000}" )
    private int tokenRefreshThreshold = 2000;

    /** The scoped. */
    private GoogleCredential scoped;

    /**
     * Inits the connection.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @PostConstruct
    public void initConnection() throws IOException {
        GoogleCredential googleCred = GoogleCredential.fromStream( new FileInputStream( FIREBASE_ACCESS_FILE ) );
        scoped = googleCred.createScoped( Arrays.asList( "https://www.googleapis.com/auth/firebase.database",
                "https://www.googleapis.com/auth/userinfo.email" ) );
        scoped.refreshToken();
    }

    /**
     * This method authenticates to firebase and gets the {@link FirebaseAccess}
     * with credential details.
     *
     * @return the Firebase access response
     */
    public FirebaseAccess authenticate() {
        if (isRefreshRequired()) {
            try {
                LOGGER.debug( "Refresh firebase token." );
                scoped.refreshToken();
            } catch ( IOException e ) {
                throw new ApplicationException( e.getLocalizedMessage(), e, INTERNAL_SERVER_ERROR );
            }
        }
        FirebaseAccess access = new FirebaseAccess();
        access.setAccessToken( scoped.getAccessToken() );
        return access;
    }

    /**
     * Checks if is refresh required.
     *
     * @return true, if is refresh required
     */
    private boolean isRefreshRequired() {
        return ( scoped.getExpirationTimeMilliseconds() - ( new Date().getTime() ) ) < tokenRefreshThreshold;
    }
}
