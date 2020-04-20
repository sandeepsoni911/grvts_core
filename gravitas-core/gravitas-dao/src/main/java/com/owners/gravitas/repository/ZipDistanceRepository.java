package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.owners.gravitas.domain.entity.ZipDistance;

public interface ZipDistanceRepository extends JpaRepository< ZipDistance, String > {

	@Modifying
	@Query("UPDATE GR_ZIP_DISTANCE zd SET zd.excluded = ?2 WHERE zd.destinationZip = ?1")
	public void updateZipDistanceExcluded(final String zipCode, final boolean excluded);

	public List<ZipDistance> findByMilesLessThanEqualAndSourceZipAndExcludedFalse(Double coverage, String sourceZip);
	
}
	
