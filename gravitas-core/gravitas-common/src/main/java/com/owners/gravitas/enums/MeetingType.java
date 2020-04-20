package com.owners.gravitas.enums;

/**
 * The Class MeetingType.
 *
 * @author bhardrah
 */
public enum MeetingType {

    /** The schedule meeting. */
    SCHEDULE_MEETING( "SCHEDULE_MEETING" ),

    /** The schedule tour. */
    SCHEDULE_TOUR( "SCHEDULE_TOUR" );

    /** The task type. */
    private String type;

    /**
     * Instantiates a new task type.
     *
     * @param type
     *            the task type
     */
    private MeetingType( final String meetingType ) {
        this.type = meetingType;
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
