package com.owners.gravitas.enums;

/**
 * The Enum TaskType.
 *
 * @author amits
 */
public enum TaskType {

    /** The claim opportunity. */
    CLAIM_OPPORTUNITY( "CLAIM_OPPORTUNITY" ),

    /** The ask premium title. */
    ASK_PREMIUM_TITLE( "ASK_PREMIUM_TITLE" ),

    /** The contact opportunity. */
    CONTACT_OPPORTUNITY( "CONTACT_OPPORTUNITY" ),

    /** The schedule meeting. */
    SCHEDULE_MEETING( "SCHEDULE_MEETING" ),

    /** The make offer. */
    MAKE_OFFER( "MAKE_OFFER" ),

    /** The schedule tour. */
    SCHEDULE_TOUR( "SCHEDULE_TOUR" ),

    /** The request information. */
    REQUEST_INFORMATION( "REQUEST_INFORMATION" ),

    /** The other. */
    OTHER( "OTHER" ),

    /** The user defined. */
    USER_DEFINED( "USER_DEFINED" );

    /** The task type. */
    private String type;

    /**
     * Instantiates a new task type.
     *
     * @param type
     *            the task type
     */
    private TaskType( final String taskType ) {
        this.type = taskType;
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
