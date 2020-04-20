package com.owners.gravitas.dto.crm.response;

import java.util.Date;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.owners.gravitas.dto.CRMBaseDTO;
import com.owners.gravitas.serializer.CustomDateDeserializer;
import com.owners.gravitas.serializer.CustomDateTimeDeserializer;

// TODO: Auto-generated Javadoc
/**
 * The Class CRMLeadResponse.
 *
 * @author vishwanathm
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class CRMLeadResponse extends CRMBaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1369721872391913855L;

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

    /** The referred. */
    private boolean referred;

    /** The property city. */
    private String propertyCity;

    /** The price ranges. */
    private String priceRanges;

    /** The order id. */
    private String orderId;

    /** The owners agent. */
    private String ownersAgent;

    /** The record type name. */
    private String recordTypeName;

    /** The created by id. */
    private String createdById;

    /** The last modified by id. */
    private String lastModifiedById;

    /** The farming buyer action. */
    private String farmingBuyerAction;

    /** The converted opportunity id. */
    private String convertedOpportunityId;
    
    /** The uuid. */
    private String uuid;

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
    @JsonProperty( "Owners_Agent__c" )
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
    @JsonProperty( "Owners_com_Order__c" )
    public void setOrderId( final String orderId ) {
        this.orderId = orderId;
    }

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
    @JsonProperty( "Property_City__c" )
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
    @JsonProperty( "Price_Ranges__c" )
    public void setPriceRanges( final String priceRanges ) {
        this.priceRanges = priceRanges;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    @JsonProperty( "id" )
    public String getId() {
        return this.id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    @JsonProperty( "Id" )
    public void setSavedId( final String id ) {
        this.id = id;
    }

    /**
     * Gets the saved id.
     *
     * @return the saved id
     */
    public String getSavedId() {
        return this.id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    @JsonProperty( "id" )
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
    @JsonProperty( "FirstName" )
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
    @JsonProperty( "LastName" )
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
    @JsonProperty( "Email" )
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
    @JsonProperty( "Phone" )
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
    @JsonProperty( "Company" )
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
    @JsonProperty( "LeadSource" )
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
    @JsonProperty( "Comments__c" )
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
    @JsonProperty( "Owners_com_Property_ID__c" )
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
    @JsonProperty( "RecordTypeId" )
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
    @JsonProperty( "Property_Address__c" )
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
    @JsonProperty( "Lead_Request_Type__c" )
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
    @JsonProperty( "Status" )
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
    @JsonProperty( "Pre_Approved_for_Mortgage__c" )
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
    @JsonProperty( "Working_with_External_Agent__c" )
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
    @JsonProperty( "Buyer_Readiness_Timeline__c" )
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
    @JsonProperty( "Marketing_Opt_In__c" )
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
    @JsonProperty( "Preferred_Contact_Time__c" )
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
    @JsonProperty( "Preferred_Contact_Method__c" )
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
    @JsonProperty( "Price_Range__c" )
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
    @JsonProperty( "Buyer_Lead_Quality__c" )
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
    @JsonProperty( "Preferred_Language__c" )
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
    @JsonProperty( "Property_State_BR__c" )
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
    @JsonProperty( "MSID__c" )
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
    @JsonProperty( "ALID__c" )
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
    @JsonProperty( "Interested_Zip_Codes__c" )
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
    @JsonProperty( "Hubzu_Property_ID__c" )
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
    @JsonProperty( "Offer_Amount__c" )
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
    @JsonDeserialize( using = CustomDateDeserializer.class )
    @JsonProperty( "Listing_Creation_Date__c" )
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
    @JsonProperty( "Financing__c" )
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
    @JsonProperty( "Website" )
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
    @JsonProperty( "Unbounce_Page_URL__c" )
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
    @JsonProperty( "Saved_Search_Values__c" )
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
    @JsonProperty( "Earnest_Money_Deposit__c" )
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
    @JsonProperty( "Purchase_Method__c" )
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
    @JsonProperty( "Down_Payment__c" )
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
    @JsonProperty( "Property_Tour_Information__c" )
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
    @JsonProperty( "Additional_Property_Information__c" )
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
    @JsonProperty( "Website_Session_Data__c" )
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
    @JsonProperty( "Owners_Visitor_ID__c" )
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
    @JsonProperty( "Notes__c" )
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
    @JsonProperty( "Median_Price__c" )
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
    @JsonDeserialize( using = CustomDateTimeDeserializer.class )
    @JsonProperty( "Last_Visit_Date_Time__c" )
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
    @JsonProperty( "Interested_In_Financing__c" )
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
    @JsonProperty( "Gravitas_Record_Audit__c" )
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
    @JsonProperty( "Deduped_by_Gravitas__c" )
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
    @JsonProperty( "MLS_Package_Type__c" )
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
    @JsonProperty( "pi_utm_campaign__c" )
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
    @JsonProperty( "pi_utm_content__c" )
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
    @JsonProperty( "pi_utm_medium__c" )
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
    @JsonProperty( "pi_utm_source__c" )
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
    @JsonProperty( "pi_utm_term__c" )
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
    @JsonProperty( "Unbounce_Page_Variant__c" )
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
    @JsonProperty( "GCLID__c" )
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
    @JsonProperty( "Gravitas_Engine_ID__c" )
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
    @JsonProperty( "Gravitas_Record_History__c" )
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
    @JsonProperty( "Gravitas_dedup_count__c" )
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
    @JsonProperty( "Owns_a_home__c" )
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
    @JsonProperty( "OwnerId" )
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
    @JsonProperty( "Reason_Closed__c" )
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
    @JsonProperty( "ConvertedDate" )
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
    @JsonProperty( "DoNotCall" )
    public void setDoNotCall( final boolean doNotCall ) {
        this.doNotCall = doNotCall;
    }

    /**
     * Checks if user has opted out of emails.
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
    @JsonProperty( "HasOptedOutOfEmail" )
    public void setDoNotEmail( final boolean doNotEmail ) {
        this.doNotEmail = doNotEmail;
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
    @JsonProperty( "Referred_Successfully_RefExc__c" )
    public void setReferred( final boolean referred ) {
        this.referred = referred;
    }

    /**
     * Gets the record type name.
     *
     * @return the record type name
     */
    public String getRecordTypeName() {
        return recordTypeName;
    }

    /**
     * Sets the record type name.
     *
     * @param recordTypeName
     *            the new record type name
     */
    @JsonProperty( "Lead_Record_Type_Name__c" )
    public void setRecordTypeName( final String recordTypeName ) {
        this.recordTypeName = recordTypeName;
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
    @JsonProperty( "CreatedById" )
    public void setCreatedById( final String createdById ) {
        this.createdById = createdById;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setCreatedDateTime(org.joda.time.
     * DateTime)
     */
    @Override
    @JsonProperty( "CreatedDate" )
    @JsonDeserialize( using = CustomDateTimeDeserializer.class )
    public void setCreatedDateTime( final DateTime createdDateTime ) {
        super.setCreatedDateTime( createdDateTime );
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
    @JsonProperty( "Farming_Buyer_Actions__c" )
    public void setFarmingBuyerAction( final String farmingBuyerAction ) {
        this.farmingBuyerAction = farmingBuyerAction;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setCreatedDateTime(org.joda.time.
     * DateTime)
     */
    @Override
    @JsonProperty( "LastModifiedDate" )
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
    @JsonProperty( "ConvertedOpportunityId" )
    public void setConvertedOpportunityId( final String convertedOpportunityId ) {
        this.convertedOpportunityId = convertedOpportunityId;
    }
    
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setPropertyBedroom(
     *  final double propertyBedroom)
     */
    @Override
    @JsonProperty( "Property_Bedrooms__c" )
    public void setPropertyBedroom( final String propertyBedroom) {
        super.setPropertyBedroom( propertyBedroom );
    }
    

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setPropertyBathroom(
     * double propertyBathroom)
     */
    @Override
    @JsonProperty( "Property_Bathrooms__c" )
    public void setPropertyBathroom(String propertyBathroom) {
        super.setPropertyBathroom( propertyBathroom );
    }
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setPropertySquareFoot(
     * double propertySquareFoot)
     */
    @Override
    @JsonProperty( "Property_Square_Footage__c" )
    public void setPropertySquareFoot(String propertySquareFoot) {
        super.setPropertySquareFoot( propertySquareFoot );
    }
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setPropertyType(
     * String propertyType)
     */
    @Override
    @JsonProperty( "Property_Type_2__c" )
    public void setPropertyType(String propertyType) {
        super.setPropertyType( propertyType );
    }
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setInquiryDate(
     * DateTime inquiryDate)
     */
    @Override
    public void setInquiryDate(DateTime inquiryDate) {
        super.setInquiryDate( inquiryDate );
    }
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setSearchCity(
     * String searchCity)
     */
    @Override
    @JsonProperty( "Search_City__c" )
    public void setSearchCity(String searchCity) {
        super.setSearchCity( searchCity );
    }
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setSearchAttributes(
     * String searchAttributes)
     */
    @Override
    @JsonProperty( "Search_Attributes__c" )
    public void setSearchAttributes(String searchAttributes) {
        super.setSearchAttributes( searchAttributes );
    }
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setPartnerIdentifier(
     * String partnerIdentifier)
     */
    @Override
    @JsonProperty( "Partner_Identifier__c" )
    public void setPartnerIdentifier(String partnerIdentifier) {
        super.setPartnerIdentifier( partnerIdentifier );
    }
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#lotSize(
     * String lotSize)
     */
    @Override
    @JsonProperty( "Lot_Size__c" )
    public void setLotSize(String lotSize) {
        super.setLotSize( lotSize );
    }
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setCreditScore(
     * String creditScore)
     */
    @Override
    @JsonProperty( "Credit_Score__c" )
    public void setCreditScore(String creditScore) {
        super.setCreditScore( creditScore );
    }
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setHomeType(
     * String homeType)
     */
    @Override
    @JsonProperty( "Home_Type__c" )
    public void setHomeType(String homeType) {
        super.setHomeType( homeType);
    }
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setBrowser(
     * String browser)
     */
    @Override
    @JsonProperty( "Browser__c" )
    public void setBrowser(String browser) {
        super.setBrowser( browser);
    }
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setOs(String os)
     */
    @Override
    @JsonProperty( "OS__c" )
    public void setOs(String os) {
        super.setOs( os);
    }
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setDevice(
     * String device)
     */
    @Override
    @JsonProperty( "Device__c" )
    public void setDevice(String device) {
        super.setDevice( device);
    }
    
    public String getUuid() {
		return uuid;
	}

    @JsonProperty( "Owners_com_UUID__c" )
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
    
}
