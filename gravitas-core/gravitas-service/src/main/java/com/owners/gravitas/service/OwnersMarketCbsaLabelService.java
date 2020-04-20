package com.owners.gravitas.service;

import com.owners.gravitas.domain.entity.OwnersMarketCbsaLabel;

/**
 * The Interface OwnersMarketCbsaLabelService.
 * 
 * @author ankusht
 */
public interface OwnersMarketCbsaLabelService {

    /**
     * Find by zip.
     *
     * @param zip
     *            the zip
     * @return the owners market cbsa label
     */
    OwnersMarketCbsaLabel findByZip( String zip );
}
