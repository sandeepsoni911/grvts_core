package com.owners.gravitas.enums;

/**
 * The Enum LeadStatus.
 *
 * @author harshads
 */
public enum LeadStatus {

    /** The open. */
    OPEN( "Open" ),

    /** The new. */
    NEW( "New" ),

    /** The lost. */
    LOST( "Lost" ),

    /** The outbound attempt. */
    OUTBOUND_ATTEMPT( "Outbound Attempt" ),

    /** The qualified. */
    QUALIFIED( "Qualified" ),

    /** FORWARDED TO REF EX Status. */
    FORWARDED_TO_REF_EX( "Forwarded to Ref.Ex" ),

    /** The invalid. */
    UNSUPPORTED( "Unsupported" ),
	
	/** The invalid. */
    CLAIMED( "Claimed" );

    /** The status. */
    private String status;

    /**
     * Instantiates a new lead status.
     *
     * @param status
     *            the status
     */
    private LeadStatus( final String status ) {
        this.status = status;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets the lead status.
     *
     * @param status
     *            the status
     * @return the lead status
     */
    public static LeadStatus getType( final String status ) {
        for ( final LeadStatus value : values() ) {
            if (value.getStatus().equalsIgnoreCase( status )) {
                return value;
            }
        }
        return UNSUPPORTED;
    }
}
