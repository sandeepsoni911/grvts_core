package com.owners.gravitas.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/***
 * The class ScheduleTourLeadRequest.
 * 
 * @author imranmoh
 *
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class ScheduleTourLeadRequest {

    /** The title. */
    private String title;

    /** The description. */
    private String description;

    /** The dueDtm. */
    private String dueDtm;

    /** The meet at. */
    private String location;

    /** The type. */
    private String type;

    /** The status. */
    private String status;

    /** The createdBy. */
    private String createdBy;

    /** The primary meeting indicator. */
    @JsonProperty
    private boolean isPrimary;

    /** The co shopping Id. */
    private String coShoppingId;

    /** email. */
    private String email;

    /** first name. */
    private String firstName;

    /** last name. */
    private String lastName;

    /** listing Id. */
    private String listingId;

    /** Mls Id. */
    private String mlsId;

    /** offer amount. */
    private String offerAmount;

    /** property address. */
    private String propertyAddress;

    /** property bathroom. */
    private String propertyBathroom;

    /** property bedroom. */
    private String propertyBedroom;

    /** property half bathroom. */
    private String propertyHalfBathroom;

    /** property image url. */
    private String propertyImageURL;

    /** property price. */
    private String propertyPrice;

    /** property tour information. */
    private String propertyTourInformation;

    /** question. */
    private String question;

    /** request type. */
    private String requestType;

    /** user Id. */
    private String userId;

    /** The timeZone. */
    private String timezone;
    
    /** The warm call transfer flag */
    private Boolean isWarmTransferCall;

    /**
	 * @return the isWarmTransferCall
	 */
	public Boolean getIsWarmTransferCall() {
		return isWarmTransferCall;
	}

	/**
	 * @param isWarmTransferCall the isWarmTransferCall to set
	 */
	public void setIsWarmTransferCall(Boolean isWarmTransferCall) {
		this.isWarmTransferCall = isWarmTransferCall;
	}

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle( final String title ) {
        this.title = title;
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
    public void setDescription( final String description ) {
        this.description = description;
    }

    /**
     * @return the dueDtm
     */
    public String getDueDtm() {
        return dueDtm;
    }

    /**
     * @param dueDtm
     *            the dueDtm to set
     */
    public void setDueDtm( final String dueDtm ) {
        this.dueDtm = dueDtm;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation( final String location ) {
        this.location = location;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType( final String type ) {
        this.type = type;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus( final String status ) {
        this.status = status;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     *            the createdBy to set
     */
    public void setCreatedBy( final String createdBy ) {
        this.createdBy = createdBy;
    }

    /**
     * @return the isPrimary
     */
    public boolean isPrimary() {
        return isPrimary;
    }

    /**
     * @param isPrimary
     *            the isPrimary to set
     */
    public void setPrimary( final boolean isPrimary ) {
        this.isPrimary = isPrimary;
    }

    /**
     * @return the coShoppingId
     */
    public String getCoShoppingId() {
        return coShoppingId;
    }

    /**
     * @param coShoppingId
     *            the coShoppingId to set
     */
    public void setCoShoppingId( final String coShoppingId ) {
        this.coShoppingId = coShoppingId;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail( final String email ) {
        this.email = email;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName( final String firstName ) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName( final String lastName ) {
        this.lastName = lastName;
    }

    /**
     * @return the listingId
     */
    public String getListingId() {
        return listingId;
    }

    /**
     * @param listingId
     *            the listingId to set
     */
    public void setListingId( final String listingId ) {
        this.listingId = listingId;
    }

    /**
     * @return the mlsId
     */
    public String getMlsId() {
        return mlsId;
    }

    /**
     * @param mlsId
     *            the mlsId to set
     */
    public void setMlsId( final String mlsId ) {
        this.mlsId = mlsId;
    }

    /**
     * @return the offerAmount
     */
    public String getOfferAmount() {
        return offerAmount;
    }

    /**
     * @param offerAmount
     *            the offerAmount to set
     */
    public void setOfferAmount( final String offerAmount ) {
        this.offerAmount = offerAmount;
    }

    /**
     * @return the propertyAddress
     */
    public String getPropertyAddress() {
        return propertyAddress;
    }

    /**
     * @param propertyAddress
     *            the propertyAddress to set
     */
    public void setPropertyAddress( final String propertyAddress ) {
        this.propertyAddress = propertyAddress;
    }

    /**
     * @return the propertyBathroom
     */
    public String getPropertyBathroom() {
        return propertyBathroom;
    }

    /**
     * @param propertyBathroom
     *            the propertyBathroom to set
     */
    public void setPropertyBathroom( final String propertyBathroom ) {
        this.propertyBathroom = propertyBathroom;
    }

    /**
     * @return the propertyBedroom
     */
    public String getPropertyBedroom() {
        return propertyBedroom;
    }

    /**
     * @param propertyBedroom
     *            the propertyBedroom to set
     */
    public void setPropertyBedroom( final String propertyBedroom ) {
        this.propertyBedroom = propertyBedroom;
    }

    /**
     * @return the propertyHalfBathroom
     */
    public String getPropertyHalfBathroom() {
        return propertyHalfBathroom;
    }

    /**
     * @param propertyHalfBathroom
     *            the propertyHalfBathroom to set
     */
    public void setPropertyHalfBathroom( final String propertyHalfBathroom ) {
        this.propertyHalfBathroom = propertyHalfBathroom;
    }

    /**
     * @return the propertyImageURL
     */
    public String getPropertyImageURL() {
        return propertyImageURL;
    }

    /**
     * @param propertyImageURL
     *            the propertyImageURL to set
     */
    public void setPropertyImageURL( final String propertyImageURL ) {
        this.propertyImageURL = propertyImageURL;
    }

    /**
     * @return the propertyPrice
     */
    public String getPropertyPrice() {
        return propertyPrice;
    }

    /**
     * @param propertyPrice
     *            the propertyPrice to set
     */
    public void setPropertyPrice( final String propertyPrice ) {
        this.propertyPrice = propertyPrice;
    }

    /**
     * @return the propertyTourInformation
     */
    public String getPropertyTourInformation() {
        return propertyTourInformation;
    }

    /**
     * @param propertyTourInformation
     *            the propertyTourInformation to set
     */
    public void setPropertyTourInformation( final String propertyTourInformation ) {
        this.propertyTourInformation = propertyTourInformation;
    }

    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question
     *            the question to set
     */
    public void setQuestion( final String question ) {
        this.question = question;
    }

    /**
     * @return the requestType
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * @param requestType
     *            the requestType to set
     */
    public void setRequestType( final String requestType ) {
        this.requestType = requestType;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId( final String userId ) {
        this.userId = userId;
    }

    /**
     * @return the timeZone
     */
    public String getTimezone() {
		return timezone;
	}

    /**
     * @param timezone
     *            the timeZone to set
     */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	
}
