package com.owners.gravitas.dto.response;

import java.util.List;

import com.owners.gravitas.dto.RecordViewNotificationDto;

public class SavedFilter{
	/** The id. */
    private String id;
    
	/** user id. */
    private String userId;
    
	/** filterName. */
    private String filterName;
	
	/** recordViewConfig id. */
    private String recordViewConfigId;

	/** filter criteria . */
    private String filterCriteria;

	/** delete . */
    private boolean delete;
    
    private List<RecordViewNotificationDto> notficationDetails;
    
	/**
	 * @return the notficationDetails
	 */
	public List<RecordViewNotificationDto> getNotficationDetails() {
		return notficationDetails;
	}

	/**
	 * @param notficationDetails the notficationDetails to set
	 */
	public void setNotficationDetails(List<RecordViewNotificationDto> notficationDetails) {
		this.notficationDetails = notficationDetails;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public String getRecordViewConfigId() {
		return recordViewConfigId;
	}

	public void setRecordViewConfigId(String recordViewConfigId) {
		this.recordViewConfigId = recordViewConfigId;
	}

	public String getFilterCriteria() {
		return filterCriteria;
	}

	public void setFilterCriteria(String filterCriteria) {
		this.filterCriteria = filterCriteria;
	}

	public boolean getDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	
}