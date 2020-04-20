package com.owners.gravitas.dto.crm.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.owners.gravitas.dto.BaseDTO;

/**
 * The Class Opportunity.
 *
 * @author vishwanathm
 */
public class CRMAccountRequest extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4467091893969514476L;

    /** The first name. */
    private String name;

    /** The phone. */
    private String phone;

    /** The mailing street. */
    private String street;

    /** The mailing city. */
    private String city;

    /** The mailing state. */
    private String state;

    /** The mailing postal code. */
    private String zip;

    /** The record type id. */
    private String recordTypeId;

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the first name.
     *
     * @param name
     *            the new first name
     */
    @JsonProperty( "Name" )
    public void setName( final String name ) {
        this.name = name;
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
    @JsonProperty( "RecordTypeId" )
    public void setRecordTypeId( final String recordTypeId ) {
        this.recordTypeId = recordTypeId;
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
    @JsonProperty( "Phone" )
    public void setPhone( final String phone ) {
        this.phone = phone;
    }

    /**
     * Gets the mailing street.
     *
     * @return the mailing street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the mailing street.
     *
     * @param street
     *            the new mailing street
     */
    @JsonProperty( "BillingStreet" )
    public void setStreet( final String street ) {
        this.street = street;
    }

    /**
     * Gets the mailing city.
     *
     * @return the mailing city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the mailing city.
     *
     * @param city
     *            the new mailing city
     */
    @JsonProperty( "BillingCity" )
    public void setCity( final String city ) {
        this.city = city;
    }

    /**
     * Gets the mailing state.
     *
     * @return the mailing state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the mailing state.
     *
     * @param state
     *            the new mailing state
     */
    @JsonProperty( "BillingState" )
    public void setState( final String state ) {
        this.state = state;
    }

    /**
     * Gets the mailing postal code.
     *
     * @return the mailing postal code
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the mailing postal code.
     *
     * @param zip
     *            the new mailing postal code
     */
    @JsonProperty( "BillingPostalCode" )
    public void setZip( final String zip ) {
        this.zip = zip;
    }

}
