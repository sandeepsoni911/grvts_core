package com.owners.gravitas.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The Enum RecordTypes.
 *
 * @author harshads
 */
@JsonFormat( shape = JsonFormat.Shape.OBJECT )
public enum RecordType {

    /** The buyer. */
    BUYER( "Buyer" ),

    /** For the affiliate email hubzu buyer. */
    HUBZU( "Hubzu" ),

    /** The seller. */
    SELLER( "Seller" ),

    /** The owners. */
    OWNERS( "Owners" ),

    /** The mls. */
    MLS( "MLS" ),

    /** The both. */
    BOTH( "Owners - Buyer and Seller" ),

    /** The unknown. */
    UNKNOWN( "Owners - Prospect" ),

    /** The OCL. */
    OWNERS_COM_LOANS( "Owners.com Loans" ),

    /** The Pts. */
    PTS( "Pts" ),

    /** The owners com. */
    OWNERS_COM( "Owners.com" );

    /** The record type. */
    private String type;

    /**
     * Instantiates a new record types.
     *
     * @param recordType
     *            the record type
     */
    private RecordType( final String recordType ) {
        this.type = recordType;
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
     * Gets the request type.
     *
     * @param status
     *            the status
     * @return the request type
     */
    public static RecordType getRecordType( final String status ) {
        for ( RecordType value : values() ) {
            if (value.getType().equalsIgnoreCase( status )) {
                return value;
            }
        }
        return null;
    }

    /**
     * Gets the record type type.
     *
     * @param recordType
     *            the record type
     * @return the record type type
     */
    public static String getRecordTypeType( final String recordType ) {
        for ( RecordType value : values() ) {
            if (value.name().equalsIgnoreCase( recordType )) {
                return value.getType();
            }
        }
        return null;
    }
}
