/**
 * BaseDTO.java
 */
package com.owners.gravitas.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This is base class of all dto classes.
 *
 * @author Harshad Shinde
 *
 */

public class BaseDTO implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = 914257690918957054L;

    /**
     * Overridden method to convert object to string.
     *
     * @return given object as string.
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString( this );
    }

}
