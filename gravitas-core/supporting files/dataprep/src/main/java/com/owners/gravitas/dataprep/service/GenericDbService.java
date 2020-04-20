package com.owners.gravitas.dataprep.service;

import java.util.List;

import com.owners.gravitas.dataprep.dto.AgentAssignmentDataDto;
import com.owners.gravitas.dataprep.dto.MarketDto;

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
    List< AgentAssignmentDataDto > getAgentsByZip( String zip );

    /**
     * Gets the market.
     *
     * @param zip
     *            the zip
     * @return the market
     */
    MarketDto getMarket( String zip );

    /**
     * Find servable zips by email.
     *
     * @param email
     *            the email
     * @return the list
     */
    List< String > getServableZips( String email );
}
