package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * The Class StateTimeZone.
 *
 * @author vishwanathm
 */
@Entity( name = "GR_STATE_TIMEZONE" )
public class StateTimeZone extends AbstractPersistable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6721178576030648282L;

    /** The state code. */
    @Column( name = "STATE_CODE", nullable = false )
    private String stateCode;

    /** The time zone. */
    @Column( name = "TIMEZONE_OFFSET", nullable = false )
    private String timeZone;

    /** The time zone. */
    @Column( name = "OFFSET_HOUR", nullable = false )
    private Integer offSetHour;

    /** The created date. */
    @Column( name = "CREATED_ON", updatable = false )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime createdDate;

    /**
     * Gets the state code.
     *
     * @return the state code
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     * Sets the state code.
     *
     * @param stateCode
     *            the new state code
     */
    public void setStateCode( final String stateCode ) {
        this.stateCode = stateCode;
    }

    /**
     * Gets the time zone.
     *
     * @return the time zone
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * Sets the time zone.
     *
     * @param timeZone
     *            the new time zone
     */
    public void setTimeZone( final String timeZone ) {
        this.timeZone = timeZone;
    }

    /**
     * Gets the off set hour.
     *
     * @return the off set hour
     */
    public Integer getOffSetHour() {
        return offSetHour;
    }

    /**
     * Sets the off set hour.
     *
     * @param offSetHour
     *            the new off set hour
     */
    public void setOffSetHour( final Integer offSetHour ) {
        this.offSetHour = offSetHour;
    }

    /**
     * Gets the created date.
     *
     * @return the created date
     */
    public DateTime getCreatedDate() {
        return createdDate == null ? null : createdDate;
    }

    /**
     * Sets the created date.
     *
     * @param createdDate
     *            the new created date
     */
    public void setCreatedDate( final DateTime createdDate ) {
        this.createdDate = createdDate == null ? null : createdDate;
    }
}
