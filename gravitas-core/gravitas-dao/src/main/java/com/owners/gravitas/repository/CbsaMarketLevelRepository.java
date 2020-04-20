package com.owners.gravitas.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owners.gravitas.domain.entity.CbsaMarketLevel;

/**
 * The Interface CbsaMarketLevelRepository.
 * 
 * @author ankusht
 */
public interface CbsaMarketLevelRepository extends JpaRepository< CbsaMarketLevel, String > {

    /**
     * Find by cbsa code.
     *
     * @param cbsaCode
     *            the cbsa code
     * @return the cbsa market level
     */
    @Query( "select cml from GR_CBSA_MARKET_LEVEL cml inner join cml.ownersMarketCbsaLabel label where label.id = :cbsaCode" )
    CbsaMarketLevel findByCbsaCode( @Param( "cbsaCode" ) String cbsaCode );

    /**
     * Find by cbsa codes.
     *
     * @param cbsaCode
     *            the cbsa code
     * @return the list
     */
    @Query( "select cml from GR_CBSA_MARKET_LEVEL cml inner join cml.ownersMarketCbsaLabel label where label.id in :cbsaCodes" )
    List< CbsaMarketLevel > findByCbsaCodes( @Param( "cbsaCodes" ) Collection< String > cbsaCodes );

    /**
     * Find threshold by cbsa code.
     *
     * @param cbsaCode
     *            the cbsa code
     * @return the int
     */
    @Query( "select cml.thresholdPeriod from GR_CBSA_MARKET_LEVEL cml inner join cml.ownersMarketCbsaLabel label where label.id = :cbsaCode" )
    int findThresholdByCbsaCode( @Param( "cbsaCode" ) String cbsaCode );
}
