package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The Class GR_RECORD_VIEW_CONFIG.
 *
 * @author javeedsy
 */
@Entity(name = "GR_RECORD_VIEW_CONFIG")
public class RecordViewConfigEntity extends AbstractAuditable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1633548100973832562L;

	@Column(name = "TABLE_NAME", nullable = false)
	private String tableName;

	@Column(name = "DISPLAY_NAME", nullable = false)
	private String displayName;

	@Column(name = "IS_ENABLED")
	private boolean isEnabled;

	@Column(name = "IS_SCHEDULED")
	private boolean isScheduled;

	@Column(name = "DOWNLOAD_ENABLED")
	private boolean isDownloadEnabled;

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

}
