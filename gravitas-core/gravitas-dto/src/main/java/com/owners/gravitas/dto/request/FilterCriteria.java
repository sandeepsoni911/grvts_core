package com.owners.gravitas.dto.request;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * Class to hold filter criteria
 * @author sandeepsoni
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterCriteria {
	
	private String recordViewConfigId;
	
	private List<String> roleList;
	
	private Integer perPage;
	
	private Integer pageNumber;
	
	private List<ConditionGroup> conditionGroup;
	
	private String orderBy;
	
	private String orderByValue;

	/**
	 * @return the recordViewConfigId
	 */
	public String getRecordViewConfigId() {
		return recordViewConfigId;
	}

	/**
	 * @param recordViewConfigId the recordViewConfigId to set
	 */
	public void setRecordViewConfigId(String recordViewConfigId) {
		this.recordViewConfigId = recordViewConfigId;
	}

	/**
	 * @return the roleList
	 */
	public List<String> getRoleList() {
		return roleList;
	}

	/**
	 * @param roleList the roleList to set
	 */
	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}

	/**
	 * @return the perPage
	 */
	public Integer getPerPage() {
		return perPage;
	}

	/**
	 * @param perPage the perPage to set
	 */
	public void setPerPage(Integer perPage) {
		this.perPage = perPage;
	}

	/**
	 * @return the pageNumber
	 */
	public Integer getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return the conditionGroup
	 */
	public List<ConditionGroup> getConditionGroup() {
		return conditionGroup;
	}

	/**
	 * @param conditionGroup the conditionGroup to set
	 */
	public void setConditionGroup(List<ConditionGroup> conditionGroup) {
		this.conditionGroup = conditionGroup;
	}

	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * @return the orderByValue
	 */
	public String getOrderByValue() {
		return orderByValue;
	}

	/**
	 * @param orderByValue the orderByValue to set
	 */
	public void setOrderByValue(String orderByValue) {
		this.orderByValue = orderByValue;
	}
	
	

}
