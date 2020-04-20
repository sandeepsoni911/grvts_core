package com.owners.gravitas.enums;

/**
 * The Enum GravitasClientType.
 */
public enum GravitasClientType {

    /** AgentAppIOS. */
    AgentAppIOS( "AgentApp-IOS" ),

    /** AgentAppAndroid. */
    AgentAppAndroid( "AgentApp-Android" );

    /** The type. */
    private String value;

    /**
     * Parameterized Constructor.
     * 
     * @param value
     */
    private GravitasClientType( final String value ) {
        this.value = value;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getValue() {
        return value;
    }
}
