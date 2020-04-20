package com.owners.gravitas.dto.request;


import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.enums.MeetingType;
import com.owners.gravitas.validators.DueDate;
import com.owners.gravitas.validators.StringEnum;

/**
 * The Class AgentMeetingRequest.
 *
 * @author bhardrah
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class AgentMeetingRequest {

    /** The title. */
    @NotBlank( message = "error.task.title.required" )
    private String title;

    /** The description. */
    private String description;

    /** The dueDtm. */
    @NotNull( message = "error.meeting.dueDate.required" )
    @DueDate( message = "error.meeting.invalid.dueDate" )
    private String dueDtm;

    /** The meet at. */
    private String location;

    /** The type. */
    @StringEnum( enumClass = MeetingType.class, message = "error.meeting.type.format" )
    private String type;

    /** The status. */
    private String status;

    /** The createdBy. */
    private String createdBy;
    
    /** The timeZone. */
    private String timezone;
    

    /** The warm call transfer flag */
//    @NotNull( message = "error.meeting.warmTransfer.required" )
    private Boolean isWarmTransferCall;

    /**
	 * @return the isWarmTransferCall
	 */
	public Boolean getIsWarmTransferCall() {
		return isWarmTransferCall;
	}

	/**
	 * @param isWarmTransferCall the isWarmTransferCall to set
	 */
	public void setIsWarmTransferCall(final Boolean isWarmTransferCall) {
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
	 * @return the dueDtm
	 */
	public String getDueDtm() {
		return dueDtm;
	}

	/**
	 * @param dueDtm the dueDtm to set
	 */
	public void setDueDtm(final String dueDtm) {
		this.dueDtm = dueDtm;
	}

	/**
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * @param timezone the timezone to set
	 */
	public void setTimezone(final String timezone) {
		this.timezone = timezone;
	}

}
