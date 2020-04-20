package com.owners.gravitas.domain.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;


/**
 * the Entity class for table RecordReportsNtfc
 * @author sandeepsoni
 *
 */
@Entity(name="gr_record_reports_ntfc")
public class RecordReportsNotficationEntity extends AbstractAuditable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1633548100973832562L;
	
	@Column(name = "saved_filter_id", nullable = false)
	private String savedFilterId;

	@Column(name = "trigger_time", nullable = false)
	private Timestamp triggerTime;

	@Column(name = "status", nullable = false)
	private String status;
	
	@Column(name = "is_reoccuring")
	private boolean isReoccuring;
	
	@Column(name = "file_type")
	private String fileType;
	
	@Column(name="filePath")
	private String filePath;
	

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the savedFilterId
	 */
	public String getSavedFilterId() {
		return savedFilterId;
	}

	/**
	 * @param savedFilterId the savedFilterId to set
	 */
	public void setSavedFilterId(String savedFilterId) {
		this.savedFilterId = savedFilterId;
	}

	/**
	 * @return the triggerTime
	 */
	public Timestamp getTriggerTime() {
		return triggerTime;
	}

	/**
	 * @param triggerTime the triggerTime to set
	 */
	public void setTriggerTime(Timestamp triggerTime) {
		this.triggerTime = triggerTime;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the isReoccuring
	 */
	public boolean isReoccuring() {
		return isReoccuring;
	}

	/**
	 * @param isReoccuring the isReoccuring to set
	 */
	public void setReoccuring(boolean isReoccuring) {
		this.isReoccuring = isReoccuring;
	}


}
