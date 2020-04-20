package com.owners.gravitas.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.owners.gravitas.constants.Constants;

/**
 * The utility class which helps to encode/decode URL
 *
 * @author raviz
 *
 */
public class EncoderUtil {

    /**
     *
     * @param url
     *            url to encode
     * @return encoded url
     * @throws UnsupportedEncodingException
     */
    public static String getEncodedUrl( final String url ) throws UnsupportedEncodingException {
        return URLEncoder.encode( url, Constants.UTF_8 );
    }
}
