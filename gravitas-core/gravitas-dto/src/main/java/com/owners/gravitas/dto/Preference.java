package com.owners.gravitas.dto;

/**
 * DTO to store a user's email preferences
 * 
 * @author ankusht
 *
 */
public class Preference {

    /** The value */
    private boolean value;

    /** The type */
    private String type;

    /**
     * Gets the value
     * 
     * @return
     */
    public boolean getValue() {
        return value;
    }

    /**
     * Sets the value
     * 
     * @param value
     */
    public void setValue( final boolean value ) {
        this.value = value;
    }

    /**
     * Gets the type
     * 
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type
     * 
     * @param type
     */
    public void setType( final String type ) {
        this.type = type;
    }
}
