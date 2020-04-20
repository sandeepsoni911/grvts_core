package com.owners.gravitas.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * QueryParams class holds the query parameters.
 *
 * @author amits
 */
public class QueryParams {

    /** The params. */
    private Map< String, String > params = new HashMap<>();

    /**
     * Adds the param.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     */
    public void add( final String key, final String value ) {
        params.put( key, value );
    }

    /**
     * Gets the params.
     *
     * @return the params
     */
    public Map< String, String > getParams() {
        return params;
    }
}
