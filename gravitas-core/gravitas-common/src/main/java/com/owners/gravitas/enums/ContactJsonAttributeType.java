package com.owners.gravitas.enums;

/**
 * The Enum ContactJsonAttributeType.
 *
 * @author shivamm
 */
public enum ContactJsonAttributeType {

    /** The saved search values. */
    SAVED_SEARCH_VALUES( "savedSearchValues", "string" ),

    /** The property tour information. */
    PROPERTY_TOUR_INFORMATION( "propertyTourInformation", "string" ),

    /** The additional property data. */
    ADDITIONAL_PROPERTY_DATA( "additionalPropertyData", "string" ),

    /** The website session data. */
    WEBSITE_SESSION_DATA( "websiteSessionData", "string" ),

    /** The order id. */
    ORDER_ID( "orderId", "string" );

    /** The key. */
    private String key;

    /** The data type. */
    private String dataType;

    /**
     * Instantiates a new contact json attribute type.
     *
     * @param key
     *            the key
     * @param dataType
     *            the data type
     */
    private ContactJsonAttributeType( final String key, final String dataType ) {
        this.key = key;
        this.dataType = dataType;
    }

    /**
     * Gets the key.
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets the data type.
     *
     * @return the data type
     */
    public String getDataType() {
        return dataType;
    }

}
