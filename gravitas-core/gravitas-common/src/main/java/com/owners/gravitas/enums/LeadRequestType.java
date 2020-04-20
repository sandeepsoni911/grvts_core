package com.owners.gravitas.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The Enum LeadRequestType.
 *
 * @author vishwanathm
 */
@JsonFormat( shape = JsonFormat.Shape.OBJECT )
public enum LeadRequestType {

    /** The make offer. */
    MAKE_OFFER( "Make An Offer" ),

    /** The shedule tour. */
    SCHEDULE_TOUR( "Schedule a Tour" ),

    /** The request information. */
    REQUEST_INFORMATION( "Request Information" ),

    /** The other. */
    OTHER( "Other" );

    /** The type. */
    private String type;

    /**
     * Instantiates a new lead request type.
     *
     * @param type
     *            the status
     */
    private LeadRequestType( final String type ) {
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

    /**
     * Gets the lead status.
     *
     * @param status
     *            the status
     * @return the lead status
     */
    public static LeadRequestType getRequestType( final String status ) {
        for ( LeadRequestType value : values() ) {
            if (value.getType().equalsIgnoreCase( status )) {
                return value;
            }
        }
        return null;
    }
}
