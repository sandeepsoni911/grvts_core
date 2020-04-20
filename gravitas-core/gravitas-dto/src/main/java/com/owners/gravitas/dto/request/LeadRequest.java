package com.owners.gravitas.dto.request;

import static com.owners.gravitas.constants.Constants.REG_EXP_EMAIL;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.UserTimeZone;

/**
 * The Class LeadRequest.
 *
 * @author vishwanathm
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class LeadRequest {

    /** first name. */
    @Size( min = 0, max = 40, message = "error.lead.firstName.size" )
    protected String firstName;
    /** last name. */
    @NotBlank( message = "error.lead.lastName.required" )
    @Size( min = 1, max = 80, message = "error.lead.lastName.size" )
    protected String lastName;
    /** email. */
    @NotBlank( message = "error.lead.email.required" )
    @Email( message = "error.lead.email.format", regexp = REG_EXP_EMAIL )
    @Size( min = 1, max = 80, message = "error.lead.email.size" )
    protected String email;
    /** phone number. */
    @Size( min = 0, max = 40, message = "error.lead.phone.size" )
    protected String phone;
    /** company. */
    @Size( min = 0, max = 255, message = "error.lead.company.size" )
    protected String company;
    /** The source. */
    protected String source;
    /** The comments. */
    @Size( min = 0, max = 2048, message = "error.lead.comments.size" )
    protected String comments;
    /** The record type. */
    protected String leadType;
    /** The listing id. */
    @Size( min = 0, max = 60, message = "error.lead.listingId.size" )
    protected String listingId;

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

    /** The property address. */
    private String propertyAddress;

    /** The mls id. */
    private String mlsId;

    /** The al id. */
    private String alId;

    /** The interested zipcodes. */
    private String interestedZipcodes;

    /** The owners com identifier. */
    private String ownersComIdentifier;

    /** The offer amount. */
    private String offerAmount;

    /** The listing creation date. */
    private String listingCreationDate;

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
    private String lastVisitDateTime;

    /** The mls package type. */
    private String mlsPackageType;

    /** The google analytics campaign. */
    private String googleAnalyticsCampaign;

    /** The google analytics content. */
    private String googleAnalyticsContent;

    /** The google analytics medium. */
    private String googleAnalyticsMedium;

    /** The google analytics source. */
    private String googleAnalyticsSource;

    /** The google analytics term. */
    private String googleAnalyticsTerm;

    /** The unbounce page variant. */
    private String unbouncePageVariant;

    /** The gcl id. */
    private String gclId;

    /** The owns home. */
    private boolean ownsHome;

    /** The user time zone. */
    private UserTimeZone userTimeZone;

    /** Interested In Finanacing Option. */
    private boolean interestedInFinancing;

    /** The loan number. */
    private int loanNumber;

    /** The property state. */
    private String propertyState;

    /** The order id. */
    private String orderId;

    /** The owners agent. */
    private String ownersAgent;

    /** The buyer lead source. */
    private String buyerLeadScore;

    /** The buyer lead label. */
    private String buyerLeadLabel;

    /** The farming buyer action. */
    private String farmingBuyerAction;
    
    /** The  property  Bedroom. */
    @DecimalMax(value="99999999999999.99", message = "propertyBedroom should be less than {value}")
    private String propertyBedroom;
    
    /** The  property  Bathroom. */
    @DecimalMax(value="99999999999999.99", message = "propertyBathroom should be less than {value}")
    private String propertyBathroom;
    
    /** The  property  SquareFoot. */
    @DecimalMax(value="99999999999999.99", message = "propertySquareFoot should be less than {value}")
    private String propertySquareFoot;
    
    /** The  property Type. */
    @Size( min = 0, max = 50, message = "error.lead.propertyType.size" )
    private String propertyType;
    
    /** The  inquiry date . */
    private String inquiryDate;
    
    /** The  search City . */
    @Size( min = 0, max = 50, message = "error.lead.searchCity.size" )
    private String searchCity;
    
    /** The  search Attributes . */
    @Size( min = 0, max = 10000, message = "error.lead.searchAttributes.size" )
    private String searchAttributes;
    
    /** The  Partner Identifier . */
    @Size( min = 0, max = 100, message = "error.lead.partnerIdentifier.size" )
    private String partnerIdentifier;
    
    /** The property city. */
    @Size( min = 0, max = 100, message = "error.lead.propertyCity.size" )
    private String propertyCity;
    
    /** The lotSize. */
    @Size( min = 0, max = 60, message = "error.lead.lotSize.size" )
    private String lotSize;
    
    /** The credit Score. */
    @Size( min = 0, max = 60, message = "error.lead.creditScore.size" )
    private String creditScore;
    
    /** The home Type. */
    @Size( min = 0, max = 60, message = "error.lead.homeType.size" )
    private String homeType;
    
    /** The browser. */
    @Size( min = 0, max = 60, message = "error.lead.browser.size" )
    private String browser;
    
    /** The Operating system. */
    @Size( min = 0, max = 60, message = "error.lead.os.size" )
    private String os;
    
    /** The device. */
    @Size( min = 0, max = 60, message = "error.lead.device.size" )
    private String device;
    
    private String propertyPrice;
    
    private String question;
    
    private String propertyImageURL;
    
    private String propertyHalfBathroom;
    
    private String coShoppingId;
    
    private String contactId;
    
    /**
     * Instantiates a new lead request basic.
     */
    public LeadRequest() {
        super();
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
     * Gets the phone.
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
     *            the new source
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
     *            the new comments
     */
    public void setComments( final String comments ) {
        this.comments = comments;
    }

    /**
     * Gets the lead type.
     *
     * @return the lead type
     */
    public String getLeadType() {
        return leadType;
    }

    /**
     * Sets the lead type.
     *
     * @param leadType
     *            the new lead type
     */
    public void setLeadType( final String leadType ) {
        this.leadType = leadType;
    }

    /**
     * Gets the listing id.
     *
     * @return the listing id
     */
    public String getListingId() {
        return listingId;
    }

    /**
     * Sets the listing id.
     *
     * @param listingId
     *            the new listing id
     */
    public void setListingId( final String listingId ) {
        this.listingId = listingId;
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
     * Checks if is marketing opt in.
     *
     * @return true, if is marketing opt in
     */
    public boolean isMarketingOptIn() {
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
     * Gets the al id.
     *
     * @return the al id
     */
    public String getAlId() {
        return alId;
    }

    /**
     * Sets the al id.
     *
     * @param alId
     *            the new al id
     */
    public void setAlId( final String alId ) {
        this.alId = alId;
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
    public String getListingCreationDate() {
        return listingCreationDate;
    }

    /**
     * Sets the listing creation date.
     *
     * @param listingCreationDate
     *            the new listing creation date
     */
    public void setListingCreationDate( final String listingCreationDate ) {
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
    public String getLastVisitDateTime() {
        return lastVisitDateTime;
    }

    /**
     * Sets the last visit date time.
     *
     * @param lastVisitDateTime
     *            the new last visit date time
     */
    public void setLastVisitDateTime( final String lastVisitDateTime ) {
        this.lastVisitDateTime = lastVisitDateTime;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString( this );
    }

    // OWNCORE-923
    /**
     * Gets the mls package type.
     *
     * @return the mls package type
     */
    public String getMlsPackageType() {
        return mlsPackageType;
    }

    /**
     * Sets the mls package type.
     *
     * @param mlsPackageType
     *            the new mls package type
     */
    public void setMlsPackageType( final String mlsPackageType ) {
        this.mlsPackageType = mlsPackageType;
    }

    /**
     * Gets the google analytics campaign.
     *
     * @return the google analytics campaign
     */
    public String getGoogleAnalyticsCampaign() {
        return googleAnalyticsCampaign;
    }

    /**
     * Sets the google analytics campaign.
     *
     * @param googleAnalyticsCampaign
     *            the new google analytics campaign
     */
    public void setGoogleAnalyticsCampaign( final String googleAnalyticsCampaign ) {
        this.googleAnalyticsCampaign = googleAnalyticsCampaign;
    }

    /**
     * Gets the google analytics content.
     *
     * @return the google analytics content
     */
    public String getGoogleAnalyticsContent() {
        return googleAnalyticsContent;
    }

    /**
     * Sets the google analytics content.
     *
     * @param googleAnalyticsMedium
     *            the new google analytics content
     */
    public void setGoogleAnalyticsContent( final String googleAnalyticsMedium ) {
        this.googleAnalyticsContent = googleAnalyticsMedium;
    }

    /**
     * Gets the google analytics medium.
     *
     * @return the google analytics medium
     */
    public String getGoogleAnalyticsMedium() {
        return googleAnalyticsMedium;
    }

    /**
     * Sets the google analytics medium.
     *
     * @param googleAnalyticsSource
     *            the new google analytics medium
     */
    public void setGoogleAnalyticsMedium( final String googleAnalyticsSource ) {
        this.googleAnalyticsMedium = googleAnalyticsSource;
    }

    /**
     * Gets the google analytics source.
     *
     * @return the google analytics source
     */
    public String getGoogleAnalyticsSource() {
        return googleAnalyticsSource;
    }

    /**
     * Sets the google analytics source.
     *
     * @param googleAnalyticsTerm
     *            the new google analytics source
     */
    public void setGoogleAnalyticsSource( final String googleAnalyticsTerm ) {
        this.googleAnalyticsSource = googleAnalyticsTerm;
    }

    /**
     * Gets the google analytics term.
     *
     * @return the google analytics term
     */
    public String getGoogleAnalyticsTerm() {
        return googleAnalyticsTerm;
    }

    /**
     * Sets the google analytics term.
     *
     * @param googleAnalyticsTerm
     *            the new google analytics term
     */
    public void setGoogleAnalyticsTerm( final String googleAnalyticsTerm ) {
        this.googleAnalyticsTerm = googleAnalyticsTerm;
    }

    /**
     * Gets the unbounce page variant.
     *
     * @return the unbounce page variant
     */
    public String getUnbouncePageVariant() {
        return unbouncePageVariant;
    }

    /**
     * Sets the unbounce page variant.
     *
     * @param unbouncePageVariant
     *            the new unbounce page variant
     */
    public void setUnbouncePageVariant( final String unbouncePageVariant ) {
        this.unbouncePageVariant = unbouncePageVariant;
    }

    /**
     * Gets the gcl id.
     *
     * @return the gcl id
     */
    public String getGclId() {
        return gclId;
    }

    /**
     * Sets the gcl id.
     *
     * @param gclId
     *            the new gcl id
     */
    public void setGclId( final String gclId ) {
        this.gclId = gclId;
    }

    /**
     * Checks if is owns home.
     *
     * @return the ownsHome
     */
    public boolean isOwnsHome() {
        return ownsHome;
    }

    /**
     * Sets the owns home.
     *
     * @param ownsHome
     *            the ownsHome to set
     */
    public void setOwnsHome( final boolean ownsHome ) {
        this.ownsHome = ownsHome;
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
     * Gets the user time zone.
     *
     * @return the userTimeZone
     */
    public UserTimeZone getUserTimeZone() {
        return userTimeZone;
    }

    /**
     * Sets the user time zone.
     *
     * @param userTimeZone
     *            the userTimeZone to set
     */
    public void setUserTimeZone( final UserTimeZone userTimeZone ) {
        this.userTimeZone = userTimeZone;
    }

    /**
     * Gets the loan number.
     *
     * @return the loan number
     */
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
     * Gets the property state.
     *
     * @return the property state
     */
    public String getPropertyState() {
        return propertyState;
    }

    /**
     * Sets the property state.
     *
     * @param propertyState
     *            the new property state
     */
    public void setPropertyState( final String propertyState ) {
        this.propertyState = propertyState;
    }

    /**
     * Gets the owners agent.
     *
     * @return the owners agent
     */
    public String getOwnersAgent() {
        return ownersAgent;
    }

    /**
     * Sets the owners agent.
     *
     * @param ownersAgent
     *            the new owners agent
     */
    public void setOwnersAgent( final String ownersAgent ) {
        this.ownersAgent = ownersAgent;
    }

    /**
     * Gets the order id.
     *
     * @return the order id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets the order id.
     *
     * @param orderId
     *            the new order id
     */
    public void setOrderId( final String orderId ) {
        this.orderId = orderId;
    }

    /**
     * Gets the buyer lead source.
     *
     * @return the buyer lead source
     */
    public String getBuyerLeadScore() {
        return buyerLeadScore;
    }

    /**
     * Sets the buyer lead source.
     *
     * @param buyerLeadScore
     *            the new buyer lead source
     */
    public void setBuyerLeadScore( final String buyerLeadScore ) {
        this.buyerLeadScore = buyerLeadScore;
    }

    /**
     * Gets the buyer lead label.
     *
     * @return the buyer lead label
     */
    public String getBuyerLeadLabel() {
        return buyerLeadLabel;
    }

    /**
     * Sets the buyer lead label.
     *
     * @param buyerLeadLabel
     *            the new buyer lead label
     */
    public void setBuyerLeadLabel( final String buyerLeadLabel ) {
        this.buyerLeadLabel = buyerLeadLabel;
    }

    /**
     * Gets the farming buyer action.
     *
     * @return the farming buyer action
     */
    public String getFarmingBuyerAction() {
        return farmingBuyerAction;
    }

    /**
     * Sets the farming buyer action.
     *
     * @param farmingBuyerAction
     *            the new farming buyer action
     */
    public void setFarmingBuyerAction( final String farmingBuyerAction ) {
        this.farmingBuyerAction = farmingBuyerAction;
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
	 * @return the propertyPrice
	 */
	public String getPropertyPrice() {
		return propertyPrice;
	}

	/**
	 * @param propertyPrice
	 *            the propertyPrice to set
	 */
	public void setPropertyPrice(String propertyPrice) {
		this.propertyPrice = propertyPrice;
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
	public void setQuestion(String question) {
		this.question = question;
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
	public void setPropertyImageURL(String propertyImageURL) {
		this.propertyImageURL = propertyImageURL;
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
	public void setPropertyHalfBathroom(String propertyHalfBathroom) {
		this.propertyHalfBathroom = propertyHalfBathroom;
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
	public void setCoShoppingId(String coShoppingId) {
		this.coShoppingId = coShoppingId;
	}

	/**
	 * @return the contactId
	 */
	public String getContactId() {
		return contactId;
	}

	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	
}
