package com.owners.gravitas.enums;

/**
 * The Enum ReasonClosed.
 *
 * @author vishwanathm
 */
public enum ReasonClosed {

    /** The unresponsive. */
    UNRESPONSIVE( "Unresponsive" ),

    /** The out of coverage area. */
    OUT_OF_COVERAGE_AREA( "Out Of Coverage Area" );

    /** The reason. */
    final String reason;

    /**
     * Instantiates a new reason closed.
     *
     * @param reason
     *            the reason
     */
    private ReasonClosed( final String reason ) {
        this.reason = reason;
    }

    /**
     * Gets the reason.
     *
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

}
