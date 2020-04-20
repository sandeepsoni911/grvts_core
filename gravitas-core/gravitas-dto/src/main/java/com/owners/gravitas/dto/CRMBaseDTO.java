package com.owners.gravitas.dto;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class CRMLead.
 *
 * @author vishwanathm
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class CRMBaseDTO extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4748707900518631620L;

    /** first name. */
    private String firstName;

    /** last name. */
    private String lastName;

    /** email. */
    private String email;

    /** phone number. */
    private String phone;

    /** company. */
    private String company;

    /** source *. */
    private String source;

    /** The comments. */
    private String comments;

    /** The listing id. */
    private String listingId;

    /** The record type. */
    private String recordType;

    /** The property address. */
    private String propertyAddress;

    /** The request type. */
    private String requestType;

    /** The lead status. */
    private String leadStatus;

    /** The pre approved for mortgage. */
    private String preApprovedForMortgage;

    /** The working with realtor. */
    private String workingWithRealtor;

    /** The buyer readiness timeline. */
    private String buyerReadinessTimeline;

    /** The marketing opt in. */
    private boolean marketingOptIn;

    /** The preferred contact time. */
    private String preferredContactTime;

    /** The preferred contact method. */
    private String preferredContactMethod;

    /** The price range. */
    private String priceRange;

    /** The buyer lead quality. */
    private String buyerLeadQuality;

    /** The preferred language. */
    private String preferredLanguage;

    /** The state. */
    private String state;

    /** The mls id. */
    private String mlsId;

    /** The alid. */
    private String alId;

    /** The interested zipcodes. */
    private String interestedZipcodes;

    /** The owners com identifier. */
    private String ownersComIdentifier;

    /** The offer amount. */
    private String offerAmount;

    /** The listing creation date. */
    private DateTime listingCreationDate;

    /** The financing. */
    private String financing;

    /** The website. */
    private String website;

    /** The lead source url. */
    private String leadSourceUrl;

    /** The saved search values. */
    private String savedSearchValues;

    /** The earnest money deposit. */
    private String earnestMoneyDeposit;

    /** The purchase method. */
    private String purchaseMethod;

    /** The down payment. */
    private String downPayment;

    /** The property tour information. */
    private String propertyTourInformation;

    /** The additional property data. */
    private String additionalPropertyData;

    /** The website session data. */
    private String websiteSessionData;

    /** The owners visitor id. */
    private String ownersVisitorId;

    /** The message. */
    private String message;

    /** The median price. */
    private String medianPrice;

    /** The last visit date time. */
    private DateTime lastVisitDateTime;

    /** The loan number. */
    private int loanNumber;

    /** Interested In Finanacing Option. */
    private boolean interestedInFinancing;

    /** The created date time. */
    private DateTime createdDateTime;

    /** The last modified date. */
    private DateTime lastModifiedDate;
    
    /** The  property  Bedroom. */
    private String propertyBedroom;
    
    /** The  property  Bathroom. */
    private String propertyBathroom;
    
    /** The  property  SquareFoot. */
    private String propertySquareFoot;
    
    /** The  property Type. */
    private String propertyType;
    
    /** The  inquiry date . */
    private DateTime inquiryDate;
    
    /** The  search City . */
    private String searchCity;
    
    /** The  search Attributes . */
    private String searchAttributes;
    
    /** The  Partner Identifier . */
    private String partnerIdentifier;
    
    /** The Lot Size **/
    private String lotSize;
    
    /** The Credit Score */
    private String creditScore;
    
    /** The Home Type */
    private String homeType;
    
    /** The browser  */
    private String browser;
    
    /** The Operating System */
    private String os;
    
    /** The device */
    private String device;
    
    

    /**
	 * @return the lotSize
	 */
	public String getLotSize() {
		return lotSize;
	}

	/**
	 * @param lotSize the lotSize to set
	 */
	public void setLotSize(String lotSize) {
		this.lotSize = lotSize;
	}

	/**
	 * @return the creditScore
	 */
	public String getCreditScore() {
		return creditScore;
	}

	/**
	 * @param creditScore the creditScore to set
	 */
	public void setCreditScore(String creditScore) {
		this.creditScore = creditScore;
	}

	/**
	 * @return the homeType
	 */
	public String getHomeType() {
		return homeType;
	}

	/**
	 * @param homeType the homeType to set
	 */
	public void setHomeType(String homeType) {
		this.homeType = homeType;
	}

	/**
	 * @return the browser
	 */
	public String getBrowser() {
		return browser;
	}

	/**
	 * @param browser the browser to set
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}

	/**
	 * @return the os
	 */
	public String getOs() {
		return os;
	}

	/**
	 * @param os the os to set
	 */
	public void setOs(String os) {
		this.os = os;
	}

	/**
	 * @return the device
	 */
	public String getDevice() {
		return device;
	}

	/**
	 * @param device the device to set
	 */
	public void setDevice(String device) {
		this.device = device;
	}

	/**
	 * @return the propertyBedroom
	 */
	public String getPropertyBedroom() {
		return propertyBedroom;
	}

	/**
	 * @param propertyBedroom the propertyBedroom to set
	 */
	public void setPropertyBedroom(String propertyBedroom) {
		this.propertyBedroom = propertyBedroom;
	}

	/**
	 * @return the propertyBathroom
	 */
	public String getPropertyBathroom() {
		return propertyBathroom;
	}

	/**
	 * @param propertyBathroom the propertyBathroom to set
	 */
	public void setPropertyBathroom(String propertyBathroom) {
		this.propertyBathroom = propertyBathroom;
	}

	/**
	 * @return the propertySquareFoot
	 */
	public String getPropertySquareFoot() {
		return propertySquareFoot;
	}

	/**
	 * @param propertySquareFoot the propertySquareFoot to set
	 */
	public void setPropertySquareFoot(String propertySquareFoot) {
		this.propertySquareFoot = propertySquareFoot;
	}

	/**
	 * @return the propertyType
	 */
	public String getPropertyType() {
		return propertyType;
	}

	/**
	 * @param propertyType the propertyType to set
	 */
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	/**
	 * @return the inquiryDate
	 */
	public DateTime getInquiryDate() {
		return inquiryDate;
	}

	/**
	 * @param inquiryDate the inquiryDate to set
	 */
	public void setInquiryDate(DateTime inquiryDate) {
		this.inquiryDate = inquiryDate;
	}


	/**
	 * @return the searchCity
	 */
	public String getSearchCity() {
		return searchCity;
	}

	/**
	 * @param searchCity the searchCity to set
	 */
	public void setSearchCity(String searchCity) {
		this.searchCity = searchCity;
	}

	/**
	 * @return the searchAttributes
	 */
	public String getSearchAttributes() {
		return searchAttributes;
	}

	/**
	 * @param searchAttributes the searchAttributes to set
	 */
	public void setSearchAttributes(String searchAttributes) {
		this.searchAttributes = searchAttributes;
	}

	/**
	 * @return the partnerIdentifier
	 */
	public String getPartnerIdentifier() {
		return partnerIdentifier;
	}

	/**
	 * @param partnerIdentifier the partnerIdentifier to set
	 */
	public void setPartnerIdentifier(String partnerIdentifier) {
		this.partnerIdentifier = partnerIdentifier;
	}

	/**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName
     *            the new first name
     */
    public void setFirstName( final String firstName ) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName
     *            the new last name
     */
    public void setLastName( final String lastName ) {
        this.lastName = lastName;
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
     *            the new email
     */
    public void setEmail( final String email ) {
        this.email = email;
    }

    /**
     * Gets phone.
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
    public void setPhone( final String phone ) {
        this.phone = phone;
    }

    /**
     * Gets the company.
     *
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * Sets the company.
     *
     * @param company
     *            the new company
     */
    public void setCompany( final String company ) {
        this.company = company;
    }

    /**
     * Gets the source.
     *
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the source.
     *
     * @param source
     *            the source to set
     */
    public void setSource( final String source ) {
        this.source = source;
    }

    /**
     * Gets the comments.
     *
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the comments.
     *
     * @param comments
     *            the comments to set
     */
    public void setComments( final String comments ) {
        this.comments = comments;
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

    /**
     * Gets the record type.
     *
     * @return the recordType
     */
    public String getRecordType() {
        return recordType;
    }

    /**
     * Sets the record type.
     *
     * @param recordType
     *            the recordType to set
     */
    public void setRecordType( final String recordType ) {
        this.recordType = recordType;
    }

    /**
     * Gets the property address.
     *
     * @return the property address
     */
    public String getPropertyAddress() {
        return propertyAddress;
    }

    /**
     * Sets the property address.
     *
     * @param propertyAddress
     *            the new property address
     */
    public void setPropertyAddress( final String propertyAddress ) {
        this.propertyAddress = propertyAddress;
    }

    /**
     * Gets the request type.
     *
     * @return the request type
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * Sets the request type.
     *
     * @param requestType
     *            the new request type
     */
    public void setRequestType( final String requestType ) {
        this.requestType = requestType;
    }

    /**
     * Gets the lead status.
     *
     * @return the lead status
     */
    public String getLeadStatus() {
        return leadStatus;
    }

    /**
     * Sets the lead status.
     *
     * @param leadStatus
     *            the new lead status
     */

    public void setLeadStatus( final String leadStatus ) {
        this.leadStatus = leadStatus;
    }

    /**
     * Gets the pre approved for mortgage.
     *
     * @return the pre approved for mortgage
     */
    public String getPreApprovedForMortgage() {
        return preApprovedForMortgage;
    }

    /**
     * Sets the pre approved for mortgage.
     *
     * @param preApprovedForMortgage
     *            the new pre approved for mortgage
     */
    public void setPreApprovedForMortgage( final String preApprovedForMortgage ) {
        this.preApprovedForMortgage = preApprovedForMortgage;
    }

    /**
     * Gets the working with realtor.
     *
     * @return the working with realtor
     */
    public String getWorkingWithRealtor() {
        return workingWithRealtor;
    }

    /**
     * Sets the working with realtor.
     *
     * @param workingWithRealtor
     *            the new working with realtor
     */
    public void setWorkingWithRealtor( final String workingWithRealtor ) {
        this.workingWithRealtor = workingWithRealtor;
    }

    /**
     * Gets the buyer readiness timeline.
     *
     * @return the buyer readiness timeline
     */
    public String getBuyerReadinessTimeline() {
        return buyerReadinessTimeline;
    }

    /**
     * Sets the buyer readiness timeline.
     *
     * @param buyerReadinessTimeline
     *            the new buyer readiness timeline
     */
    public void setBuyerReadinessTimeline( final String buyerReadinessTimeline ) {
        this.buyerReadinessTimeline = buyerReadinessTimeline;
    }

    /**
     * Gets the marketing opt in.
     *
     * @return the marketing opt in
     */
    public boolean getMarketingOptIn() {
        return marketingOptIn;
    }

    /**
     * Sets the marketing opt in.
     *
     * @param marketingOptIn
     *            the new marketing opt in
     */
    public void setMarketingOptIn( final boolean marketingOptIn ) {
        this.marketingOptIn = marketingOptIn;
    }

    /**
     * Gets the preferred contact time.
     *
     * @return the preferred contact time
     */
    public String getPreferredContactTime() {
        return preferredContactTime;
    }

    /**
     * Sets the preferred contact time.
     *
     * @param preferredContactTime
     *            the new preferred contact time
     */
    public void setPreferredContactTime( final String preferredContactTime ) {
        this.preferredContactTime = preferredContactTime;
    }

    /**
     * Gets the preferred contact method.
     *
     * @return the preferred contact method
     */
    public String getPreferredContactMethod() {
        return preferredContactMethod;
    }

    /**
     * Sets the preferred contact method.
     *
     * @param preferredContactMethod
     *            the new preferred contact method
     */
    public void setPreferredContactMethod( final String preferredContactMethod ) {
        this.preferredContactMethod = preferredContactMethod;
    }

    /**
     * Gets the price range.
     *
     * @return the price range
     */
    public String getPriceRange() {
        return priceRange;
    }

    /**
     * Sets the price range.
     *
     * @param priceRange
     *            the new price range
     */
    public void setPriceRange( final String priceRange ) {
        this.priceRange = priceRange;
    }

    /**
     * Gets the buyer lead quality.
     *
     * @return the buyer lead quality
     */
    public String getBuyerLeadQuality() {
        return buyerLeadQuality;
    }

    /**
     * Sets the buyer lead quality.
     *
     * @param buyerLeadQuality
     *            the new buyer lead quality
     */
    public void setBuyerLeadQuality( final String buyerLeadQuality ) {
        this.buyerLeadQuality = buyerLeadQuality;
    }

    /**
     * Gets the preferred language.
     *
     * @return the preferred language
     */
    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    /**
     * Sets the preferred language.
     *
     * @param preferredLanguage
     *            the new preferred language
     */
    public void setPreferredLanguage( final String preferredLanguage ) {
        this.preferredLanguage = preferredLanguage;
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
     * Gets the mls id.
     *
     * @return the mls id
     */
    public String getMlsId() {
        return mlsId;
    }

    /**
     * Sets the mls id.
     *
     * @param mlsId
     *            the new mls id
     */
    public void setMlsId( final String mlsId ) {
        this.mlsId = mlsId;
    }

    /**
     * Gets the alid.
     *
     * @return the alid
     */
    public String getAlId() {
        return alId;
    }

    /**
     * Sets the alid.
     *
     * @param aLID
     *            the new alid
     */
    public void setAlId( final String aLID ) {
        alId = aLID;
    }

    /**
     * Gets the interested zipcodes.
     *
     * @return the interested zipcodes
     */
    public String getInterestedZipcodes() {
        return interestedZipcodes;
    }

    /**
     * Sets the interested zipcodes.
     *
     * @param interestedZipcodes
     *            the new interested zipcodes
     */
    public void setInterestedZipcodes( final String interestedZipcodes ) {
        this.interestedZipcodes = interestedZipcodes;
    }

    /**
     * Gets the owners com identifier.
     *
     * @return the owners com identifier
     */
    public String getOwnersComIdentifier() {
        return ownersComIdentifier;
    }

    /**
     * Sets the owners com identifier.
     *
     * @param ownersComIdentifier
     *            the new owners com identifier
     */
    public void setOwnersComIdentifier( final String ownersComIdentifier ) {
        this.ownersComIdentifier = ownersComIdentifier;
    }

    /**
     * Gets the offer amount.
     *
     * @return the offer amount
     */
    public String getOfferAmount() {
        return offerAmount;
    }

    /**
     * Sets the offer amount.
     *
     * @param offerAmount
     *            the new offer amount
     */
    public void setOfferAmount( final String offerAmount ) {
        this.offerAmount = offerAmount;
    }

    /**
     * Gets the listing creation date.
     *
     * @return the listing creation date
     */
    public DateTime getListingCreationDate() {
        return listingCreationDate;
    }

    /**
     * Sets the listing creation date.
     *
     * @param listingCreationDate
     *            the new listing creation date
     */
    public void setListingCreationDate( final DateTime listingCreationDate ) {
        this.listingCreationDate = listingCreationDate;
    }

    /**
     * Gets the financing.
     *
     * @return the financing
     */
    public String getFinancing() {
        return financing;
    }

    /**
     * Sets the financing.
     *
     * @param financing
     *            the new financing
     */
    public void setFinancing( final String financing ) {
        this.financing = financing;
    }

    /**
     * Gets the website.
     *
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Sets the website.
     *
     * @param website
     *            the new website
     */
    public void setWebsite( final String website ) {
        this.website = website;
    }

    /**
     * Gets the lead source url.
     *
     * @return the lead source url
     */
    public String getLeadSourceUrl() {
        return leadSourceUrl;
    }

    /**
     * Sets the lead source url.
     *
     * @param leadSourceUrl
     *            the new lead source url
     */
    public void setLeadSourceUrl( final String leadSourceUrl ) {
        this.leadSourceUrl = leadSourceUrl;
    }

    /**
     * Gets the saved search values.
     *
     * @return the saved search values
     */
    public String getSavedSearchValues() {
        return savedSearchValues;
    }

    /**
     * Sets the saved search values.
     *
     * @param savedSearchValues
     *            the new saved search values
     */
    public void setSavedSearchValues( final String savedSearchValues ) {
        this.savedSearchValues = savedSearchValues;
    }

    /**
     * Gets the earnest money deposit.
     *
     * @return the earnest money deposit
     */
    public String getEarnestMoneyDeposit() {
        return earnestMoneyDeposit;
    }

    /**
     * Sets the earnest money deposit.
     *
     * @param earnestMoneyDeposit
     *            the new earnest money deposit
     */
    public void setEarnestMoneyDeposit( final String earnestMoneyDeposit ) {
        this.earnestMoneyDeposit = earnestMoneyDeposit;
    }

    /**
     * Gets the purchase method.
     *
     * @return the purchase method
     */
    public String getPurchaseMethod() {
        return purchaseMethod;
    }

    /**
     * Sets the purchase method.
     *
     * @param purchaseMethod
     *            the new purchase method
     */
    public void setPurchaseMethod( final String purchaseMethod ) {
        this.purchaseMethod = purchaseMethod;
    }

    /**
     * Gets the down payment.
     *
     * @return the down payment
     */
    public String getDownPayment() {
        return downPayment;
    }

    /**
     * Sets the down payment.
     *
     * @param downPayment
     *            the new down payment
     */
    public void setDownPayment( final String downPayment ) {
        this.downPayment = downPayment;
    }

    /**
     * Gets the property tour information.
     *
     * @return the property tour information
     */
    public String getPropertyTourInformation() {
        return propertyTourInformation;
    }

    /**
     * Sets the property tour information.
     *
     * @param propertyTourInformation
     *            the new property tour information
     */
    public void setPropertyTourInformation( final String propertyTourInformation ) {
        this.propertyTourInformation = propertyTourInformation;
    }

    /**
     * Gets the additional property data.
     *
     * @return the additional property data
     */
    public String getAdditionalPropertyData() {
        return additionalPropertyData;
    }

    /**
     * Sets the additional property data.
     *
     * @param additionalPropertyData
     *            the new additional property data
     */
    public void setAdditionalPropertyData( final String additionalPropertyData ) {
        this.additionalPropertyData = additionalPropertyData;
    }

    /**
     * Gets the website session data.
     *
     * @return the website session data
     */
    public String getWebsiteSessionData() {
        return websiteSessionData;
    }

    /**
     * Sets the website session data.
     *
     * @param websiteSessionData
     *            the new website session data
     */
    public void setWebsiteSessionData( final String websiteSessionData ) {
        this.websiteSessionData = websiteSessionData;
    }

    /**
     * Gets the owners visitor id.
     *
     * @return the owners visitor id
     */
    public String getOwnersVisitorId() {
        return ownersVisitorId;
    }

    /**
     * Sets the owners visitor id.
     *
     * @param ownersVisitorId
     *            the new owners visitor id
     */
    public void setOwnersVisitorId( final String ownersVisitorId ) {
        this.ownersVisitorId = ownersVisitorId;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message
     *            the new message
     */
    public void setMessage( final String message ) {
        this.message = message;
    }

    /**
     * Gets the median price.
     *
     * @return the median price
     */
    public String getMedianPrice() {
        return medianPrice;
    }

    /**
     * Sets the median price.
     *
     * @param medianPrice
     *            the new median price
     */
    public void setMedianPrice( final String medianPrice ) {
        this.medianPrice = medianPrice;
    }

    /**
     * Gets the last visit date time.
     *
     * @return the last visit date time
     */
    public DateTime getLastVisitDateTime() {
        return lastVisitDateTime;
    }

    /**
     * Sets the last visit date time.
     *
     * @param lastVisitDateTime
     *            the new last visit date time
     */
    public void setLastVisitDateTime( final DateTime lastVisitDateTime ) {
        this.lastVisitDateTime = lastVisitDateTime;
    }

    /**
     * Gets the loan number.
     *
     * @return the loan number
     */
    @JsonProperty( "Loan_Number__c" )
    public int getLoanNumber() {
        return loanNumber;
    }

    /**
     * Sets the loan number.
     *
     * @param loanNumber
     *            the new loan number
     */
    public void setLoanNumber( final int loanNumber ) {
        this.loanNumber = loanNumber;
    }

    /**
     * Checks if is interested in financing.
     *
     * @return the interestedinfinancing
     */
    public boolean isInterestedInFinancing() {
        return interestedInFinancing;
    }

    /**
     * Sets the interested in financing.
     *
     * @param interestedInFinancing
     *            the interestedInFinancing to set
     */
    public void setInterestedInFinancing( final boolean interestedInFinancing ) {
        this.interestedInFinancing = interestedInFinancing;
    }

    /**
     * Gets the created date time.
     *
     * @return the created date time
     */
    public DateTime getCreatedDateTime() {
        return createdDateTime;
    }

    /**
     * Sets the created date time.
     *
     * @param createdDateTime
     *            the new created date time
     */
    public void setCreatedDateTime( final DateTime createdDateTime ) {
        this.createdDateTime = createdDateTime;
    }

    /**
     * Gets the last modified date.
     *
     * @return the last modified date
     */
    public DateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets the last modified date.
     *
     * @param lastModifiedDate
     *            the new last modified date
     */
    public void setLastModifiedDate( final DateTime lastModifiedDate ) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
