package com.owners.gravitas.enums;

/**
 * The Enum BuyerStage.
 *
 * @author ankusht
 */
public enum BuyerStage {

    /** The new. */
    NEW( "New", "new" ),

    /** The claimed. */
    CLAIMED( "Claimed", "claimed" ),

    /** The in contact. */
    IN_CONTACT( "In Contact", "inContact" ),

    /** The showing homes. */
    SHOWING_HOMES( "Showing Homes", "showingHomes" ),

    /** The writing offer. */
    WRITING_OFFER( "Writing Offer", "writingOffer" ),

    /** The securing financing. */
    SECURING_FINANCING( "Securing Financing", "securingFinancing" ),

    /** The pending sale. */
    PENDING_SALE( "Pending Sale", "pendingSale" ),

    /** The sold. */
    SOLD( "Sold", "sold" ),

    /** The closed lost. */
    CLOSED_LOST( "Closed Lost", "closedLost" ),

    /** The in person meeting. */
    IN_PERSON_MEETING( "In-person meeting", "inPersonMeeting" ),

    /** The leased. */
    LEASED( "Leased", "leased" ),

    /** The facetoface. */
    FACETOFACE( "Face To Face Meeting", "faceToFaceMeeting" ),

    /** The forwarded to ref ex. */
    FORWARDED_TO_REF_EX( "Forwarded to Ref.Ex", "forwardedToRefEx" ),
    
    /** The unknown. */
    UNKNOWN( "Unknown", "unknown" );

    /**
     * Instantiates a new buyer stage.
     *
     * @param stage
     *            the stage
     * @param responseKey
     *            the response key
     */
    private BuyerStage( final String stage, final String responseKey ) {
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
    public static BuyerStage getStageType( final String stage ) {
        for ( final BuyerStage value : values() ) {
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
    public static BuyerStage getDisplayLabel( final String responseKey ) {
        for ( final BuyerStage value : values() ) {
            if (value.getResponseKey().equalsIgnoreCase( responseKey )) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
