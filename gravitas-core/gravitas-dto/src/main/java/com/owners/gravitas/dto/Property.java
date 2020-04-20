package com.owners.gravitas.dto;

import static com.owners.gravitas.constants.Constants.REG_EXP_ALPANUMERICS;
import static com.owners.gravitas.constants.Constants.REG_EXP_NUMERICS;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * The Class Property.
 *
 * @author vishwanathm
 */
public class Property extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4392284603457248929L;

    /** The id. */
    @NotNull( message = "error.listing.id.required" )
    @Size( max = 100, message = "error.listing.id.size" )
    @Pattern( regexp = REG_EXP_ALPANUMERICS, message = "error.listing.id.format" )
    private String listingId;

    /** The property type. */
    @Size( max = 120, message = "error.property.type.size" )
    private String propertyType;

    /** The price. */
    @Size( max = 10, message = "error.price.size" )
    @Pattern( regexp = REG_EXP_NUMERICS, message = "error.price.format" )
    private String price;

    /** The number beds. */
    @Size( max = 10, message = "error.noofbeds.size" )
    @Pattern( regexp = REG_EXP_NUMERICS, message = "error.noofbeds.format" )
    private String numberBeds;

    /** The number full baths. */
    @Size( max = 10, message = "error.nooffullbaths.size" )
    @Pattern( regexp = REG_EXP_NUMERICS, message = "error.nooffullbaths.format" )
    private String numberFullBaths;

    /** The number half baths. */
    @Size( max = 10, message = "error.noofhalfbaths.size" )
    @Pattern( regexp = REG_EXP_NUMERICS, message = "error.noofhalfbaths.format" )
    private String numberHalfBaths;

    /** The square feet. */
    @Size( max = 10, message = "error.squarefeet.size" )
    @Pattern( regexp = REG_EXP_NUMERICS, message = "error.squarefeet.format" )
    private String squareFeet;

    /** The address. */
    @Valid
    @NotNull( message = "error.address.required" )
    private SellerAddress address;

    /**
     * Gets the property type.
     *
     * @return the property type
     */
    public String getPropertyType() {
        return propertyType;
    }

    /**
     * Sets the property type.
     *
     * @param propertyType
     *            the new property type
     */
    public void setPropertyType( final String propertyType ) {
        this.propertyType = propertyType;
    }

    /**
     * Gets the price.
     *
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets the price.
     *
     * @param price
     *            the new price
     */
    public void setPrice( final String price ) {
        this.price = price;
    }

    /**
     * Gets the number beds.
     *
     * @return the number beds
     */
    public String getNumberBeds() {
        return numberBeds;
    }

    /**
     * Sets the number beds.
     *
     * @param numberBeds
     *            the new number beds
     */
    public void setNumberBeds( final String numberBeds ) {
        this.numberBeds = numberBeds;
    }

    /**
     * Gets the number full baths.
     *
     * @return the number full baths
     */
    public String getNumberFullBaths() {
        return numberFullBaths;
    }

    /**
     * Sets the number full baths.
     *
     * @param numberFullBaths
     *            the new number full baths
     */
    public void setNumberFullBaths( final String numberFullBaths ) {
        this.numberFullBaths = numberFullBaths;
    }

    /**
     * Gets the number half baths.
     *
     * @return the number half baths
     */
    public String getNumberHalfBaths() {
        return numberHalfBaths;
    }

    /**
     * Sets the number half baths.
     *
     * @param numberHalfBaths
     *            the new number half baths
     */
    public void setNumberHalfBaths( final String numberHalfBaths ) {
        this.numberHalfBaths = numberHalfBaths;
    }

    /**
     * Gets the square feet.
     *
     * @return the square feet
     */
    public String getSquareFeet() {
        return squareFeet;
    }

    /**
     * Sets the square feet.
     *
     * @param squareFeet
     *            the new square feet
     */
    public void setSquareFeet( final String squareFeet ) {
        this.squareFeet = squareFeet;
    }

    /**
     * Gets the address.
     *
     * @return the address
     */
    public SellerAddress getAddress() {
        return address;
    }

    /**
     * Sets the address.
     *
     * @param address
     *            the new address
     */
    public void setAddress( final SellerAddress address ) {
        this.address = address;
    }

    /**
     * Gets the listing id.
     *
     * @return the listingId
     */
    public String getListingId() {
        return listingId;
    }

    /**
     * Sets the listing id.
     *
     * @param listingId
     *            the listingId to set
     */
    public void setListingId( final String listingId ) {
        this.listingId = listingId;
    }
}
