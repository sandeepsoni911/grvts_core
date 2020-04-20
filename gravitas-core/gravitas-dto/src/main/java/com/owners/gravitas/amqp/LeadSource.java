package com.owners.gravitas.amqp;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.owners.gravitas.dto.CRMBaseDTO;
import com.owners.gravitas.serializer.CustomDateTimeDeserializer;
import com.owners.gravitas.serializer.CustomDateTimeSerializer;

/**
 * The Class LeadSource.
 *
 * @author vishwanathm
 */
public class LeadSource extends CRMBaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5945828726364071067L;

    /** The id. */
    private String id;

    /** The audit record. */
    private String auditRecord;

    /** The is de duped. */
    private boolean isDeDuped;

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

    /** The gravitas engine id. */
    private String engineId;

    /** The record history. */
    private String recordHistory;

    /** The dedupe counter. */
    private Integer deDupCounter;

    /** The owns home. */
    private boolean ownsHome;

    /** The owner id. */
    private String ownerId;

    /** The closed reason. */
    private String closedReason;

    /** The converted date. */
    private String convertedDate;

    /** The do not call. */
    private boolean doNotCall;

    /** The do not email. */
    private boolean doNotEmail;

    /** The record type name. */
    private String recordTypeName;

    /** The referred. */
    private boolean referred;

    /** The property city. */
    private String propertyCity;

    /** The price ranges. */
    private String priceRanges;

    /** The created by id. */
    private String createdById;

    /** The last modified by id. */
    private String lastModifiedById;

    /** The farming buyer action. */
    private String farmingBuyerAction;

    /** The converted opportunity id. */
    private String convertedOpportunityId;

    /**
     * Gets the property city.
     *
     * @return the property city
     */
    public String getPropertyCity() {
        return propertyCity;
    }

    /**
     * Sets the property city.
     *
     * @param propertyCity
     *            the new property city
     */
    public void setPropertyCity( final String propertyCity ) {
        this.propertyCity = propertyCity;
    }

    /**
     * Gets the price ranges.
     *
     * @return the price ranges
     */
    public String getPriceRanges() {
        return priceRanges;
    }

    /**
     * Sets the price ranges.
     *
     * @param priceRanges
     *            the new price ranges
     */
    /** The price ranges. */
    public void setPriceRanges( final String priceRanges ) {
        this.priceRanges = priceRanges;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId( final String id ) {
        this.id = id;
    }

    /**
     * Gets the first name.
     *
     * @param firstName
     *            the new first name
     * @return the first name
     */
    @Override
    public void setFirstName( final String firstName ) {
        super.setFirstName( firstName );
    }

    /**
     * Gets the last name.
     *
     * @param lastName
     *            the new last name
     * @return the last name
     */
    @Override
    public void setLastName( final String lastName ) {
        super.setLastName( lastName );
    }

    /**
     * Gets the email.
     *
     * @param email
     *            the new email
     * @return the email
     */
    @Override
    public void setEmail( final String email ) {
        super.setEmail( email );
    }

    /**
     * Gets phone.
     *
     * @param phone
     *            the new phone
     * @return the phone
     */
    @Override
    public void setPhone( final String phone ) {
        super.setPhone( phone );
    }

    /**
     * Gets the company.
     *
     * @param company
     *            the new company
     * @return the company
     */
    @Override
    public void setCompany( final String company ) {
        super.setCompany( company );
    }

    /**
     * Gets the source.
     *
     * @param source
     *            the new source
     * @return the source
     */
    @Override
    public void setSource( final String source ) {
        super.setSource( source );
    }

    /**
     * Gets the comments.
     *
     * @param comments
     *            the new comments
     * @return the comments
     */
    @Override
    public void setComments( final String comments ) {
        super.setComments( comments );
    }

    /**
     * Gets the listing id.
     *
     * @param listingId
     *            the new listing id
     * @return the listingId
     */
    @Override
    public void setListingId( final String listingId ) {
        super.setListingId( listingId );
    }

    /**
     * Gets the record type.
     *
     * @param recordType
     *            the new record type
     * @return the recordType
     */
    @Override
    public void setRecordType( final String recordType ) {
        super.setRecordType( recordType );
    }

    /**
     * Gets the property address.
     *
     * @param propertyAddress
     *            the new property address
     * @return the property address
     */
    @Override
    public void setPropertyAddress( final String propertyAddress ) {
        super.setPropertyAddress( propertyAddress );
    }

    /**
     * Gets the request type.
     *
     * @param requestType
     *            the new request type
     * @return the request type
     */
    @Override
    public void setRequestType( final String requestType ) {
        super.setRequestType( requestType );
    }

    /**
     * Gets the lead status.
     *
     * @param leadStatus
     *            the new lead status
     * @return the lead status
     */
    @Override
    public void setLeadStatus( final String leadStatus ) {
        super.setLeadStatus( leadStatus );
    }

    /**
     * Gets the pre approved for mortgage.
     *
     * @param preApprovedForMortgage
     *            the new pre approved for mortgage
     * @return the pre approved for mortgage
     */
    @Override
    public void setPreApprovedForMortgage( final String preApprovedForMortgage ) {
        super.setPreApprovedForMortgage( preApprovedForMortgage );
    }

    /**
     * Gets the working with realtor.
     *
     * @param workingWithRealtor
     *            the new working with realtor
     * @return the working with realtor
     */
    @Override
    public void setWorkingWithRealtor( final String workingWithRealtor ) {
        super.setWorkingWithRealtor( workingWithRealtor );
    }

    /**
     * Gets the buyer readiness timeline.
     *
     * @param buyerReadinessTimeline
     *            the new buyer readiness timeline
     * @return the buyer readiness timeline
     */
    @Override
    public void setBuyerReadinessTimeline( final String buyerReadinessTimeline ) {
        super.setBuyerReadinessTimeline( buyerReadinessTimeline );
    }

    /**
     * Gets the marketing opt in.
     *
     * @param marketingOptIn
     *            the new marketing opt in
     * @return the marketing opt in
     */
    @Override
    public void setMarketingOptIn( final boolean marketingOptIn ) {
        super.setMarketingOptIn( marketingOptIn );
    }

    /**
     * Gets the preferred contact time.
     *
     * @param preferredContactTime
     *            the new preferred contact time
     * @return the preferred contact time
     */
    @Override
    public void setPreferredContactTime( final String preferredContactTime ) {
        super.setPreferredContactTime( preferredContactTime );
    }

    /**
     * Gets the preferred contact method.
     *
     * @param preferredContactMethod
     *            the new preferred contact method
     * @return the preferred contact method
     */
    @Override
    public void setPreferredContactMethod( final String preferredContactMethod ) {
        super.setPreferredContactMethod( preferredContactMethod );
    }

    /**
     * Gets the price range.
     *
     * @param priceRange
     *            the new price range
     * @return the price range
     */
    @Override
    public void setPriceRange( final String priceRange ) {
        super.setPriceRange( priceRange );
    }

    /**
     * Gets the buyer lead quality.
     *
     * @param buyerLeadQuality
     *            the new buyer lead quality
     * @return the buyer lead quality
     */
    @Override
    public void setBuyerLeadQuality( final String buyerLeadQuality ) {
        super.setBuyerLeadQuality( buyerLeadQuality );
    }

    /**
     * Gets the preferred language.
     *
     * @param preferredLanguage
     *            the new preferred language
     * @return the preferred language
     */
    @Override
    public void setPreferredLanguage( final String preferredLanguage ) {
        super.setPreferredLanguage( preferredLanguage );
    }

    /**
     * Gets the state.
     *
     * @param state
     *            the new state
     * @return the state
     */
    @Override
    public void setState( final String state ) {
        super.setState( state );
    }

    /**
     * Gets the mls id.
     *
     * @param mlsId
     *            the new mls id
     * @return the mls id
     */
    @Override
    public void setMlsId( final String mlsId ) {
        super.setMlsId( mlsId );
    }

    /**
     * Gets the alid.
     *
     * @param aLID
     *            the new al id
     * @return the alid
     */
    @Override
    public void setAlId( final String aLID ) {
        super.setAlId( aLID );
    }

    /**
     * Gets the interested zipcodes.
     *
     * @param interestedZipcodes
     *            the new interested zipcodes
     * @return the interested zipcodes
     */
    @Override
    public void setInterestedZipcodes( final String interestedZipcodes ) {
        super.setInterestedZipcodes( interestedZipcodes );
    }

    /**
     * Gets the owners com identifier.
     *
     * @param ownersComIdentifier
     *            the new owners com identifier
     * @return the owners com identifier
     */
    @Override
    public void setOwnersComIdentifier( final String ownersComIdentifier ) {
        super.setOwnersComIdentifier( ownersComIdentifier );
    }

    /**
     * Gets the offer amount.
     *
     * @param offerAmount
     *            the new offer amount
     * @return the offer amount
     */
    @Override
    public void setOfferAmount( final String offerAmount ) {
        super.setOfferAmount( offerAmount );
    }

    /**
     * Sets the listing creation date.
     *
     * @param listingCreationDate
     *            the new listing creation date
     */
    @Override
    public void setListingCreationDate( final DateTime listingCreationDate ) {
        super.setListingCreationDate( listingCreationDate );
    }

    /**
     * Gets the financing.
     *
     * @param financing
     *            the new financing
     * @return the financing
     */
    @Override
    public void setFinancing( final String financing ) {
        super.setFinancing( financing );
    }

    /**
     * Gets the website.
     *
     * @param website
     *            the new website
     * @return the website
     */
    @Override
    public void setWebsite( final String website ) {
        super.setWebsite( website );
    }

    /**
     * Gets the lead source url.
     *
     * @param leadSourceUrl
     *            the new lead source url
     * @return the lead source url
     */
    @Override
    public void setLeadSourceUrl( final String leadSourceUrl ) {
        super.setLeadSourceUrl( leadSourceUrl );
    }

    /**
     * Gets the saved search values.
     *
     * @param savedSearchValues
     *            the new saved search values
     * @return the saved search values
     */
    @Override
    public void setSavedSearchValues( final String savedSearchValues ) {
        super.setSavedSearchValues( savedSearchValues );
    }

    /**
     * Gets the earnest money deposit.
     *
     * @param earnestMoneyDeposit
     *            the new earnest money deposit
     * @return the earnest money deposit
     */
    @Override
    public void setEarnestMoneyDeposit( final String earnestMoneyDeposit ) {
        super.setEarnestMoneyDeposit( earnestMoneyDeposit );
    }

    /**
     * Gets the purchase method.
     *
     * @param purchaseMethod
     *            the new purchase method
     * @return the purchase method
     */
    @Override
    public void setPurchaseMethod( final String purchaseMethod ) {
        super.setPurchaseMethod( purchaseMethod );
    }

    /**
     * Gets the down payment.
     *
     * @param downPayment
     *            the new down payment
     * @return the down payment
     */
    @Override
    public void setDownPayment( final String downPayment ) {
        super.setDownPayment( downPayment );
    }

    /**
     * Gets the property tour information.
     *
     * @param propertyTourInformation
     *            the new property tour information
     * @return the property tour information
     */
    @Override
    public void setPropertyTourInformation( final String propertyTourInformation ) {
        super.setPropertyTourInformation( propertyTourInformation );
    }

    /**
     * Gets the additional property data.
     *
     * @param additionalPropertyData
     *            the new additional property data
     * @return the additional property data
     */
    @Override
    public void setAdditionalPropertyData( final String additionalPropertyData ) {
        super.setAdditionalPropertyData( additionalPropertyData );
    }

    /**
     * Gets the website session data.
     *
     * @param websiteSessionData
     *            the new website session data
     * @return the website session data
     */
    @Override
    public void setWebsiteSessionData( final String websiteSessionData ) {
        super.setWebsiteSessionData( websiteSessionData );
    }

    /**
     * Gets the owners visitor id.
     *
     * @param ownersVisitorId
     *            the new owners visitor id
     * @return the owners visitor id
     */
    @Override
    public void setOwnersVisitorId( final String ownersVisitorId ) {
        super.setOwnersVisitorId( ownersVisitorId );
    }

    /**
     * Gets the message.
     *
     * @param message
     *            the new message
     * @return the message
     */
    @Override
    public void setMessage( final String message ) {
        super.setMessage( message );
    }

    /**
     * Gets the median price.
     *
     * @param medianPrice
     *            the new median price
     * @return the median price
     */
    @Override
    public void setMedianPrice( final String medianPrice ) {
        super.setMedianPrice( medianPrice );
    }

    /**
     * Sets the last visit date time.
     *
     * @param lastVisitDateTime
     *            the new last visit date time
     */
    @Override
    public void setLastVisitDateTime( final DateTime lastVisitDateTime ) {
        super.setLastVisitDateTime( lastVisitDateTime );
    }

    /**
     * Sets the interested in financing.
     *
     * @param interestedInFinancing
     *            the interestedInFinancing to set
     */
    @Override
    public void setInterestedInFinancing( final boolean interestedInFinancing ) {
        super.setInterestedInFinancing( interestedInFinancing );
    }

    /**
     * Gets the audit record.
     *
     * @return the auditRecord
     */
    public String getAuditRecord() {
        return auditRecord;
    }

    /**
     * Sets the audit record.
     *
     * @param auditRecord
     *            the auditRecord to set
     */
    public void setAuditRecord( final String auditRecord ) {
        this.auditRecord = auditRecord;
    }

    /**
     * Checks if is de duped.
     *
     * @return the isDeDuped
     */
    public boolean isDeDuped() {
        return isDeDuped;
    }

    /**
     * Sets the de duped.
     *
     * @param isDeDuped
     *            the isDeDuped to set
     */
    public void setDeDuped( final boolean isDeDuped ) {
        this.isDeDuped = isDeDuped;
    }

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
     * @param googleAnalyticsContent
     *            the new google analytics content
     */
    public void setGoogleAnalyticsContent( final String googleAnalyticsContent ) {
        this.googleAnalyticsContent = googleAnalyticsContent;
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
     * @param googleAnalyticsMedium
     *            the new google analytics medium
     */
    public void setGoogleAnalyticsMedium( final String googleAnalyticsMedium ) {
        this.googleAnalyticsMedium = googleAnalyticsMedium;
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
     * @param googleAnalyticsSource
     *            the new google analytics source
     */
    public void setGoogleAnalyticsSource( final String googleAnalyticsSource ) {
        this.googleAnalyticsSource = googleAnalyticsSource;
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
     * Gets the gravitas engine id.
     *
     * @return the gravitas engine id
     */
    public String getEngineId() {
        return engineId;
    }

    /**
     * Sets the gravitas engine id.
     *
     * @param gravitasEngineId
     *            the new engine id
     */
    public void setEngineId( final String gravitasEngineId ) {
        this.engineId = gravitasEngineId;
    }

    /**
     * Gets the record history.
     *
     * @return the record history
     */
    public String getRecordHistory() {
        return recordHistory;
    }

    /**
     * Sets the record history.
     *
     * @param recordHistory
     *            the new record history
     */
    public void setRecordHistory( final String recordHistory ) {
        this.recordHistory = recordHistory;
    }

    /**
     * Gets the dedupe counter.
     *
     * @return the dedupe counter
     */
    public Integer getDeDupCounter() {
        return deDupCounter;
    }

    /**
     * Sets the dedupe counter.
     *
     * @param dedupeCounter
     *            the new de dup counter
     */
    public void setDeDupCounter( final Integer dedupeCounter ) {
        this.deDupCounter = dedupeCounter;
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
     * Gets the owner id.
     *
     * @return the ownerId
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * Sets the owner id.
     *
     * @param ownerId
     *            the ownerId to set
     */
    public void setOwnerId( final String ownerId ) {
        this.ownerId = ownerId;
    }

    /**
     * Gets the closed reason.
     *
     * @return the closedReason
     */
    public String getClosedReason() {
        return closedReason;
    }

    /**
     * Sets the closed reason.
     *
     * @param lostReason
     *            the new closed reason
     */
    public void setClosedReason( final String lostReason ) {
        this.closedReason = lostReason;
    }

    /**
     * Gets the converted date.
     *
     * @return the convertedDate
     */
    public String getConvertedDate() {
        return convertedDate;
    }

    /**
     * Sets the converted date.
     *
     * @param convertedDate
     *            the convertedDate to set
     */
    public void setConvertedDate( final String convertedDate ) {
        this.convertedDate = convertedDate;
    }

    /**
     * Checks if is do not call.
     *
     * @return the doNotCall
     */
    public boolean isDoNotCall() {
        return doNotCall;
    }

    /**
     * Sets the do not call.
     *
     * @param doNotCall
     *            the doNotCall to set
     */
    public void setDoNotCall( final boolean doNotCall ) {
        this.doNotCall = doNotCall;
    }

    /**
     * Checks if is do not email.
     *
     * @return the doNotEmail
     */
    public boolean isDoNotEmail() {
        return doNotEmail;
    }

    /**
     * Sets the do not email.
     *
     * @param doNotEmail
     *            the doNotEmail to set
     */
    public void setDoNotEmail( final boolean doNotEmail ) {
        this.doNotEmail = doNotEmail;
    }

    /**
     * Gets the record type name.
     *
     * @return the recordTypeName
     */
    public String getRecordTypeName() {
        return recordTypeName;
    }

    /**
     * Sets the record type name.
     *
     * @param recordTypeName
     *            the recordTypeName to set
     */
    public void setRecordTypeName( final String recordTypeName ) {
        this.recordTypeName = recordTypeName;
    }

    /**
     * Checks if is referred.
     *
     * @return the referred
     */
    public boolean isReferred() {
        return referred;
    }

    /**
     * Sets the referred.
     *
     * @param referred
     *            the referred to set
     */
    public void setReferred( final boolean referred ) {
        this.referred = referred;
    }

    /**
     * Gets the created by id.
     *
     * @return the created by id
     */
    public String getCreatedById() {
        return createdById;
    }

    /**
     * Sets the created by id.
     *
     * @param createdById
     *            the new created by id
     */
    public void setCreatedById( final String createdById ) {
        this.createdById = createdById;
    }

    /**
     * Gets the last modified by id.
     *
     * @return the last modified by id
     */
    public String getLastModifiedById() {
        return lastModifiedById;
    }

    /**
     * Sets the last modified by id.
     *
     * @param lastModifiedById
     *            the new last modified by id
     */
    @JsonProperty( "LastModifiedById" )
    public void setLastModifiedById( final String lastModifiedById ) {
        this.lastModifiedById = lastModifiedById;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getCreatedDateTime()
     */
    @Override
    @JsonSerialize( using = CustomDateTimeSerializer.class )
    public DateTime getCreatedDateTime() {
        return super.getCreatedDateTime();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setCreatedDateTime(org.joda.time.
     * DateTime)
     */
    @Override
    @JsonDeserialize( using = CustomDateTimeDeserializer.class )
    public void setCreatedDateTime( final DateTime createdDateTime ) {
        super.setCreatedDateTime( createdDateTime );
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

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getLastModifiedDate()
     */
    @Override
    @JsonSerialize( using = CustomDateTimeSerializer.class )
    public DateTime getLastModifiedDate() {
        return super.getLastModifiedDate();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.CRMBaseDTO#setLastModifiedDate(org.joda.time.
     * DateTime)
     */
    @Override
    @JsonDeserialize( using = CustomDateTimeDeserializer.class )
    public void setLastModifiedDate( final DateTime lastModifiedDate ) {
        super.setLastModifiedDate( lastModifiedDate );
    }

    /**
     * Gets the converted opportunity id.
     *
     * @return the converted opportunity id
     */
    public String getConvertedOpportunityId() {
        return convertedOpportunityId;
    }

    /**
     * Sets the converted opportunity id.
     *
     * @param convertedOpportunityId
     *            the new converted opportunity id
     */
    public void setConvertedOpportunityId( final String convertedOpportunityId ) {
        this.convertedOpportunityId = convertedOpportunityId;
    }
    
    
    @Override
    @JsonIgnore
    @JsonDeserialize( using = CustomDateTimeDeserializer.class )
    public void setInquiryDate(DateTime inquiryDate) {
        super.setInquiryDate( inquiryDate );
    }
}
