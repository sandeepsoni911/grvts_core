package com.owners.gravitas.amqp;

import java.math.BigDecimal;
import java.util.Date;

import com.owners.gravitas.dto.BaseDTO;
import com.owners.gravitas.dto.Contact;
import com.owners.gravitas.enums.EventType;

/**
 * The Class OpportunityHolder holds opportunity details.
 *
 * @author vishwanathm
 */
public class OpportunitySource extends BaseDTO {

    /** For Serialization. */
    private static final long serialVersionUID = 1495858877151261992L;

    /** The event type. */
    private EventType eventType;

    /** The contacts. */
    private Contact primaryContact;

    /** The stage. */
    private String stage;

    /** The property ids. */
    private String listingId;

    /** The crm id. */
    private String crmId;

    /** The lead source. */
    private String leadSource;

    /** The working with external agent. */
    private String workingWithExternalAgent;

    /** The buyer readiness timeline. */
    private String buyerReadinessTimeline;

    /** The budget. */
    private String budget;

    /** The reason lost. */
    private String reasonLost;

    /** The buy side commission. */
    private String buySideCommission;

    /** The sales price. */
    private BigDecimal salesPrice;

    /** The expected contract date. */
    private Date expectedContractDate;

    /** The pre approved amount. */
    private BigDecimal preApprovedAmount;

    /** The opportunity type. */
    private String opportunityType;

    /** The commission base price. */
    private BigDecimal commissionBasePrice;

    /** The title company. */
    private String titleCompany;

    /** The pending date. */
    private Date pendingDate;

    /** The actual closing date. */
    private Date actualClosingDate;

    /** The expected agent revenue. */
    private BigDecimal expectedAgentRevenue;

    /** The expected owners com revenue. */
    private BigDecimal expectedOwnersComRevenue;

    /** The notes. */
    private String notes;

    /** The pre approved for mortgage. */
    private String preApprovedForMortgage;

    /** The interested zip codes. */
    private String interestedZipCodes;

    /** The financing. */
    private String financing;

    /** The median price. */
    private BigDecimal medianPrice;

    /** The agent email. */
    private String agentEmail;

    /** The lead request type. */
    private String leadRequestType;

    /** The offer amount. */
    private BigDecimal offerAmount;

    /** The earnest money deposit. */
    private String earnestMoneyDeposit;

    /** The purchase method. */
    private String purchaseMethod;

    /** The down payment. */
    private String downPayment;

    /** The property tour information. */
    private String propertyTourInformation;

    /** The lead message. */
    private String leadMessage;

    /** The prefered language. */
    private String preferedLanguage;

    /** The request summary. */
    private String requestSummary;

    /** The last modified by. */
    private String lastModifiedBy;

    /** Stage Changed flag. */
    private boolean isStageChanged;

    /** Title Selection Reason. */
    private String titleSelectionReason;

    /** The price range. */
    private String priceRange;

    /** The property address. */
    private String propertyAddress;

    /** The listing side commission. */
    private String listingSideCommission;

    /** The list price. */
    private BigDecimal listPrice;

    /** The list date. */
    private Date listDate;

    /** The expiration date. */
    private Date expirationDate;

    /** The opportunity owner name. */
    private String opportunityOwnerName;

    /** The opportunity owner email. */
    private String opportunityOwnerEmail;

    /** The property state. */
    private String propertyState;

    /** The record type. */
    private String recordType;

    /** The buyer lead quality. */
    private String buyerLeadQuality;

    /** The dedup count. */
    private String dedupCount;

    /** The property city. */
    private String propertyCity;

    /** The referred. */
    private boolean referred;

    /** The price ranges. */
    private String priceRanges;

    /** The seller property state. */
    private String sellerPropertyState;

    private String propertyZip;
    
    private String farmingGroup;
    

    public String getFarmingGroup() {
        return farmingGroup;
    }

