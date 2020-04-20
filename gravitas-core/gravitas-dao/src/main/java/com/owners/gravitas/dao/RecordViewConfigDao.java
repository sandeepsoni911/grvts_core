package com.owners.gravitas.dao;

import java.util.List;

import com.owners.gravitas.domain.entity.RecordViewConfigEntity;

/**
 * Interface to have all record view config contracts
 * 
 * @author javeedsy
 *
 */
public interface RecordViewConfigDao {

	RecordViewConfigEntity findOne(String id);

	List<RecordViewConfigEntity> getRecordViewConfigListBasedOnRecordView(List<String> reportList);
}
