package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * The Class MarketingEmailLog.
 *
 * @author vishwanathm
 */
@Entity( name = "GR_MARKETING_EMAIL_QUEUE" )
public class MarketingEmailLog extends AbstractPersistable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8797919269777747508L;

    /** The lead id. */
    @Column( name = "LEAD_ID", nullable = false )
    private String leadId;

    /** The created date. */
    @Column( name = "CREATED_ON", updatable = false )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime createdDate;

    /**
     * Gets the lead id.
     *
     * @return the lead id
     */
    public String getLeadId() {
        return leadId;
    }

    /**
     * Sets the lead id.
     *
     * @param leadId
     *            the new lead id
     */
    public void setLeadId( final String leadId ) {
        this.leadId = leadId;
    }

    /**
     * Gets the created date.
     *
     * @return the created date
     */
    public DateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the created date.
     *
     * @param createdDate
     *            the new created date
     */
    public void setCreatedDate( final DateTime createdDate ) {
        this.createdDate = createdDate;
    }
}
