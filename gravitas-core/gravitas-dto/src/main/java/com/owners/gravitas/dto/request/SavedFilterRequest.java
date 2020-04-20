package com.owners.gravitas.dto.request;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.BaseDTO;

/**
 * The SavedFilterRequest Class.
 *
 * @author sandeepsoni
 *
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class SavedFilterRequest extends BaseDTO{
    
    /**
	 * Serial version
	 */
	private static final long serialVersionUID = -6780524483934640223L;

	/** The id. */
    @Size( max = 255, message = "error.filter.id.size" )
    private String id;
    
	/** user id. */
	@NotBlank( message = "error.filter.userId.required" )
    @Size( min = 1, max = 36, message = "error.filter.userId.size" )
    private String userId;
    
	/** filterName. */
	@NotBlank( message = "error.filter.filterName.required" )
    @Size( min = 1, max = 36, message = "error.filter.filterName.size" )
    private String filterName;
	
	/** recordViewConfig id. */
	@NotBlank( message = "error.filter.recordViewConfigId.required" )
    @Size( min = 1, max = 36, message = "error.filter.recordViewConfigId.size" )
    private String recordViewConfigId;

	/** filter criteria . */
	@NotBlank( message = "error.filter.filterCriteria.required" )
    @Size( min = 1, max = 2048, message = "error.filter.filterCriteria.size" )
    private String filterCriteria;

	/** delete . */
    private boolean delete;

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
