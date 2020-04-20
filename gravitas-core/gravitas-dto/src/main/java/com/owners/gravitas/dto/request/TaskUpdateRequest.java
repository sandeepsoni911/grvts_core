package com.owners.gravitas.dto.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class TaskUpdateRequest.
 *
 * @author vishwanathm
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class TaskUpdateRequest extends AgentTaskRequest {

    /** The schedule dtm. */
    private Date scheduleDtm;

    /** The title selection reason. */
    private String titleSelectionReason;

    /** The cancellation reason. */
    private String cancellationReason;

    /** The status. */
    private String status;

    /**
     * Gets the title selection reason.
     *
     * @return the title selection reason
     */
    public String getTitleSelectionReason() {
        return titleSelectionReason;
    }

    /**
     * Sets the title selection reason.
     *
     * @param titleSelectionReason
     *            the new title selection reason
     */
    public void setTitleSelectionReason( final String titleSelectionReason ) {
        this.titleSelectionReason = titleSelectionReason;
    }

    /**
     * Gets the schedule dtm.
     *
     * @return the scheduleDtm
     */
    public Date getScheduleDtm() {
        if (scheduleDtm != null) {
            scheduleDtm = ( Date ) scheduleDtm.clone();
        }
        return scheduleDtm;
    }

    /**
     * Sets the schedule dtm.
     *
     * @param scheduleDtm
     *            the dueDtm to set
     */
    public void setScheduleDtm( final Date scheduleDtm ) {
        if (scheduleDtm != null) {
            this.scheduleDtm = ( Date ) scheduleDtm.clone();
        }
    }

    /**
     * Gets the cancellation reason.
     *
     * @return the cancellation reason
     */
    public String getCancellationReason() {
        return cancellationReason;
    }

    /**
     * Sets the cancellation reason.
     *
     * @param cancellationReason
     *            the new cancellation reason
     */
    public void setCancellationReason( final String cancellationReason ) {
        this.cancellationReason = cancellationReason;
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
