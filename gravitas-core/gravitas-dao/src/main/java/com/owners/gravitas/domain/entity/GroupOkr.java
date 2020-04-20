package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * The Class GroupOkr.
 *
 * @author raviz
 */
@Entity( name = "GR_GROUP_OKR" )
public class GroupOkr extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6257520288262024888L;

    /** The test start date. */
    @Column( name = "TEST_START_DATE", nullable = false )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime testStartDate;

    /** The test end date. */
    @Column( name = "TEST_END_DATE", nullable = false )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime testEndDate;

    /** The related okr. */
    @Column( name = "RELATED_OKR", nullable = false )
    private String relatedOkr;

    /** The group. */
    @OneToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "GROUP_ID", nullable = false )
    private Group group;

    /**
     * Instantiates a new group okr.
     */
    public GroupOkr() {

    }

    /**
     * Gets the test start date.
     *
     * @return the test start date
     */
    public DateTime getTestStartDate() {
        return testStartDate;
    }

    /**
     * Sets the test start date.
     *
     * @param testStartDate
     *            the new test start date
     */
    public void setTestStartDate( final DateTime testStartDate ) {
        this.testStartDate = testStartDate;
    }

    /**
     * Gets the test end date.
     *
     * @return the test end date
     */
    public DateTime getTestEndDate() {
        return testEndDate;
    }

    /**
     * Sets the test end date.
     *
     * @param testEndDate
     *            the new test end date
     */
    public void setTestEndDate( final DateTime testEndDate ) {
        this.testEndDate = testEndDate;
    }

    /**
     * Gets the related okr.
     *
     * @return the related okr
     */
    public String getRelatedOkr() {
        return relatedOkr;
    }

    /**
     * Sets the related okr.
     *
     * @param relatedOkr
     *            the new related okr
     */
    public void setRelatedOkr( final String relatedOkr ) {
        this.relatedOkr = relatedOkr;
    }

    /**
     * Gets the group.
     *
     * @return the group
     */
    public Group getgroup() {
        return group;
    }

    /**
     * Sets the group.
     *
     * @param group
     *            the new group
     */
    public void setgroup( final Group group ) {
        this.group = group;
    }
}
