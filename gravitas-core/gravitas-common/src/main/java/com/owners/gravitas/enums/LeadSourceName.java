package com.owners.gravitas.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The Enum LeadSource.
 *
 * @author kushwaja
 */
@JsonFormat( shape = JsonFormat.Shape.OBJECT )
public enum LeadSourceName {

    /** TheOwners Agent App. */
    OWNERS_AGENT_APP( "Owners Agent App" ),

    /** The outside referral. */
    OUTSIDE_REFERRAL( "Outside Referral" );

    /** The source. */
    private String source;

    /**
     * Instantiates a new lead source.
     *
     * @param source
     *            the source
     */
    private LeadSourceName( final String source ) {
        this.source = source;
    }

    /**
     * Gets the source.
     *
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * Gets the lead source.
     *
     * @param source
     *            the source
     * @return the lead source
     */
    public static LeadSourceName getRequestType( final String status ) {
        for ( final LeadSourceName value : values() ) {
            if (value.getSource().equalsIgnoreCase( status )) {
                return value;
            }
        }
        return null;
    }
}
