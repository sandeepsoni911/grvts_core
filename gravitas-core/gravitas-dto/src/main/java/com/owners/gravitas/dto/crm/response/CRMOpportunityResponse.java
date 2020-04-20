package com.owners.gravitas.dto.crm.response;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.owners.gravitas.dto.CRMOpportunity;
import com.owners.gravitas.serializer.CustomDateDeserializer;
import com.owners.gravitas.serializer.CustomDateTimeDeserializer;

/**
 * The Class CRMOpportunityResponse.
 *
 * @author harshads
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class CRMOpportunityResponse extends CRMOpportunity {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6036523629366366983L;

    /** The id. */
    private String id;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return this.id;
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
    @JsonProperty( "Id" )
    public void setSavedId( final String id ) {
        this.id = id;
    }

    /**
     * Sets the order id.
     *
     * @param id
     *            the new id
     */
    @JsonProperty( "Owners_com_Order__c" )
    public void setOrderId( final String orderId ) {
        super.setOrderId( orderId );
    }

    /**
     * Sets the state name.
     *
     * @param stageName
     *            the new stage name
     */
    @JsonProperty( "StageName" )
    public void setStageName( final String stageName ) {
        super.setStageName( stageName );
    }

    /**
     * Sets the type.
     *
     * @param type
     *            the new type
     */
    @JsonProperty( "Type" )
    public void setType( final String type ) {
        super.setType( type );
    }

    /**
     * Sets the property address.
     *
     * @param propertyAddress
     *            the new property address
     */
    @JsonProperty( "Property_Address__c" )
    public void setPropertyAddress( final String propertyAddress ) {
        super.setPropertyAddress( propertyAddress );
    }

    /**
     * Sets the property city del.
     *
     * @param propertyCity
     *            the new property city
     */
    @JsonProperty( "Property_City_del__c" )
    public void setPropertyCity( final String propertyCity ) {
        super.setPropertyCity( propertyCity );
    }

    /**
     * Sets the alid.
     *
     * @param alid
     *            the alid to set
     */
    @JsonProperty( "ALID__c" )
    public void setAlId( final String alid ) {
        super.setAlId( alid );
    }

    /**
     * Sets the property state.
     *
     * @param propertyState
     *            the new property state
     */
    @JsonProperty( "Property_State__c" )
    public void setState( final String propertyState ) {
        super.setState( propertyState );
    }

    /**
     * Sets the property zip.
     *
     * @param propertyZip
     *            the new property zip
     */
    @JsonProperty( "Property_Zip_del__c" )
    public void setPropertyZip( final String propertyZip ) {
        super.setPropertyZip( propertyZip );
    }

    /**
     * Sets the listing id.
     *
     * @param listingId
     *            the new listing id
     */
    @JsonProperty( "Listing_ID__c" )
    public void setListingId( final String listingId ) {
        super.setListingId( listingId );
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the name to set
     */
    @JsonProperty( "Name" )
    public void setName( final String name ) {
        super.setName( name );
    }

    /**
     * Sets the account id.
     *
     * @param accountId
     *            the accountId to set
     */
    @JsonProperty( "AccountId" )
    public void setAccountId( final String accountId ) {
        super.setAccountId( accountId );
    }

    /**
     * Sets the contact id.
     *
     * @param contactId
     *            the new contact id
     */
    @JsonProperty( "Primary_Contact__c" )
    public void setContactId( final String contactId ) {
        super.setContactId( contactId );
    }

    /**
     * Sets the probability.
     *
     * @param probability
     *            the new probability
     */
    @JsonProperty( "Probability" )
    public void setProbability( final Integer probability ) {
        super.setProbability( probability );
    }

    /**
     * Sets the notes.
     *
     * @param notes
     *            the new notes
     */
    @JsonProperty( "Opportunity_Notes__c" )
    public void setComments( final String comments ) {
        super.setComments( comments );
    }

    /**
     * Sets the pre approved for mortgage.
     *
     * @param preApprovedForMortgage
     *            the preApprovedForMortgage to set
     */
    @JsonProperty( "Pre_Approved_for_Mortgage__c" )
    public void setPreApprovedForMortgage( final String preApprovedForMortgage ) {
        super.setPreApprovedForMortgage( preApprovedForMortgage );
    }

    /**
     * Sets the working with realtor.
     *
     * @param workingWithRealtor
     *            the workingWithRealtor to set
     */
    @JsonProperty( "Working_with_External_Agent__c" )
    public void setWorkingWithRealtor( final String workingWithRealtor ) {
        super.setWorkingWithRealtor( workingWithRealtor );
    }

    /**
     * Sets the buyer readiness timeline.
     *
     * @param buyerReadinessTimeline
     *            the buyerReadinessTimeline to set
     */
    @JsonProperty( "Buyer_Readiness_Timeline__c" )
    public void setBuyerReadinessTimeline( final String buyerReadinessTimeline ) {
        super.setBuyerReadinessTimeline( buyerReadinessTimeline );
    }

    /**
     * Sets the preferred contact time.
     *
     * @param preferredContactTime
     *            the preferredContactTime to set
     */
    @JsonProperty( "Preferred_Contact_Time__c" )
    public void setPreferredContactTime( final String preferredContactTime ) {
        super.setPreferredContactTime( preferredContactTime );
    }

    /**
     * Sets the preferred contact method.
     *
     * @param preferredContactMethod
     *            the preferredContactMethod to set
     */
    @JsonProperty( "Preferred_Contact_Method__c" )
    public void setPreferredContactMethod( final String preferredContactMethod ) {
        super.setPreferredContactMethod( preferredContactMethod );
    }

    /**
     * Sets the price range.
     *
     * @param priceRange
     *            the priceRange to set
     */
    @JsonProperty( "Price_Range__c" )
    public void setPriceRange( final String priceRange ) {
        super.setPriceRange( priceRange );
    }

    /**
     * Sets the package type.
     *
     * @param packageType
     *            the new package type
     */
    @JsonProperty( "MLS_Package_Type__c" )
    public void setPackageType( final String packageType ) {
        super.setPackageType( packageType );
    }

    /**
     * Sets the buyer lead quality.
     *
     * @param buyerLeadQuality
     *            the buyerLeadQuality to set
     */
    @JsonProperty( "Buyer_Lead_Quality__c" )
    public void setBuyerLeadQuality( final String buyerLeadQuality ) {
        super.setBuyerLeadQuality( buyerLeadQuality );
    }

    /**
     * Sets the preferred language.
     *
     * @param preferredLanguage
     *            the preferredLanguage to set
     */
    @JsonProperty( "Preferred_Language__c" )
    public void setPreferredLanguage( final String preferredLanguage ) {
        super.setPreferredLanguage( preferredLanguage );
    }

    /**
     * Sets the mls id.
     *
     * @param mlsId
     *            the mlsId to set
     */
    @JsonProperty( "MSID__c" )
    public void setMlsId( final String mlsId ) {
        super.setMlsId( mlsId );
    }

    /**
     * Sets the interested zipcodes.
     *
     * @param interestedZipcodes
     *            the interestedZipcodes to set
     */
    @JsonProperty( "Interested_Zip_Codes__c" )
    public void setInterestedZipcodes( final String interestedZipcodes ) {
        super.setInterestedZipcodes( interestedZipcodes );
    }

    /**
     * Sets the owners com identifier.
     *
     * @param ownersComIdentifier
     *            the ownersComIdentifier to set
     */
    @JsonProperty( "Property_ID_MLS_ID__c" )
    public void setOwnersComIdentifier( final String ownersComIdentifier ) {
        super.setOwnersComIdentifier( ownersComIdentifier );
    }

    /**
     * Sets the offer amount.
     *
     * @param offerAmount
     *            the offerAmount to set
     */
    @JsonProperty( "Offer_Amount__c" )
    public void setOfferAmount( final String offerAmount ) {
        super.setOfferAmount( offerAmount );
    }

    /**
     * Sets the listing creation date.
     *
     * @param listingCreationDate
     *            the listingCreationDate to set
     */
    @JsonDeserialize( using = CustomDateDeserializer.class )
    @JsonProperty( "Listing_Creation_Date__c" )
    public void setListingCreationDate( final DateTime listingCreationDate ) {
        super.setListingCreationDate( listingCreationDate );
    }

    /**
     * Sets the financing.
     *
     * @param financing
     *            the financing to set
     */
    @JsonProperty( "Financing__c" )
    public void setFinancing( final String financing ) {
        super.setFinancing( financing );
    }

    /**
     * Sets the lead source url.
     *
     * @param leadSourceUrl
     *            the leadSourceUrl to set
     */
    @JsonProperty( "Lead_Source_URL__c" )
    public void setLeadSourceUrl( final String leadSourceUrl ) {
        super.setLeadSourceUrl( leadSourceUrl );
    }

    /**
     * Sets the saved search values.
     *
     * @param savedSearchValues
     *            the savedSearchValues to set
     */
    @JsonProperty( "Saved_Search_Values__c" )
    public void setSavedSearchValues( final String savedSearchValues ) {
        super.setSavedSearchValues( savedSearchValues );
    }

    /**
     * Sets the earnest money deposit.
     *
     * @param earnestMoneyDeposit
     *            the earnestMoneyDeposit to set
     */
    @JsonProperty( "Earnest_Money_Deposit__c" )
    public void setEarnestMoneyDeposit( final String earnestMoneyDeposit ) {
        super.setEarnestMoneyDeposit( earnestMoneyDeposit );
    }

    /**
     * Sets the purchase method.
     *
     * @param purchaseMethod
     *            the purchaseMethod to set
     */
    @JsonProperty( "Purchase_Method__c" )
    public void setPurchaseMethod( final String purchaseMethod ) {
        super.setPurchaseMethod( purchaseMethod );
    }

    /**
     * Sets the down payment.
     *
     * @param downPayment
     *            the downPayment to set
     */
    @JsonProperty( "Down_Payment__c" )
    public void setDownPayment( final String downPayment ) {
        super.setDownPayment( downPayment );
    }

    /**
     * Sets the property tour information.
     *
     * @param propertyTourInformation
     *            the propertyTourInformation to set
     */
    @JsonProperty( "Property_Tour_Information__c" )
    public void setPropertyTourInformation( final String propertyTourInformation ) {
        super.setPropertyTourInformation( propertyTourInformation );
    }

    /**
     * Sets the additional property data.
     *
     * @param additionalPropertyData
     *            the additionalPropertyData to set
     */
    @JsonProperty( "Additional_Property_Information__c" )
    public void setAdditionalPropertyData( final String additionalPropertyData ) {
        super.setAdditionalPropertyData( additionalPropertyData );
    }

    /**
     * Sets the website session data.
     *
     * @param websiteSessionData
     *            the websiteSessionData to set
     */
    @JsonProperty( "Website_Session_Data__c" )
    public void setWebsiteSessionData( final String websiteSessionData ) {
        super.setWebsiteSessionData( websiteSessionData );
    }

    /**
     * Sets the owners visitor id.
     *
     * @param ownersVisitorId
     *            the ownersVisitorId to set
     */
    @JsonProperty( "Owners_Visitor_ID__c" )
    public void setOwnersVisitorId( final String ownersVisitorId ) {
        super.setOwnersVisitorId( ownersVisitorId );
    }

    /**
     * Sets the message.
     *
     * @param message
     *            the message to set
     */
    @JsonProperty( "Lead_Message__c" )
    public void setMessage( final String message ) {
        super.setMessage( message );
    }

    /**
     * Sets the lost reason.
     *
     * @param lostReason
     *            the lostReason to set
     */
    @JsonProperty( "Reason_Lost__c" )
    public void setLostReason( final String lostReason ) {
        super.setLostReason( lostReason );
    }

    /**
     * Sets the buy side commisstion.
     *
     * @param buySideCommisstion
     *            the buySideCommisstion to set
     */
    @JsonProperty( "Commission_Post_Closing__c" )
    public void setBuySideCommisstion( final String buySideCommisstion ) {
        super.setBuySideCommisstion( buySideCommisstion );
    }

    /**
     * Sets the sales price.
     *
     * @param salesPrice
     *            the salesPrice to set
     */
    @JsonProperty( "Sales_Price_Post_Closing__c" )
    public void setSalesPrice( final BigDecimal salesPrice ) {
        super.setSalesPrice( salesPrice );
    }

    /**
     * Sets the expected contract date.
     *
     * @param expectedContractDate
     *            the expectedContractDate to set
     */
    @JsonDeserialize( using = CustomDateDeserializer.class )
    @JsonProperty( "CloseDate" )
    public void setExpectedContractDate( final DateTime expectedContractDate ) {
        super.setExpectedContractDate( expectedContractDate );
    }

    /**
     * Sets the pre approved amount.
     *
     * @param preApprovedAmount
     *            the preApprovedAmount to set
     */
    @JsonProperty( "Pre_approved_Amount__c" )
    public void setPreApprovedAmount( final BigDecimal preApprovedAmount ) {
        super.setPreApprovedAmount( preApprovedAmount );
    }

    /**
     * Sets the request type.
     *
     * @param requestType
     *            the requestType to set
     */
    @JsonProperty( "Lead_Request_Type__c" )
    public void setRequestType( final String requestType ) {
        super.setRequestType( requestType );
    }

    /**
     * Sets the lead type.
     *
     * @param leadType
     *            the leadType to set
     */
    @JsonProperty( "Lead_RecordType_Mirror__c" )
    public void setLeadType( final String leadType ) {
        super.setLeadType( leadType );
    }

    /**
     * Sets the audit record.
     *
     * @param auditRecord
     *            the auditRecord to set
     */
    @JsonProperty( "Gravitas_Record_Audit__c" )
    public void setAuditRecord( final String auditRecord ) {
        super.setAuditRecord( auditRecord );
    }

    /**
     * Sets the source.
     *
     * @param source
     *            the source to set
     */
    @JsonProperty( "LeadSource" )
    public void setSource( final String source ) {
        super.setSource( source );
    }

    /**
     * Sets the de duped.
     *
     * @param isDeDuped
     *            the isDeDuped to set
     */
    @JsonProperty( "Deduped_by_Gravitas__c" )
    public void setDeDuped( final boolean isDeDuped ) {
        super.setDeDuped( isDeDuped );
    }

    /**
     * Gets the median price.
     *
     * @return the median price
     */
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
    @JsonDeserialize( using = CustomDateTimeDeserializer.class )
    @JsonProperty( "Last_Visit_Date_Time__c" )
    public void setLastVisitDateTime( final DateTime lastVisitDateTime ) {
        super.setLastVisitDateTime( lastVisitDateTime );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setRecordType(java.lang.String)
     */
    @JsonProperty( "Record_Type_Name__c" )
    public void setRecordTypeName( final String recordTypeName ) {
        super.setRecordTypeName( recordTypeName );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.CRMOpportunity#setOpportunityLabel(java.lang.
     * String)
     */
    @JsonProperty( "Opportunity_Label__c" )
    public void setLabel( final String opportunityLabel ) {
        super.setLabel( opportunityLabel );
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.dto.CRMOpportunity#setOpportunityScore(java.lang.
     * String)
     */
    @JsonProperty( "Opportunity_Score__c" )
    public void setScore( final String opportunityScore ) {
        super.setScore( opportunityScore );
    }

    /**
     * Sets the Seller property state.
     *
     * @param propertyState
     *            the new Seller property state
     */
    @JsonProperty( "Property_States_del__c" )
    public void setSellerState( final String propertyState ) {
        super.setSellerState( propertyState );
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setCreatedDateTime(org.joda.time.
     * DateTime)
     */
    @JsonProperty( "CreatedDate" )
    @JsonDeserialize( using = CustomDateTimeDeserializer.class )
    public void setCreatedDateTime( final DateTime createdDateTime ) {
        super.setCreatedDateTime( createdDateTime );
    }
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setPropertyBedroom(
     *  final double propertyBedroom)
     */
    @Override
    @JsonProperty( "Property_Bedrooms__c" )
    @JsonIgnore
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
    @JsonIgnore
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
    @JsonIgnore
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
    @JsonIgnore
    public void setPropertyType(String propertyType) {
        super.setPropertyType( propertyType );
    }
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setInquiryDate(
     * DateTime inquiryDate)
     */
    @Override
    @JsonDeserialize( using = CustomDateDeserializer.class )
    @JsonProperty( "Buyer_inquiry_date__c" )
    @JsonIgnore
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
    @JsonIgnore
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
    @JsonIgnore
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
    @JsonIgnore
    public void setPartnerIdentifier(String partnerIdentifier) {
        super.setPartnerIdentifier( partnerIdentifier );
    }
    
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#lotSize(
     * String lotSize)
     */
    @Override
    @JsonIgnore
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
    @JsonIgnore
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
    @JsonIgnore
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
    @JsonIgnore
    @JsonProperty( "Browser__c" )
    public void setBrowser(String browser) {
        super.setBrowser( browser);
    }
    
    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#setOs(String os)
     */
    @Override
    @JsonIgnore
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
    @JsonIgnore
    @JsonProperty( "Device__c" )
    public void setDevice(String device) {
        super.setDevice( device);
    }
}
