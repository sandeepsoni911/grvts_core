package com.owners.gravitas.enums;

/**
 * The Enum GoogleFieldTypes.
 * 
 * @author pabhishek
 */
public enum GoogleFieldTypes {

    /** The work. */
    WORK( "work" ),

    /** The other fields. */
    OTHER_FIELDS( "otherFields" ),

    /** The biodata. */
    BIODATA( "bioData" ),

    /** The home. */
    HOME( "home" ),

    /** The street address. */
    STREET_ADDRESS( "streetAddress" ),

    /** The country. */
    COUNTRY( "country" ),

    /** The locality. */
    LOCALITY( "locality" ),

    /** The region. */
    REGION( "region" ),

    /** The postal code. */
    POSTAL_CODE( "postalCode" ),

    /** The other. */
    OTHER( "other" ),

    /** The address. */
    ADDRESS( "address" ),

    /** The type. */
    TYPE( "type" ),

    /** The value. */
    VALUE( "value" );

    /** The type. */
    private String type;

    /**
     * Instantiates a new google field types.
     *
     * @param type
     *            the type
     */
    private GoogleFieldTypes( String type ) {
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
     * Gets the google field types.
     *
     * @param fieldValue
     *            the field value
     * @return the google field types
     */
    public static GoogleFieldTypes getGoogleFieldTypes( final String fieldValue ) {
        for ( GoogleFieldTypes value : values() ) {
            if (value.getType().equalsIgnoreCase( fieldValue )) {
                return value;
            }
        }
        return null;
    }

}
