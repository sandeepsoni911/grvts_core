package com.owners.gravitas.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owners.gravitas.dao.GenericDao;
import com.owners.gravitas.dao.RecordFieldConfigDao;
import com.owners.gravitas.dao.RecordRoleConfigDao;
import com.owners.gravitas.dao.RecordViewConfigDao;
import com.owners.gravitas.dao.RecordViewDao;
import com.owners.gravitas.domain.entity.RecordFieldConfigEntity;
import com.owners.gravitas.domain.entity.RecordReportsNotficationEntity;
import com.owners.gravitas.domain.entity.RecordRoleConfigEntity;
import com.owners.gravitas.domain.entity.RecordViewConfigEntity;
import com.owners.gravitas.repository.RecordReportNotificationRepository;
import com.owners.gravitas.service.RecordViewService;

/**
 * Implementation class for Reporting contracts
 * 
 * @author sandeepsoni
 *
 */
@Service
public class RecordViewServiceImpl implements RecordViewService {

	@Autowired
	RecordViewDao recordViewDao;

	@Autowired
	RecordRoleConfigDao recordRoleConfigDao;

	@Autowired
	RecordViewConfigDao recordViewConfigDao;

	@Autowired
	RecordFieldConfigDao recordFieldConfigDao;
	
	@Autowired
	GenericDao genericDao;
	
	@Autowired
	RecordReportNotificationRepository recordReportNotificationRepository;

	@Override
	public List<Map<String, Object>> getRecordView(String recordViewQuery) {
		return recordViewDao.getRecordView(recordViewQuery);
	}

	@Override
	public List<RecordRoleConfigEntity> getRoleAndReportConfigList(List<String> roleList) {
		return recordRoleConfigDao.getRoleAndReportConfigList(roleList);
	}

	@Override
	public List<Map<String, Object>> getRecordViewIdAndDisplayNameList(String roleId) {
		return recordRoleConfigDao.getRecordViewIdAndDisplayNameList(roleId);
	}

	@Override
	public List<RecordViewConfigEntity> getRecordViewConfigListBasedOnRecordView(List<String> reportList) {
		return recordViewConfigDao.getRecordViewConfigListBasedOnRecordView(reportList);
	}

	@Override
	public List<RecordFieldConfigEntity> getFieldsConfigListBasedOnRecordView(List<String> reportList) {
		return recordFieldConfigDao.getFieldsConfigListBasedOnRecordView(reportList);
	}

	@Override
	public RecordViewConfigEntity getRecordViewById(String id) {
		return recordViewConfigDao.findOne(id);
	}

	@Override
	public Integer getRecordViewTotalCount(String recordViewQuery) {
		return recordViewDao.getRecordViewTotalCount(recordViewQuery);
	}

	@Override
	public List<RecordReportsNotficationEntity> getPendingNotifcationList() {
		return recordReportNotificationRepository.getPendingNotifications();
	}

	@Override
	public RecordReportsNotficationEntity saveOrUpdateRecordReportNotification(
			RecordReportsNotficationEntity recordReportsNotficationEntity) {
		return recordReportNotificationRepository.save(recordReportsNotficationEntity);
	}
	
	@Override
	public List<RecordReportsNotficationEntity> getBySavedSearchId(String savedSearchId) {
		return recordReportNotificationRepository.getBySavedSearchId(savedSearchId);
	}
	
}
