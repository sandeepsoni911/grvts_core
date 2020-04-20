package com.owners.gravitas.dto;


import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * DTO class 
 * @author sandeepsoni
 *
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class RecordViewNotificationDto {

	
	private String savedFilterId;

	private Timestamp triggerTime;

	private String status;
	
	private boolean isReoccuring;
	
	private String fileType;
	
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
	
	
}
