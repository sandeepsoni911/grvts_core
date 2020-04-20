package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * The Class StageLog.
 *
 * @author shivamm
 *         The Class OpportunityStageLog.
 */
@Entity( name = "GR_OPPORTUNITY_STAGE_LOG" )
@EntityListeners( AuditingEntityListener.class )
public class StageLog extends AbstractPersistable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1633548100973832562L;

    /** The stage. */
    @Column( name = "STAGE", nullable = false )
    private String stage;

    /** The stage. */
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "OPPORTUNITY_ID", nullable = false )
    private Opportunity opportunity;

    /** The created by. */
    @CreatedBy
    @Column( name = "CREATED_BY", nullable = false )
    private String createdBy;

    /** The created date. */
    @CreatedDate
    @Column( name = "CREATED_ON", nullable = false )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime createdDate;

    /**
     * Gets the stage.
     *
     * @return the stage
     */
    public String getStage() {
        return stage;
    }

    /**
     * Sets the stage.
     *
     * @param stage
     *            the new stage
     */
    public void setStage( String stage ) {
        this.stage = stage;
    }

    /**
     * Gets the created by.
     *
     * @return the created by
     */
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.domain.entity.AbstractAuditable#getCreatedBy()
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy the new created by
     */
    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.domain.entity.AbstractAuditable#setCreatedBy(java.
     * lang.String)
     */
    public void setCreatedBy( String createdBy ) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the created date.
     *
     * @return the created date
     */
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.domain.entity.AbstractAuditable#getCreatedDate()
     */
    public DateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the created date.
     *
     * @param createdDate the new created date
     */
    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.domain.entity.AbstractAuditable#setCreatedDate(org.
     * joda.time.DateTime)
     */
    public void setCreatedDate( DateTime createdDate ) {
        this.createdDate = createdDate;
    }

    /**
     * Gets the opportunity.
     *
     * @return the opportunity
     */
    public Opportunity getOpportunity() {
        return opportunity;
    }

    /**
     * Sets the opportunity.
     *
     * @param opportunity the new opportunity
     */
    public void setOpportunity( Opportunity opportunity ) {
        this.opportunity = opportunity;
    }
}
