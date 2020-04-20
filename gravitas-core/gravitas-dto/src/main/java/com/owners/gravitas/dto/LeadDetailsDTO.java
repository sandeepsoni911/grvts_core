package com.owners.gravitas.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LeadDetailsDTO extends BaseDTO {

	/** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5073898347353377795L;
    
    /** The additional property data. */
    @JsonProperty("Additional Property Data")
    private String additionalPropertyData;
    
    /** The al Id. */
    @JsonProperty("Al Id")
    private String alId;
    
    /** The buyer lead label. */
    @JsonProperty("Buyer Lead Label")
    private String buyerLeadLabel;
    
    /** The buyer lead quality. */
    @JsonProperty("Buyer Lead Quality")
    private String buyerLeadQuality;
    
    /** The buyer readiness timeline. */
    @JsonProperty("Buyer Readiness Timeline")
    private String buyerReadinessTimeline;
    
    /** The comments. */
    @JsonProperty("Comments")
    private String comments;
    
    /** The lead company */
    @JsonProperty("Company")
    private String company;
    
    /** The lead created date. */
    @JsonProperty("Created Date")
    private String createdDate;
    
    /** The down payment. */
    @JsonProperty("Down Payment")
    private String downPayment;
    
    /** The earnest money deposit. */
    @JsonProperty("Earnest Money Deposit")
    private String earnestMoneyDeposit;
    
    /** The lead email. */
    @JsonProperty("Email")
    private String email;
    
    /** The farming buyer actions. */
    @JsonProperty("Farming Buyer Actions")
    private String farmingBuyerActions;
    
    /** The farming failure code. */
    @JsonProperty("Farming failure Code")
    private String farmingfailureCode;
    
    /** The farming group. */
    @JsonProperty("Farming Group")
    private String farmingGroup;

    /** The farming status. */
    @JsonProperty("Farming Status")
    private String farmingStatus;
    
    /** The farming system actions. */
    @JsonProperty("Farming System Actions")
    private String farmingSystemActions;
    
    /** The financing. */
    @JsonProperty("Financing")
    private String financing;
    
    /** The gcl Id. */
    @JsonProperty("Gcl Id")
    private String gclId;
    
    /** The inquiry date. */
    @JsonProperty("Inquiry Date")
    private String inquiryDate;    
    
    /** The interested in financing. */
    @JsonProperty("Interested In Financing")
    private String interestedInFinancing;
    
    /** The interested zip codes. */
    @JsonProperty("Interested Zip Codes")
    private String interestedZipCodes;
    
    /** The buyer readiness timeline. */
    @JsonProperty("Lead Name")
    private String leadName;
    
    /** The buyer readiness timeline. */
    @JsonProperty("Last Modified Date")
    private String lastModifiedDate;
    
    /** The last visit date time. */
    @JsonProperty("Last Visit DateTime")
    private String lastVisitDateTime;    
    
    /** The lead source Url. */
    @JsonProperty("Lead Source Url")
    private String leadSourceUrl;
    
    /** The listing created date. */
    @JsonProperty("Listing Creation Date")
    private String listingCreationDate;
    
    /** The loan number. */
    @JsonProperty("Loan Number")
    private String loanNumber;
    
    /** The marketing option. */
    @JsonProperty("Marketing OptIn")
    private String marketingOptIn;

    /** The median price. */
    @JsonProperty("Median Price")
    private String medianPrice;
    
    /** The message. */
    @JsonProperty("Message")
    private String message; 
    
    /** The mls Id. */
    @JsonProperty("Mls Id")
    private String mlsId;

    /** The mls package type. */
    @JsonProperty("Mls Package Type")
    private String mlsPackageType;
    
    /** The notes. */
    @JsonProperty("Notes")
    private String notes;
    
    /** The offer amount. */
    @JsonProperty("Offer Amount")
    private String offerAmount;
    
    /** The order Id. */
    @JsonProperty("Order Id")
    private String orderId;
    
    /** The own home. */
    @JsonProperty("Own Home")
    private String ownHome;
    
    /** The owners com identifier. */
    @JsonProperty("Owners Com Identifier")
    private String ownersComIdentifier;

    /** The owners visitor Id. */
    @JsonProperty("Owners Visitor Id")
    private String ownersVisitorId;
    
    /** The partner identifier. */
    @JsonProperty("Partner Identifier")
    private String partnerIdentifier;
    
    /** The lead phone. */
    @JsonProperty("Phone")
    private String phone;
    
    /** The preferred contact method. */
    @JsonProperty("Preferred Contact Method")
    private String preferredContactmethod;

    /** The preferred contact time. */
    @JsonProperty("Preferred Contact Time")
    private String preferredContactTime;
    
    /** The pre approved for mortgage. */
    @JsonProperty("PreApproved For Mortgage")
    private String preApprovedForMortgage;

    /** The preferred language. */
    @JsonProperty("Preferred Language")
    private String preferredLanguage;
    
    /** The price range. */
    @JsonProperty("Price Range")
    private String priceRange;
    
    /** The property address. */
    @JsonProperty("Property Address")
    private String propertyAddress;
    
    /** The property bathroom. */
    @JsonProperty("Property Bathroom")
    private String propertyBathroom;
    
    /** The property bedroom. */
    @JsonProperty("Property Bedroom")
    private String propertyBedroom;
    
    /** The property city. */
    @JsonProperty("Property City")
    private String propertyCity;
    
    /** The property square foot. */
    @JsonProperty("Property Square Foot")
    private String propertySquareFoot;
    
    /** The property tour information. */
    @JsonProperty("Property Tour Information")
    private String propertyTourInformation;
    
    /** The property type. */
    @JsonProperty("Property Type")
    private String propertyType;    
    
    /** The purchase method. */
    @JsonProperty("Purchase Method")
    private String purchaseMethod;
    
    /** The request type. */
    @JsonProperty("Request Type")
    private String requestType; 
    
    /** The saved search values. */
    @JsonProperty("Saved Search Values")
    private String savedSearchValues;
    
    /** The lead score. */
    @JsonProperty("Score")
    private String score;
    
    /** The search attriutes. */
    @JsonProperty("Search Attributes")
    private String searchAttributes;
    
    /** The search city. */
    @JsonProperty("Search City")
    private String searchCity;        
    
    /** The current stage of lead in gravitas system. */
    @JsonProperty("Stage")
    private String stage;
    
    /** The lead state. */
    @JsonProperty("State")
    private String state;
    
    /** The lead status. */
    @JsonProperty("Status")
    private String status;
    
    /** The lead source. */
    @JsonProperty("Source")
    private String source;
    
    /** The unbounce page variant. */
    @JsonProperty("Unbounce Page Variant")
    private String unbouncePageVariant;
    
    /** The website. */
    @JsonProperty("Website")
    private String website;
    
    /** The web session data. */
    @JsonProperty("Website Session Data")
    private String websiteSessionData;
    
    /** The working with realtor. */
    @JsonProperty("Working With Realtor")
    private String workingWithRealtor;

	/**
	 * @return the additionalPropertyData
	 */
	public String getAdditionalPropertyData() {
		return additionalPropertyData;
	}

	/**
	 * @param additionalPropertyData the additionalPropertyData to set
	 */
	public void setAdditionalPropertyData(String additionalPropertyData) {
		this.additionalPropertyData = additionalPropertyData;
	}

	/**
	 * @return the alId
	 */
	public String getAlId() {
		return alId;
	}

	/**
	 * @param alId the alId to set
	 */
	public void setAlId(String alId) {
		this.alId = alId;
	}

	/**
	 * @return the buyerLeadLabel
	 */
	public String getBuyerLeadLabel() {
		return buyerLeadLabel;
	}

	/**
	 * @param buyerLeadLabel the buyerLeadLabel to set
	 */
	public void setBuyerLeadLabel(String buyerLeadLabel) {
		this.buyerLeadLabel = buyerLeadLabel;
	}

	/**
	 * @return the buyerLeadQuality
	 */
	public String getBuyerLeadQuality() {
		return buyerLeadQuality;
	}

	/**
	 * @param buyerLeadQuality the buyerLeadQuality to set
	 */
	public void setBuyerLeadQuality(String buyerLeadQuality) {
		this.buyerLeadQuality = buyerLeadQuality;
	}

	/**
	 * @return the buyerReadinessTimeline
	 */
	public String getBuyerReadinessTimeline() {
		return buyerReadinessTimeline;
	}

	/**
	 * @param buyerReadinessTimeline the buyerReadinessTimeline to set
	 */
	public void setBuyerReadinessTimeline(String buyerReadinessTimeline) {
		this.buyerReadinessTimeline = buyerReadinessTimeline;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the downPayment
	 */
	public String getDownPayment() {
		return downPayment;
	}

	/**
	 * @param downPayment the downPayment to set
	 */
	public void setDownPayment(String downPayment) {
		this.downPayment = downPayment;
	}

	/**
	 * @return the earnestMoneyDeposit
	 */
	public String getEarnestMoneyDeposit() {
		return earnestMoneyDeposit;
	}

	/**
	 * @param earnestMoneyDeposit the earnestMoneyDeposit to set
	 */
	public void setEarnestMoneyDeposit(String earnestMoneyDeposit) {
		this.earnestMoneyDeposit = earnestMoneyDeposit;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the farmingBuyerActions
	 */
	public String getFarmingBuyerActions() {
		return farmingBuyerActions;
	}

	/**
	 * @param farmingBuyerActions the farmingBuyerActions to set
	 */
	public void setFarmingBuyerActions(String farmingBuyerActions) {
		this.farmingBuyerActions = farmingBuyerActions;
	}

	/**
	 * @return the farmingfailureCode
	 */
	public String getFarmingfailureCode() {
		return farmingfailureCode;
	}

	/**
	 * @param farmingfailureCode the farmingfailureCode to set
	 */
	public void setFarmingfailureCode(String farmingfailureCode) {
		this.farmingfailureCode = farmingfailureCode;
	}

	/**
	 * @return the farmingGroup
	 */
	public String getFarmingGroup() {
		return farmingGroup;
	}

	/**
	 * @param farmingGroup the farmingGroup to set
	 */
	public void setFarmingGroup(String farmingGroup) {
		this.farmingGroup = farmingGroup;
	}

	/**
	 * @return the farmingStatus
	 */
	public String getFarmingStatus() {
		return farmingStatus;
	}

	/**
	 * @param farmingStatus the farmingStatus to set
	 */
	public void setFarmingStatus(String farmingStatus) {
		this.farmingStatus = farmingStatus;
	}

	/**
	 * @return the farmingSystemActions
	 */
	public String getFarmingSystemActions() {
		return farmingSystemActions;
	}

	/**
	 * @param farmingSystemActions the farmingSystemActions to set
	 */
	public void setFarmingSystemActions(String farmingSystemActions) {
		this.farmingSystemActions = farmingSystemActions;
	}

	/**
	 * @return the financing
	 */
	public String getFinancing() {
		return financing;
	}

	/**
	 * @param financing the financing to set
	 */
	public void setFinancing(String financing) {
		this.financing = financing;
	}

	/**
	 * @return the gclId
	 */
	public String getGclId() {
		return gclId;
	}

	/**
	 * @param gclId the gclId to set
	 */
	public void setGclId(String gclId) {
		this.gclId = gclId;
	}

	/**
	 * @return the inquiryDate
	 */
	public String getInquiryDate() {
		return inquiryDate;
	}

	/**
	 * @param inquiryDate the inquiryDate to set
	 */
	public void setInquiryDate(String inquiryDate) {
		this.inquiryDate = inquiryDate;
	}

	/**
	 * @return the interestedInFinancing
	 */
	public String getInterestedInFinancing() {
		return interestedInFinancing;
	}

	/**
	 * @param interestedInFinancing the interestedInFinancing to set
	 */
	public void setInterestedInFinancing(String interestedInFinancing) {
		this.interestedInFinancing = interestedInFinancing;
	}

	/**
	 * @return the interestedZipCodes
	 */
	public String getInterestedZipCodes() {
		return interestedZipCodes;
	}

	/**
	 * @param interestedZipCodes the interestedZipCodes to set
	 */
	public void setInterestedZipCodes(String interestedZipCodes) {
		this.interestedZipCodes = interestedZipCodes;
	}

	/**
	 * @return the lastModifiedDate
	 */
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * @return the lastVisitDateTime
	 */
	public String getLastVisitDateTime() {
		return lastVisitDateTime;
	}

	/**
	 * @param lastVisitDateTime the lastVisitDateTime to set
	 */
	public void setLastVisitDateTime(String lastVisitDateTime) {
		this.lastVisitDateTime = lastVisitDateTime;
	}

	/**
	 * @return the leadSourceUrl
	 */
	public String getLeadSourceUrl() {
		return leadSourceUrl;
	}

	/**
	 * @param leadSourceUrl the leadSourceUrl to set
	 */
	public void setLeadSourceUrl(String leadSourceUrl) {
		this.leadSourceUrl = leadSourceUrl;
	}

	/**
	 * @return the listingCreationDate
	 */
	public String getListingCreationDate() {
		return listingCreationDate;
	}

	/**
	 * @param listingCreationDate the listingCreationDate to set
	 */
	public void setListingCreationDate(String listingCreationDate) {
		this.listingCreationDate = listingCreationDate;
	}

	/**
	 * @return the loanNumber
	 */
	public String getLoanNumber() {
		return loanNumber;
	}

	/**
	 * @param loanNumber the loanNumber to set
	 */
	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}

	/**
	 * @return the marketingOptIn
	 */
	public String getMarketingOptIn() {
		return marketingOptIn;
	}

	/**
	 * @param marketingOptIn the marketingOptIn to set
	 */
	public void setMarketingOptIn(String marketingOptIn) {
		this.marketingOptIn = marketingOptIn;
	}

	/**
	 * @return the medianPrice
	 */
	public String getMedianPrice() {
		return medianPrice;
	}

	/**
	 * @param medianPrice the medianPrice to set
	 */
	public void setMedianPrice(String medianPrice) {
		this.medianPrice = medianPrice;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the mlsId
	 */
	public String getMlsId() {
		return mlsId;
	}

	/**
	 * @param mlsId the mlsId to set
	 */
	public void setMlsId(String mlsId) {
		this.mlsId = mlsId;
	}

	/**
	 * @return the mlsPackageType
	 */
	public String getMlsPackageType() {
		return mlsPackageType;
	}

	/**
	 * @param mlsPackageType the mlsPackageType to set
	 */
	public void setMlsPackageType(String mlsPackageType) {
		this.mlsPackageType = mlsPackageType;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the offerAmount
	 */
	public String getOfferAmount() {
		return offerAmount;
	}

	/**
	 * @param offerAmount the offerAmount to set
	 */
	public void setOfferAmount(String offerAmount) {
		this.offerAmount = offerAmount;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the ownHome
	 */
	public String getOwnHome() {
		return ownHome;
	}

	/**
	 * @param ownHome the ownHome to set
	 */
	public void setOwnHome(String ownHome) {
		this.ownHome = ownHome;
	}

	/**
	 * @return the ownersComIdentifier
	 */
	public String getOwnersComIdentifier() {
		return ownersComIdentifier;
	}

	/**
	 * @param ownersComIdentifier the ownersComIdentifier to set
	 */
	public void setOwnersComIdentifier(String ownersComIdentifier) {
		this.ownersComIdentifier = ownersComIdentifier;
	}

	/**
	 * @return the ownersVisitorId
	 */
	public String getOwnersVisitorId() {
		return ownersVisitorId;
	}

	/**
	 * @param ownersVisitorId the ownersVisitorId to set
	 */
	public void setOwnersVisitorId(String ownersVisitorId) {
		this.ownersVisitorId = ownersVisitorId;
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
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the preferredContactmethod
	 */
	public String getPreferredContactmethod() {
		return preferredContactmethod;
	}

	/**
	 * @param preferredContactmethod the preferredContactmethod to set
	 */
	public void setPreferredContactmethod(String preferredContactmethod) {
		this.preferredContactmethod = preferredContactmethod;
	}

	/**
	 * @return the preferredContactTime
	 */
	public String getPreferredContactTime() {
		return preferredContactTime;
	}

	/**
	 * @param preferredContactTime the preferredContactTime to set
	 */
	public void setPreferredContactTime(String preferredContactTime) {
		this.preferredContactTime = preferredContactTime;
	}

	/**
	 * @return the preApprovedForMortgage
	 */
	public String getPreApprovedForMortgage() {
		return preApprovedForMortgage;
	}

	/**
	 * @param preApprovedForMortgage the preApprovedForMortgage to set
	 */
	public void setPreApprovedForMortgage(String preApprovedForMortgage) {
		this.preApprovedForMortgage = preApprovedForMortgage;
	}

	/**
	 * @return the preferredLanguage
	 */
	public String getPreferredLanguage() {
		return preferredLanguage;
	}

	/**
	 * @param preferredLanguage the preferredLanguage to set
	 */
	public void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}

	/**
	 * @return the priceRange
	 */
	public String getPriceRange() {
		return priceRange;
	}

	/**
	 * @param priceRange the priceRange to set
	 */
	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	/**
	 * @return the propertyAddress
	 */
	public String getPropertyAddress() {
		return propertyAddress;
	}

	/**
	 * @param propertyAddress the propertyAddress to set
	 */
	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
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
	 * @return the propertyCity
	 */
	public String getPropertyCity() {
		return propertyCity;
	}

	/**
	 * @param propertyCity the propertyCity to set
	 */
	public void setPropertyCity(String propertyCity) {
		this.propertyCity = propertyCity;
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
	 * @return the propertyTourInformation
	 */
	public String getPropertyTourInformation() {
		return propertyTourInformation;
	}

	/**
	 * @param propertyTourInformation the propertyTourInformation to set
	 */
	public void setPropertyTourInformation(String propertyTourInformation) {
		this.propertyTourInformation = propertyTourInformation;
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
	 * @return the purchaseMethod
	 */
	public String getPurchaseMethod() {
		return purchaseMethod;
	}

	/**
	 * @param purchaseMethod the purchaseMethod to set
	 */
	public void setPurchaseMethod(String purchaseMethod) {
		this.purchaseMethod = purchaseMethod;
	}

	/**
	 * @return the requestType
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType the requestType to set
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * @return the savedSearchValues
	 */
	public String getSavedSearchValues() {
		return savedSearchValues;
	}

	/**
	 * @param savedSearchValues the savedSearchValues to set
	 */
	public void setSavedSearchValues(String savedSearchValues) {
		this.savedSearchValues = savedSearchValues;
	}

	/**
	 * @return the score
	 */
	public String getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(String score) {
		this.score = score;
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
	 * @return the stage
	 */
	public String getStage() {
		return stage;
	}

	/**
	 * @param stage the stage to set
	 */
	public void setStage(String stage) {
		this.stage = stage;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the unbouncePageVariant
	 */
	public String getUnbouncePageVariant() {
		return unbouncePageVariant;
	}

	/**
	 * @param unbouncePageVariant the unbouncePageVariant to set
	 */
	public void setUnbouncePageVariant(String unbouncePageVariant) {
		this.unbouncePageVariant = unbouncePageVariant;
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * @return the websiteSessionData
	 */
	public String getWebsiteSessionData() {
		return websiteSessionData;
	}

	/**
	 * @param websiteSessionData the websiteSessionData to set
	 */
	public void setWebsiteSessionData(String websiteSessionData) {
		this.websiteSessionData = websiteSessionData;
	}

	/**
	 * @return the workingWithRealtor
	 */
	public String getWorkingWithRealtor() {
		return workingWithRealtor;
	}

	/**
	 * @param workingWithRealtor the workingWithRealtor to set
	 */
	public void setWorkingWithRealtor(String workingWithRealtor) {
		this.workingWithRealtor = workingWithRealtor;
	}

	/**
	 * @return the leadName
	 */
	public String getLeadName() {
		return leadName;
	}

	/**
	 * @param leadName the leadName to set
	 */
	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}

}
