package com.owners.gravitas.enums;

/**
 * The Enum RefCode.
 *
 * @author raviz
 */
public enum RefCode {

    /** The agent group. */
    AGENT_GROUP( "AGENT_GROUP" );

    /**
     * Instantiates a new ref code.
     *
     * @param code
     *            the code
     */
    private RefCode( final String code ) {
        this.code = code;
    }

    /** The code. */
    private final String code;

    /**
     * Gets the code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

}
