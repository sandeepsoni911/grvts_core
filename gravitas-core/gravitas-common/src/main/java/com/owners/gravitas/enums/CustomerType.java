package com.owners.gravitas.enums;

/**
 * The Enum CustomerType.
 *
 * @author vishwanathm
 */
public enum CustomerType {

    NEW_CUSTOMER( "New Customer" );

    /** customer Type. */
    private String type;

    /**
     * Instantiates a new customer type.
     *
     * @param customerType
     *            the customer type
     */
    private CustomerType( final String customerType ) {
        this.type = customerType;
    }

    /**
     * This method returns the error code.
     *
     * @return error code.
     */
    public String getType() {
        return type;
    }
}
