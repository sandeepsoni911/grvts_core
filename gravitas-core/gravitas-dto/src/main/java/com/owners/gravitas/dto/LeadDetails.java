package com.owners.gravitas.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The class Lead Details
 * 
 * @author imranmoh
 *
 */
public class LeadDetails extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 5073898347353377795L;

    /** The lead name. */
    private String leadName;

    /** The lead score. */
    private String score;

    /** The lead crmId. */
    private String crmId;

    /** The lead state. */
    private String state;

    /** The lead email. */
    private String email;

    /** The lead phone. */
    private String phone;

    @JsonIgnore
    /** The lead status. */
    private String status;

    /** The lead created date. */
    private String createdDate;

    @JsonIgnore
    /** The lead company */
    private String company;

    @JsonIgnore
    /** The lead source. */
    private String source;

    @JsonIgnore
    /** The current stage of lead in gravitas system. */
    private String stage;

    /** The additional property data. */
    private String additionalPropertyData;

    /** The al Id. */
    private String alId;

    /** The buyer lead label. */
    private String buyerLeadLabel;

    /** The buyer lead quality. */
    private String buyerLeadQuality;

    /** The buyer readiness timeline. */
    private String buyerReadinessTimeline;

    /** The comments. */
    private String comments;

    /** The down payment. */
    private String downPayment;

    /** The earnest money deposit. */
    private String earnestMoneyDeposit;

    /** The farming buyer actions. */
    private String farmingBuyerActions;

    /** The farming failure code. */
    private String farmingfailureCode;

    /** The farming group. */
    private String farmingGroup;

    /** The farming status. */
    private String farmingStatus;

    /** The farming system actions. */
    private String farmingSystemActions;

    /** The financing. */
    private String financing;

    /** The gcl Id. */
    private String gclId;

    /** The inquiry date. */
    private String inquiryDate;

    /** The interested in financing. */
    private String interestedInFinancing;

    /** The interested zip codes. */
    private String interestedZipCodes;

    /** The last visit date time. */
    private String lastVisitDateTime;

    /** The lead source Url. */
    private String leadSourceUrl;

    /** The listing created date. */
    private String listingCreationDate;

    /** The loan number. */
    private String loanNumber;

    /** The marketing option. */
    private String marketingOptIn;

    /** The median price. */
    private String medianPrice;

    /** The message. */
    private String message;

    /** The mls Id. */
    private String mlsId;

    /** The mls package type. */
    private String mlsPackageType;

    /** The notes. */
    private String notes;

    /** The offer amount. */
    private String offerAmount;

    /** The order Id. */
    private String orderId;

    /** The owners com identifier. */
    private String ownersComIdentifier;

    /** The owners visitor Id. */
    private String ownersVisitorId;

    /** The own home. */
    private String ownHome;

    /** The partner identifier. */
    private String partnerIdentifier;

    /** The pre approved for mortgage. */
    private String preApprovedForMortgage;

    /** The preferred contact method. */
    private String preferredContactmethod;

    /** The preferred contact time. */
    private String preferredContactTime;

    /** The preferred language. */
    private String preferredLanguage;

    /** The price range. */
    private String priceRange;

    /** The property address. */
    private String propertyAddress;

    /** The property bathroom. */
    private String propertyBathroom;

    /** The property bedroom. */
    private String propertyBedroom;

    /** The property city. */
    private String propertyCity;

    /** The property square foot. */
    private String propertySquareFoot;

    /** The property tour information. */
    private String propertyTourInformation;

    /** The property type. */
    private String propertyType;

    /** The purchase method. */
    private String purchaseMethod;

    /** The request type. */
    private String requestType;

    /** The saved search values. */
    private String savedSearchValues;

    /** The search attributes. */
    private String searchAttributes;

    /** The search city. */
    private String searchCity;

    /** The unbounce page variant. */
    private String unbouncePageVariant;

    /** The website. */
    private String website;

    /** The web session data. */
    private String websiteSessionData;

    /** The working with realtor. */
    private String workingWithRealtor;

    /**
     * @return the leadName
     */
    public String getLeadName() {
        return leadName;
    }

    /**
     * @param leadName
     *            the leadName to set
     */
    public void setLeadName( final String leadName ) {
        this.leadName = leadName;
    }

    /**
     * @return the score
     */
    public String getScore() {
        return score;
    }

    /**
     * @param score
     *            the score to set
     */
    public void setScore( final String score ) {
        this.score = score;
    }

    /**
     * @return the crmId
     */
    public String getCrmId() {
        return crmId;
    }

    /**
     * @param crmId
     *            the crmId to set
     */
    public void setCrmId( final String crmId ) {
        this.crmId = crmId;
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
    public void setState( final String state ) {
        this.state = state;
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
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone( final String phone ) {
        this.phone = phone;
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
     * @return the createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate
     *            the createdDate to set
     */
    public void setCreatedDate( final String createdDate ) {
        this.createdDate = createdDate;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company
     *            the company to set
     */
    public void setCompany( final String company ) {
        this.company = company;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source
     *            the source to set
     */
    public void setSource( final String source ) {
        this.source = source;
    }

    /**
     * @return the stage
     */
    public String getStage() {
        return stage;
    }

    /**
     * @param stage
     *            the stage to set
     */
    public void setStage( final String stage ) {
        this.stage = stage;
    }

    /**
     * @return the additionalPropertyData
     */
    public String getAdditionalPropertyData() {
        return additionalPropertyData;
    }

    /**
     * @param additionalPropertyData
     *            the additionalPropertyData to set
     */
    public void setAdditionalPropertyData( final String additionalPropertyData ) {
        this.additionalPropertyData = additionalPropertyData;
    }

    /**
     * @return the alId
     */
    public String getAlId() {
        return alId;
    }

    /**
     * @param alId
     *            the alId to set
     */
    public void setAlId( final String alId ) {
        this.alId = alId;
    }

    /**
     * @return the buyerLeadLabel
     */
    public String getBuyerLeadLabel() {
        return buyerLeadLabel;
    }

    /**
     * @param buyerLeadLabel
     *            the buyerLeadLabel to set
     */
    public void setBuyerLeadLabel( final String buyerLeadLabel ) {
        this.buyerLeadLabel = buyerLeadLabel;
    }

    /**
     * @return the buyerLeadQuality
     */
    public String getBuyerLeadQuality() {
        return buyerLeadQuality;
    }

    /**
     * @param buyerLeadQuality
     *            the buyerLeadQuality to set
     */
    public void setBuyerLeadQuality( final String buyerLeadQuality ) {
        this.buyerLeadQuality = buyerLeadQuality;
    }

    /**
     * @return the buyerReadinessTimeline
     */
    public String getBuyerReadinessTimeline() {
        return buyerReadinessTimeline;
    }

    /**
     * @param buyerReadinessTimeline
     *            the buyerReadinessTimeline to set
     */
    public void setBuyerReadinessTimeline( final String buyerReadinessTimeline ) {
        this.buyerReadinessTimeline = buyerReadinessTimeline;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments
     *            the comments to set
     */
    public void setComments( final String comments ) {
        this.comments = comments;
    }

    /**
     * @return the downPayment
     */
    public String getDownPayment() {
        return downPayment;
    }

    /**
     * @param downPayment
     *            the downPayment to set
     */
    public void setDownPayment( final String downPayment ) {
        this.downPayment = downPayment;
    }

    /**
     * @return the earnestMoneyDeposit
     */
    public String getEarnestMoneyDeposit() {
        return earnestMoneyDeposit;
    }

    /**
     * @param earnestMoneyDeposit
     *            the earnestMoneyDeposit to set
     */
    public void setEarnestMoneyDeposit( final String earnestMoneyDeposit ) {
        this.earnestMoneyDeposit = earnestMoneyDeposit;
    }

    /**
     * @return the farmingBuyerActions
     */
    public String getFarmingBuyerActions() {
        return farmingBuyerActions;
    }

    /**
     * @param farmingBuyerActions
     *            the farmingBuyerActions to set
     */
    public void setFarmingBuyerActions( final String farmingBuyerActions ) {
        this.farmingBuyerActions = farmingBuyerActions;
    }

    /**
     * @return the farmingfailureCode
     */
    public String getFarmingfailureCode() {
        return farmingfailureCode;
    }

    /**
     * @param farmingfailureCode
     *            the farmingfailureCode to set
     */
    public void setFarmingfailureCode( final String farmingfailureCode ) {
        this.farmingfailureCode = farmingfailureCode;
    }

    /**
     * @return the farmingGroup
     */
    public String getFarmingGroup() {
        return farmingGroup;
    }

    /**
     * @param farmingGroup
     *            the farmingGroup to set
     */
    public void setFarmingGroup( final String farmingGroup ) {
        this.farmingGroup = farmingGroup;
    }

    /**
     * @return the farmingStatus
     */
    public String getFarmingStatus() {
        return farmingStatus;
    }

    /**
     * @param farmingStatus
     *            the farmingStatus to set
     */
    public void setFarmingStatus( final String farmingStatus ) {
        this.farmingStatus = farmingStatus;
    }

    /**
     * @return the farmingSystemActions
     */
    public String getFarmingSystemActions() {
        return farmingSystemActions;
    }

    /**
     * @param farmingSystemActions
     *            the farmingSystemActions to set
     */
    public void setFarmingSystemActions( final String farmingSystemActions ) {
        this.farmingSystemActions = farmingSystemActions;
    }

    /**
     * @return the financing
     */
    public String getFinancing() {
        return financing;
    }

    /**
     * @param financing
     *            the financing to set
     */
    public void setFinancing( final String financing ) {
        this.financing = financing;
    }

    /**
     * @return the gclId
     */
    public String getGclId() {
        return gclId;
    }

    /**
     * @param gclId
     *            the gclId to set
     */
    public void setGclId( final String gclId ) {
        this.gclId = gclId;
    }

    /**
     * @return the inquiryDate
     */
    public String getInquiryDate() {
        return inquiryDate;
    }

    /**
     * @param inquiryDate
     *            the inquiryDate to set
     */
    public void setInquiryDate( final String inquiryDate ) {
        this.inquiryDate = inquiryDate;
    }

    /**
     * @return the interestedInFinancing
     */
    public String getInterestedInFinancing() {
        return interestedInFinancing;
    }

    /**
     * @param interestedInFinancing
     *            the interestedInFinancing to set
     */
    public void setInterestedInFinancing( final String interestedInFinancing ) {
        this.interestedInFinancing = interestedInFinancing;
    }

    /**
     * @return the interestedZipCodes
     */
    public String getInterestedZipCodes() {
        return interestedZipCodes;
    }

    /**
     * @param interestedZipCodes
     *            the interestedZipCodes to set
     */
    public void setInterestedZipCodes( final String interestedZipCodes ) {
        this.interestedZipCodes = interestedZipCodes;
    }

    /**
     * @return the lastVisitDateTime
     */
    public String getLastVisitDateTime() {
        return lastVisitDateTime;
    }

    /**
     * @param lastVisitDateTime
     *            the lastVisitDateTime to set
     */
    public void setLastVisitDateTime( final String lastVisitDateTime ) {
        this.lastVisitDateTime = lastVisitDateTime;
    }

    /**
     * @return the leadSourceUrl
     */
    public String getLeadSourceUrl() {
        return leadSourceUrl;
    }

    /**
     * @param leadSourceUrl
     *            the leadSourceUrl to set
     */
    public void setLeadSourceUrl( final String leadSourceUrl ) {
        this.leadSourceUrl = leadSourceUrl;
    }

    /**
     * @return the listingCreationDate
     */
    public String getListingCreationDate() {
        return listingCreationDate;
    }

    /**
     * @param listingCreationDate
     *            the listingCreationDate to set
     */
    public void setListingCreationDate( final String listingCreationDate ) {
        this.listingCreationDate = listingCreationDate;
    }

    /**
     * @return the loanNumber
     */
    public String getLoanNumber() {
        return loanNumber;
    }

    /**
     * @param loanNumber
     *            the loanNumber to set
     */
    public void setLoanNumber( final String loanNumber ) {
        this.loanNumber = loanNumber;
    }

    /**
     * @return the marketingOptIn
     */
    public String getMarketingOptIn() {
        return marketingOptIn;
    }

    /**
     * @param marketingOptIn
     *            the marketingOptIn to set
     */
    public void setMarketingOptIn( final String marketingOptIn ) {
        this.marketingOptIn = marketingOptIn;
    }

    /**
     * @return the medianPrice
     */
    public String getMedianPrice() {
        return medianPrice;
    }

    /**
     * @param medianPrice
     *            the medianPrice to set
     */
    public void setMedianPrice( final String medianPrice ) {
        this.medianPrice = medianPrice;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage( final String message ) {
        this.message = message;
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
     * @return the mlsPackageType
     */
    public String getMlsPackageType() {
        return mlsPackageType;
    }

    /**
     * @param mlsPackageType
     *            the mlsPackageType to set
     */
    public void setMlsPackageType( final String mlsPackageType ) {
        this.mlsPackageType = mlsPackageType;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes
     *            the notes to set
     */
    public void setNotes( final String notes ) {
        this.notes = notes;
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
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     *            the orderId to set
     */
    public void setOrderId( final String orderId ) {
        this.orderId = orderId;
    }

    /**
     * @return the ownersComIdentifier
     */
    public String getOwnersComIdentifier() {
        return ownersComIdentifier;
    }

    /**
     * @param ownersComIdentifier
     *            the ownersComIdentifier to set
     */
    public void setOwnersComIdentifier( final String ownersComIdentifier ) {
        this.ownersComIdentifier = ownersComIdentifier;
    }

    /**
     * @return the ownersVisitorId
     */
    public String getOwnersVisitorId() {
        return ownersVisitorId;
    }

    /**
     * @param ownersVisitorId
     *            the ownersVisitorId to set
     */
    public void setOwnersVisitorId( final String ownersVisitorId ) {
        this.ownersVisitorId = ownersVisitorId;
    }

    /**
     * @return the ownHome
     */
    public String getOwnHome() {
        return ownHome;
    }

    /**
     * @param ownHome
     *            the ownHome to set
     */
    public void setOwnHome( final String ownHome ) {
        this.ownHome = ownHome;
    }

    /**
     * @return the partnerIdentifier
     */
    public String getPartnerIdentifier() {
        return partnerIdentifier;
    }

    /**
     * @param partnerIdentifier
     *            the partnerIdentifier to set
     */
    public void setPartnerIdentifier( final String partnerIdentifier ) {
        this.partnerIdentifier = partnerIdentifier;
    }

    /**
     * @return the preApprovedForMortgage
     */
    public String getPreApprovedForMortgage() {
        return preApprovedForMortgage;
    }

    /**
     * @param preApprovedForMortgage
     *            the preApprovedForMortgage to set
     */
    public void setPreApprovedForMortgage( final String preApprovedForMortgage ) {
        this.preApprovedForMortgage = preApprovedForMortgage;
    }

    /**
     * @return the preferredContactmethod
     */
    public String getPreferredContactmethod() {
        return preferredContactmethod;
    }

    /**
     * @param preferredContactmethod
     *            the preferredContactmethod to set
     */
    public void setPreferredContactmethod( final String preferredContactmethod ) {
        this.preferredContactmethod = preferredContactmethod;
    }

    /**
     * @return the preferredContactTime
     */
    public String getPreferredContactTime() {
        return preferredContactTime;
    }

    /**
     * @param preferredContactTime
     *            the preferredContactTime to set
     */
    public void setPreferredContactTime( final String preferredContactTime ) {
        this.preferredContactTime = preferredContactTime;
    }

    /**
     * @return the preferredLanguage
     */
    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    /**
     * @param preferredLanguage
     *            the preferredLanguage to set
     */
    public void setPreferredLanguage( final String preferredLanguage ) {
        this.preferredLanguage = preferredLanguage;
    }

    /**
     * @return the priceRange
     */
    public String getPriceRange() {
        return priceRange;
    }

    /**
     * @param priceRange
     *            the priceRange to set
     */
    public void setPriceRange( final String priceRange ) {
        this.priceRange = priceRange;
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
     * @return the propertyCity
     */
    public String getPropertyCity() {
        return propertyCity;
    }

    /**
     * @param propertyCity
     *            the propertyCity to set
     */
    public void setPropertyCity( final String propertyCity ) {
        this.propertyCity = propertyCity;
    }

    /**
     * @return the propertySquareFoot
     */
    public String getPropertySquareFoot() {
        return propertySquareFoot;
    }

    /**
     * @param propertySquareFoot
     *            the propertySquareFoot to set
     */
    public void setPropertySquareFoot( final String propertySquareFoot ) {
        this.propertySquareFoot = propertySquareFoot;
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
     * @return the propertyType
     */
    public String getPropertyType() {
        return propertyType;
    }

    /**
     * @param propertyType
     *            the propertyType to set
     */
    public void setPropertyType( final String propertyType ) {
        this.propertyType = propertyType;
    }

    /**
     * @return the purchaseMethod
     */
    public String getPurchaseMethod() {
        return purchaseMethod;
    }

    /**
     * @param purchaseMethod
     *            the purchaseMethod to set
     */
    public void setPurchaseMethod( final String purchaseMethod ) {
        this.purchaseMethod = purchaseMethod;
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
     * @return the savedSearchValues
     */
    public String getSavedSearchValues() {
        return savedSearchValues;
    }

    /**
     * @param savedSearchValues
     *            the savedSearchValues to set
     */
    public void setSavedSearchValues( final String savedSearchValues ) {
        this.savedSearchValues = savedSearchValues;
    }

    /**
     * @return the searchAttributes
     */
    public String getSearchAttributes() {
        return searchAttributes;
    }

    /**
     * @param searchAttributes
     *            the searchAttributes to set
     */
    public void setSearchAttributes( final String searchAttributes ) {
        this.searchAttributes = searchAttributes;
    }

    /**
     * @return the searchCity
     */
    public String getSearchCity() {
        return searchCity;
    }

    /**
     * @param searchCity
     *            the searchCity to set
     */
    public void setSearchCity( final String searchCity ) {
        this.searchCity = searchCity;
    }

    /**
     * @return the unbouncePageVariant
     */
    public String getUnbouncePageVariant() {
        return unbouncePageVariant;
    }

    /**
     * @param unbouncePageVariant
     *            the unbouncePageVariant to set
     */
    public void setUnbouncePageVariant( final String unbouncePageVariant ) {
        this.unbouncePageVariant = unbouncePageVariant;
    }

    /**
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website
     *            the website to set
     */
    public void setWebsite( final String website ) {
        this.website = website;
    }

    /**
     * @return the websiteSessionData
     */
    public String getWebsiteSessionData() {
        return websiteSessionData;
    }

    /**
     * @param websiteSessionData
     *            the websiteSessionData to set
     */
    public void setWebsiteSessionData( final String websiteSessionData ) {
        this.websiteSessionData = websiteSessionData;
    }

    /**
     * @return the workingWithRealtor
     */
    public String getWorkingWithRealtor() {
        return workingWithRealtor;
    }

    /**
     * @param workingWithRealtor
     *            the workingWithRealtor to set
     */
    public void setWorkingWithRealtor( final String workingWithRealtor ) {
        this.workingWithRealtor = workingWithRealtor;
    }

}
