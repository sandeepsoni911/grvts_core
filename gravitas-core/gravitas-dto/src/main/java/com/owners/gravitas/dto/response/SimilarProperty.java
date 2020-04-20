package com.owners.gravitas.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.hubzu.chimera.dto.srp.matchrank.MatchRankDetails;

/**
 * @author gururasm
 *
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class SimilarProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    private String propertyId;
    
    private String streetNumber;

    private String addressLine1="";
    
    private String addressLine2="";
    
    private String unitNumber="";

    private String streetName;

    private String city;

    private String state;

    private String zip;

    private Double listPrice;

    private Integer bedRooms;

    private Double bathRooms;

    private BigInteger space;

    private String propertyStatus;

    private String thumnailUrl;

    private Long savedDaysCount;

    private Long updatedDaysCount;

    private String listingUrl;

    private boolean isHideAddress;

    private boolean isSaved;
    
    private String propertyType;
    
    private String propertyListingType;
    
    private Double distance;
    
    private Date createdDate;
    
    private int imageCount;
    
    private List<String> imageUrlList;
    
    private String sellerOfficeName;
    
    private String sellerFirstName;
    
    private String sellerLastName;
    
    private String mlsId;
    
    private boolean logoReqSRP;

    private boolean logoVisible;
    
    private String mlsBoardName;
    
    private String mlsBoardImageURL;
    
    private String listingStatus;
    
    private String mappedListingStatus;
    
    private String listingSubStatus;
    
    private boolean requestATour;
    
    private Double matchRankScore;

    private Map< String, Integer > features;

    //private MatchRankDetails matchRankDetails;

    /**
     * @return the propertyId
     */
    public String getPropertyId() {
        return propertyId;
    }

    /**
     * @param propertyId
     *            the propertyId to set
     */
    public void setPropertyId( String propertyId ) {
        this.propertyId = propertyId;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber( String streetNumber ) {
        this.streetNumber = streetNumber;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1( String addressLine1 ) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2( String addressLine2 ) {
        this.addressLine2 = addressLine2;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber( String unitNumber ) {
        this.unitNumber = unitNumber;
    }

    /**
     * @return the streetName
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * @param streetName
     *            the streetName to set
     */
    public void setStreetName( String streetName ) {
        this.streetName = streetName;
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
    public void setCity( String city ) {
        this.city = city;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState( String state ) {
        this.state = state;
    }

    /**
     * @return the zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip
     *            the zip to set
     */
    public void setZip( String zip ) {
        this.zip = zip;
    }

    /**
     * @return the listPrice
     */
    public Double getListPrice() {
        return listPrice;
    }

    /**
     * @param listPrice
     *            the listPrice to set
     */
    public void setListPrice( Double listPrice ) {
        this.listPrice = listPrice;
    }

    /**
     * @return the bedRooms
     */
    public Integer getBedRooms() {
        return bedRooms;
    }

    /**
     * @param bedRooms
     *            the bedRooms to set
     */
    public void setBedRooms( Integer bedRooms ) {
        this.bedRooms = bedRooms;
    }

    /**
     * @return the bathRooms
     */
    public Double getBathRooms() {
        return bathRooms;
    }

    /**
     * @param bathRooms
     *            the bathRooms to set
     */
    public void setBathRooms( Double bathRooms ) {
        this.bathRooms = bathRooms;
    }

    /**
     * @return the space
     */
    public BigInteger getSpace() {
        return space;
    }

    /**
     * @param space
     *            the space to set
     */
    public void setSpace( BigInteger space ) {
        this.space = space;
    }

    /**
     * @return the propertyStatus
     */
    public String getPropertyStatus() {
        return propertyStatus;
    }

    /**
     * @param propertyStatus
     *            the propertyStatus to set
     */
    public void setPropertyStatus( String propertyStatus ) {
        this.propertyStatus = propertyStatus;
    }

    /**
     * @return the thumnailUrl
     */
    public String getThumnailUrl() {
        return thumnailUrl;
    }

    /**
     * @param thumnailUrl
     *            the thumnailUrl to set
     */
    public void setThumnailUrl( String thumnailUrl ) {
        this.thumnailUrl = thumnailUrl;
    }

    /**
     * @return the savedDaysCount
     */
    public Long getSavedDaysCount() {
        return savedDaysCount;
    }

    /**
     * @param savedDaysCount
     *            the savedDaysCount to set
     */
    public void setSavedDaysCount( Long savedDaysCount ) {
        this.savedDaysCount = savedDaysCount;
    }

    /**
     * @return the updatedDaysCount
     */
    public Long getUpdatedDaysCount() {
        return updatedDaysCount;
    }

    /**
     * @param updatedDaysCount
     *            the updatedDaysCount to set
     */
    public void setUpdatedDaysCount( Long updatedDaysCount ) {
        this.updatedDaysCount = updatedDaysCount;
    }

    /**
     * @return the listingUrl
     */
    public String getListingUrl() {
        return listingUrl;
    }

    /**
     * @param listingUrl
     *            the listingUrl to set
     */
    public void setListingUrl( String listingUrl ) {
        this.listingUrl = listingUrl;
    }

    /**
     * @return the isHideAddress
     */
    public boolean isHideAddress() {
        return isHideAddress;
    }

    /**
     * @param isHideAddress
     *            the isHideAddress to set
     */
    public void setHideAddress( boolean isHideAddress ) {
        this.isHideAddress = isHideAddress;
    }

    /**
     * @return the isSaved
     */
    public boolean isSaved() {
        return isSaved;
    }

    /**
     * @param isSaved
     *            the isSaved to set
     */
    public void setSaved( boolean isSaved ) {
        this.isSaved = isSaved;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType( String propertyType ) {
        this.propertyType = propertyType;
    }

    public String getPropertyListingType() {
        return propertyListingType;
    }

    public void setPropertyListingType( String propertyListingType ) {
        this.propertyListingType = propertyListingType;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance( Double distance ) {
        this.distance = distance;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate( Date createdDate ) {
        this.createdDate = createdDate;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount( int imageCount ) {
        this.imageCount = imageCount;
    }

    public List< String > getImageUrlList() {
        return imageUrlList;
    }

    public void setImageUrlList( List< String > imageUrlList ) {
        this.imageUrlList = imageUrlList;
    }

    public String getSellerOfficeName() {
        return sellerOfficeName;
    }

    public void setSellerOfficeName( String sellerOfficeName ) {
        this.sellerOfficeName = sellerOfficeName;
    }

    public String getSellerFirstName() {
        return sellerFirstName;
    }

    public void setSellerFirstName( String sellerFirstName ) {
        this.sellerFirstName = sellerFirstName;
    }

    public String getSellerLastName() {
        return sellerLastName;
    }

    public void setSellerLastName( String sellerLastName ) {
        this.sellerLastName = sellerLastName;
    }

    public String getMlsId() {
        return mlsId;
    }

    public void setMlsId( String mlsId ) {
        this.mlsId = mlsId;
    }

    public boolean isLogoReqSRP() {
        return logoReqSRP;
    }

    public void setLogoReqSRP( boolean logoReqSRP ) {
        this.logoReqSRP = logoReqSRP;
    }

    public boolean isLogoVisible() {
        return logoVisible;
    }

    public void setLogoVisible( boolean logoVisible ) {
        this.logoVisible = logoVisible;
    }

    public String getMlsBoardName() {
        return mlsBoardName;
    }

    public void setMlsBoardName( String mlsBoardName ) {
        this.mlsBoardName = mlsBoardName;
    }

    public String getMlsBoardImageURL() {
        return mlsBoardImageURL;
    }

    public void setMlsBoardImageURL( String mlsBoardImageURL ) {
        this.mlsBoardImageURL = mlsBoardImageURL;
    }

    public String getListingStatus() {
        return listingStatus;
    }

    public void setListingStatus( String listingStatus ) {
        this.listingStatus = listingStatus;
    }

    public String getMappedListingStatus() {
        return mappedListingStatus;
    }

    public void setMappedListingStatus( String mappedListingStatus ) {
        this.mappedListingStatus = mappedListingStatus;
    }

    public String getListingSubStatus() {
        return listingSubStatus;
    }

    public void setListingSubStatus( String listingSubStatus ) {
        this.listingSubStatus = listingSubStatus;
    }

    public boolean isRequestATour() {
        return requestATour;
    }

    public void setRequestATour( boolean requestATour ) {
        this.requestATour = requestATour;
    }

    public Double getMatchRankScore() {
        return matchRankScore;
    }

    public void setMatchRankScore( Double matchRankScore ) {
        this.matchRankScore = matchRankScore;
    }

    public Map< String, Integer > getFeatures() {
        return features;
    }

    public void setFeatures( Map< String, Integer > features ) {
        this.features = features;
    }

/*    public MatchRankDetails getMatchRankDetails() {
        return matchRankDetails;
    }

    public void setMatchRankDetails( MatchRankDetails matchRankDetails ) {
        this.matchRankDetails = matchRankDetails;
    }
*/
}
