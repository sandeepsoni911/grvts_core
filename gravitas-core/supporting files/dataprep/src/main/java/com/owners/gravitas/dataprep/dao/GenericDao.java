package com.owners.gravitas.dataprep.dao;

import java.util.List;

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
     * Insert data.
     *
     * @param queryString
     *            the query string
     */
    void insertData( String queryString );

    /**
     * Find cube rec by email and zip.
     *
     * @param email
     *            the email
     * @param zip
     *            the zip
     * @return the list
     */
    List< Object[] > findCubeRecByEmailAndZip( String email, String zip );

    /**
     * Find vdim rec by email.
     *
     * @param email
     *            the email
     * @return the list
     */
    List< Object[] > findVdimRecByEmail( String email );

    /**
     * Gets the agents by zip.
     *
     * @param zip
     *            the zip
     * @return the agents by zip
     */
    List< Object[] > getAgentsByZip( String zip );

    /**
     * Gets the market thresholds by cbsa.
     *
     * @param cbsa
     *            the cbsa
     * @return the market thresholds by cbsa
     */
    Object[] getMarketThresholdsByCbsa( String cbsa );

    /**
     * Find zips by email.
     *
     * @param email
     *            the email
     * @return the list
     */
    List< Object[] > findZipsByEmail( String email );
}
