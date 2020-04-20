package com.owners.gravitas.enums;

/**
 * The Enum GoogleFieldProjection.
 *
 * @author harshads
 */
public enum GoogleFieldProjection {

    /** The basic. */
    BASIC( "basic" ),

    /** The custom. */
    CUSTOM( "custom" ),

    /** The full. */
    FULL( "full" );

    /** The type. */
    private String type;

    /**
     * Instantiates a new google field projection.
     *
     * @param type
     *            the type
     */
    private GoogleFieldProjection( String type ) {
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
