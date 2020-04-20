package com.owners.gravitas.enums;

/**
 * The Enum BuyerFarmType.
 *
 * @author harshads
 */
public enum BuyerFarmType {

    /** The long term buyer. */
    LONG_TERM_BUYER( "Long Term" ),

    /** The active buyer. */
    ACTIVE_BUYER( "Active" ),

    /** The lost buyer. */
    LOST_BUYER( "Lost" );

    /** The type. */
    private String type;

    /**
     * Instantiates a new buyer farm type.
     *
     * @param type
     *            the type
     */
    private BuyerFarmType( final String type ) {
        this.setType( type );
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
}
