package com.owners.gravitas.amqp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.BaseDTO;

/**
 * 
 * @author gururasm
 *
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class UserTrackingDetail extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1772191176025732974L;

    /** The user id. */
    private String userId;

    /** The email id. */
    private String emailId;

    /** The visited date. */
    private long visitedDate;

    /** The last visited date. */
    private long lastVisitedDate;

    /** The time spent on site in sec. */
    private long timeSpentOnSiteInSec;

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId( String userId ) {
        this.userId = userId;
    }

    /**
     * @return the emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId
     *            the emailId to set
     */
    public void setEmailId( String emailId ) {
        this.emailId = emailId;
    }

    /**
     * @return the visitedDate
     */
    public long getVisitedDate() {
        return visitedDate;
    }

    /**
     * @param visitedDate
     *            the visitedDate to set
     */
    public void setVisitedDate( long visitedDate ) {
        this.visitedDate = visitedDate;
    }

    /**
     * @return the lastVisitedDate
     */
    public long getLastVisitedDate() {
        return lastVisitedDate;
    }

    /**
     * @param lastVisitedDate
     *            the lastVisitedDate to set
     */
    public void setLastVisitedDate( long lastVisitedDate ) {
        this.lastVisitedDate = lastVisitedDate;
    }

    /**
     * @return the timeSpentOnSiteInSec
     */
    public long getTimeSpentOnSiteInSec() {
        return timeSpentOnSiteInSec;
    }

    /**
     * @param timeSpentOnSiteInSec
     *            the timeSpentOnSiteInSec to set
     */
    public void setTimeSpentOnSiteInSec( long timeSpentOnSiteInSec ) {
        this.timeSpentOnSiteInSec = timeSpentOnSiteInSec;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
