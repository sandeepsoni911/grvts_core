package com.owners.gravitas.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * The Class LeadEmailParsingStatus.
 *
 * @author amits
 */
@Entity( name = "GR_EMAIL_PARSING_LOG" )
public class LeadEmailParsingLog extends AbstractPersistable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3134752266045846647L;

    /** The Constant DATA_SIZE_LIMIT. */
    private static final int DATA_SIZE_LIMIT = 255;

    /** The lead source. */
    @Column( name = "LEAD_SOURCE", nullable = true )
    private String leadSource;

    /** The mail received time. */
    @Column( name = "RECEIVED_ON", nullable = false )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime mailReceivedTime;

    /** The from email. */
    @Column( name = "FROM_EMAIL", nullable = false )
    private String fromEmail;

    /** The to email. */
    @Column( name = "TO_EMAILS", nullable = false )
    private String toEmail;

    /** The subject. */
    @Column( name = "SUBJECT", nullable = false )
    private String subject;

    /** The lead email. */
    @Column( name = "LEAD_EMAIL", nullable = true )
    private String leadEmail;

    /** The status. */
    @Column( name = "STATUS", nullable = false )
    private String status;

    /** The failuer reason. */
    @Column( name = "FAILURE_REASON", nullable = true )
    private String failuerReason;

    /** The created by. */
    @Column( name = "CREATED_BY", updatable = false )
    private String createdBy;

    /** The created date. */
    @Column( name = "CREATED_ON", updatable = false )
    @Type( type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime" )
    private DateTime createdDate;

    /**
     * Instantiates a new action log.
     */
    public LeadEmailParsingLog() {
        // default constructor
    }

    /**
     * Gets the lead source.
     *
     * @return the leadSource
     */
    public String getLeadSource() {
        return leadSource;
    }

    /**
     * Sets the lead source.
     *
     * @param leadSource
     *            the leadSource to set
     */
    public void setLeadSource( String leadSource ) {
        this.leadSource = leadSource;
    }

    /**
     * Gets the mail received time.
     *
     * @return the mailReceivedTime
     */
    public DateTime getMailReceivedTime() {
        return mailReceivedTime;
    }

    /**
     * Sets the mail received time.
     *
     * @param mailReceivedTime
     *            the mailReceivedTime to set
     */
    public void setMailReceivedTime( DateTime mailReceivedTime ) {
        this.mailReceivedTime = mailReceivedTime;
    }

    /**
     * Gets the from email.
     *
     * @return the fromEmail
     */
    public String getFromEmail() {
        return fromEmail;
    }

    /**
     * Sets the from email.
     *
     * @param fromEmail
     *            the fromEmail to set
     */
    public void setFromEmail( String fromEmail ) {
        this.fromEmail = fromEmail;
    }

    /**
     * Gets the to email.
     *
     * @return the toEmail
     */
    public String getToEmail() {
        return toEmail;
    }

    /**
     * Sets the to email.
     *
     * @param toEmail
     *            the toEmail to set
     */
    public void setToEmail( String toEmail ) {
        this.toEmail = toEmail;
    }

    /**
     * Gets the subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject.
     *
     * @param subject
     *            the subject to set
     */
    public void setSubject( String subject ) {
        this.subject = subject;
    }

    /**
     * Gets the lead email.
     *
     * @return the leadEmail
     */
    public String getLeadEmail() {
        return leadEmail;
    }

    /**
     * Sets the lead email.
     *
     * @param leadEmail
     *            the leadEmail to set
     */
    public void setLeadEmail( String leadEmail ) {
        this.leadEmail = leadEmail;
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
     *            the status to set
     */
    public void setStatus( String status ) {
        this.status = status;
    }

    /**
     * Gets the failuer reason.
     *
     * @return the failuerReason
     */
    public String getFailuerReason() {
        return failuerReason;
    }

    /**
     * Sets the failuer reason.
     *
     * @param failuerReason
     *            the failuerReason to set
     */
    public void setFailuerReason( String failuerReason ) {
        this.failuerReason = trimToSize( failuerReason, DATA_SIZE_LIMIT );
    }

    /**
     * Gets the created by.
     *
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy
     *            the createdBy to set
     */
    public void setCreatedBy( String createdBy ) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the created date.
     *
     * @return the createdDate
     */
    public DateTime getCreatedDate() {
        return createdDate == null ? null : createdDate;
    }

    /**
     * Sets the created date.
     *
     * @param createdDate
     *            the createdDate to set
     */
    public void setCreatedDate( DateTime createdDate ) {
        this.createdDate = createdDate == null ? null : createdDate;
    }

    /**
     * Trim to size.
     *
     * @param value
     *            the value
     * @param size
     *            the size
     * @return the string
     */
    private String trimToSize( final String value, final int size ) {
        return null == value || value.length() < size ? value : value.substring( 0, size );
    }
}
