package com.owners.gravitas.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.owners.gravitas.dao.RecordViewDao;

/**
 * Implementation class for RecordViewDao
 * 
 * @author sandeepsoni
 *
 */
@Repository
public class RecordViewDaoImpl implements RecordViewDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> getRecordView(String recordViewQuery) {
		
		List<Map<String, Object>> resultSet = jdbcTemplate.queryForList(recordViewQuery);
		return resultSet;
	}

	@Override
	public Integer getRecordViewTotalCount(String recordViewQuery) {
		Integer totalCount = jdbcTemplate.queryForObject(recordViewQuery, Integer.class);
		return totalCount;
	}

	

}
