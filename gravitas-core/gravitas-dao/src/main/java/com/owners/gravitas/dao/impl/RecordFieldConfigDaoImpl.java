package com.owners.gravitas.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.dao.RecordFieldConfigDao;
import com.owners.gravitas.domain.entity.RecordFieldConfigEntity;
import com.owners.gravitas.repository.RecordFieldConfigRepository;

/**
 * Implementation class for RecordViewDao
 * 
 * @author javeedsy
 *
 */
@Repository
public class RecordFieldConfigDaoImpl implements RecordFieldConfigDao {

	@Autowired
	private RecordFieldConfigRepository recordFieldConfigRepository;

	@Override
	public List<RecordFieldConfigEntity> getFieldsConfigListBasedOnRecordView(List<String> reportList) {
		return recordFieldConfigRepository.findByRecordViewConfigIdIn(reportList);
	}

}
