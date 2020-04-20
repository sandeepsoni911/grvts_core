package com.owners.gravitas.dao;

import java.util.List;

import com.owners.gravitas.domain.entity.RecordFieldConfigEntity;

/**
 * Interface to have all record view field config contracts
 * 
 * @author javeedsy
 *
 */
public interface RecordFieldConfigDao {

	List<RecordFieldConfigEntity> getFieldsConfigListBasedOnRecordView(List<String> reportList);

}
