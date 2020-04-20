package com.owners.gravitas.domain;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.TourDetails;
import com.owners.gravitas.enums.RequestType;

/**
 * The Class Request.
 *
 * @author amits
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class Request extends BaseDomain {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1275460588964778773L;

    /** The request type. */
    private RequestType type;

    /** The opportunity id. */
    private String opportunityId;

    /** The converted. */
    private boolean converted;

    /** The property id. */
    private String listingId;

    /** The opportunity notes. */
    private String opportunityNotes;

    /** The pre approvaed for mortgage. */
    private String preApprovaedForMortgage;

    /** The offer amount. */
    private BigDecimal offerAmount;

    /** The earnest money deposit. */
    private String earnestMoneyDeposit;

    /** The purchase method. */
    private String purchaseMethod;

    /** The down payment. */
    private String downPayment;

    /** The property tour info. */
    private String propertyTourInfo;

    /** The lead message. */
    private String leadMessage;

    /** The created dtm. */
    private Long createdDtm;

    /** The created by. */
    private String createdBy;

    /** The last modified dtm. */
    private Long lastModifiedDtm;

    /** The task id. */
    private String taskId;

    /** The deleted. */
    private Boolean deleted = Boolean.FALSE;

    /** The last viewed dtm. */
    private Long lastViewedDtm;

    /** The dates. */
    private List< List< TourDetails > > dates;

    /** The status. */
    private String status;

    /**
     * Gets the opportunity id.
     *
     * @return the opportunity id
     */
    public String getOpportunityId() {
        return opportunityId;
    }

    /**
     * Sets the opportunity id.
     *
     * @param opportunityId
     *            the new opportunity id
     */
    public void setOpportunityId( final String opportunityId ) {
        this.opportunityId = opportunityId;
    }

    /**
     * Checks if is converted.
     *
     * @return true, if is converted
     */
    public boolean isConverted() {
        return converted;
    }

    /**
     * Sets the converted.
     *
     * @param converted
     *            the new converted
     */
    public void setConverted( final boolean converted ) {
        this.converted = converted;
    }

    /**
     * Gets the property id.
     *
     * @return the property id
     */
    public String getListingId() {
        return listingId;
    }

    /**
     * Sets the property id.
     *
     * @param propertyId
     *            the new listing id
     */
    public void setListingId( final String propertyId ) {
        this.listingId = propertyId;
    }

    /**
     * Gets the opportunity notes.
     *
     * @return the opportunity notes
     */
    public String getOpportunityNotes() {
        return opportunityNotes;
    }

    /**
     * Sets the opportunity notes.
     *
     * @param opportunityNotes
     *            the new opportunity notes
     */
    public void setOpportunityNotes( final String opportunityNotes ) {
        this.opportunityNotes = opportunityNotes;
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
     * Gets the request type.
     *
     * @return the request type
     */
    public RequestType getType() {
        return type;
    }

    /**
     * Sets the request type.
     *
     * @param type
     *            the new type
     */
    public void setType( final RequestType type ) {
        this.type = type;
    }

    /**
     * Gets the pre approvaed for mortgage.
     *
     * @return the pre approvaed for mortgage
     */
    public String getPreApprovaedForMortgage() {
        return preApprovaedForMortgage;
    }

    /**
     * Sets the pre approvaed for mortgage.
     *
     * @param preApprovaedForMortgage
     *            the new pre approvaed for mortgage
     */
    public void setPreApprovaedForMortgage( final String preApprovaedForMortgage ) {
        this.preApprovaedForMortgage = preApprovaedForMortgage;
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
     * Gets the property tour info.
     *
     * @return the property tour info
     */
    public String getPropertyTourInfo() {
        return propertyTourInfo;
    }

    /**
     * Sets the property tour info.
     *
     * @param propertyTourInfo
     *            the new property tour info
     */
    public void setPropertyTourInfo( final String propertyTourInfo ) {
        this.propertyTourInfo = propertyTourInfo;
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
     * Gets the task id.
     *
     * @return the task id
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * Sets the task id.
     *
     * @param taskId
     *            the new task id
     */
    public void setTaskId( final String taskId ) {
        this.taskId = taskId;
    }

    /**
     * Gets the deleted.
     *
     * @return the deleted
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * Sets the deleted.
     *
     * @param deleted
     *            the deleted to set
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
     * Gets the dates.
     *
     * @return the dates
     */
    public List< List< TourDetails > > getDates() {
        return dates;
    }

    /**
     * Sets the dates.
     *
     * @param dates
     *            the dates to set
     */
    public void setDates( final List< List< TourDetails > > dates ) {
        this.dates = dates;
    }

    /**
     * Gets the Status.
     *
     * @return the Status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the Status.
     *
     * @param Status
     *            the Status to set
     */
    public void setStatus( String status ) {
        this.status = status;
    }
}
