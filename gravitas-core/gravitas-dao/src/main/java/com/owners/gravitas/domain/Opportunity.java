/*
 *
 */
package com.owners.gravitas.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class OpportunitySource.
 *
 * @author amits
 */
@JsonIgnoreProperties( ignoreUnknown = true )
@JsonInclude( JsonInclude.Include.NON_NULL )
public class Opportunity extends BaseDomain {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -8852680567751915158L;

    /** The contacts. */
    private Set< String > contacts = new HashSet<>();

    /** The action flow ids. */
    private List< String > actionFlowIds = new ArrayList<>();

    /** The stage. */
    private String stage;

    /** The stage history. */
    private List< Stage > stageHistory = new ArrayList<>();

    /** The property ids. */
    private List< String > listingIds = new ArrayList<>();

    /** The crm id. */
    private String crmId;
    /** The accepted. */
    private Boolean accepted;

    /** The accepted dtm. */
    private Long acceptedDtm;

    /** The initial contact dtm. */
    private Long initialContactDtm;

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

    /** The area of interest. */
    private String areaOfInterest;

    /** The financing. */
    private String financing;

    /** The median price. */
    private BigDecimal medianPrice;

    /** The created dtm. */
    private Long createdDtm;

    /** The created by. */
    private String createdBy;

    /** The request summary. */
    private String requestSummary;

    /** The last modified dtm. */
    private Long lastModifiedDtm;

    /** The preferred language. */
    private String preferredLanguage;

    /** The delete. */
    private Boolean deleted;

    /** The last viewed dtm. */
    private Long lastViewedDtm;

    /** Title Selection Reason. */
    private String titleSelectionReason;

    /** The assigned dtm. */
    private Long assignedDtm;

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

    /** The offer amount. */
    private BigDecimal offerAmount;

    /** The first contact dtm. */
    private Long firstContactDtm;

    /** The best time to contact. */
    private String bestTimeToContact;

    /** The is client expecting call. */
    private String isClientExpectingCall;

    /** The first time home buyer. */
    private String firstTimeHomeBuyer;

    /** The selling home as part of purchase. */
    private String sellingHomeAsPartOfPurchase;

    /** The agent ocl notes. */
    private String agentOclNotes;

    /** The ocl referral status. */
    private String oclReferralStatus;

    /**
     * Gets the contacts.
     *
     * @return the contacts
     */
    public Set< String > getContacts() {
        return contacts;
    }

