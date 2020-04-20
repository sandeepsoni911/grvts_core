package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owners.gravitas.domain.entity.RecordViewConfigEntity;

/**
 * The Interface RecordViewConfigRepository.
 *
 * @author javeedsy
 */
public interface RecordViewConfigRepository extends JpaRepository<RecordViewConfigEntity, String> {

	@Query(value = "SELECT * FROM GR_RECORD_VIEW_CONFIG WHERE ID in (:report_id_list)", nativeQuery = true)
	public List<RecordViewConfigEntity> findByIdIn(@Param(value = "report_id_list") List<String> reportList);

}
