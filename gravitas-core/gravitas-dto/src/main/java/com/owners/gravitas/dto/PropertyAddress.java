package com.owners.gravitas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class PropertyAddress extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 337219214989038498L;

    /** The Address line 1. */
    private String addressLine1;

    /** The Address line 2. */
    private String addressLine2;

    /** The city. */
    private String city;

    /** The state. */
    private String state;

    /** The zip code. */
    private String zip;

    private String latitude;
    
    private String longitude;

    /**
     *
     * @return
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     *
     * @param addressLine1
     */
    public void setAddressLine1( final String addressLine1 ) {
        this.addressLine1 = addressLine1;
    }

    /**
     *
     * @return
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     *
     * @param addressLine2
     */
    public void setAddressLine2( final String addressLine2 ) {
        this.addressLine2 = addressLine2;
    }

    /**
     *
     * @return
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     */
    public void setState( final String state ) {
        this.state = state;
    }

    /**
     *
     * @return
     */
    public String getZip() {
        return zip;
    }

    /**
     *
     * @param zip
     */
    public void setZip( final String zip ) {
        this.zip = zip;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity( final String city ) {
        this.city = city;
    }

    /**
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude
     *            the latitude to set
     */
    public void setLatitude( String latitude ) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude
     *            the longitude to set
     */
    public void setLongitude( String longitude ) {
        this.longitude = longitude;
    }
}
