package com.owners.gravitas.dto;

import com.hubzu.notification.dto.model.BaseDto;

/**
 * The Class OpportunityDetailsDTO.
 *
 * @author amits
 */
public class OpportunityDetailsDTO extends BaseDto {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4518190317134607966L;

    /** The buyer name. */
    private String buyerName;

    /** The buyer email. */
    private String buyerEmail;

    /** The buyer phone. */
    private String buyerPhone;

    /** The opportunity id. */
    private String opportunityId;

    /**
     * Instantiates a new opportunity details dto.
     */
    public OpportunityDetailsDTO() {
        super();
    }

    /**
     * Gets the buyer name.
     *
     * @return the buyer name
     */
    public String getBuyerName() {
        return buyerName;
    }

    /**
     * Sets the buyer name.
     *
     * @param buyerName
     *            the new buyer name
     */
    public void setBuyerName( final String buyerName ) {
        this.buyerName = buyerName;
    }

    /**
     * Gets the buyer email.
     *
     * @return the buyer email
     */
    public String getBuyerEmail() {
        return buyerEmail;
    }

    /**
     * Sets the buyer email.
     *
     * @param buyerEmail
     *            the new buyer email
     */
    public void setBuyerEmail( final String buyerEmail ) {
        this.buyerEmail = buyerEmail;
    }

    /**
     * Gets the buyer phone.
     *
     * @return the buyer phone
     */
    public String getBuyerPhone() {
        return buyerPhone;
    }

    /**
     * Sets the buyer phone.
     *
     * @param buyerPhone
     *            the new buyer phone
     */
    public void setBuyerPhone( final String buyerPhone ) {
        this.buyerPhone = buyerPhone;
    }

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
}
