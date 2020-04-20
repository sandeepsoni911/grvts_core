package com.owners.gravitas.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import com.owners.gravitas.dao.helper.BasicRestAuthenticator;
import com.owners.gravitas.dao.helper.FirebaseRestAuthenticator;

/**
 * The Class BaseDaoImpl.
 *
 * @author vishwanathm
 */
public class BaseDaoImpl {
    /** The firebase host. */
    @Value( value = "${firebase.host.url}" )
    protected String firebaseHost;

    /** The rest template. */
    @Autowired
    protected RestTemplate restTemplate;

    /** The authenticator. */
    @Autowired
    protected FirebaseRestAuthenticator authenticator;

    /** The basic authenticator. */
    @Autowired
    protected BasicRestAuthenticator basicAuthenticator;

    /**
     * Gets the firebase host.
     *
     * @return the firebase host
     */
    public String getFirebaseHost() {
        return firebaseHost;
    }

    /**
     * Gets the basic authenticator.
     *
     * @return the basic authenticator
     */
    public BasicRestAuthenticator getBasicAuthenticator() {
        return basicAuthenticator;
    }

    /**
     * Gets the rest template.
     *
     * @return the rest template
     */
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    /**
     * Gets the authenticator.
     *
     * @return the authenticator
     */
    public FirebaseRestAuthenticator getAuthenticator() {
        return authenticator;
    }

    /**
     * Builds the firebase url.
     *
     * @param url
     *            the url
     * @return the string
     */
    public String buildFirebaseURL( final String url ) {
        return firebaseHost + url + ".json";
    }

    /**
     * Builds the firebase url.
     *
     * the url
     *
     * @return the string
     */
    public String buildFirebaseQueryURL( final String queryStr ) {
        return firebaseHost + queryStr;
    }
}
