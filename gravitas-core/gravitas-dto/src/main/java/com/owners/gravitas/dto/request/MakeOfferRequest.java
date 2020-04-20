package com.owners.gravitas.dto.request;

import static com.owners.gravitas.constants.Constants.REGEX_CURRENCY;
import static com.owners.gravitas.enums.LeadRequestType.MAKE_OFFER;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class MakeOfferRequest.
 *
 * @author vishwanathm
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class MakeOfferRequest extends RequestTypeLeadRequest {

    /** The offer amount. */
    @Size( min = 0, max = 19, message = "error.lead.offerAmount.size" )
    @Pattern( regexp = REGEX_CURRENCY, message = "error.lead.offerAmount.format" )
    private String offerAmount;

    /** The earnest money deposit. */
    @Size( min = 0, max = 19, message = "error.lead.earnestMoneyDeposit.size" )
    @Pattern( regexp = REGEX_CURRENCY, message = "error.lead.earnestMoneyDeposit.format" )
    private String earnestMoneyDeposit;

    /** The purchase method. */
    @Size( min = 0, max = 8, message = "error.lead.purchaseMethod.size" )
    private String purchaseMethod;

    /** The down payment. */
    @Size( min = 0, max = 19, message = "error.lead.downPayment.size" )
    @Pattern( regexp = REGEX_CURRENCY, message = "error.lead.downPayment.format" )
    private String downPayment;

    /** The pre approved for mortgage. */
    @Size( min = 0, max = 50, message = "error.lead.preApprovedForMortgage.size" )
    private String preApprovedForMortgage;

    /**
     * Gets the request type.
     *
     * @return the request type
     */
    public String getRequestType() {
        return MAKE_OFFER.toString();
    }

    /**
     * Gets the offer amount.
     *
     * @return the offer amount
     */
    public String getOfferAmount() {
        return offerAmount;
    }

    /**
     * Sets the offer amount.
     *
     * @param offerAmount
     *            the new offer amount
     */
    public void setOfferAmount( final String offerAmount ) {
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
}
