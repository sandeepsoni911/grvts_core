package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owners.gravitas.domain.entity.RecordFieldConfigEntity;

/**
 * The Interface RecordFieldConfigRepository.
 *
 * @author javeedsy
 */
public interface RecordFieldConfigRepository extends JpaRepository<RecordFieldConfigEntity, String> {

	@Query(value = "SELECT * FROM GR_RECORD_FIELD_CONFIG WHERE REC_VIEW_CONFIG_ID IN (:report_id_list)", nativeQuery = true)
	public List<RecordFieldConfigEntity> findByRecordViewConfigIdIn(
			@Param(value = "report_id_list") List<String> reportList);

}
