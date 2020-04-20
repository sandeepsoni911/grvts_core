package com.owners.gravitas.enums;

/**
 * The Enum SellerStage.
 *
 * @author ankusht
 */
public enum SellerStage {

    /** The new. */
    NEW( "New", "new" ),

    /** The claimed. */
    CLAIMED( "Claimed", "claimed" ),

    /** The in contact. */
    IN_CONTACT( "In Contact", "inContact" ),

    /** The listing appointment. */
    LISTING_APPOINTMENT( "Listing Appointment", "listingAppointment" ),

    /** The active unlisted. */
    ACTIVE_UNLISTED( "Active - Unlisted", "activeUnlisted" ),

    /** The active mls. */
    ACTIVE_MLS( "Active - MLS", "activeMls" ),

    /** The pending sale. */
    PENDING_SALE( "Pending Sale", "pendingSale" ),

    /** The sold. */
    SOLD( "Sold", "sold" ),

    /** The temporary off market. */
    TEMPORARY_OFF_MARKET( "Temporary Off Market", "temporaryOffMarket" ),

    /** The closed lost. */
    CLOSED_LOST( "Closed Lost", "closedLost" ),

    /** The expired. */
    EXPIRED( "Expired", "expired" ),

    /** The cancelled. */
    CANCELLED( "Cancelled", "cancelled" ),

    /** The upgrade. */
    UPGRADE( "Upgrade", "upgrade" ),

    /** The downgrade. */
    DOWNGRADE( "Downgrade", "downgrade" ),

    /** The listing agreement signed. */
    LISTING_AGREEMENT_SIGNED( "Listing Agreement Signed", "listingAgreementSigned" ),

    /** The active rental. */
    ACTIVE_RENTAL( "Active - Rental", "activeRental" ),

    /** The leased. */
    LEASED( "Leased", "leased" ),

    /** The facetoface. */
    FACETOFACE( "Face To Face Meeting", "faceToFaceMeeting" ),
    
    /** The forwarded to ref ex. */
    FORWARDED_TO_REF_EX( "Forwarded to Ref.Ex", "forwardedToRefEx" ),
    
    /** The unknown. */
    UNKNOWN( "Unknown", "unknown" );

    /**
     * Instantiates a new seller stage.
     *
     * @param stage
     *            the stage
     * @param responseKey
     *            the response key
     */
    private SellerStage( final String stage, final String responseKey ) {
        this.stage = stage;
        this.responseKey = responseKey;
    }

    /** The stage. */
    private final String stage;

    /** The response key. */
    private final String responseKey;

    /**
     * Gets the stage.
     *
     * @return the stage
     */
    public String getStage() {
        return stage;
    }

    /**
     * Gets the response key.
     *
     * @return the response key
     */
    public String getResponseKey() {
        return responseKey;
    }

    /**
     * Gets the stage type.
     *
     * @param stage
     *            the stage
     * @return the stage type
     */
    public static SellerStage getStageType( final String stage ) {
        for ( final SellerStage value : values() ) {
            if (value.getStage().equalsIgnoreCase( stage )) {
                return value;
            }
        }
        return UNKNOWN;
    }

    /**
     * Gets the display label.
     *
     * @param responseKey
     *            the response key
     * @return the display label
     */
    public static SellerStage getDisplayLabel( final String responseKey ) {
        for ( final SellerStage value : values() ) {
            if (value.getResponseKey().equalsIgnoreCase( responseKey )) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
