package com.owners.gravitas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owners.gravitas.domain.entity.OwnersMarketCbsaLabel;

/**
 * The Interface OwnersMarketCbsaLabelRepository.
 * 
 * @author ankusht
 */
public interface OwnersMarketCbsaLabelRepository extends JpaRepository< OwnersMarketCbsaLabel, String > {

    /**
     * Find by zip.
     *
     * @param zip
     *            the zip
     * @return the owners market cbsa label
     */
    @Query( nativeQuery = true, value = "select l.* from GR_OWNERS_MARKET_CBSA_LABEL l inner join "
            + " me_zipcbsa mez on mez.cbsa=l.id where mez.zip=:zip" )
    OwnersMarketCbsaLabel findByZip( @Param( "zip" ) String zip );
}
