package com.owners.gravitas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.owners.gravitas.domain.entity.ZipDistance;
import com.owners.gravitas.repository.ZipDistanceRepository;
import com.owners.gravitas.service.ZipDistanceService;

/**
 * The Class ZipDistanceServiceImpl.
 *
 * @author lavjeetk
 */
@Service
@Transactional
public class ZipDistanceServiceImpl implements ZipDistanceService {

	/** The zip distance repository. */
	@Autowired
	ZipDistanceRepository zipDistanceRepository;

	/* (non-Javadoc)
	 * @see com.owners.gravitas.service.ZipDistanceService#getZipsWithinCoverageArea(java.lang.Double, java.lang.String)
	 */
	@Override
	public List<ZipDistance> getZipsWithinCoverageArea(final Double coverage, final String sourceZip) {
		return zipDistanceRepository.findByMilesLessThanEqualAndSourceZipAndExcludedFalse(coverage, sourceZip);
	}

	/* (non-Javadoc)
	 * @see com.owners.gravitas.service.ZipDistanceService#updateZipDistanceExcluded(java.lang.String, boolean)
	 */
	@Override
	public void updateZipDistanceExcluded(String zipCode, boolean isExclude) {
		zipDistanceRepository.updateZipDistanceExcluded(zipCode, isExclude);
	}

}