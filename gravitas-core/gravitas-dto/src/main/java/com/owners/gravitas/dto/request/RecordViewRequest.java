package com.owners.gravitas.dto.request;
/**
 * This is class to contain request details
 * for record view APIs
 * @author sandeepsoni
 *
 */

import java.util.List;

public class RecordViewRequest {

	private String tableName;
	
	private String columnName;
	
	private String perPage;
	
	private Integer pageNumber;
	
	private String orderBy;
	
	private String where;
	
	private String groupByColumn;
	
	private String aggFnName;
	
	private String aggFnCol;
	
	private String joinTableA;
	
	private String tableAColumns;
	
	private String tableAIdColumn;
	
	private String joinTableB;
	
	private String tableBColumns;
	
	private String tableBIdColumn;
	
	private String recordViewConfigId;
	
	private List<String> roleList;
	
	private List<String> maskedColumnList;
	
	private List<ConditionGroup> conditionGroup;
	
	private String orderByValue;
	
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
	 * @return the maskedColumnList
	 */
	public List<String> getMaskedColumnList() {
		return maskedColumnList;
	}
	/**
	 * @param maskedColumnList the maskedColumnList to set
	 */
	public void setMaskedColumnList(List<String> maskedColumnList) {
		this.maskedColumnList = maskedColumnList;
	}
	/**
	 * @return the perPage
	 */
	public String getPerPage() {
		return perPage;
	}
	/**
	 * @param perPage the perPage to set
	 */
	public void setPerPage(String perPage) {
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
	 * @return the aggFnName
	 */
	public String getAggFnName() {
		return aggFnName;
	}
	/**
	 * @param aggFnName the aggFnName to set
	 */
	public void setAggFnName(String aggFnName) {
		this.aggFnName = aggFnName;
	}
	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}
	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
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
	 * @return the where
	 */
	public String getWhere() {
		return where;
	}
	/**
	 * @param where the where to set
	 */
	public void setWhere(String where) {
		this.where = where;
	}
	/**
	 * @return the groupByColumn
	 */
	public String getGroupByColumn() {
		return groupByColumn;
	}
	/**
	 * @param groupByColumn the groupByColumn to set
	 */
	public void setGroupByColumn(String groupByColumn) {
		this.groupByColumn = groupByColumn;
	}
	/**
	 * @return the aggFnCol
	 */
	public String getAggFnCol() {
		return aggFnCol;
	}
	/**
	 * @param aggFnCol the aggFnCol to set
	 */
	public void setAggFnCol(String aggFnCol) {
		this.aggFnCol = aggFnCol;
	}
	/**
	 * @return the joinTableA
	 */
	public String getJoinTableA() {
		return joinTableA;
	}
	/**
	 * @param joinTableA the joinTableA to set
	 */
	public void setJoinTableA(String joinTableA) {
		this.joinTableA = joinTableA;
	}
	/**
	 * @return the tableAColumns
	 */
	public String getTableAColumns() {
		return tableAColumns;
	}
	/**
	 * @param tableAColumns the tableAColumns to set
	 */
	public void setTableAColumns(String tableAColumns) {
		this.tableAColumns = tableAColumns;
	}
	/**
	 * @return the tableAIdColumn
	 */
	public String getTableAIdColumn() {
		return tableAIdColumn;
	}
	/**
	 * @param tableAIdColumn the tableAIdColumn to set
	 */
	public void setTableAIdColumn(String tableAIdColumn) {
		this.tableAIdColumn = tableAIdColumn;
	}
	/**
	 * @return the jointTableB
	 */
	public String getJoinTableB() {
		return joinTableB;
	}
	/**
	 * @param jointTableB the jointTableB to set
	 */
	public void setJoinTableB(String jointTableB) {
		this.joinTableB = jointTableB;
	}
	/**
	 * @return the tableBColumns
	 */
	public String getTableBColumns() {
		return tableBColumns;
	}
	/**
	 * @param tableBColumns the tableBColumns to set
	 */
	public void setTableBColumns(String tableBColumns) {
		this.tableBColumns = tableBColumns;
	}
	/**
	 * @return the tableBIdColumn
	 */
	public String getTableBIdColumn() {
		return tableBIdColumn;
	}
	/**
	 * @param tableBIdColumn the tableBIdColumn to set
	 */
	public void setTableBIdColumn(String tableBIdColumn) {
		this.tableBIdColumn = tableBIdColumn;
	}

}
