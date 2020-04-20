package com.owners.gravitas.domain.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * The Class ContactActivity.
 * 
 * @author pabhishek
 */
@Entity( name = "GR_CONTACT_ACTIVITY" )
public class ContactActivity extends AbstractAuditable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1811797055761466825L;

    /** The owners com id. */
    @Column( name = "ownerscom_id", nullable = false )
    private String ownersComId;

    /** The ref code. */
    @ManyToOne( )
    @JoinColumn( name = "activity_config_id" )
    private RefCode refCode;

    /** The notification id. */
    @Column( name = "notification_id", nullable = false )
    private String notificationId;

    /** The source. */
    @Column( name = "source", nullable = false )
    private String source;

    /** The alert type. */
    @Column( name = "alert_type", nullable = false )
    private String alertType;

    /** The activity properties. */
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JoinColumn( name = "activity_id", nullable = false )
    private List< ActivityProperty > activityProperties;

    /**
     * Gets the owners com id.
     *
     * @return the owners com id
     */
    public String getOwnersComId() {
        return ownersComId;
    }

    /**
     * Sets the owners com id.
     *
     * @param ownersComId
     *            the new owners com id
     */
    public void setOwnersComId( final String ownersComId ) {
        this.ownersComId = ownersComId;
    }

    /**
     * Gets the ref code.
     *
     * @return the ref code
     */
    public RefCode getRefCode() {
        return refCode;
    }

    /**
     * Sets the ref code.
     *
     * @param refCode
     *            the new ref code
     */
    public void setRefCode( final RefCode refCode ) {
        this.refCode = refCode;
    }

    /**
     * Gets the notification id.
     *
     * @return the notification id
     */
    public String getNotificationId() {
        return notificationId;
    }

    /**
     * Sets the notification id.
     *
     * @param notificationId
     *            the new notification id
     */
    public void setNotificationId( final String notificationId ) {
        this.notificationId = notificationId;
    }

    /**
     * Gets the source.
     *
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the source.
     *
     * @param source
     *            the new source
     */
    public void setSource( final String source ) {
        this.source = source;
    }

    /**
     * Gets the alert type.
     *
     * @return the alert type
     */
    public String getAlertType() {
        return alertType;
    }

    /**
     * Sets the alert type.
     *
     * @param alertType
     *            the new alert type
     */
    public void setAlertType( final String alertType ) {
        this.alertType = alertType;
    }

    /**
     * Gets the activity properties.
     *
     * @return the activity properties
     */
    public List< ActivityProperty > getActivityProperties() {
        return activityProperties;
    }

    /**
     * Sets the activity properties.
     *
     * @param activityProperties
     *            the new activity properties
     */
    public void setActivityProperties( final List< ActivityProperty > activityProperties ) {
        this.activityProperties = activityProperties;
    }
}
