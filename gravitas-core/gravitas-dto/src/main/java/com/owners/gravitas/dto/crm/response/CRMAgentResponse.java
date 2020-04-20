package com.owners.gravitas.dto.crm.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.owners.gravitas.dto.BaseDTO;

/**
 * The Class CRMAgentResponse.
 *
 * @author amits,raviz
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class CRMAgentResponse extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3336168996885646112L;

    /** The id. */
    private String id;

    /** full name. */
    private String name;

    /** The email. */
    private String email;

    /** The phone. */
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
    @JsonProperty( "id" )
    public String getId() {
        return this.id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    @JsonProperty( "Id" )
    public void setSavedId( final String id ) {
        this.id = id;
    }

    /**
     * Gets the saved id.
     *
     * @return the saved id
     */
    public String getSavedId() {
        return this.id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    @JsonProperty( "id" )
    public void setId( final String id ) {
        this.id = id;
    }

    /**
     * Gets the full name.
     *
     * @return the full name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the full name.
     *
     * @param name
     *            the full name to set
     */
    @JsonProperty( "Name" )
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
     *            the email to set
     */
    @JsonProperty( "Email__c" )
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
     *            the phone to set
     */
    @JsonProperty( "Phone__c" )
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
     *            the state to set
     */
    @JsonProperty( "State__c" )
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
     *            the status to set
     */
    @JsonProperty( "Status__c" )
    public void setStatus( final String status ) {
        this.status = status;
    }

    /**
     * Gets the address 1.
     *
     * @return the address 1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Sets the address 1.
     *
     * @param address1
     *            the new address 1
     */
    @JsonProperty( "Address1__c" )
    public void setAddress1( final String address1 ) {
        this.address1 = address1;
    }

    /**
     * Gets the address 2.
     *
     * @return the address 2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Sets the address 2.
     *
     * @param address2
     *            the new address 2
     */
    @JsonProperty( "Address2__c" )
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
    @JsonProperty( "City__c" )
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
    @JsonProperty( "Zip_Code__c" )
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
    @JsonProperty( "Notes__c" )
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
    @JsonProperty( "Driving_Radius_miles__c" )
    public void setDrivingRadius( final String drivingRadius ) {
        this.drivingRadius = drivingRadius;
    }

    /**
     * Gets the record type id.
     *
     * @return the recordTypeId
     */
    public String getRecordTypeId() {
        return recordTypeId;
    }

    /**
     * Sets the record type id.
     *
     * @param recordTypeId
     *            the recordTypeId to set
     */
    @JsonProperty( "RecordTypeId" )
    public void setRecordTypeId( final String recordTypeId ) {
        this.recordTypeId = recordTypeId;
    }

    /**
     * Gets the agent type.
     *
     * @return the agentType
     */
    public String getAgentType() {
        return agentType;
    }

    /**
     * Sets the agent type.
     *
     * @param agentType
     *            the agentType to set
     */
    @JsonProperty( "Agent_Type__c" )
    public void setAgentType( final String agentType ) {
        this.agentType = agentType;
    }

    /**
     * Gets the starting date.
     *
     * @return the startingDate
     */
    public Date getStartingDate() {
        return startingDate;
    }

    /**
     * Sets the starting date.
     *
     * @param startingDate
     *            the startingDate to set
     */
    @JsonProperty( "Agent_App_Starting_Date__c" )
    public void setStartingDate( final Date startingDate ) {
        this.startingDate = startingDate;
    }

    /**
     * Gets the mobile carrier.
     *
     * @return the mobileCarrier
     */
    public String getMobileCarrier() {
        return mobileCarrier;
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
     *            the license to set
     */
    @JsonProperty( "License_Number__c" )
    public void setLicense( final String license ) {
        this.license = license;
    }

    /**
     * Sets the mobile carrier.
     *
     * @param mobileCarrier
     *            the mobileCarrier to set
     */
    @JsonProperty( "Agent_Mobile_Carrier__c" )
    public void setMobileCarrier( final String mobileCarrier ) {
        this.mobileCarrier = mobileCarrier;
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
    @JsonProperty( "Field_Agent__c" )
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
    @JsonProperty( "Active__c" )
    public void setAvailable( final boolean isAvailable ) {
        this.isAvailable = isAvailable;
    }

}
