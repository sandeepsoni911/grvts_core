package com.owners.gravitas.dto;

import com.hubzu.notification.dto.model.BaseDto;

/**
 * The Class CheckinDetailsDTO.
 *
 * @author amits
 */
public class TaskCheckinDetailsDTO extends BaseDto {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2436493986520765055L;

    /** The checkin date. */
    private String checkinDate;

    /** The location. */
    private String location;

    /** The status. */
    private String status;

    /**
     * Instantiates a new checkin details dto.
     */
    public TaskCheckinDetailsDTO() {
        super();
    }

    /**
     * Gets the checkin date.
     *
     * @return the checkin date
     */
    public String getCheckinDate() {
        return checkinDate;
    }

    /**
     * Sets the checkin date.
     *
     * @param checkinDate
     *            the new checkin date
     */
    public void setCheckinDate( final String checkinDate ) {
        this.checkinDate = checkinDate;
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
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status
     *            the new status
     */
    public void setStatus( final String status ) {
        this.status = status;
    }
}
