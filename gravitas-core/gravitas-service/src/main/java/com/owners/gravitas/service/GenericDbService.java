package com.owners.gravitas.service;

import java.util.List;
import java.util.Map;

/**
 * The Interface GenericDbService - service layer to fetch data from tables not
 * having their entities..
 * 
 * @author ankusht
 */
public interface GenericDbService {

    /**
     * Find market by zip.
     *
     * @param zip
     *            the zip
     * @return the string
     */
    String findCbsaByZip( String zip );

    /**
     * Execute query.
     *
     * @param queryStr
     *            the query str
     * @param params
     *            the params
     * @return the list
     */
    List< Object[] > executeQuery( String queryStr, Map< String, String > params );
}
