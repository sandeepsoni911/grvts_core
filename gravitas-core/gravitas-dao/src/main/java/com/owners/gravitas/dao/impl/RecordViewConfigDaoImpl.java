package com.owners.gravitas.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.dao.RecordViewConfigDao;
import com.owners.gravitas.domain.entity.RecordViewConfigEntity;
import com.owners.gravitas.repository.RecordViewConfigRepository;

/**
 * Implementation class for RecordViewDao
 * 
 * @author javeedsy
 *
 */
@Repository
public class RecordViewConfigDaoImpl implements RecordViewConfigDao {

	@Autowired
	private RecordViewConfigRepository recordViewConfigRepository;

	public RecordViewConfigEntity findOne(String id) {
		return recordViewConfigRepository.findOne(id);
	}

	@Override
	public List<RecordViewConfigEntity> getRecordViewConfigListBasedOnRecordView(List<String> reportList) {
		return recordViewConfigRepository.findByIdIn(reportList);
	}

}
