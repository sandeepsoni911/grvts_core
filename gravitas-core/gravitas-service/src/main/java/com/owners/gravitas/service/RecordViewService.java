package com.owners.gravitas.service;

import java.util.List;
import java.util.Map;

import com.owners.gravitas.domain.entity.RecordFieldConfigEntity;
import com.owners.gravitas.domain.entity.RecordReportsNotficationEntity;
import com.owners.gravitas.domain.entity.RecordRoleConfigEntity;
import com.owners.gravitas.domain.entity.RecordViewConfigEntity;

/**
 * This interface will expose all contracts related to reporting
 * 
 * @author sandeepsoni
 *
 */
public interface RecordViewService {

	List<Map<String, Object>> getRecordView(String recordViewQuery);

	List<RecordRoleConfigEntity> getRoleAndReportConfigList(List<String> roleList);

	List<Map<String, Object>> getRecordViewIdAndDisplayNameList(String roleId);

	List<RecordViewConfigEntity> getRecordViewConfigListBasedOnRecordView(List<String> reportList);

	List<RecordFieldConfigEntity> getFieldsConfigListBasedOnRecordView(List<String> reportList);

	RecordViewConfigEntity getRecordViewById(String id);
	/**
	 * To Get Total count for query
	 * @param recordViewQuery
	 * @return
	 */
	Integer getRecordViewTotalCount(String recordViewQuery);
	
	/**
	 * To get pending notifications
	 * @return
	 */
	List<RecordReportsNotficationEntity> getPendingNotifcationList();
	
	/**
	 * To save or Update RecordReportsNotficationEntity
	 * @param recordReportsNotficationEntity
	 * @return {@link RecordReportsNotficationEntity}
	 */
	RecordReportsNotficationEntity saveOrUpdateRecordReportNotification(RecordReportsNotficationEntity recordReportsNotficationEntity);
	/**
	 * To get ReportsNotification by savedSearch id
	 * @param recordReportsNotficationEntity
	 * @return
	 */
	List<RecordReportsNotficationEntity> getBySavedSearchId(String savedFilterId);

}
