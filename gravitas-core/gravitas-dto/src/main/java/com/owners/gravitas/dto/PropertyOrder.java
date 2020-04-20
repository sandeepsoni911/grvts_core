package com.owners.gravitas.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * The Class PropertyOrder.
 *
 * @author vishwanathm
 */
public class PropertyOrder extends BaseDTO {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8851461985134103218L;

    /** The order type. */
    @NotBlank(message = "error.ordertype.required")
    @Size( max = 100, message = "error.ordertype.size" )
    private String orderType;

    /** The property. */
    @Valid
    @NotNull(message = "error.property.required")
    private Property property;

    /**
     * Gets the order type.
     *
     * @return the order type
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * Sets the order type.
     *
     * @param orderType
     *            the new order type
     */
    public void setOrderType( final String orderType ) {
        this.orderType = orderType;
    }

    /**
     * Gets the property.
     *
     * @return the property
     */
    public Property getProperty() {
        return property;
    }

    /**
     * Sets the property.
     *
     * @param property
     *            the new property
     */
    public void setProperty( final Property property ) {
        this.property = property;
    }
}
