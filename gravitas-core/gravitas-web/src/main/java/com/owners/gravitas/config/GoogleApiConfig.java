package com.owners.gravitas.config;

import static com.google.api.services.admin.directory.DirectoryScopes.ADMIN_DIRECTORY_USER;
import static com.google.api.services.bigquery.BigqueryScopes.BIGQUERY;
import static com.owners.gravitas.constants.Constants.BIGQUERY_ACCESS_FILE;
import static com.owners.gravitas.constants.Constants.GRAVITAS;
import static com.owners.gravitas.constants.Constants.GRAVITAS_CONFIG_DIR;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.bigquery.Bigquery;

/**
 * The Class GoogleApiConfig.
 *
 * @author harshads
 */
@Configuration
public class GoogleApiConfig {

    /** The gravitas service account. */
    @Value( "${gravitas.service.account.id}" )
    private String serviceAccountId;

    @Value( "${gravitas.service.account.user}" )
    private String serviceAccountUser;

    /**
     * Setup directory service.
     *
     * @return the directory
     * @throws GeneralSecurityException
     *             the general security exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Bean( name = "directoryService" )
    public Directory setupDirectoryService() throws GeneralSecurityException, IOException {
        final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        final HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final GoogleCredential credential = new GoogleCredential.Builder().setTransport( HTTP_TRANSPORT )
                .setJsonFactory( JSON_FACTORY ).setServiceAccountId( serviceAccountId )
                .setServiceAccountPrivateKeyFromP12File( new File( GRAVITAS_CONFIG_DIR, "google-api-access.p12" ) )
                .setServiceAccountScopes( Collections.singleton( ADMIN_DIRECTORY_USER ) )
                .setServiceAccountUser( serviceAccountUser ).build();
        return new Directory.Builder( HTTP_TRANSPORT, JSON_FACTORY, credential ).setApplicationName( GRAVITAS ).build();
    }

    /**
     * Setup bigquery service.
     *
     * @return the bigquery
     * @throws GeneralSecurityException
     *             the general security exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Bean( name = "bigqueryService" )
    public Bigquery setupBigqueryService() throws GeneralSecurityException, IOException {
        final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        final HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final GoogleCredential credential = GoogleCredential.fromStream( new FileInputStream( BIGQUERY_ACCESS_FILE ) )
                .createScoped( Collections.singleton( BIGQUERY ) );
        return new Bigquery.Builder( HTTP_TRANSPORT, JSON_FACTORY, credential ).setApplicationName( GRAVITAS ).build();
    }
}
