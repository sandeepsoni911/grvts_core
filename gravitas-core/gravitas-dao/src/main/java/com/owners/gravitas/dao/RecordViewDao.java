package com.owners.gravitas.dao;

import java.util.List;
import java.util.Map;

/**
 * Interface to have all record view contracts
 * 
 * @author sandeepsoni
 *
 */
public interface RecordViewDao {

	/**
	 * To get Record view details
	 * 
	 * @param recordViewQuery
	 * @return
	 */
	public List<Map<String, Object>> getRecordView(String recordViewQuery);

	/**
	 * To get Record view total Count
	 * 
	 * @param recordViewQuery
	 * @return
	 */
	public Integer getRecordViewTotalCount(String recordViewQuery);

}
