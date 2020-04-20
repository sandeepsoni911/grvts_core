package com.owners.gravitas.util;

import static com.owners.gravitas.constants.Constants.BASIC;
import static com.owners.gravitas.constants.Constants.BEARER;
import static com.owners.gravitas.constants.Constants.SF_AUTO_ASSIGN_HEADER;
import static java.lang.Boolean.FALSE;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * The Class RestUtil.
 *
 * @author vishwanathm
 */
public class RestUtil {

    /**
     * Instantiates a new CRM util.
     */
    private RestUtil() {
    }

    /**
     * Builds the request.
     *
     * @param userId
     *            the user id
     * @param body
     *            the request body
     * @return the http entity
     */
    public static HttpEntity< Object > buildRequest( final HttpHeaders headers, final Object body ) {
        return new HttpEntity< Object >( body, headers );
    }

    /**
     * Creates the http header.
     *
     * @param accessTokenRes
     *            the access token res
     * @return the http headers
     */
    public static HttpHeaders createHttpHeader( final String accessToken ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE );
        headers.add( HttpHeaders.AUTHORIZATION, BEARER + accessToken );
        return headers;
    }

    /**
     * Creates the http header.
     *
     * @param accessToken
     *            the access token
     * @param autoAssign
     *            the auto assign
     * @return the http headers
     */
    public static HttpHeaders createHttpHeader( final String accessToken, final boolean autoAssign ) {
        final HttpHeaders headers = createHttpHeader( accessToken );
        if (!autoAssign) {
            headers.add( SF_AUTO_ASSIGN_HEADER, FALSE.toString() );
        }
        return headers;
    }

    /**
     * Creates the basic http header.
     *
     * @param accessTokenRes
     *            the access token res
     * @return the http headers
     */
    public static HttpHeaders createBasicHttpHeader( final String accessToken ) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE );
        headers.add( HttpHeaders.AUTHORIZATION, BASIC + accessToken );
        return headers;
    }
}
