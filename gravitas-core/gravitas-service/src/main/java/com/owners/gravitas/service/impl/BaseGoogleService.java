package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.GOOGLE_ACCESS_FILE;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.owners.gravitas.exception.ApplicationException;

/**
 * The Class BaseGoogleService.
 *
 * @author shivamm
 */
public abstract class BaseGoogleService {
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( BaseGoogleService.class );

    /** The gravitas service account. */
    @Value( "${gravitas.service.account.id}" )
    private String serviceAccountId;

    /** The Constant JSON_FACTORY. */
    protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Gets the credential.
     *
     * @param agentEmail
     *            the agent email
     * @param HTTP_TRANSPORT
     *            the http transport
     * @return the credential
     */
    protected GoogleCredential getCredential( final String agentEmail, final HttpTransport HTTP_TRANSPORT,
            final List< String > scopes ) {
        try {
            return new GoogleCredential.Builder().setTransport( HTTP_TRANSPORT ).setJsonFactory( JSON_FACTORY )
                    .setServiceAccountId( serviceAccountId )
                    .setServiceAccountPrivateKeyFromP12File( new File( GOOGLE_ACCESS_FILE ) )
                    .setServiceAccountScopes( scopes ).setServiceAccountUser( agentEmail ).build();
        } catch ( GeneralSecurityException | IOException exp ) {
            LOGGER.error( "Problem in authentication agent for google apis: " + exp.getLocalizedMessage(), exp );
            throw new ApplicationException(
                    "Problem in authentication agent for google apis: " + exp.getLocalizedMessage(), exp );
        }
    }
}
