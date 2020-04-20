package com.owners.gravitas.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * request/response object for {@link SaveSearchResponse } entity.
 * 
 * @author gururasm
 *
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class SaveSearch implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The save search id. */
    private String saveSearchId;

    /** The search name. */
    private String searchName;

    private Integer polygonUiId;

    /** The search text. */
    private String polygonLabel;

    /** The user id. */
    private String userId;

    /** The notification frequency. */
    private String notificationFrequency;

    /** The property type. */
    private String propertyType;

    /** The property source. */
    private String propertySource;

    /** The min bed. */
    private Integer minBed;

    /** The max bed. */
    private Integer maxBed;

    /** The min bath. */
    private Integer minBath;

    /** The max bath. */
    private Integer maxBath;

    /** The min price. */
    private BigDecimal minPrice;

    /** The max price. */
    private BigDecimal maxPrice;

    /** The max square footage. */
    private Integer maxSquareFootage;

    /** The min square footage. */
    private Integer minSquareFootage;
    
    /** min lot size */
    private Integer minLotSize;
    
    /** max lot size */
    private Integer maxLotSize;
    
    /** min year built */
    private Integer minYearBuilt;
    
    /** max year built */
    private Integer maxYearBuilt;

    /** The featured by owners. */
    private Byte featuredByOwners;

    /** The new listings. */
    private Byte newListings;

    /** The has open house. */
    private Byte hasOpenHouse;
    
    private Byte isNotify;
    
    private String searchUrl;
    
    private Integer maxDaysMarket;
    
    private String searchKeywords;
    
    private String searchSort;

    /**
	 * @return the isNotify
	 */
	public Byte getIsNotify() {
		return isNotify;
	}

	/**
	 * @param isNotify the isNotify to set
	 */
	public void setIsNotify(Byte isNotify) {
		this.isNotify = isNotify;
	}

    /**
     * Gets the save search id.
     *
     * @return the saveSearchId
     */
    public String getSaveSearchId() {
        return saveSearchId;
    }

    /**
     * Sets the save search id.
     *
     * @param saveSearchId
     *            the saveSearchId to set
     */
    public void setSaveSearchId( String saveSearchId ) {
        this.saveSearchId = saveSearchId;
    }

    /**
     * Gets the search name.
     *
     * @return the searchName
     */
    public String getSearchName() {
        return searchName;
    }

    /**
     * Sets the search name.
     *
     * @param searchName
     *            the searchName to set
     */
    public void setSearchName( String searchName ) {
        this.searchName = searchName;
    }

    /**
     * Gets the user id.
     *
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId
     *            the userId to set
     */
    public void setUserId( String userId ) {
        this.userId = userId;
    }

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
     * Gets the min bed.
     *
     * @return the minBed
     */
    public Integer getMinBed() {
        return minBed;
    }

    /**
     * Sets the min bed.
     *
     * @param minBed
     *            the minBed to set
     */
    public void setMinBed( Integer minBed ) {
        this.minBed = minBed;
    }

    /**
     * Gets the max bed.
     *
     * @return the maxBed
     */
    public Integer getMaxBed() {
        return maxBed;
    }

    /**
     * Sets the max bed.
     *
     * @param maxBed
     *            the maxBed to set
     */
    public void setMaxBed( Integer maxBed ) {
        this.maxBed = maxBed;
    }

    /**
     * Gets the min bath.
     *
     * @return the minBath
     */
    public Integer getMinBath() {
        return minBath;
    }

    /**
     * Sets the min bath.
     *
     * @param minBath
     *            the minBath to set
     */
    public void setMinBath( Integer minBath ) {
        this.minBath = minBath;
    }

    /**
     * Gets the max bath.
     *
     * @return the maxBath
     */
    public Integer getMaxBath() {
        return maxBath;
    }

    /**
     * Sets the max bath.
     *
     * @param maxBath
     *            the maxBath to set
     */
    public void setMaxBath( Integer maxBath ) {
        this.maxBath = maxBath;
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
     * @return the polygonUiId
     */
    public Integer getPolygonUiId() {
        return polygonUiId;
    }

    /**
     * @param polygonUiId the polygonUiId to set
     */
    public void setPolygonUiId( Integer polygonUiId ) {
        this.polygonUiId = polygonUiId;
    }

    /**
     * @return the polygonLabel
     */
    public String getPolygonLabel() {
        return polygonLabel;
    }

    /**
     * @param polygonLabel the polygonLabel to set
     */
    public void setPolygonLabel( String polygonLabel ) {
        this.polygonLabel = polygonLabel;
    }

    /**
     * @return the searchUrl
     */
    public String getSearchUrl() {
        return searchUrl;
    }

    /**
     * @param searchUrl the searchUrl to set
     */
    public void setSearchUrl( String searchUrl ) {
        this.searchUrl = searchUrl;
    }

    /**
     * @return the minLotSize
     */
    public Integer getMinLotSize() {
        return minLotSize;
    }

    /**
     * @param minLotSize the minLotSize to set
     */
    public void setMinLotSize( Integer minLotSize ) {
        this.minLotSize = minLotSize;
    }

    /**
     * @return the maxLotSize
     */
    public Integer getMaxLotSize() {
        return maxLotSize;
    }

    /**
     * @param maxLotSize the maxLotSize to set
     */
    public void setMaxLotSize( Integer maxLotSize ) {
        this.maxLotSize = maxLotSize;
    }

    /**
     * @return the minYearBuilt
     */
    public Integer getMinYearBuilt() {
        return minYearBuilt;
    }

    /**
     * @param minYearBuilt the minYearBuilt to set
     */
    public void setMinYearBuilt( Integer minYearBuilt ) {
        this.minYearBuilt = minYearBuilt;
    }

    /**
     * @return the maxYearBuilt
     */
    public Integer getMaxYearBuilt() {
        return maxYearBuilt;
    }

    /**
     * @param maxYearBuilt the maxYearBuilt to set
     */
    public void setMaxYearBuilt( Integer maxYearBuilt ) {
        this.maxYearBuilt = maxYearBuilt;
    }

    /**
     * @return the maxDaysMarket
     */
    public Integer getMaxDaysMarket() {
        return maxDaysMarket;
    }

    /**
     * @param maxDaysMarket the maxDaysMarket to set
     */
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
     * @param searchKeywords the searchKeywords to set
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
     * @param searchSort the searchSort to set
     */
    public void setSearchSort( String searchSort ) {
        this.searchSort = searchSort;
    }
    
}
