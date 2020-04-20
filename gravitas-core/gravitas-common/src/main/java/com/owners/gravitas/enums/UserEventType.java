/**
 * 
 */
package com.owners.gravitas.enums;

/**
 * @author bhardrah
 *
 */
public enum UserEventType {

    /** The Favorited. */
    FAVORITED( "PROPERTY FAVORITED" ),
    
    /** The REPEAT PDP viewed. */
    REPEAT_PDP_VIEWED( "REPEAT PDP VIEWED" ),

    /** The PDP viewed. */
    PDP_VIEWED( "PDP VIEWED" ),

    /** The PDP shared. */
    PDP_SHARED( "PDP SHARED" ),
    
    /** The Tour request abandoned. */
    TOUR_REQUEST_ABANDONED( "TOUR REQUEST ABANDONED" ),

    /** Tour Saved. */
    TOUR_SAVED( "TOUR SAVED" ),

    /** The Undecided PDP viewed. */
    PDP_VIEWED_INDECISIVE( "PDP VIEWED" ),
    
    /** ENGAGED TOS. */
    ENGAGED_TOS( "ENGAGED TO SITE" ),
    
    /** UNDECIDED PDP VIEWED. */
    UNDECIDED_PDP_VIEWED( "UNDECIDED PDP VIEWED" ),
    
    /** UNENGAGED 30. */
    UNENGAGED_30( "UNENGAGED TO SITE" ),
    
    /** The Saved search follow up 1. */
    SAVE_SEARCH_FOLLOWUP_1( "FOLLOWUP_1 EMAIL SENT" ),

    /** The Saved search follow up 2. */
    SAVE_SEARCH_FOLLOWUP_2( "FOLLOWUP_2 EMAIL SENT" );
    
    /** The type. */
    private String type;

    /**
     * Instantiates a new event type.
     *
     * @param type
     *            the type
     */
    private UserEventType( String type ) {
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
