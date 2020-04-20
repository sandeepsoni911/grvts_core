package com.owners.gravitas.dto.response;

import java.util.List;
import java.util.Map;

/**
 * This class response for record view related APIs
 * 
 * @author sandeepsoni
 *
 */

public class RecordViewResponse extends BaseResponse {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5880307885731426165L;

	private List<Map<String, Object>> data;
	
	private Integer totalCount;
	
	private Object configData;

	/**
	 * @return the configData
	 */
	public Object getConfigData() {
		return configData;
	}

	/**
	 * @param configData the configData to set
	 */
	public void setConfigData(Object configData) {
		this.configData = configData;
	}

	/**
	 * @return the totalCount
	 */
	public Integer getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the data
	 */
	public List<Map<String, Object>> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}

}
