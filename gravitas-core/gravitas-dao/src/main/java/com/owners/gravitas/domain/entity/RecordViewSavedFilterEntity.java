package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * The Class Saved Filter.
 * 
 
 * @author sonisan
 */
@Entity( name = "GR_RECORD_SAVED_FILTERS" )
public class RecordViewSavedFilterEntity extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -653017420701082687L;

    /** The userId. */
    @Column( name = "USER_ID", nullable = false )
    private String userId;

    /** The role filterName. */
    @Column( name = "FILTER_NAME", unique = true, nullable = false )
    private String filterName;

    /** The recordViewConfigId. */
    @Column( name = "REC_VIEW_CONFIG_ID", unique = true, nullable = false )
    private String recordViewConfigId;

    /** The filterCriteria. */
    @Column( name = "FILTER_CRITERIA", nullable = true )
    private String filterCriteria;
    
    /** The is deleted. */
    @Column( name = "IS_DELETED", nullable = true )
    private boolean isDeleted;
    
    /**
     * Instantiates a new saved filter.
     */
    public RecordViewSavedFilterEntity() {
    }

    /**
     * Instantiates a new filter.
     * @param userId
     * @param filterName
     * @param recordViewConfigId
     * @param filterCriteria
     */
	public RecordViewSavedFilterEntity(String userId, String filterName, String recordViewConfigId, String filterCriteria) {
		super();
		this.userId = userId;
		this.filterName = filterName;
		this.recordViewConfigId = recordViewConfigId;
		this.filterCriteria = filterCriteria;
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

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
