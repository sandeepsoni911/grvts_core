package com.owners.gravitas.dao;

import java.util.List;
import java.util.Map;

import com.owners.gravitas.domain.entity.RecordRoleConfigEntity;

/**
 * Interface to have all record view role config contracts
 * 
 * @author javeedsy
 *
 */
public interface RecordRoleConfigDao {

	List<RecordRoleConfigEntity> getRoleAndReportConfigList(List<String> roleList);

	List<Map<String, Object>> getRecordViewIdAndDisplayNameList(String roleId);

}
