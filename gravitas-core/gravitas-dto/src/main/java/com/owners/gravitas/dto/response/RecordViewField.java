package com.owners.gravitas.dto.response;

/**
 * This class RecordViewField
 * 
 * @author javeedsy
 *
 */

public class RecordViewField {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5880307885731426165L;

	private String id;
	private String recordViewConfigId;
	private String columnName;
	private String displayName;
	private String regexValidation;
	private boolean isVisible;
	private boolean isMasked;
	private boolean isSortable;
	private boolean isDownloadEnabled;
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
	 * @return the recordViewConfigId
	 */
	public String getRecordViewConfigId() {
		return recordViewConfigId;
	}

	/**
	 * @param recordViewConfigId
	 *            the recordViewConfigId to set
	 */
	public void setRecordViewConfigId(String recordViewConfigId) {
		this.recordViewConfigId = recordViewConfigId;
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName
	 *            the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
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
	 * @return the regexValidation
	 */
	public String getRegexValidation() {
		return regexValidation;
	}

	/**
	 * @param regexValidation
	 *            the regexValidation to set
	 */
	public void setRegexValidation(String regexValidation) {
		this.regexValidation = regexValidation;
	}

	/**
	 * @return the isVisible
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * @param isVisible
	 *            the isVisible to set
	 */
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	/**
	 * @return the isMasked
	 */
	public boolean isMasked() {
		return isMasked;
	}

	/**
	 * @param isMasked
	 *            the isMasked to set
	 */
	public void setMasked(boolean isMasked) {
		this.isMasked = isMasked;
	}

	/**
	 * @return the isSortable
	 */
	public boolean isSortable() {
		return isSortable;
	}

	/**
	 * @param isSortable
	 *            the isSortable to set
	 */
	public void setSortable(boolean isSortable) {
		this.isSortable = isSortable;
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

}
