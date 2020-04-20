package com.owners.gravitas.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The Enum GravitasSystemType.
 *
 * @author ankusht
 */
@JsonFormat( shape = JsonFormat.Shape.OBJECT )
public enum GravitasSystemType {

    /** The firebase. */
    FIREBASE( "Firebase Service" ),

    /** The salesforce. */
    SALESFORCE( "Salesforce Service" ),

    /** The gravitas db. */
    GRAVITAS_DB( "Gravitas DB Service" ),

    /** The rabbit mq. */
    RABBIT_MQ( "Rabbit MQ Service" );

    /** The type. */
    private String type;

    /**
     * Instantiates a new gravitas system type.
     *
     * @param systemType
     *            the system type
     */
    private GravitasSystemType( final String systemType ) {
        this.type = systemType;
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
     * Gets the system type.
     *
     * @param status
     *            the status
     * @return the system type
     */
    public static GravitasSystemType getSystemType( final String status ) {
        for ( GravitasSystemType value : values() ) {
            if (value.getType().equalsIgnoreCase( status )) {
                return value;
            }
        }
        return null;
    }
}
