package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The Class GR_RECORD_FIELD_CONFIG.
 *
 * @author javeedsy
 */
@Entity(name = "GR_RECORD_FIELD_CONFIG")
public class RecordFieldConfigEntity extends AbstractAuditable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1633548100973832562L;

	@Column(name = "REC_VIEW_CONFIG_ID", nullable = false)
	private String recordViewConfigId;

	@Column(name = "COL_NAME", nullable = false)
	private String columnName;

	@Column(name = "DISPLAY_NAME", nullable = false)
	private String displayName;

	@Column(name = "REGEX_VALIDATION", nullable = false)
	private String regexValidation;

	@Column(name = "IS_VISIBLE")
	private boolean isVisible;

	@Column(name = "IS_MASKED")
	private boolean isMasked;

	@Column(name = "IS_SORTABLE")
	private boolean isSortable;

	@Column(name = "DOWNLOAD_ENABLED")
	private boolean isDownloadEnabled;
	
	@Column(name = "datatype")
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
