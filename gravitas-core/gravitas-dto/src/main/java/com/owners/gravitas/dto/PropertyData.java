package com.owners.gravitas.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class PropertyData.
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class PropertyData extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 552757569996710352L;
    
    private String propertyID;

    /** The property address. */
    private PropertyAddress propertyAddress;

    /** The bath rooms. */
    private BigDecimal bathRooms;

    /** The bed rooms. */
    private String bedRooms;

    /** The price. */
    private String price;
    
    private String propertyType;

    /** The size. */
    private String size;
    
    private String description;

    /** The images. */
    private List< Image > images;

    /** The pdp url. */
    private String pdpURL;

    /** The canonical pdp url. */
    private String canonicalPdpURL;

    /** The mls id. */
    private String mlsID;
    
    private Map< String, List< String > > mapParameters;

    private String mlsBoardImageURL;
    
    private boolean hideAddress;
    
    /**
     * @return the propertyID
     */
    public String getPropertyID() {
        return propertyID;
    }

    /**
     * @param propertyID
     *            the propertyID to set
     */
    public void setPropertyID( String propertyID ) {
        this.propertyID = propertyID;
    }

    public String getMlsBoardImageURL() {
        return mlsBoardImageURL;
    }

    public void setMlsBoardImageURL(String mlsBoardImageURL) {
        this.mlsBoardImageURL = mlsBoardImageURL;
    }

    /**
     * Gets the pdp url.
     *
     * @return the pdpURL
     */
    public String getPdpURL() {
        return pdpURL;
    }

    /**
     * Sets the pdp url.
     *
     * @param pdpURL
     *            the pdpURL to set
     */
    public void setPdpURL( String pdpURL ) {
        this.pdpURL = pdpURL;
    }

    /**
     * Gets the canonical pdp url.
     *
     * @return the canonicalPdpURL
     */
    public String getCanonicalPdpURL() {
        return canonicalPdpURL;
    }

    /**
     * Sets the canonical pdp url.
     *
     * @param canonicalPdpURL
     *            the canonicalPdpURL to set
     */
    public void setCanonicalPdpURL( String canonicalPdpURL ) {
        this.canonicalPdpURL = canonicalPdpURL;
    }

    /**
     * Gets the property address.
     *
     * @return the propertyAddress
     */
    public PropertyAddress getPropertyAddress() {
        return propertyAddress;
    }

    /**
     * Sets the property address.
     *
     * @param propertyAddress
     *            the propertyAddress to set
     */
    public void setPropertyAddress( final PropertyAddress propertyAddress ) {
        this.propertyAddress = propertyAddress;
    }

    /**
     * Gets the bath rooms.
     *
     * @return the bathRooms
     */
    public BigDecimal getBathRooms() {
        return bathRooms;
    }

    /**
     * Sets the bath rooms.
     *
     * @param bathRooms
     *            the bathRooms to set
     */
    public void setBathRooms( final BigDecimal bathRooms ) {
        this.bathRooms = bathRooms;
    }

    /**
     * Gets the bed rooms.
     *
     * @return the bedRooms
     */
    public String getBedRooms() {
        return bedRooms;
    }

    /**
     * Sets the bed rooms.
     *
     * @param bedRooms
     *            the bedRooms to set
     */
    public void setBedRooms( final String bedRooms ) {
        this.bedRooms = bedRooms;
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
     *            the price to set
     */
    public void setPrice( final String price ) {
        this.price = price;
    }
    
    /**
     * @return the propertyType
     */
    public String getPropertyType() {
        return propertyType;
    }

    /**
     * @param propertyType
     *            the propertyType to set
     */
    public void setPropertyType( String propertyType ) {
        this.propertyType = propertyType;
    }

    /**
     * Gets the size.
     *
     * @return the size
     */
    public String getSize() {
        return size;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription( String description ) {
        this.description = description;
    }

    /**
     * Sets the size.
     *
     * @param size
     *            the size to set
     */
    public void setSize( final String size ) {
        this.size = size;
    }

    /**
     * Gets the images.
     *
     * @return the images
     */
    public List< Image > getImages() {
        return images;
    }

    /**
     * Sets the images.
     *
     * @param images
     *            the images to set
     */
    public void setImages( final List< Image > images ) {
        this.images = images;
    }

    /**
     * Gets the mls id.
     *
     * @return the mls id
     */
    public String getMlsID() {
        return mlsID;
    }

    /**
     * Sets the mls id.
     *
     * @param mlsID
     *            the new mls id
     */
    public void setMlsID( String mlsID ) {
        this.mlsID = mlsID;
    }

    public Map<String, List<String>> getMapParameters() {
        return mapParameters;
    }

    public void setMapParameters(Map<String, List<String>> mapParameters) {
        this.mapParameters = mapParameters;
    }
    
    public boolean isHideAddress() {
        return hideAddress;
    }

    public void setHideAddress(boolean hideAddress) {
        this.hideAddress = hideAddress;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PropertyData [propertyID=");
        builder.append(propertyID);
        builder.append(", propertyAddress=");
        builder.append(propertyAddress);
        builder.append(", bathRooms=");
        builder.append(bathRooms);
        builder.append(", bedRooms=");
        builder.append(bedRooms);
        builder.append(", price=");
        builder.append(price);
        builder.append(", propertyType=");
        builder.append(propertyType);
        builder.append(", size=");
        builder.append(size);
        builder.append(", description=");
        builder.append(description);
        builder.append(", images=");
        builder.append(images);
        builder.append(", pdpURL=");
        builder.append(pdpURL);
        builder.append(", canonicalPdpURL=");
        builder.append(canonicalPdpURL);
        builder.append(", mlsID=");
        builder.append(mlsID);
        builder.append(", mapParameters=");
        builder.append(mapParameters);
        builder.append(", mlsBoardImageURL=");
        builder.append(mlsBoardImageURL);
        builder.append(", hideAddress=");
        builder.append(hideAddress);
        builder.append("]");
        return builder.toString();
    }
    
}
