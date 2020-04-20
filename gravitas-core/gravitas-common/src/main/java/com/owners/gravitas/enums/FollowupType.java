package com.owners.gravitas.enums;

/**
 * The Enum FollowupType.
 *
 * @author raviz
 */
public enum FollowupType {

    /** The follow up 1. */
    FOLLOW_UP_1( "SAVE_SEARCH_FOLLOWUP_1" ),

    /** The follow up 2. */
    FOLLOW_UP_2( "SAVE_SEARCH_FOLLOWUP_2" );

    /** The type. */
    private final String type;

    /**
     * Instantiates a new notification event type.
     *
     * @param customerType
     *            the customer type
     */
    private FollowupType( final String customerType ) {
        this.type = customerType;
    }

    /**
     * This method returns the error code.
     *
     * @return error code.
     */
    public String getType() {
        return type;
    }
}