    /**
     * Sets the contacts.
     *
     * @param contacts
     *            the new contacts
     */
    public void setContacts( final Set< String > contacts ) {
        this.contacts = contacts;
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
     * Gets the stage history.
     *
     * @return the stage history
     */
    public List< Stage > getStageHistory() {
        return stageHistory;
    }

    /**
     * Sets the stage history.
     *
     * @param stageHistory
     *            the stage history
     */
    public void setStageHistory( final List< Stage > stageHistory ) {
        this.stageHistory = stageHistory;
    }

    /**
     * Gets the property ids.
     *
     * @return the property ids
     */
    public List< String > getListingIds() {
        return listingIds;
    }

    /**
     * Sets the property ids.
     *
     * @param propertyIds
     *            the new listing ids
     */
    public void setListingIds( final List< String > propertyIds ) {
        this.listingIds = propertyIds;
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
     * Checks if is accepted.
     *
     * @return true, if is accepted
     */
    public Boolean isAccepted() {
        return accepted;
    }

    /**
     * Sets the accepted.
     *
     * @param accepted
     *            the new accepted
     */
    public void setAccepted( final Boolean accepted ) {
        this.accepted = accepted;
    }

    /**
     * Gets the accepted dtm.
     *
     * @return the accepted dtm
     */
    public Long getAcceptedDtm() {
        return acceptedDtm;
    }

    /**
     * Sets the accepted dtm.
     *
     * @param acceptedDtm
     *            the new accepted dtm
     */
    public void setAcceptedDtm( final Long acceptedDtm ) {
        this.acceptedDtm = acceptedDtm;
    }

    /**
     * Gets the initial contact dtm.
     *
     * @return the initial contact dtm
     */
    public Long getInitialContactDtm() {
        return initialContactDtm;
    }

    /**
     * Sets the initial contact dtm.
     *
     * @param initialContactDtm
     *            the new initial contact dtm
     */
    public void setInitialContactDtm( final Long initialContactDtm ) {
        this.initialContactDtm = initialContactDtm;
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
     * Gets the area of interest.
     *
     * @return the area of interest
     */
    public String getAreaOfInterest() {
        return areaOfInterest;
    }

    /**
     * Sets the area of interest.
     *
     * @param areaOfInterest
     *            the new area of interest
     */
    public void setAreaOfInterest( final String areaOfInterest ) {
        this.areaOfInterest = areaOfInterest;
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
     * Gets the created dtm.
     *
     * @return the created dtm
     */
    public Long getCreatedDtm() {
        return createdDtm;
    }

    /**
     * Sets the created dtm.
     *
     * @param createdDtm
     *            the new created dtm
     */
    public void setCreatedDtm( final Long createdDtm ) {
        this.createdDtm = createdDtm;
    }

    /**
     * Gets the created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy
     *            the new created by
     */
    public void setCreatedBy( final String createdBy ) {
        this.createdBy = createdBy;
    }

    /**
     * Adds the contact.
     *
     * @param contact
     *            the contact
     */
    public void addContact( final String contact ) {
        contacts.add( contact );
    }

    /**
     * Adds the listing id.
     *
     * @param listingId
     *            the listing id
     */
    public void addListingId( final String listingId ) {
        listingIds.add( listingId );
    }

    /**
     * Adds the stage.
     *
     * @param stage
     *            the stage
     */
    public void addStage( final Stage stage ) {
        this.stageHistory.add( stage );
    }

    /**
     * Pop stage.
     *
     * @return the stage
     */
    public Stage popStage() {
        if (!this.stageHistory.isEmpty()) {
            return this.stageHistory.get( this.stageHistory.size() - 1 );
        }
        return null;
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
     * Gets the last modified dtm.
     *
     * @return the last modified dtm
     */
    public Long getLastModifiedDtm() {
        return lastModifiedDtm;
    }

    /**
     * Sets the last modified dtm.
     *
     * @param lastModifiedDtm
     *            the new last modified dtm
     */
    public void setLastModifiedDtm( final Long lastModifiedDtm ) {
        this.lastModifiedDtm = lastModifiedDtm;
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
     * Gets the delete.
     *
     * @return the delete
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * Sets the delete.
     *
     * @param deleted
     *            the new deleted
     */
    public void setDeleted( final Boolean deleted ) {
        this.deleted = deleted;
    }

    /**
     * Gets the last viewed dtm.
     *
     * @return the last viewed dtm
     */
    public Long getLastViewedDtm() {
        return lastViewedDtm;
    }

    /**
     * Sets the last viewed dtm.
     *
     * @param lastViewedDtm
     *            the new last viewed dtm
     */
    public void setLastViewedDtm( final Long lastViewedDtm ) {
        this.lastViewedDtm = lastViewedDtm;
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
     * Gets the assigned dtm.
     *
     * @return the assigned dtm
     */
    public Long getAssignedDtm() {
        return assignedDtm;
    }

    /**
     * Sets the assigned dtm.
     *
     * @param assignedDtm
     *            the new assigned dtm
     */
    public void setAssignedDtm( final Long assignedDtm ) {
        this.assignedDtm = assignedDtm;
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
     * Gets the list dtm.
     *
     * @return the listDtm
     */
    public Date getListDate() {
        return listDate;
    }

    /**
     * Sets the list date.
     *
     * @param listDate
     *            the new list date
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
     * Gets the offer amount.
     *
     * @return the offerAmount
     */
    public BigDecimal getOfferAmount() {
        return offerAmount;
    }

    /**
     * Sets the offer amount.
     *
     * @param offerAmount
     *            the offerAmount to set
     */
    public void setOfferAmount( final BigDecimal offerAmount ) {
        this.offerAmount = offerAmount;
    }

    /**
     * Gets the first contact dtm.
     *
     * @return the first contact dtm
     */
    public Long getFirstContactDtm() {
        return firstContactDtm;
    }

    /**
     * Sets the first contact dtm.
     *
     * @param firstContactDtm
     *            the new first contact dtm
     */
    public void setFirstContactDtm( final Long firstContactDtm ) {
        this.firstContactDtm = firstContactDtm;
    }

    /**
     * Gets the action flow ids.
     *
     * @return the action flow ids
     */
    public List< String > getActionFlowIds() {
        return actionFlowIds;
    }

    /**
     * Sets the action flow ids.
     *
     * @param actionFlowIds
     *            the new action flow ids
     */
    public void setActionFlowIds( final List< String > actionFlowIds ) {
        this.actionFlowIds = actionFlowIds;
    }

    /**
     * Gets the best time to contact.
     *
     * @return the best time to contact
     */
    public String getBestTimeToContact() {
        return bestTimeToContact;
    }

    /**
     * Sets the best time to contact.
     *
     * @param bestTimeToContact
     *            the new best time to contact
     */
    public void setBestTimeToContact( final String bestTimeToContact ) {
        this.bestTimeToContact = bestTimeToContact;
    }

    /**
     * Gets the checks if is client expecting call.
     *
     * @return the checks if is client expecting call
     */
    public String getIsClientExpectingCall() {
        return isClientExpectingCall;
    }

    /**
     * Sets the checks if is client expecting call.
     *
     * @param isClientExpectingCall
     *            the new checks if is client expecting call
     */
    public void setIsClientExpectingCall( final String isClientExpectingCall ) {
        this.isClientExpectingCall = isClientExpectingCall;
    }

    /**
     * Gets the first time home buyer.
     *
     * @return the first time home buyer
     */
    public String getFirstTimeHomeBuyer() {
        return firstTimeHomeBuyer;
    }

    /**
     * Sets the first time home buyer.
     *
     * @param firstTimeHomeBuyer
     *            the new first time home buyer
     */
    public void setFirstTimeHomeBuyer( final String firstTimeHomeBuyer ) {
        this.firstTimeHomeBuyer = firstTimeHomeBuyer;
    }

    /**
     * Gets the selling home as part of purchase.
     *
     * @return the selling home as part of purchase
     */
    public String getSellingHomeAsPartOfPurchase() {
        return sellingHomeAsPartOfPurchase;
    }

    /**
     * Sets the selling home as part of purchase.
     *
     * @param sellingHomeAsPartOfPurchase
     *            the new selling home as part of purchase
     */
    public void setSellingHomeAsPartOfPurchase( final String sellingHomeAsPartOfPurchase ) {
        this.sellingHomeAsPartOfPurchase = sellingHomeAsPartOfPurchase;
    }

    /**
     * Gets the agent ocl notes.
     *
     * @return the agent ocl notes
     */
    public String getAgentOclNotes() {
        return agentOclNotes;
    }

    /**
     * Sets the agent ocl notes.
     *
     * @param agentOclNotes
     *            the new agent ocl notes
     */
    public void setAgentOclNotes( final String agentOclNotes ) {
        this.agentOclNotes = agentOclNotes;
    }

    /**
     * Gets the ocl referral status.
     *
     * @return the ocl referral status
     */
    public String getOclReferralStatus() {
        return oclReferralStatus;
    }

    /**
     * Sets the ocl referral status.
     *
     * @param oclReferralStatus
     *            the new ocl referral status
     */
    public void setOclReferralStatus( final String oclReferralStatus ) {
        this.oclReferralStatus = oclReferralStatus;
    }

}
