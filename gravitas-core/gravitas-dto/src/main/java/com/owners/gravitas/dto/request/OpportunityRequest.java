package com.owners.gravitas.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.PropertyOrder;
import com.owners.gravitas.dto.Seller;

/**
 * The Class OpportunityRequest.
 *
 * @author vishwanathm
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class OpportunityRequest {

    /** The seller. */
    @Valid
    @NotNull( message = "error.seller.required" )
    private Seller seller;

    /** The property order. */
    @Valid
    @NotNull( message = "error.propertyorder.required" )
    private PropertyOrder propertyOrder;

    /**
     * Gets the seller.
     *
     * @return the seller
     */
    public Seller getSeller() {
        return seller;
    }

    /**
     * Sets the seller.
     *
     * @param seller
     *            the new seller
     */
    public void setSeller( final Seller seller ) {
        this.seller = seller;
    }

    /**
     * Gets the property order.
     *
     * @return the property order
     */
    public PropertyOrder getPropertyOrder() {
        return propertyOrder;
    }

    /**
     * Sets the property order.
     *
     * @param propertyOrder
     *            the new property order
     */
    public void setPropertyOrder( final PropertyOrder propertyOrder ) {
        this.propertyOrder = propertyOrder;
    }
}
