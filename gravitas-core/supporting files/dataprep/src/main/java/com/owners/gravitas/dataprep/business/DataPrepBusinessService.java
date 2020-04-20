package com.owners.gravitas.dataprep.business;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.owners.gravitas.dataprep.dto.AgentAssignmentDataDto;
import com.owners.gravitas.dataprep.dto.MarketDto;
import com.owners.gravitas.dataprep.service.GenericDbService;

/**
 * The Class DataPrepBusinessService.
 * 
 * @author ankusht
 */
@Component
public class DataPrepBusinessService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( DataPrepBusinessService.class );

    /** The data insert in cube. */
    @Value( "${data.cube.insert}" )
    private String dataInsertInCube;

    /** The data insert in cube. */
    @Value( "${data.vdim.insert}" )
    private String dataInsertInVdim;

    /** The generic db service. */
    @Autowired
    private GenericDbService genericDbService;

    /**
     * Map agents to zips.
     *
     * @param dataDtos
     *            the data dtos
     */
    public void mapAgentsToZips( final List< AgentAssignmentDataDto > dataDtos ) {
        dataDtos.forEach( dto -> {
            final String email = dto.getEmail();
            final String zip = dto.getZip();
            final String name = dto.getName();
            final String state = dto.getState();
            final String homeZip = dto.getHomeZip();
            final double score = dto.getScore();
            LOGGER.info( "Record: " + dto );

            final List< Object[] > cubeRecs = genericDbService.findCubeRecByEmailAndZip( email, zip );
            if (CollectionUtils.isEmpty( cubeRecs )) {
                final String queryString = String.format( dataInsertInCube, name, email, state, homeZip, zip );
                genericDbService.insertData( queryString );
                LOGGER.info( "Added to CUBE, Record: " + dto );
            }

            final List< Object[] > vdimRecs = genericDbService.findVdimRecByEmail( email );
            if (CollectionUtils.isEmpty( vdimRecs )) {
                final String queryString = String.format( dataInsertInVdim, state, name, email, score );
                genericDbService.insertData( queryString );
                LOGGER.info( "Added to V_DIM, Record: " + dto );
            }
        } );
    }

    /**
     * Gets the agents by zip.
     *
     * @param zip
     *            the zip
     * @return the agents by zip
     */
    public List< AgentAssignmentDataDto > getAgentsByZip( final String zip ) {
        return genericDbService.getAgentsByZip( zip );
    }

    /**
     * Gets the market.
     *
     * @param zip
     *            the zip
     * @return the market
     */
    public MarketDto getMarket( final String zip ) {
        return genericDbService.getMarket( zip );
    }

    /**
     * Gets the servable zips.
     *
     * @param email
     *            the email
     * @return the servable zips
     */
    public List< String > getServableZips( final String email ) {
        final List< String > list = genericDbService.getServableZips( email );
        Collections.sort( list );
        return list;
    }
}
