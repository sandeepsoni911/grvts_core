package com.owners.gravitas.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.owners.gravitas.dao.RecordRoleConfigDao;
import com.owners.gravitas.domain.entity.RecordRoleConfigEntity;
import com.owners.gravitas.repository.RecordRoleConfigRepository;

/**
 * Implementation class for RecordViewDao
 * 
 * @author javeedsy
 *
 */
@Service
public class RecordRoleConfigDaoImpl implements RecordRoleConfigDao {

	@Autowired
	private RecordRoleConfigRepository recordRoleConfigRepository;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<RecordRoleConfigEntity> getRoleAndReportConfigList(List<String> roleList) {
		return recordRoleConfigRepository.findByRoleIdIn(roleList);
	}

	@Override
	public List<Map<String, Object>> getRecordViewIdAndDisplayNameList(String roleId) {
		return jdbcTemplate.queryForList("SELECT vc.id, vc.display_name \n"
				+ "FROM gr_record_role_config rc INNER JOIN gr_record_view_config vc \n"
				+ "ON rc.rec_view_config_id = vc.id WHERE rc.role_id = '" + roleId + "' "
				        + "order by vc.display_name asc ");
	}

}
