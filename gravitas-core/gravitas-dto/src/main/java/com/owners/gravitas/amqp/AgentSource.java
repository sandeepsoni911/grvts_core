package com.owners.gravitas.amqp;

import java.util.Date;

import com.owners.gravitas.dto.BaseDTO;

/**
 * The Class AgentSource.
 *
 * @author vishwanathm
 */
public class AgentSource extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -7640323245221081823L;

    /** The id. */
    private String id;

    /** full name. */
    private String name;

    /** email. */
    private String email;

    /** phone number. */
    private String phone;

    /** The state. */
    private String state;

    /** The agent status. */
    private String status;

    /** The address 1. */
    private String address1;

    /** The address 2. */
    private String address2;

    /** The city. */
    private String city;

    /** The home zip. */
    private String homeZip;

    /** The notes. */
    private String notes;

    /** The driving radius. */
    private String drivingRadius;

    /** The record type id. */
    private String recordTypeId;

    /** The agent type. */
    private String agentType;

    /** The starting date. */
    private Date startingDate;

    /** The mobile carrier. */
    private String mobileCarrier;

    /** The license. */
    private String license;

    /** The is field agent. */
    private boolean isFieldAgent;

    /** The is available. */
    private boolean isAvailable;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param crmId
     *            the new id
     */
    public void setId( final String crmId ) {
        this.id = crmId;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName( final String name ) {
        this.name = name;
    }

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
     * Gets the state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state.
     *
     * @param state
     *            the new state
     */
    public void setState( final String state ) {
        this.state = state;
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

    /**
     * Gets the address1.
     *
     * @return the address1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Sets the address1.
     *
     * @param address1
     *            the new address1
     */
    public void setAddress1( final String address1 ) {
        this.address1 = address1;
    }

    /**
     * Gets the address2.
     *
     * @return the address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Sets the address2.
     *
     * @param address2
     *            the new address2
     */
    public void setAddress2( final String address2 ) {
        this.address2 = address2;
    }

    /**
     * Gets the city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     *
     * @param city
     *            the new city
     */
    public void setCity( final String city ) {
        this.city = city;
    }

    /**
     * Gets the home zip.
     *
     * @return the home zip
     */
    public String getHomeZip() {
        return homeZip;
    }

    /**
     * Sets the home zip.
     *
     * @param homeZip
     *            the new home zip
     */
    public void setHomeZip( final String homeZip ) {
        this.homeZip = homeZip;
    }

    /**
     * Gets the notes.
     *
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the notes.
     *
     * @param notes
     *            the new notes
     */
    public void setNotes( final String notes ) {
        this.notes = notes;
    }

    /**
     * Gets the driving radius.
     *
     * @return the driving radius
     */
    public String getDrivingRadius() {
        return drivingRadius;
    }

    /**
     * Sets the driving radius.
     *
     * @param drivingRadius
     *            the new driving radius
     */
    public void setDrivingRadius( final String drivingRadius ) {
        this.drivingRadius = drivingRadius;
    }

    /**
     * Gets the record type id.
     *
     * @return the record type id
     */
    public String getRecordTypeId() {
        return recordTypeId;
    }

    /**
     * Sets the record type id.
     *
     * @param recordTypeId
     *            the new record type id
     */
    public void setRecordTypeId( final String recordTypeId ) {
        this.recordTypeId = recordTypeId;
    }

    /**
     * Gets the agent type.
     *
     * @return the agent type
     */
    public String getAgentType() {
        return agentType;
    }

    /**
     * Sets the agent type.
     *
     * @param agentType
     *            the new agent type
     */
    public void setAgentType( final String agentType ) {
        this.agentType = agentType;
    }

    /**
     * Gets the starting date.
     *
     * @return the starting date
     */
    public Date getStartingDate() {
        return startingDate;
    }

    /**
     * Sets the starting date.
     *
     * @param startingDate
     *            the new starting date
     */
    public void setStartingDate( final Date startingDate ) {
        this.startingDate = startingDate;
    }

    /**
     * Gets the mobile carrier.
     *
     * @return the mobile carrier
     */
    public String getMobileCarrier() {
        return mobileCarrier;
    }

    /**
     * Sets the mobile carrier.
     *
     * @param mobileCarrier
     *            the new mobile carrier
     */
    public void setMobileCarrier( final String mobileCarrier ) {
        this.mobileCarrier = mobileCarrier;
    }

    /**
     * Gets the license.
     *
     * @return the license
     */
    public String getLicense() {
        return license;
    }

    /**
     * Sets the license.
     *
     * @param license
     *            the new license
     */
    public void setLicense( final String license ) {
        this.license = license;
    }

    /**
     * Checks if is field agent.
     *
     * @return true, if is field agent
     */
    public boolean isFieldAgent() {
        return isFieldAgent;
    }

    /**
     * Sets the field agent.
     *
     * @param isFieldAgent
     *            the new field agent
     */
    public void setFieldAgent( final boolean isFieldAgent ) {
        this.isFieldAgent = isFieldAgent;
    }

    /**
     * Checks if is available.
     *
     * @return true, if is available
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Sets the available.
     *
     * @param isAvailable
     *            the new available
     */
    public void setAvailable( final boolean isAvailable ) {
        this.isAvailable = isAvailable;
    }
}
