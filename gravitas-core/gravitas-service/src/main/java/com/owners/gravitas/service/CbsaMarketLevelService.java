package com.owners.gravitas.service;

import java.util.Collection;
import java.util.List;

import com.owners.gravitas.domain.entity.CbsaMarketLevel;

/**
 * The Interface CbsaMarketLevelService.
 * 
 * @author ankusht
 */
public interface CbsaMarketLevelService {

    /**
     * Find by cbsa code.
     *
     * @param cbsaCode
     *            the cbsa code
     * @return the cbsa market level
     */
    CbsaMarketLevel findByCbsaCode( String cbsaCode );

    /**
     * Find by cbsa code.
     *
     * @param cbsaCodes
     *            the cbsa codes
     * @return the cbsa market level
     */
    List< CbsaMarketLevel > findByCbsaCodes( Collection< String > cbsaCodes );

    /**
     * Find threshold by cbsa code.
     *
     * @param cbsaCode
     *            the cbsa code
     * @return the int
     */
    int findThresholdByCbsaCode( String cbsaCode );
}
