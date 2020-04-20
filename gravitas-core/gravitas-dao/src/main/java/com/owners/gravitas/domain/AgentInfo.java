package com.owners.gravitas.domain;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class AgentInfo.
 *
 * @author amits
 */
@JsonInclude( JsonInclude.Include.NON_NULL )
public class AgentInfo extends BaseDomain {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3784978779318419151L;

    /** The email. */
    private String email;

    /** The devices. */
    private Set< Device > devices = new HashSet<>();

    /** The on duty. */
    private Boolean onDuty;

    /** The off duty start dtm. */
    private Long offDutyStartDtm;

    /** The off duty end dtm. */
    private Long offDutyEndDtm;

    /** The last login dtm. */
    private Long lastLoginDtm;

    /** The last modified dtm. */
    private Long lastModifiedDtm;

    /** The phone. */
    private String phone;
    
    /** The signature. */
    private String signature;

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email
     *            the new email
     */
    public void setEmail( final String email ) {
        this.email = email;
    }

    /**
     * Gets the devices.
     *
     * @return the devices
     */
    public Set< Device > getDevices() {
        return devices;
    }

    /**
     * Sets the devices.
     *
     * @param devices
     *            the new devices
     */
    public void setDevices( final Set< Device > devices ) {
        this.devices = devices;
    }

    /**
     * Adds the device.
     *
     * @param device
     *            the device
     * @return true, if successful
     */
    public boolean addDevice( final Device device ) {
        return devices.add( device );
    }

    /**
     * Removes the device.
     *
     * @param device
     *            the device
     * @return true, if successful
     */
    public boolean removeDevice( final Device device ) {
        return devices.remove( device );
    }

    /**
     * Checks if is on duty.
     *
     * @return true, if is on duty
     */
    public Boolean isOnDuty() {
        return onDuty;
    }

    /**
     * Sets the on duty.
     *
     * @param onDuty
     *            the new on duty
     */
    public void setOnDuty( final Boolean onDuty ) {
        this.onDuty = onDuty;
    }

    /**
     * Gets the off duty start dtm.
     *
     * @return the off duty start dtm
     */
    public Long getOffDutyStartDtm() {
        return offDutyStartDtm;
    }

    /**
     * Sets the off duty start dtm.
     *
     * @param offDutyStartDtm
     *            the new off duty start dtm
     */
    public void setOffDutyStartDtm( final Long offDutyStartDtm ) {
        this.offDutyStartDtm = offDutyStartDtm;
    }

    /**
     * Gets the off duty end dtm.
     *
     * @return the off duty end dtm
     */
    public Long getOffDutyEndDtm() {
        return offDutyEndDtm;
    }

    /**
     * Sets the off duty end dtm.
     *
     * @param offDutyEndDtm
     *            the new off duty end dtm
     */
    public void setOffDutyEndDtm( final Long offDutyEndDtm ) {
        this.offDutyEndDtm = offDutyEndDtm;
    }

    /**
     * Gets the last login dtm.
     *
     * @return the last login dtm
     */
    public Long getLastLoginDtm() {
        return lastLoginDtm;
    }

    /**
     * Sets the last login dtm.
     *
     * @param lastLoginDtm
     *            the new last login dtm
     */
    public void setLastLoginDtm( final Long lastLoginDtm ) {
        this.lastLoginDtm = lastLoginDtm;
    }

    /**
     * Gets the last modified dtm.
     *
     * @return the last modified dtm
     */
    public Long getLastModifiedDtm() {
        return lastModifiedDtm;
    }

    /**
     * Sets the last modified dtm.
     *
     * @param lastModifiedDtm
     *            the new last modified dtm
     */
    public void setLastModifiedDtm( final Long lastModifiedDtm ) {
        this.lastModifiedDtm = lastModifiedDtm;
    }

    /**
     * Gets the phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone.
     *
     * @param phone
     *            the new phone
     */
    public void setPhone( final String phone ) {
        this.phone = phone;
    }
    
    /**
     * Gets the signature
     * 
     * @return the signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * 
     * Sets the signature
     * 
     * @param signature
     *            the signature to set
     */
    public void setSignature( final String signature ) {
        this.signature = signature;
    }
}
