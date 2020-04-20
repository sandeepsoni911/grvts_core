package com.owners.gravitas.service;

import java.util.List;

import com.owners.gravitas.domain.entity.ZipDistance;

/**
 * The Interface AgentCoverageService.
 *
 * @author lavjeetk
 */
public interface ZipDistanceService {

    /**
     * Gets the zips within coverage area.
     *
     * @param coverage
     *            the coverage
     * @param sourceZip
     *            the source zip
     * @return the zips within coverage area
     */
    public List< ZipDistance > getZipsWithinCoverageArea( final Double coverage, final String sourceZip );

    /**
     * Update zip distance excluded.
     *
     * @param zipCode
     *            the zip code
     * @param isExclude
     *            the is exclude
     */
    public void updateZipDistanceExcluded( final String zipCode, final boolean isExclude );

}
