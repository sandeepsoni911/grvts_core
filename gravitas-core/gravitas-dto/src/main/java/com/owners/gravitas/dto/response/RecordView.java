package com.owners.gravitas.dto.response;

import java.io.Serializable;
import java.util.List;

/**
 * The class RecordView
 * 
 * @author javeedsy
 *
 */

public class RecordView implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5880307885731426165L;

	private String id;
	private String tableName;
	private String displayName;
	private boolean isEnabled;
	private boolean isFilterEnabled;
	private boolean isScheduled;
	private boolean isDownloadEnabled;
	private String commaSeperatedVisibleColumnList;
	private List<RecordViewField> recordViewFieldList;
	private List<String> maskedColumnList;
	private String dataType;

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName
	 *            the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the isEnabled
	 */
	public boolean isEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled
	 *            the isEnabled to set
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * @return the isFilterEnabled
	 */
	public boolean isFilterEnabled() {
		return isFilterEnabled;
	}

	/**
	 * @param isFilterEnabled
	 *            the isFilterEnabled to set
	 */
	public void setFilterEnabled(boolean isFilterEnabled) {
		this.isFilterEnabled = isFilterEnabled;
	}

	/**
	 * @return the isScheduled
	 */
	public boolean isScheduled() {
		return isScheduled;
	}

	/**
	 * @param isScheduled
	 *            the isScheduled to set
	 */
	public void setScheduled(boolean isScheduled) {
		this.isScheduled = isScheduled;
	}

	/**
	 * @return the isDownloadEnabled
	 */
	public boolean isDownloadEnabled() {
		return isDownloadEnabled;
	}

	/**
	 * @param isDownloadEnabled
	 *            the isDownloadEnabled to set
	 */
	public void setDownloadEnabled(boolean isDownloadEnabled) {
		this.isDownloadEnabled = isDownloadEnabled;
	}

	/**
	 * @return the commaSeperatedVisibleColumnList
	 */
	public String getCommaSeperatedVisibleColumnList() {
		return commaSeperatedVisibleColumnList;
	}

	/**
	 * @param commaSeperatedVisibleColumnList
	 *            the commaSeperatedVisibleColumnList to set
	 */
	public void setCommaSeperatedVisibleColumnList(String commaSeperatedVisibleColumnList) {
		this.commaSeperatedVisibleColumnList = commaSeperatedVisibleColumnList;
	}

	/**
	 * @return the recordViewFieldList
	 */
	public List<RecordViewField> getRecordViewFieldList() {
		return recordViewFieldList;
	}

	/**
	 * @param recordViewFieldList
	 *            the recordViewFieldList to set
	 */
	public void setRecordViewFieldList(List<RecordViewField> recordViewFieldList) {
		this.recordViewFieldList = recordViewFieldList;
	}

}
