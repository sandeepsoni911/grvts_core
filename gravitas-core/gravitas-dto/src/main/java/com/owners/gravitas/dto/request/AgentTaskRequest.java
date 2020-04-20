package com.owners.gravitas.dto.request;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.enums.TaskType;
import com.owners.gravitas.validators.StringEnum;

/**
 * The Class AgentTaskRequest.
 *
 * @author vishwanathm
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class AgentTaskRequest {

    /** The title. */
    @NotBlank( message = "error.task.title.required" )
    private String title;

    /** The description. */
    private String description;

    /** The dueDtm. */
    private Date dueDtm;

    /** The meet at. */
    private String location;

    /** The type. */
    @StringEnum( enumClass = TaskType.class, message = "error.task.type.format" )
    private String type;

    /** The status. */
    private String status;

    /** The createdBy. */
    private String createdBy;

    /** The listing id. */
    private String listingId;


    /** The isPrimary. */
    private boolean isPrimary;

    /** The co shopping Id. */
    private String coShoppingId;
    
    /** The warm transfer call flag. */
    private Boolean isWarmTransferCall = Boolean.FALSE;

    /**
	 * @return the isWarmTransferCall
	 */
	public Boolean getIsWarmTransferCall() {
		return isWarmTransferCall;
	}

	/**
	 * @param isWarmTransferCall the isWarmTransferCall to set
	 */
	public void setIsWarmTransferCall(Boolean isWarmTransferCall) {
		this.isWarmTransferCall = isWarmTransferCall;
	}



    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title
     *            the new title
     */
    public void setTitle( final String title ) {
        this.title = title;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param notes
     *            the new description
     */
    public void setDescription( final String notes ) {
        this.description = notes;
    }

    /**
     * Gets the dueDtm.
     *
     * @return the dueDtm
     */
    public Date getDueDtm() {
        if (dueDtm != null) {
            return ( Date ) dueDtm.clone();
        }
        return null;
    }

    /**
     * Sets the dueDtm.
     *
     * @param dueDtm
     *            the new date tm
     */
    public void setDueDtm( final Date dueDtm ) {
        if (dueDtm != null) {
            this.dueDtm = ( Date ) dueDtm.clone();
        }
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location.
     *
     * @param location
     *            the new location
     */
    public void setLocation( final String location ) {
        this.location = location;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type
     *            the new type
     */
    public void setType( final String type ) {
        this.type = type;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus( final String status ) {
        this.status = status;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     *            the createdBy to set
     */
    public void setCreatedBy( final String createdBy ) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the listing id.
     *
     * @return the listing id
     */
    public String getListingId() {
        return listingId;
    }

    /**
     * Sets the listing id.
     *
     * @param listingId
     *            the new listing id
     */
    public void setListingId( final String listingId ) {
        this.listingId = listingId;
    }

    /**
     * @return the isPrimary
     */
    public boolean isPrimary() {
        return isPrimary;
    }

    /**
     * @param isPrimary
     *            the isPrimary to set
     */
    public void setPrimary( final boolean isPrimary ) {
        this.isPrimary = isPrimary;
    }

    /**
     * @return the coShoppingId
     */
    public String getCoShoppingId() {
        return coShoppingId;
    }

    /**
     * @param coShoppingId
     *            the coShoppingId to set
     */
    public void setCoShoppingId( final String coShoppingId ) {
        this.coShoppingId = coShoppingId;
    }
}
