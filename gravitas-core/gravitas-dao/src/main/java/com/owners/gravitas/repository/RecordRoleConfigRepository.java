package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owners.gravitas.domain.entity.RecordRoleConfigEntity;

/**
 * The Interface RecordRoleConfigRepository.
 *
 * @author javeedsy
 */
public interface RecordRoleConfigRepository extends JpaRepository<RecordRoleConfigEntity, String> {

	@Query(value = "SELECT * FROM GR_RECORD_ROLE_CONFIG WHERE ROLE_ID IN (:role_id_list)", nativeQuery = true)
	List<RecordRoleConfigEntity> findByRoleIdIn(@Param(value = "role_id_list") List<String> roleIdList);

}
