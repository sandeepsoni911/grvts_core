/*
 *
 */
package com.owners.gravitas.dto.crm.request;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.owners.gravitas.dto.CRMBaseDTO;
import com.owners.gravitas.serializer.CustomDateSerializer;
import com.owners.gravitas.serializer.CustomDateTimeSerializer;

/**
 * The Class CRMLeadRequest.
 *
 * @author vishwanathm
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class CRMLeadRequest extends CRMBaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5734118512909119947L;

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
    private String gravitasEngineId;

    /** The record history. */
    private String recordHistory;

    /** The dedupe counter. */
    private Integer deDupCounter = 0;

    /** The owns home. */
    private boolean ownsHome;

    /** The property state. */
    private String propertyState;

    /** The mailing state. */
    private String mailingState;

    /** The property city. */
    private String propertyCity;

    /** The price ranges. */
    private String priceRanges;

    /** The order id. */
    private String orderId;

    /** The owners agent. */
    private String ownersAgent;

    /** The farming buyer action. */
    private String farmingBuyerAction;

    /**
     * Gets the owners agent.
     *
     * @return the owners agent
     */
    @JsonProperty( "Owners_Agent__c" )
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
    @JsonProperty( "Owners_com_Order__c" )
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
     * Gets the property city.
     *
     * @return the property city
     */
    @JsonProperty( "Property_City__c" )
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
    @JsonProperty( "Price_Ranges__c" )
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
     * Gets the first name.
     *
     * @return the first name
     */
    @Override
    @JsonProperty( "FirstName" )
    public String getFirstName() {
        return super.getFirstName();
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    @Override
    @JsonProperty( "LastName" )
    public String getLastName() {
        return super.getLastName();
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    @Override
    @JsonProperty( "Email" )
    public String getEmail() {
        return super.getEmail();
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    @Override
    @JsonProperty( "Phone" )
    public String getPhone() {
        return super.getPhone();
    }

    /**
     * Gets the company.
     *
     * @return the company
     */
    @Override
    @JsonProperty( "Company" )
    public String getCompany() {
        return super.getCompany();
    }

    /**
     * Gets the source.
     *
     * @return the source
     */
    @Override
    @JsonProperty( "LeadSource" )
    public String getSource() {
        return super.getSource();
    }

    /**
     * Gets the source.
     *
     * @return the source
     */
    @Override
    @JsonProperty( "Interested_In_Financing__c" )
    public boolean isInterestedInFinancing() {
        return super.isInterestedInFinancing();
    }

    /**
     * Gets the comments.
     *
     * @return the comments
     */
    @Override
    @JsonProperty( "Comments__c" )
    public String getComments() {
        return super.getComments();
    }

    /**
     * Gets the listing id.
     *
     * @return the listingId
     */
    @Override
    @JsonProperty( "Owners_com_Property_ID__c" )
    public String getListingId() {
        return super.getListingId();
    }

    /**
     * Gets the record type.
     *
     * @return the recordType
     */
    @Override
    @JsonProperty( "RecordTypeId" )
    public String getRecordType() {
        return super.getRecordType();
    }

    /**
     * Gets the property address.
     *
     * @return the property address
     */
    @Override
    @JsonProperty( "Property_Address__c" )
    public String getPropertyAddress() {
        return super.getPropertyAddress();
    }

    /**
     * Gets the request type.
     *
     * @return the request type
     */
    @Override
    @JsonProperty( "Lead_Request_Type__c" )
    public String getRequestType() {
        return super.getRequestType();
    }

    /**
     * Gets the lead status.
     *
     * @return the lead status
     */
    @Override
    @JsonProperty( "Status" )
    public String getLeadStatus() {
        return super.getLeadStatus();
    }

    /**
     * Gets the pre approved for mortgage.
     *
     * @return the pre approved for mortgage
     */
    @Override
    @JsonProperty( "Pre_Approved_for_Mortgage__c" )
    public String getPreApprovedForMortgage() {
        return super.getPreApprovedForMortgage();
    }

    /**
     * Gets the working with realtor.
     *
     * @return the working with realtor
     */
    @Override
    @JsonProperty( "Working_with_External_Agent__c" )
    public String getWorkingWithRealtor() {
        return super.getWorkingWithRealtor();
    }

    /**
     * Gets the buyer readiness timeline.
     *
     * @return the buyer readiness timeline
     */
    @Override
    @JsonProperty( "Buyer_Readiness_Timeline__c" )
    public String getBuyerReadinessTimeline() {
        return super.getBuyerReadinessTimeline();
    }

    /**
     * Gets the marketing opt in.
     *
     * @return the marketing opt in
     */
    @Override
    @JsonProperty( "Marketing_Opt_In__c" )
    public boolean getMarketingOptIn() {
        return super.getMarketingOptIn();
    }

    /**
     * Gets the preferred contact time.
     *
     * @return the preferred contact time
     */
    @Override
    @JsonProperty( "Preferred_Contact_Time__c" )
    public String getPreferredContactTime() {
        return super.getPreferredContactTime();
    }

    /**
     * Gets the preferred contact method.
     *
     * @return the preferred contact method
     */
    @Override
    @JsonProperty( "Preferred_Contact_Method__c" )
    public String getPreferredContactMethod() {
        return super.getPreferredContactMethod();
    }

    /**
     * Gets the price range.
     *
     * @return the price range
     */
    @Override
    @JsonProperty( "Price_Range__c" )
    public String getPriceRange() {
        return super.getPriceRange();
    }

    /**
     * Gets the buyer lead quality.
     *
     * @return the buyer lead quality
     */
    @Override
    @JsonProperty( "Buyer_Lead_Quality__c" )
    public String getBuyerLeadQuality() {
        return super.getBuyerLeadQuality();
    }

    /**
     * Gets the preferred language.
     *
     * @return the preferred language
     */
    @Override
    @JsonProperty( "Preferred_Language__c" )
    public String getPreferredLanguage() {
        return super.getPreferredLanguage();
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    @Override
    @JsonProperty( "Property_State_BR__c" )
    public String getState() {
        return super.getState();
    }

    /**
     * Gets the mls id.
     *
     * @return the mls id
     */
    @Override
    @JsonProperty( "MSID__c" )
    public String getMlsId() {
        return super.getMlsId();
    }

    /**
     * Gets the alid.
     *
     * @return the alid
     */
    @Override
    @JsonProperty( "ALID__c" )
    public String getAlId() {
        return super.getAlId();
    }

    /**
     * Gets the interested zipcodes.
     *
     * @return the interested zipcodes
     */
    @Override
    @JsonProperty( "Interested_Zip_Codes__c" )
    public String getInterestedZipcodes() {
        return super.getInterestedZipcodes();
    }

    /**
     * Gets the owners com identifier.
     *
     * @return the owners com identifier
     */
    @Override
    @JsonProperty( "Hubzu_Property_ID__c" )
    public String getOwnersComIdentifier() {
        return super.getOwnersComIdentifier();
    }

    /**
     * Gets the offer amount.
     *
     * @return the offer amount
     */
    @Override
    @JsonProperty( "Offer_Amount__c" )
    public String getOfferAmount() {
        return super.getOfferAmount();
    }

    /**
     * Gets the listing creation date.
     *
     * @return the listing creation date
     */
    @Override
    @JsonProperty( "Listing_Creation_Date__c" )
    @JsonSerialize( using = CustomDateSerializer.class )
    public DateTime getListingCreationDate() {
        return super.getListingCreationDate();
    }

    /**
     * Gets the financing.
     *
     * @return the financing
     */
    @Override
    @JsonProperty( "Financing__c" )
    public String getFinancing() {
        return super.getFinancing();
    }

    /**
     * Gets the website.
     *
     * @return the website
     */
    @Override
    @JsonProperty( "Website" )
    public String getWebsite() {
        return super.getWebsite();
    }

    /**
     * Gets the lead source url.
     *
     * @return the lead source url
     */
    @Override
    @JsonProperty( "Unbounce_Page_URL__c" )
    public String getLeadSourceUrl() {
        return super.getLeadSourceUrl();
    }

    /**
     * Gets the saved search values.
     *
     * @return the saved search values
     */
    @Override
    @JsonProperty( "Saved_Search_Values__c" )
    public String getSavedSearchValues() {
        return super.getSavedSearchValues();
    }

    /**
     * Gets the earnest money deposit.
     *
     * @return the earnest money deposit
     */
    @Override
    @JsonProperty( "Earnest_Money_Deposit__c" )
    public String getEarnestMoneyDeposit() {
        return super.getEarnestMoneyDeposit();
    }

    /**
     * Gets the purchase method.
     *
     * @return the purchase method
     */
    @Override
    @JsonProperty( "Purchase_Method__c" )
    public String getPurchaseMethod() {
        return super.getPurchaseMethod();
    }

    /**
     * Gets the down payment.
     *
     * @return the down payment
     */
    @Override
    @JsonProperty( "Down_Payment__c" )
    public String getDownPayment() {
        return super.getDownPayment();
    }

    /**
     * Gets the property tour information.
     *
     * @return the property tour information
     */
    @Override
    @JsonProperty( "Property_Tour_Information__c" )
    public String getPropertyTourInformation() {
        return super.getPropertyTourInformation();
    }

    /**
     * Gets the additional property data.
     *
     * @return the additional property data
     */
    @Override
    @JsonProperty( "Additional_Property_Information__c" )
    public String getAdditionalPropertyData() {
        return super.getAdditionalPropertyData();
    }

    /**
     * Gets the website session data.
     *
     * @return the website session data
     */
    @Override
    @JsonProperty( "Website_Session_Data__c" )
    public String getWebsiteSessionData() {
        return super.getWebsiteSessionData();
    }

    /**
     * Gets the owners visitor id.
     *
     * @return the owners visitor id
     */
    @Override
    @JsonProperty( "Owners_Visitor_ID__c" )
    public String getOwnersVisitorId() {
        return super.getOwnersVisitorId();
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    @Override
    @JsonProperty( "Notes__c" )
    public String getMessage() {
        return super.getMessage();
    }

    /**
     * Gets the median price.
     *
     * @return the median price
     */
    @Override
    @JsonProperty( "Median_Price__c" )
    public String getMedianPrice() {
        return super.getMedianPrice();
    }

    /**
     * Gets the last visit date time.
     *
     * @return the last visit date time
     */
    @Override
    @JsonProperty( "Last_Visit_Date_Time__c" )
    @JsonSerialize( using = CustomDateTimeSerializer.class )
    public DateTime getLastVisitDateTime() {
        return super.getLastVisitDateTime();
    }

    /**
     * Gets the audit record.
     *
     * @return the auditRecord
     */
    @JsonProperty( "Gravitas_Record_Audit__c" )
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
    @JsonProperty( "Deduped_by_Gravitas__c" )
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
    @JsonProperty( "MLS_Package_Type__c" )
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
    @JsonProperty( "pi_utm_campaign__c" )
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
    @JsonProperty( "pi_utm_content__c" )
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
    @JsonProperty( "pi_utm_medium__c" )
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
    @JsonProperty( "pi_utm_source__c" )
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
    @JsonProperty( "pi_utm_term__c" )
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
    @JsonProperty( "Unbounce_Page_Variant__c" )
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
     * Gets the gc lid.
     *
     * @return the gc lid
     */
    @JsonProperty( "GCLID__c" )
    public String getGclId() {
        return gclId;
    }

    /**
     * Sets the gc lid.
     *
     * @param gcLID
     *            the new gc lid
     */
    public void setGclId( final String gcLID ) {
        this.gclId = gcLID;
    }

    /**
     * Gets the gravitas engine id.
     *
     * @return the gravitas engine id
     */
    @JsonProperty( "Gravitas_Engine_ID__c" )
    public String getGravitasEngineId() {
        return gravitasEngineId;
    }

    /**
     * Sets the gravitas engine id.
     *
     * @param gravitasEngineId
     *            the new gravitas engine id
     */
    public void setGravitasEngineId( final String gravitasEngineId ) {
        this.gravitasEngineId = gravitasEngineId;
    }

    /**
     * Gets the record history.
     *
     * @return the record history
     */
    @JsonProperty( "Gravitas_Record_History__c" )
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
    @JsonProperty( "Gravitas_dedup_count__c" )
    public Integer getDeDupCounter() {
        return deDupCounter;
    }

    /**
     * Sets the dedupe counter.
     *
     * @param deDupCounter
     *            the new dedupe counter
     */
    public void setDeDupCounter( final Integer deDupCounter ) {
        this.deDupCounter = deDupCounter;
    }

    /**
     * Checks if is owns home.
     *
     * @return the ownsHome
     */
    @JsonProperty( "Owns_a_home__c" )
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
     * Gets the property state.
     *
     * @return the property state
     */
    @JsonProperty( "Property_State2__c" )
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
     * Gets the mailing state.
     *
     * @return the mailing state
     */
    @JsonProperty( "Mailing_State__c" )
    public String getMailingState() {
        return mailingState;
    }

    /**
     * Sets the mailing state.
     *
     * @param mailingState
     *            the new mailing state
     */
    public void setMailingState( final String mailingState ) {
        this.mailingState = mailingState;
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getCreatedDateTime()
     */
    @Override
    @JsonProperty( "createdDate" )
    @JsonSerialize( using = CustomDateTimeSerializer.class )
    @JsonIgnore
    public DateTime getCreatedDateTime() {
        return super.getCreatedDateTime();
    }

    /**
     * Gets the farming buyer action.
     *
     * @return the farming buyer action
     */
    @JsonProperty( "Farming_Buyer_Actions__c" )
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
    @JsonProperty( "LastModifiedDate" )
    @JsonSerialize( using = CustomDateTimeSerializer.class )
    @JsonIgnore
    public DateTime getLastModifiedDate() {
        return super.getLastModifiedDate();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getPropertyBedroom()
     */
    @Override
    @JsonProperty( "Property_Bedrooms__c" )
    public String getPropertyBedroom() {
        return super.getPropertyBedroom();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getPropertyBathroom()
     */
    @Override
    @JsonProperty( "Property_Bathrooms__c" )
    public String getPropertyBathroom() {
        return super.getPropertyBathroom();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getPropertySquareFoot()
     */
    @Override
    @JsonProperty( "Property_Square_Footage__c" )
    public String getPropertySquareFoot() {
        return super.getPropertySquareFoot();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getPropertyType()
     */
    @Override
    @JsonProperty( "Property_Type_2__c" )
    public String getPropertyType() {
        return super.getPropertyType();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getInquiryDate()
     */
    @Override
    @JsonProperty( "Buyer_inquiry_date__c" )
    @JsonSerialize( using = CustomDateSerializer.class )
    public DateTime getInquiryDate() {
        return super.getInquiryDate();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getSearchCity()
     */
    @Override
    @JsonProperty( "Search_City__c" )
    public String getSearchCity() {
        return super.getSearchCity();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getSearchAttributes()
     */
    @Override
    @JsonProperty( "Search_Attributes__c" )
    public String getSearchAttributes() {
        return super.getSearchAttributes();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getPartnerIdentifier()
     */
    @Override
    @JsonProperty( "Partner_Identifier__c" )
    public String getPartnerIdentifier() {
        return super.getPartnerIdentifier();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#Lot_Size__c()
     */
    @Override
    @JsonProperty( "Lot_Size__c" )
    public String getLotSize() {
        return super.getLotSize();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getCreditScore()
     */
    @Override
    @JsonProperty( "Credit_Score__c" )
    public String getCreditScore() {
        return super.getCreditScore();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getHomeType()
     */
    @Override
    @JsonProperty( "Home_Type__c" )
    public String getHomeType() {
        return super.getHomeType();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getBrowser()
     */
    @Override
    @JsonProperty( "Browser__c" )
    public String getBrowser() {
        return super.getBrowser();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getOs()
     */
    @Override
    @JsonProperty( "OS__c" )
    public String getOs() {
        return super.getOs();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getDevice()
     */
    @Override
    @JsonProperty( "Device__c" )
    public String getDevice() {
        return super.getDevice();
    }

}
