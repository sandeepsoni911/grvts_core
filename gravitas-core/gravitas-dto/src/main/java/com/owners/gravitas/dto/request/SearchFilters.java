package com.owners.gravitas.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This class contains saved search filter details.
 * 
 * @author kushwaja
 *
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class SearchFilters implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The notification frequency. */
    private String notificationFrequency;

    /** The property type. */
    private String propertyType;

    /** The property source. */
    private String propertySource;

    /** The min bed. */
    private String bed;

    /** The min bath. */
    private String bath;

    /** The min price. */
    private BigDecimal minPrice;

    /** The max price. */
    private BigDecimal maxPrice;

    /** The max square footage. */
    private Integer maxSquareFootage;

    /** The min square footage. */
    private Integer minSquareFootage;

    /** The featured by owners. */
    private Byte featuredByOwners;

    /** The new listings. */
    private Byte newListings;

    /** The has open house. */
    private Byte hasOpenHouse;

    /** min lot size */
    private Integer minLotSize;

    /** max lot size */
    private Integer maxLotSize;

    /** min year built */
    private Integer minYearBuilt;

    /** max year built */
    private Integer maxYearBuilt;

    private Integer maxDaysMarket;

    private String searchKeywords;

    private String searchSort;

    /**
     * Gets the notification frequency.
     *
     * @return the notificationFrequency
     */
    public String getNotificationFrequency() {
        return notificationFrequency;
    }

    /**
     * Sets the notification frequency.
     *
     * @param notificationFrequency
     *            the notificationFrequency to set
     */
    public void setNotificationFrequency( String notificationFrequency ) {
        this.notificationFrequency = notificationFrequency;
    }

    /**
     * Gets the property type.
     *
     * @return the propertyType
     */
    public String getPropertyType() {
        return propertyType;
    }

    /**
     * Sets the property type.
     *
     * @param propertyType
     *            the propertyType to set
     */
    public void setPropertyType( String propertyType ) {
        this.propertyType = propertyType;
    }

    /**
     * Gets the min price.
     *
     * @return the minPrice
     */
    public BigDecimal getMinPrice() {
        return minPrice;
    }

    /**
     * Sets the min price.
     *
     * @param minPrice
     *            the minPrice to set
     */
    public void setMinPrice( BigDecimal minPrice ) {
        this.minPrice = minPrice;
    }

    /**
     * Gets the max price.
     *
     * @return the maxPrice
     */
    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    /**
     * Sets the max price.
     *
     * @param maxPrice
     *            the maxPrice to set
     */
    public void setMaxPrice( BigDecimal maxPrice ) {
        this.maxPrice = maxPrice;
    }

    /**
     * Gets the property source.
     *
     * @return the propertySource
     */
    public String getPropertySource() {
        return propertySource;
    }

    /**
     * Sets the property source.
     *
     * @param propertySource
     *            the propertySource to set
     */
    public void setPropertySource( String propertySource ) {
        this.propertySource = propertySource;
    }

    /**
     * Gets the max square footage.
     *
     * @return the maxSquareFootage
     */
    public Integer getMaxSquareFootage() {
        return maxSquareFootage;
    }

    /**
     * Sets the max square footage.
     *
     * @param maxSquareFootage
     *            the maxSquareFootage to set
     */
    public void setMaxSquareFootage( Integer maxSquareFootage ) {
        this.maxSquareFootage = maxSquareFootage;
    }

    /**
     * Gets the min square footage.
     *
     * @return the minSquareFootage
     */
    public Integer getMinSquareFootage() {
        return minSquareFootage;
    }

    /**
     * Sets the min square footage.
     *
     * @param minSquareFootage
     *            the minSquareFootage to set
     */
    public void setMinSquareFootage( Integer minSquareFootage ) {
        this.minSquareFootage = minSquareFootage;
    }

    /**
     * Gets the featured by owners.
     *
     * @return the featuredByOwners
     */
    public Byte getFeaturedByOwners() {
        return featuredByOwners;
    }

    /**
     * Sets the featured by owners.
     *
     * @param featuredByOwners
     *            the featuredByOwners to set
     */
    public void setFeaturedByOwners( Byte featuredByOwners ) {
        this.featuredByOwners = featuredByOwners;
    }

    /**
     * Gets the new listings.
     *
     * @return the newListings
     */
    public Byte getNewListings() {
        return newListings;
    }

    /**
     * Sets the new listings.
     *
     * @param newListings
     *            the newListings to set
     */
    public void setNewListings( Byte newListings ) {
        this.newListings = newListings;
    }

    /**
     * Gets the checks for open house.
     *
     * @return the hasOpenHouse
     */
    public Byte getHasOpenHouse() {
        return hasOpenHouse;
    }

    /**
     * Sets the checks for open house.
     *
     * @param hasOpenHouse
     *            the hasOpenHouse to set
     */
    public void setHasOpenHouse( Byte hasOpenHouse ) {
        this.hasOpenHouse = hasOpenHouse;
    }

    /**
     * Gets the bed.
     *
     * @return the bed
     */
    public String getBed() {
        return bed;
    }

    /**
     * Sets the bed.
     *
     * @param bed
     *            the bed to set
     */
    public void setBed( String bed ) {
        this.bed = bed;
    }

    /**
     * Gets the bath.
     *
     * @return the bath
     */
    public String getBath() {
        return bath;
    }

    /**
     * Sets the bath.
     *
     * @param bath
     *            the bath to set
     */
    public void setBath( String bath ) {
        this.bath = bath;
    }

    public Integer getMinLotSize() {
        return minLotSize;
    }

    public void setMinLotSize( Integer minLotSize ) {
        this.minLotSize = minLotSize;
    }

    public Integer getMaxLotSize() {
        return maxLotSize;
    }

    public void setMaxLotSize( Integer maxLotSize ) {
        this.maxLotSize = maxLotSize;
    }

    public Integer getMinYearBuilt() {
        return minYearBuilt;
    }

    public void setMinYearBuilt( Integer minYearBuilt ) {
        this.minYearBuilt = minYearBuilt;
    }

    public Integer getMaxYearBuilt() {
        return maxYearBuilt;
    }

    public void setMaxYearBuilt( Integer maxYearBuilt ) {
        this.maxYearBuilt = maxYearBuilt;
    }

    public Integer getMaxDaysMarket() {
        return maxDaysMarket;
    }

    public void setMaxDaysMarket( Integer maxDaysMarket ) {
        this.maxDaysMarket = maxDaysMarket;
    }

    /**
     * @return the searchKeywords
     */
    public String getSearchKeywords() {
        return searchKeywords;
    }

    /**
     * @param searchKeywords
     *            the searchKeywords to set
     */
    public void setSearchKeywords( String searchKeywords ) {
        this.searchKeywords = searchKeywords;
    }

    /**
     * @return the searchSort
     */
    public String getSearchSort() {
        return searchSort;
    }

    /**
     * @param searchSort
     *            the searchSort to set
     */
    public void setSearchSort( String searchSort ) {
        this.searchSort = searchSort;
    }

}
