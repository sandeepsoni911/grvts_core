package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owners.gravitas.domain.entity.RecordViewSavedFilterEntity;


/**
 * The Interface UserRepository.
 *
 * @author sonisan
 */
public interface SavedFilterRepository extends JpaRepository< RecordViewSavedFilterEntity, String > {


    @Query(value = "SELECT * FROM GR_RECORD_SAVED_FILTERS WHERE IS_DELETED=false AND USER_ID = :user_id order by filter_name asc", nativeQuery = true)
	public List<RecordViewSavedFilterEntity> findFiltersByUserId(
			@Param(value = "user_id") String usewr_id);
}
