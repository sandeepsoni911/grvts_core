
package com.owners.gravitas.service.task;

import static com.owners.gravitas.enums.GoogleFieldProjection.FULL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.User;

/**
 * @author madhavk
 *
 */
@Component
public class UserProfileTask {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( UserProfileTask.class );

    @Autowired
    private Directory directoryService;

    /** The email batch size. */
    @Value( "${gravitas.users.fetch.batch.size}" )
    private int emailBatchSize;

    /**
     * Gets the google profile.
     *
     * @param emails
     *            the emails
     * @return the google profile
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public List< User > getUserProfiles( final List< String > emails ) {
        LOGGER.info("Getting google users for {}", emails);
        final List< User > googleUsers = new ArrayList<>();
        if (!emails.isEmpty()) {
            try {
                for ( int i = 0; i < emails.size(); i += emailBatchSize ) {
                    final int end = Math.min( emails.size(), i + emailBatchSize );
                    final BatchRequest directoryBatch = directoryService.batch();
                    for ( final String email : emails.subList( i, end ) ) {
                        directoryService.users().get( email ).setProjection( FULL.getType() ).queue( directoryBatch,
                                new JsonBatchCallback< User >() {
                                    @Override
                                    public void onSuccess( final User user, final HttpHeaders responseHeaders )
                                            throws IOException {
                                        LOGGER.info( "Google User found for {} ", email );
                                        googleUsers.add( user );
                                    }

                                    @Override
                                    public void onFailure( final GoogleJsonError e, final HttpHeaders responseHeaders )
                                            throws IOException {
                                        LOGGER.info( "google user not found for {}, code : {}, reason : {} ", email, e.getCode(), e.toPrettyString() );
                                    }
                                } );

                    }
                    directoryBatch.execute();
                }
            } catch ( final Exception e ) {
                LOGGER.error( "Exception while getting users from google {}", emails, e );
            }
        }
        return googleUsers;
    }
}
