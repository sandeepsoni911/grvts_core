package com.owners.gravitas.dao;

import java.util.List;
import java.util.Map;

/**
 * The Interface GenericDao - to be used to fetch data from tables for which
 * entity do not exist.
 * 
 * @author ankusht
 */
public interface GenericDao {

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
