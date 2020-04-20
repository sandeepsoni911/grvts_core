package com.owners.gravitas.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * The Class SellerAddress.
 *
 * @author vishwanathm
 */
public class SellerAddress extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8976054514384084581L;

    /** The address1. */
    @NotBlank( message = "error.address1.required" )
    @Size( max = 255, message = "error.address1.size" )
    private String address1;

    /** The address2. */
    private String address2;

    /** The city. */
    @NotBlank( message = "error.city.required" )
    @Size( max = 255, message = "error.city.size" )
    private String city;

    /** The state. */
    @NotBlank( message = "error.state.required" )
    @Size( max = 255, message = "error.state.size" )
    private String state;

    /** The county. */
    @NotBlank( message = "error.county.required" )
    @Size( max = 255, message = "error.county.size" )
    private String county;

    /** The zip. */
    @NotBlank( message = "error.zip.required" )
    @Size( max = 255, message = "error.zip.size" )
    private String zip;

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
     * Gets the county.
     *
     * @return the county
     */
    public String getCounty() {
        return county;
    }

    /**
     * Sets the county.
     *
     * @param county
     *            the new county
     */
    public void setCounty( final String county ) {
        this.county = county;
    }

    /**
     * Gets the zip.
     *
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the zip.
     *
     * @param zip
     *            the new zip
     */
    public void setZip( final String zip ) {
        this.zip = zip;
    }

}
