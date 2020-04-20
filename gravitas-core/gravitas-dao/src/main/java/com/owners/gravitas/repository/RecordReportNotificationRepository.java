package com.owners.gravitas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.owners.gravitas.domain.entity.RecordReportsNotficationEntity;
/**
 * The repository 
 * @author sandeepsoni
 *
 */

public interface RecordReportNotificationRepository extends JpaRepository<RecordReportsNotficationEntity, String>{
	
	static String PENDING_NOTIFICATION_LIST = "Select notifications from com.owners.gravitas.domain"
			+ ".entity.RecordReportsNotficationEntity notifications where notifications.status = 'PENDING'";
	

	@Query(value=PENDING_NOTIFICATION_LIST)
	List<RecordReportsNotficationEntity> getPendingNotifications();
	
	@Query(value="Select notifications from com.owners.gravitas.domain.entity.RecordReportsNotficationEntity "
			+ " notifications where notifications.savedFilterId= :savedSearchId")
	List<RecordReportsNotficationEntity> getBySavedSearchId(@Param(value = "savedSearchId") String savedSearchId);
	

}
