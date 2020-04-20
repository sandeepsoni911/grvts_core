package com.owners.gravitas.dataprep.service.impl;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.dataprep.dao.GenericDao;
import com.owners.gravitas.dataprep.dto.AgentAssignmentDataDto;
import com.owners.gravitas.dataprep.dto.MarketDto;
import com.owners.gravitas.dataprep.service.GenericDbService;

/**
 * The Class GenericDbServiceImpl.
 * 
 * @author ankusht
 */
@Service
public class GenericDbServiceImpl implements GenericDbService {

    /** The Constant DEFAULT. */
    private static final String DEFAULT = "default";

    /** The generic dao. */
    @Autowired
    private GenericDao genericDao;

    /**
     * Find market by zip.
     *
     * @param zip
     *            the zip
     * @return the string
     */
    @Override
    @Transactional( readOnly = true )
    public String findCbsaByZip( final String zip ) {
        return genericDao.findCbsaByZip( zip );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.GenericDbService#insertData(java.lang.String)
     */
    @Override
    @Transactional
    public void insertData( final String queryString ) {
        genericDao.insertData( queryString );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.GenericDbService#findCubeRecByEmailAndZip(
     * java.lang.String, java.lang.String)
     */
    @Override
    @Transactional( readOnly = true )
    public List< Object[] > findCubeRecByEmailAndZip( final String email, final String zip ) {
        return genericDao.findCubeRecByEmailAndZip( email, zip );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.GenericDbService#findVdimRecByEmail(java.lang
     * .String)
     */
    @Override
    @Transactional( readOnly = true )
    public List< Object[] > findVdimRecByEmail( final String email ) {
        return genericDao.findVdimRecByEmail( email );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dataprep.service.GenericDbService#getAgentsByZip(java
     * .lang.String)
     */
    @Override
    @Transactional( readOnly = true )
    public List< AgentAssignmentDataDto > getAgentsByZip( final String zip ) {
        final List< Object[] > agentsByZip = genericDao.getAgentsByZip( zip );
        return agentsByZip.stream().map( a -> {
            Double score = null;
            if (a[2] != null) {
                score = ( ( BigDecimal ) a[2] ).doubleValue();
            }
            return new AgentAssignmentDataDto( a[0].toString(), a[1].toString(), score, a[3].toString(),
                    a[4].toString(), a[5].toString() );
        } ).collect( Collectors.toList() );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dataprep.service.GenericDbService#getMarket(java.lang
     * .String)
     */
    @Override
    @Transactional( readOnly = true )
    public MarketDto getMarket( final String zip ) {
        final String cbsaByZip = genericDao.findCbsaByZip( zip );
        final String cbsa = ( cbsaByZip == null || cbsaByZip.isEmpty() ) ? DEFAULT : cbsaByZip;
        final Object[] marketThresholds = genericDao.getMarketThresholdsByCbsa( cbsa );
        return new MarketDto( cbsa, intfor( marketThresholds[2] ), intfor( marketThresholds[3] ),
                intfor( marketThresholds[4] ), intfor( marketThresholds[5] ), intfor( marketThresholds[6] ),
                intfor( marketThresholds[7] ), intfor( marketThresholds[8] ), intfor( marketThresholds[9] ) );
    }

    /**
     * Intfor.
     *
     * @param object
     *            the object
     * @return the int
     */
    private int intfor( final Object object ) {
        return Integer.parseInt( object.toString() );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dataprep.service.GenericDbService#getServableZips(
     * java.lang.String)
     */
    @Override
    @Transactional( readOnly = true )
    public List< String > getServableZips( final String email ) {
        final List< String > zips = new LinkedList<>();
        final List< Object[] > zipsByEmail = genericDao.findZipsByEmail( email );
        for ( final Object arr : zipsByEmail ) {
            zips.add( arr.toString() );
        }
        return zips;
    }
}
