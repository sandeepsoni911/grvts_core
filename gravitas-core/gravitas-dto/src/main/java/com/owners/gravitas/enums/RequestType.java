package com.owners.gravitas.enums;

/**
 * The Enum RequestType.
 */
public enum RequestType {

    /** The buyer offer. */
    BUYER_OFFER( "Make An Offer" ),

    /** The appointment. */
    APPOINTMENT( "Schedule a Tour" ),

    /** The inquiry. */
    INQUIRY( "Request Information" );
    
    /** The type. */
    private String type;

    /**
     * Instantiates a new lead request type.
     *
     * @param type
     *            the status
     */
    private RequestType( final String type ) {
        this.type = type;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }
}
