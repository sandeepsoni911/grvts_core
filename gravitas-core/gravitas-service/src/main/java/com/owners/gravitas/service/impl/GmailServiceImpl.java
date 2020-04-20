package com.owners.gravitas.service.impl;

import static com.google.api.services.gmail.GmailScopes.GMAIL_COMPOSE;
import static com.google.api.services.gmail.GmailScopes.GMAIL_MODIFY;
import static com.google.api.services.gmail.GmailScopes.GMAIL_SEND;
import static com.google.api.services.gmail.GmailScopes.MAIL_GOOGLE_COM;
import static com.owners.gravitas.constants.Constants.GMAIL_USER_ID;
import static com.owners.gravitas.constants.Constants.GRAVITAS;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.owners.gravitas.exception.ApplicationException;
import com.owners.gravitas.service.GmailService;

/**
 * The Class GmailServiceImpl.
 *
 * @author vishwanathm
 */
@Service
public class GmailServiceImpl extends BaseGoogleService implements GmailService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( GmailServiceImpl.class );

    /** The Constant SCOPES. */
    private static final List< String > GMAIL_SCOPES = Arrays.asList( GMAIL_SEND, MAIL_GOOGLE_COM, GMAIL_COMPOSE,
            GMAIL_MODIFY );

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.GmailService#sendEmail(com.google.api.
     * services.gmail.Gmail, java.lang.String,
     * com.google.api.services.gmail.model.Message)
     */
    @Override
    public String sendEmail( final Gmail gmailService, final Message message ) {
        try {
            final Message sentMessage = gmailService.users().messages().send( GMAIL_USER_ID, message ).execute();
            return sentMessage.getId();
        } catch ( final IOException e ) {
            throw new ApplicationException( e.getMessage(), e );
        }
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.service.GmailService#getGmailService(java.lang.
     * String)
     */
    @Override
    public Gmail getGmailService( final String agentEmail ) {
        try {
            final HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            final GoogleCredential credential = getCredential( agentEmail, HTTP_TRANSPORT, GMAIL_SCOPES );
            return new Gmail.Builder( HTTP_TRANSPORT, JSON_FACTORY, credential ).setApplicationName( GRAVITAS ).build();
        } catch ( GeneralSecurityException | IOException exp ) {
            LOGGER.error( "Problem in getting gmail service: " + exp.getLocalizedMessage(), exp );
            throw new ApplicationException( "Problem in getting gmail service: " + exp.getLocalizedMessage(), exp );
        }
    }
}
