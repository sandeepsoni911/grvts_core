package com.owners.gravitas.dto.crm.request;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.owners.gravitas.dto.CRMOpportunity;
import com.owners.gravitas.serializer.CustomDateSerializer;
import com.owners.gravitas.serializer.CustomDateTimeSerializer;

/**
 * The Class CRMOpportunityRequest.
 *
 * @author vishwanathm
 */
public class CRMOpportunityRequest extends CRMOpportunity {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2638041472031695339L;

    /**
     * Gets the first name.
     *
     * @return the first name
     */
    @Override
    @JsonIgnore
    public String getFirstName() {
        return super.getFirstName();
    }

    /**
     * Gets the last name.
     *
     * @return the last name
     */
    @Override
    @JsonIgnore
    public String getLastName() {
        return super.getLastName();
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    @Override
    @JsonIgnore
    public String getEmail() {
        return super.getEmail();
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    @Override
    @JsonIgnore
    public String getPhone() {
        return super.getPhone();
    }

    /**
     * Gets the company.
     *
     * @return the company
     */
    @Override
    @JsonIgnore
    public String getCompany() {
        return super.getCompany();
    }

    /**
     * Gets the record type id.
     *
     * @return the recordTypeId
     */
    @Override
    @JsonProperty( "RecordTypeId" )
    public String getRecordType() {
        return super.getRecordType();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMOpportunity#getOrderId()
     */
    @Override
    @JsonProperty( "Owners_com_Order__c" )
    public String getOrderId() {
        return super.getOrderId();
    }

    /**
     * Gets the lead status.
     *
     * @return the lead status
     */
    @Override
    @JsonIgnore
    public String getLeadStatus() {
        return super.getLeadStatus();
    }

    /**
     * Gets the marketing opt in.
     *
     * @return the marketing opt in
     */
    @Override
    @JsonIgnore
    public boolean getMarketingOptIn() {
        return super.getMarketingOptIn();
    }

    /**
     * Gets the website.
     *
     * @return the website
     */
    @Override
    @JsonIgnore
    public String getWebsite() {
        return super.getWebsite();
    }

    /**
     * Gets the state name.
     *
     * @return the state name
     */
    @Override
    @JsonProperty( "StageName" )
    public String getStageName() {
        return super.getStageName();
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    @JsonProperty( "Type" )
    public String getType() {
        return super.getType();
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
     * Gets the property city del.
     *
     * @return the property city del
     */
    @Override
    @JsonProperty( "Property_City_del__c" )
    public String getPropertyCity() {
        return super.getPropertyCity();
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
     * Gets the property state.
     *
     * @return the property state
     */
    @Override
    @JsonProperty( "Property_State__c" )
    public String getState() {
        return super.getState();
    }

    /**
     * Gets the property zip.
     *
     * @return the property zip
     */
    @Override
    @JsonProperty( "Property_Zip_del__c" )
    public String getPropertyZip() {
        return super.getPropertyZip();
    }

    /**
     * Gets the listing id.
     *
     * @return the listing id
     */
    @Override
    @JsonProperty( "Listing_ID__c" )
    public String getListingId() {
        return super.getListingId();
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    @Override
    @JsonProperty( "Name" )
    public String getName() {
        return super.getName();
    }

    /**
     * Gets the account id.
     *
     * @return the accountId
     */
    @Override
    @JsonProperty( "AccountId" )
    public String getAccountId() {
        return super.getAccountId();
    }

    /**
     * Gets the contact id.
     *
     * @return the contact id
     */
    @Override
    @JsonProperty( "Primary_Contact__c" )
    public String getContactId() {
        return super.getContactId();
    }

    /**
     * Gets the probability.
     *
     * @return the probability
     */
    @Override
    @JsonProperty( "Probability" )
    public Integer getProbability() {
        return super.getProbability();
    }

    /**
     * Gets the notes.
     *
     * @return the notes.
     */
    @Override
    @JsonProperty( "Opportunity_Notes__c" )
    public String getComments() {
        return super.getComments();
    }

    /**
     * Gets the pre approved for mortgage.
     *
     * @return the preApprovedForMortgage
     */
    @Override
    @JsonProperty( "Pre_Approved_for_Mortgage__c" )
    public String getPreApprovedForMortgage() {
        return super.getPreApprovedForMortgage();
    }

    /**
     * Gets the working with realtor.
     *
     * @return the workingWithRealtor
     */
    @Override
    @JsonProperty( "Working_with_External_Agent__c" )
    public String getWorkingWithRealtor() {
        return super.getWorkingWithRealtor();
    }

    /**
     * Gets the buyer readiness timeline.
     *
     * @return the buyerReadinessTimeline
     */
    @Override
    @JsonProperty( "Buyer_Readiness_Timeline__c" )
    public String getBuyerReadinessTimeline() {
        return super.getBuyerReadinessTimeline();
    }

    /**
     * Gets the preferred contact time.
     *
     * @return the preferredContactTime
     */
    @Override
    @JsonProperty( "Preferred_Contact_Time__c" )
    public String getPreferredContactTime() {
        return super.getPreferredContactTime();
    }

    /**
     * Gets the preferred contact method.
     *
     * @return the preferredContactMethod
     */
    @Override
    @JsonProperty( "Preferred_Contact_Method__c" )
    public String getPreferredContactMethod() {
        return super.getPreferredContactMethod();
    }

    /**
     * Gets the price range.
     *
     * @return the priceRange
     */
    @Override
    @JsonProperty( "Price_Range__c" )
    public String getPriceRange() {
        return super.getPriceRange();
    }

    /**
     * Gets the package type.
     *
     * @return the package type
     */
    @Override
    @JsonProperty( "MLS_Package_Type__c" )
    public String getPackageType() {
        return super.getPackageType();
    }

    /**
     * Gets the buyer lead quality.
     *
     * @return the buyerLeadQuality
     */
    @Override
    @JsonProperty( "Buyer_Lead_Quality__c" )
    public String getBuyerLeadQuality() {
        return super.getBuyerLeadQuality();
    }

    /**
     * Gets the preferred language.
     *
     * @return the preferredLanguage
     */
    @Override
    @JsonProperty( "Preferred_Language__c" )
    public String getPreferredLanguage() {
        return super.getPreferredLanguage();
    }

    /**
     * Gets the mls id.
     *
     * @return the mlsId
     */
    @Override
    @JsonProperty( "MSID__c" )
    public String getMlsId() {
        return super.getMlsId();
    }

    /**
     * Gets the interested zipcodes.
     *
     * @return the interestedZipcodes
     */
    @Override
    @JsonProperty( "Interested_Zip_Codes__c" )
    public String getInterestedZipcodes() {
        return super.getInterestedZipcodes();
    }

    /**
     * Gets the owners com identifier.
     *
     * @return the ownersComIdentifier
     */
    @Override
    @JsonProperty( "Property_ID_MLS_ID__c" )
    public String getOwnersComIdentifier() {
        return super.getOwnersComIdentifier();
    }

    /**
     * Gets the offer amount.
     *
     * @return the offerAmount
     */
    @Override
    @JsonProperty( "Offer_Amount__c" )
    public String getOfferAmount() {
        return super.getOfferAmount();
    }

    /**
     * Gets the listing creation date.
     *
     * @return the listingCreationDate
     */
    @Override
    @JsonSerialize( using = CustomDateSerializer.class )
    @JsonProperty( "Listing_Creation_Date__c" )
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
     * Checks if is interested in financing.
     *
     * @return the interestedinfinancing
     */
    @Override
    @JsonIgnore
    public boolean isInterestedInFinancing() {
        return super.isInterestedInFinancing();
    }

    /**
     * Gets the lead source url.
     *
     * @return the leadSourceUrl
     */
    @Override
    @JsonProperty( "Lead_Source_URL__c" )
    public String getLeadSourceUrl() {
        return super.getLeadSourceUrl();
    }

    /**
     * Gets the saved search values.
     *
     * @return the savedSearchValues
     */
    @Override
    @JsonProperty( "Saved_Search_Values__c" )
    public String getSavedSearchValues() {
        return super.getSavedSearchValues();
    }

    /**
     * Gets the earnest money deposit.
     *
     * @return the earnestMoneyDeposit
     */
    @Override
    @JsonProperty( "Earnest_Money_Deposit__c" )
    public String getEarnestMoneyDeposit() {
        return super.getEarnestMoneyDeposit();
    }

    /**
     * Gets the purchase method.
     *
     * @return the purchaseMethod
     */
    @Override
    @JsonProperty( "Purchase_Method__c" )
    public String getPurchaseMethod() {
        return super.getPurchaseMethod();
    }

    /**
     * Gets the down payment.
     *
     * @return the downPayment
     */
    @Override
    @JsonProperty( "Down_Payment__c" )
    public String getDownPayment() {
        return super.getDownPayment();
    }

    /**
     * Gets the property tour information.
     *
     * @return the propertyTourInformation
     */
    @Override
    @JsonProperty( "Property_Tour_Information__c" )
    public String getPropertyTourInformation() {
        return super.getPropertyTourInformation();
    }

    /**
     * Gets the additional property data.
     *
     * @return the additionalPropertyData
     */
    @Override
    @JsonProperty( "Additional_Property_Information__c" )
    public String getAdditionalPropertyData() {
        return super.getAdditionalPropertyData();
    }

    /**
     * Gets the website session data.
     *
     * @return the websiteSessionData
     */
    @Override
    @JsonProperty( "Website_Session_Data__c" )
    public String getWebsiteSessionData() {
        return super.getWebsiteSessionData();
    }

    /**
     * Gets the owners visitor id.
     *
     * @return the ownersVisitorId
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
    @JsonProperty( "Lead_Message__c" )
    public String getMessage() {
        return super.getMessage();
    }

    /**
     * Gets the lost reason.
     *
     * @return the lostReason
     */
    @Override
    @JsonProperty( "Reason_Lost__c" )
    public String getLostReason() {
        return super.getLostReason();
    }

    /**
     * Gets the buy side commisstion.
     *
     * @return the buySideCommisstion
     */
    @Override
    @JsonProperty( "Commission_Post_Closing__c" )
    public String getBuySideCommisstion() {
        return super.getBuySideCommisstion();
    }

    /**
     * Gets the sales price.
     *
     * @return the salesPrice
     */
    @Override
    @JsonProperty( "Sales_Price_Post_Closing__c" )
    public BigDecimal getSalesPrice() {
        return super.getSalesPrice();
    }

    /**
     * Gets the expected contract date.
     *
     * @return the expectedContractDate
     */
    @Override
    @JsonSerialize( using = CustomDateSerializer.class )
    @JsonProperty( "CloseDate" )
    public DateTime getExpectedContractDate() {
        return super.getExpectedContractDate();
    }

    /**
     * Gets the pre approved amount.
     *
     * @return the preApprovedAmount
     */
    @Override
    @JsonProperty( "Pre_approved_Amount__c" )
    public BigDecimal getPreApprovedAmount() {
        return super.getPreApprovedAmount();
    }

    /**
     * Gets the request type.
     *
     * @return the requestType
     */
    @Override
    @JsonProperty( "Lead_Request_Type__c" )
    public String getRequestType() {
        return super.getRequestType();
    }

    /**
     * Gets the lead type.
     *
     * @return the leadType
     */
    @Override
    @JsonProperty( "Lead_RecordType_Mirror__c" )
    public String getLeadType() {
        return super.getLeadType();
    }

    /**
     * Gets the audit record.
     *
     * @return the auditRecord
     */
    @Override
    @JsonProperty( "Gravitas_Record_Audit__c" )
    public String getAuditRecord() {
        return super.getAuditRecord();
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
     * Checks if is de duped.
     *
     * @return the isDeDuped
     */
    @Override
    @JsonProperty( "Deduped_by_Gravitas__c" )
    public boolean isDeDuped() {
        return super.isDeDuped();
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

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMOpportunity#getOpportunityLabel()
     */
    @Override
    @JsonProperty( "Opportunity_Label__c" )
    public String getLabel() {
        return super.getLabel();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMOpportunity#getOpportunityScore()
     */
    @Override
    @JsonProperty( "Opportunity_Score__c" )
    public String getScore() {
        return super.getScore();
    }

    /**
     * Gets the record type name.
     *
     * @return the recordTypeName
     */
    @Override
    @JsonProperty( "Record_Type_Name__c" )
    @JsonIgnore
    public String getRecordTypeName() {
        return super.getRecordTypeName();
    }

    /**
     * Gets the seller state.
     *
     * @param propertyState
     *            the property state
     * @return the seller state
     */
    @Override
    @JsonProperty( "Property_States_del__c" )
    @JsonIgnore
    public String getSellerState() {
        return super.getSellerState();
    }

    /*
     * (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getCreatedDateTime()
     */
    @Override
    @JsonProperty( "CreatedDate" )
    @JsonSerialize( using = CustomDateTimeSerializer.class )
    @JsonIgnore
    public DateTime getCreatedDateTime() {
        return super.getCreatedDateTime();
    }

    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getLastModifiedDate()
     */
    @Override
    @JsonProperty( "LastModifiedDate" )
    @JsonSerialize( using = CustomDateTimeSerializer.class )
    @JsonIgnore
    public DateTime getLastModifiedDate() {
        return super.getLastModifiedDate();
    }
    
    
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getPropertyBedroom()
     */
    @Override
    @JsonIgnore
    public String getPropertyBedroom() {
        return super.getPropertyBedroom();
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getPropertyBathroom()
     */
    @Override
    @JsonIgnore
    public String getPropertyBathroom() {
        return super.getPropertyBathroom();
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getPropertySquareFoot()
     */
    @Override
    @JsonIgnore
    public String getPropertySquareFoot() {
        return super.getPropertySquareFoot();
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getPropertyType()
     */
    @Override
    @JsonIgnore
    public String getPropertyType() {
        return super.getPropertyType();
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getInquiryDate()
     */
    @Override
    @JsonIgnore
    public DateTime getInquiryDate() {
        return super.getInquiryDate();
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getSearchCity()
     */
    @Override
    @JsonIgnore
    public String getSearchCity() {
        return super.getSearchCity();
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getSearchAttributes()
     */
    @Override
    @JsonIgnore
    public String getSearchAttributes() {
        return super.getSearchAttributes();
    }
    
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getPartnerIdentifier()
     */
    @Override
    @JsonIgnore
    public String getPartnerIdentifier() {
        return super.getPartnerIdentifier();
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#Lot_Size__c()
     */
    @Override
    @JsonIgnore
    public String getLotSize() {
        return super.getLotSize();
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getCreditScore()
     */
    @Override
    @JsonIgnore
    public String getCreditScore() {
        return super.getCreditScore();
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getHomeType()
     */
    @Override
    @JsonIgnore
    public String getHomeType() {
        return super.getHomeType();
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getBrowser()
     */
    @Override
    @JsonIgnore
    public String getBrowser() {
        return super.getBrowser();
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getOs()
     */
    @Override
    @JsonIgnore
    public String getOs() {
        return super.getOs();
    }
    
    /* (non-Javadoc)
     * @see com.owners.gravitas.dto.CRMBaseDTO#getDevice()
     */
    @Override
    @JsonIgnore
    public String getDevice() {
        return super.getDevice();
    }
    
}