    public void setFarmingGroup( String farmingGroup ) {
        this.farmingGroup = farmingGroup;
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
    public void setPriceRanges( final String priceRanges ) {
        this.priceRanges = priceRanges;
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
    public void setPropertyCity( final String propertyCity ) {
        this.propertyCity = propertyCity;
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
     * Checks if is referred.
     *
     * @return true, if is referred
     */
    public boolean isReferred() {
        return referred;
    }

    /**
     * Sets the referred.
     *
     * @param referred
     *            the new referred
     */
    public void setReferred( final boolean referred ) {
        this.referred = referred;
    }

    /**
     * Gets the opportunity owner name.
     *
     * @return the opportunity owner name
     */
    public String getOpportunityOwnerName() {
        return opportunityOwnerName;
    }

    /**
     * Sets the opportunity owner name.
     *
     * @param opportunityOwnerName
     *            the new opportunity owner name
     */
    public void setOpportunityOwnerName( final String opportunityOwnerName ) {
        this.opportunityOwnerName = opportunityOwnerName;
    }

    /**
     * Gets the opportunity owner email.
     *
     * @return the opportunity owner email
     */
    public String getOpportunityOwnerEmail() {
        return opportunityOwnerEmail;
    }

    /**
     * Sets the opportunity owner email.
     *
     * @param opportunityOwnerEmail
     *            the new opportunity owner email
     */
    public void setOpportunityOwnerEmail( final String opportunityOwnerEmail ) {
        this.opportunityOwnerEmail = opportunityOwnerEmail;
    }

    /**
     * Gets the primary contact.
     *
     * @return the primary contact
     */
    public Contact getPrimaryContact() {
        return primaryContact;
    }

    /**
     * Sets the primary contact.
     *
     * @param primaryContact
     *            the new primary contact
     */
    public void setPrimaryContact( final Contact primaryContact ) {
        this.primaryContact = primaryContact;
    }

    /**
     * Gets the stage.
     *
     * @return the stage
     */
    public String getStage() {
        return stage;
    }

    /**
     * Sets the stage.
     *
     * @param stage
     *            the new stage
     */
    public void setStage( final String stage ) {
        this.stage = stage;
    }

    /**
     * Gets the property ids.
     *
     * @return the property ids
     */
    public String getListingId() {
        return listingId;
    }

    /**
     * Sets the property ids.
     *
     * @param propertyIds
     *            the new listing ids
     */
    public void setListingId( final String propertyIds ) {
        this.listingId = propertyIds;
    }

    /**
     * Gets the crm id.
     *
     * @return the crm id
     */
    public String getCrmId() {
        return crmId;
    }

    /**
     * Sets the crm id.
     *
     * @param crmId
     *            the new crm id
     */
    public void setCrmId( final String crmId ) {
        this.crmId = crmId;
    }

    /**
     * Gets the lead source.
     *
     * @return the lead source
     */
    public String getLeadSource() {
        return leadSource;
    }

    /**
     * Sets the lead source.
     *
     * @param leadSource
     *            the new lead source
     */
    public void setLeadSource( final String leadSource ) {
        this.leadSource = leadSource;
    }

    /**
     * Gets the working with external agent.
     *
     * @return the working with external agent
     */
    public String getWorkingWithExternalAgent() {
        return workingWithExternalAgent;
    }

    /**
     * Sets the working with external agent.
     *
     * @param workingWithExternalAgent
     *            the new working with external agent
     */
    public void setWorkingWithExternalAgent( final String workingWithExternalAgent ) {
        this.workingWithExternalAgent = workingWithExternalAgent;
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
     * Gets the budget.
     *
     * @return the budget
     */
    public String getBudget() {
        return budget;
    }

    /**
     * Sets the budget.
     *
     * @param budget
     *            the new budget
     */
    public void setBudget( final String budget ) {
        this.budget = budget;
    }

    /**
     * Gets the reason lost.
     *
     * @return the reason lost
     */
    public String getReasonLost() {
        return reasonLost;
    }

    /**
     * Sets the reason lost.
     *
     * @param reasonLost
     *            the new reason lost
     */
    public void setReasonLost( final String reasonLost ) {
        this.reasonLost = reasonLost;
    }

    /**
     * Gets the buy side commission.
     *
     * @return the buy side commission
     */
    public String getBuySideCommission() {
        return buySideCommission;
    }

    /**
     * Sets the buy side commission.
     *
     * @param buySideCommission
     *            the new buy side commission
     */
    public void setBuySideCommission( final String buySideCommission ) {
        this.buySideCommission = buySideCommission;
    }

    /**
     * Gets the sales price.
     *
     * @return the sales price
     */
    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    /**
     * Sets the sales price.
     *
     * @param salesPrice
     *            the new sales price
     */
    public void setSalesPrice( final BigDecimal salesPrice ) {
        this.salesPrice = salesPrice;
    }

    /**
     * Gets the expected contract date.
     *
     * @return the expected contract date
     */
    public Date getExpectedContractDate() {
        return expectedContractDate;
    }

    /**
     * Sets the expected contract date.
     *
     * @param expectedContractDate
     *            the new expected contract date
     */
    public void setExpectedContractDate( final Date expectedContractDate ) {
        this.expectedContractDate = expectedContractDate;
    }

    /**
     * Gets the pre approved amount.
     *
     * @return the pre approved amount
     */
    public BigDecimal getPreApprovedAmount() {
        return preApprovedAmount;
    }

    /**
     * Sets the pre approved amount.
     *
     * @param preApprovedAmount
     *            the new pre approved amount
     */
    public void setPreApprovedAmount( final BigDecimal preApprovedAmount ) {
        this.preApprovedAmount = preApprovedAmount;
    }

    /**
     * Gets the opportunity type.
     *
     * @return the opportunity type
     */
    public String getOpportunityType() {
        return opportunityType;
    }

    /**
     * Sets the opportunity type.
     *
     * @param opportunityType
     *            the new opportunity type
     */
    public void setOpportunityType( final String opportunityType ) {
        this.opportunityType = opportunityType;
    }

    /**
     * Gets the commission base price.
     *
     * @return the commission base price
     */
    public BigDecimal getCommissionBasePrice() {
        return commissionBasePrice;
    }

    /**
     * Sets the commission base price.
     *
     * @param commissionBasePrice
     *            the new commission base price
     */
    public void setCommissionBasePrice( final BigDecimal commissionBasePrice ) {
        this.commissionBasePrice = commissionBasePrice;
    }

    /**
     * Gets the title company.
     *
     * @return the title company
     */
    public String getTitleCompany() {
        return titleCompany;
    }

    /**
     * Sets the title company.
     *
     * @param titleCompany
     *            the new title company
     */
    public void setTitleCompany( final String titleCompany ) {
        this.titleCompany = titleCompany;
    }

    /**
     * Gets the pending date.
     *
     * @return the pending date
     */
    public Date getPendingDate() {
        return pendingDate;
    }

    /**
     * Sets the pending date.
     *
     * @param pendingDate
     *            the new pending date
     */
    public void setPendingDate( final Date pendingDate ) {
        this.pendingDate = pendingDate;
    }

    /**
     * Gets the actual closing date.
     *
     * @return the actual closing date
     */
    public Date getActualClosingDate() {
        return actualClosingDate;
    }

    /**
     * Sets the actual closing date.
     *
     * @param actualClosingDate
     *            the new actual closing date
     */
    public void setActualClosingDate( final Date actualClosingDate ) {
        this.actualClosingDate = actualClosingDate;
    }

    /**
     * Gets the expected agent revenue.
     *
     * @return the expected agent revenue
     */
    public BigDecimal getExpectedAgentRevenue() {
        return expectedAgentRevenue;
    }

    /**
     * Sets the expected agent revenue.
     *
     * @param expectedAgentRevenue
     *            the new expected agent revenue
     */
    public void setExpectedAgentRevenue( final BigDecimal expectedAgentRevenue ) {
        this.expectedAgentRevenue = expectedAgentRevenue;
    }

    /**
     * Gets the expected owners com revenue.
     *
     * @return the expected owners com revenue
     */
    public BigDecimal getExpectedOwnersComRevenue() {
        return expectedOwnersComRevenue;
    }

    /**
     * Sets the expected owners com revenue.
     *
     * @param expectedOwnersComRevenue
     *            the new expected owners com revenue
     */
    public void setExpectedOwnersComRevenue( final BigDecimal expectedOwnersComRevenue ) {
        this.expectedOwnersComRevenue = expectedOwnersComRevenue;
    }

    /**
     * Gets the notes.
     *
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the notes.
     *
     * @param notes
     *            the new notes
     */
    public void setNotes( final String notes ) {
        this.notes = notes;
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
     * Gets the interested zip codes.
     *
     * @return the interested zip codes
     */
    public String getInterestedZipCodes() {
        return interestedZipCodes;
    }

    /**
     * Sets the interested zip codes.
     *
     * @param interestedZipCodes
     *            the new interested zip codes
     */
    public void setInterestedZipCodes( final String interestedZipCodes ) {
        this.interestedZipCodes = interestedZipCodes;
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
     * Gets the median price.
     *
     * @return the median price
     */
    public BigDecimal getMedianPrice() {
        return medianPrice;
    }

    /**
     * Sets the median price.
     *
     * @param medianPrice
     *            the new median price
     */
    public void setMedianPrice( final BigDecimal medianPrice ) {
        this.medianPrice = medianPrice;
    }

    /**
     * Gets the event type.
     *
     * @return the eventType
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Sets the event type.
     *
     * @param eventType
     *            the eventType to set
     */
    public void setEventType( final EventType eventType ) {
        this.eventType = eventType;
    }

    /**
     * Gets the agent email.
     *
     * @return the agentEmail
     */
    public String getAgentEmail() {
        return agentEmail;
    }

    /**
     * Sets the agent email.
     *
     * @param agentEmail
     *            the agentEmail to set
     */
    public void setAgentEmail( final String agentEmail ) {
        this.agentEmail = agentEmail;
    }

    /**
     * Gets the lead request type.
     *
     * @return the lead request type
     */
    public String getLeadRequestType() {
        return leadRequestType;
    }

    /**
     * Sets the lead request type.
     *
     * @param leadRequestType
     *            the new lead request type
     */
    public void setLeadRequestType( final String leadRequestType ) {
        this.leadRequestType = leadRequestType;
    }

    /**
     * Gets the offer amount.
     *
     * @return the offer amount
     */
    public BigDecimal getOfferAmount() {
        return offerAmount;
    }

    /**
     * Sets the offer amount.
     *
     * @param offerAmount
     *            the new offer amount
     */
    public void setOfferAmount( final BigDecimal offerAmount ) {
        this.offerAmount = offerAmount;
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
     * Gets the lead message.
     *
     * @return the lead message
     */
    public String getLeadMessage() {
        return leadMessage;
    }

    /**
     * Sets the lead message.
     *
     * @param leadMessage
     *            the new lead message
     */
    public void setLeadMessage( final String leadMessage ) {
        this.leadMessage = leadMessage;
    }

    /**
     * Gets the prefered language.
     *
     * @return the prefered language
     */
    public String getPreferedLanguage() {
        return preferedLanguage;
    }

    /**
     * Sets the prefered language.
     *
     * @param preferedLanguage
     *            the new prefered language
     */
    public void setPreferedLanguage( final String preferedLanguage ) {
        this.preferedLanguage = preferedLanguage;
    }

    /**
     * Gets the request summary.
     *
     * @return the request summary
     */
    public String getRequestSummary() {
        return requestSummary;
    }

    /**
     * Sets the request summary.
     *
     * @param requestSummary
     *            the new request summary
     */
    public void setRequestSummary( final String requestSummary ) {
        this.requestSummary = requestSummary;
    }

    /**
     * Gets the last modified by.
     *
     * @return the last modified by
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * Sets the last modified by.
     *
     * @param lastModifiedBy
     *            the new last modified by
     */
    public void setLastModifiedBy( final String lastModifiedBy ) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /**
     * Checks if is stage changed.
     *
     * @return true, if is stage changed
     */
    public boolean isStageChanged() {
        return isStageChanged;
    }

    /**
     * Sets the stage changed.
     *
     * @param isStageChanged
     *            the new stage changed
     */
    public void setStageChanged( final boolean isStageChanged ) {
        this.isStageChanged = isStageChanged;
    }

    /**
     * Gets the title Selection Reason.
     *
     * @return the titleSelectionReason
     */
    public String getTitleSelectionReason() {
        return titleSelectionReason;
    }

    /**
     * Sets the title Selection Reason.
     *
     * @param titleSelectionReason
     *            the titleSelectionReason to set
     */
    public void setTitleSelectionReason( final String titleSelectionReason ) {
        this.titleSelectionReason = titleSelectionReason;
    }

    /**
     * Gets the price range.
     *
     * @return the priceRange
     */
    public String getPriceRange() {
        return priceRange;
    }

    /**
     * Sets the price range.
     *
     * @param priceRange
     *            the priceRange to set
     */
    public void setPriceRange( final String priceRange ) {
        this.priceRange = priceRange;
    }

    /**
     * Gets the property address.
     *
     * @return the propertyAddress
     */
    public String getPropertyAddress() {
        return propertyAddress;
    }

    /**
     * Sets the property address.
     *
     * @param propertyAddress
     *            the propertyAddress to set
     */
    public void setPropertyAddress( final String propertyAddress ) {
        this.propertyAddress = propertyAddress;
    }

    /**
     * Gets the listing side commission.
     *
     * @return the listingSideCommission
     */
    public String getListingSideCommission() {
        return listingSideCommission;
    }

    /**
     * Sets the listing side commission.
     *
     * @param listingSideCommission
     *            the listingSideCommission to set
     */
    public void setListingSideCommission( final String listingSideCommission ) {
        this.listingSideCommission = listingSideCommission;
    }

    /**
     * Gets the list price.
     *
     * @return the listPrice
     */
    public BigDecimal getListPrice() {
        return listPrice;
    }

    /**
     * Sets the list price.
     *
     * @param listPrice
     *            the listPrice to set
     */
    public void setListPrice( final BigDecimal listPrice ) {
        this.listPrice = listPrice;
    }

    /**
     * Gets the list date.
     *
     * @return the listDate
     */
    public Date getListDate() {
        return listDate;
    }

    /**
     * Sets the list date.
     *
     * @param listDate
     *            the new list date
     *            >>>>>>> master
     */
    public void setListDate( final Date listDate ) {
        this.listDate = listDate;
    }

    /**
     * Gets the expiration date.
     *
     * @return the expirationDate
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the expiration date.
     *
     * @param expirationDate
     *            the expirationDate to set
     */
    public void setExpirationDate( final Date expirationDate ) {
        this.expirationDate = expirationDate;
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
     * Gets the buyer lead quality.
     *
     * @return the buyerLeadQuality
     */
    public String getBuyerLeadQuality() {
        return buyerLeadQuality;
    }

    /**
     * Sets the buyer lead quality.
     *
     * @param buyerLeadQuality
     *            the buyerLeadQuality to set
     */
    public void setBuyerLeadQuality( final String buyerLeadQuality ) {
        this.buyerLeadQuality = buyerLeadQuality;
    }

    /**
     * Gets the dedup count.
     *
     * @return the dedupCount
     */
    public String getDedupCount() {
        return dedupCount;
    }

    /**
     * Sets the dedup count.
     *
     * @param dedupCount
     *            the dedupCount to set
     */
    public void setDedupCount( final String dedupCount ) {
        this.dedupCount = dedupCount;
    }

    /**
     * Gets the seller property state.
     *
     * @return the seller property state
     */
    public String getSellerPropertyState() {
        return sellerPropertyState;
    }

    /**
     * Sets the seller property state.
     *
     * @param sellerPropertyState
     *            the new seller property state
     */
    public void setSellerPropertyState( final String sellerPropertyState ) {
        this.sellerPropertyState = sellerPropertyState;
    }

    /**
     * Gets the property zip.
     *
     * @return the property zip
     */
    public String getPropertyZip() {
        return propertyZip;
    }

    /**
     * Sets the property zip.
     *
     * @param propertyZip
     *            the new property zip
     */
    public void setPropertyZip( final String propertyZip ) {
        this.propertyZip = propertyZip;
    }
}
