/*
 *
 */
package com.owners.gravitas.enums;

/**
 * The Enum UserStatusType.
 *
 * @author pabhishek
 */
public enum UserStatusType {

    /** The active. */
    ACTIVE( "active" ),

    /** The inactive. */
    INACTIVE( "inactive" ),

    /** The hold. */
    HOLD( "hold" ),

    /** The onboarding. */
    ONBOARDING( "onboarding" ),

    /** The unsupported. */
    UNKNOWN( "unknown" );

    /** The status. */
    private final String status;

    /**
     * Instantiates a new user status type.
     *
     * @param status
     *            the status
     */
    private UserStatusType( final String status ) {
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
     * Gets the status type.
     *
     * @param status
     *            the status
     * @return the status type
     */
    public static UserStatusType getStatusType( final String status ) {
        for ( final UserStatusType value : values() ) {
            if (value.getStatus().equalsIgnoreCase( status )) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
