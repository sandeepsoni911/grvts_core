package com.owners.gravitas.dto;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class CRMOpportunity.
 *
 * @author vishwanathm
 *
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class CRMOpportunity extends CRMBaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6285144134460073399L;

    /** The name. */
    private String name;

    /** The state name. */
    private String stageName;

    /** The type. */
    private String type;

    /** The property city del. */
    private String propertyCity;

    /** The property zip. */
    private String propertyZip;

    /** The account id. */
    private String accountId;

    /** The account id. */
    private String contactId;

    /** The probability. */
    private Integer probability;

    /** The lost reason. */
    private String lostReason;

    /** The buy side commisstion. */
    private String buySideCommisstion;

    /** The sales price. */
    private BigDecimal salesPrice;

    /** The package type. */
    private String packageType;

    /** The expected contract date. */
    private DateTime expectedContractDate;

    /** The pre approved amount. */
    private BigDecimal preApprovedAmount;

    /** The lead type. */
    private String leadType;

    /** The audit record. */
    private String auditRecord;

    /** The is de duped. */
    private boolean isDeDuped;

    /** The label. */
    private String label;

    /** The score. */
    private String score;

    /** The record type. */
    private String recordTypeName;

    /** The order id. */
    private String orderId;

    /** The seller state. */
    private String sellerState;

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
     * Gets the stage name.
     *
     * @return the stage name
     */
    public String getStageName() {
        return stageName;
    }

    /**
     * Sets the stage name.
     *
     * @param stageName
     *            the new stage name
     */
    public void setStageName( final String stageName ) {
        this.stageName = stageName;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type
     *            the new type
     */
    public void setType( final String type ) {
        this.type = type;
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

    /**
     * Gets the account id.
     *
     * @return the account id
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Sets the account id.
     *
     * @param accountId
     *            the new account id
     */
    public void setAccountId( final String accountId ) {
        this.accountId = accountId;
    }

    /**
     * Gets the contact id.
     *
     * @return the contact id
     */
    public String getContactId() {
        return contactId;
    }

    /**
     * Sets the contact id.
     *
     * @param contactId
     *            the new contact id
     */
    public void setContactId( final String contactId ) {
        this.contactId = contactId;
    }

    /**
     * Gets the probability.
     *
     * @return the probability
     */
    public Integer getProbability() {
        return probability;
    }

    /**
     * Sets the probability.
     *
     * @param probability
     *            the new probability
     */
    public void setProbability( final Integer probability ) {
        this.probability = probability;
    }

    /**
     * Gets the lost reason.
     *
     * @return the lost reason
     */
    public String getLostReason() {
        return lostReason;
    }

    /**
     * Sets the lost reason.
     *
     * @param lostReason
     *            the new lost reason
     */
    public void setLostReason( String lostReason ) {
        this.lostReason = lostReason;
    }

    /**
     * Gets the buy side commisstion.
     *
     * @return the buy side commisstion
     */
    public String getBuySideCommisstion() {
        return buySideCommisstion;
    }

    /**
     * Sets the buy side commisstion.
     *
     * @param buySideCommisstion
     *            the new buy side commisstion
     */
    public void setBuySideCommisstion( final String buySideCommisstion ) {
        this.buySideCommisstion = buySideCommisstion;
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
     * Gets the package type.
     *
     * @return the package type
     */
    public String getPackageType() {
        return packageType;
    }

    /**
     * Sets the package type.
     *
     * @param packageType
     *            the new package type
     */
    public void setPackageType( final String packageType ) {
        this.packageType = packageType;
    }

    /**
     * Gets the expected contract date.
     *
     * @return the expected contract date
     */
    public DateTime getExpectedContractDate() {
        return expectedContractDate;
    }

    /**
     * Sets the expected contract date.
     *
     * @param expectedContractDate
     *            the new expected contract date
     */
    public void setExpectedContractDate( final DateTime expectedContractDate ) {
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
     * Gets the audit record.
     *
     * @return the audit record
     */
    public String getAuditRecord() {
        return auditRecord;
    }

    /**
     * Sets the audit record.
     *
     * @param auditRecord
     *            the new audit record
     */
    public void setAuditRecord( final String auditRecord ) {
        this.auditRecord = auditRecord;
    }

    /**
     * Checks if is de duped.
     *
     * @return true, if is de duped
     */
    public boolean isDeDuped() {
        return isDeDuped;
    }

    /**
     * Sets the de duped.
     *
     * @param isDeDuped
     *            the new de duped
     */
    public void setDeDuped( final boolean isDeDuped ) {
        this.isDeDuped = isDeDuped;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName( final String name ) {
        this.name = name;
    }

    /**
     * Gets the opportunity label.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the opportunity label.
     *
     * @param label
     *            the label to set
     */
    public void setLabel( final String opportunityLabel ) {
        this.label = opportunityLabel;
    }

    /**
     * Gets the opportunity score.
     *
     * @return the score
     */
    public String getScore() {
        return score;
    }

    /**
     * Sets the opportunity score.
     *
     * @param score
     *            the score to set
     */
    public void setScore( final String opportunityScore ) {
        this.score = opportunityScore;
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
     * Gets the seller state.
     *
     * @return the sellerState
     */
    public String getSellerState() {
        return sellerState;
    }

    /**
     * Sets the seller state.
     *
     * @param sellerState
     *            the sellerState to set
     */
    public void setSellerState(final String sellerState ) {
        this.sellerState = sellerState;
    }
}
