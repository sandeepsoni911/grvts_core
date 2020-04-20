/**
 * BaseDomain.java
 */
package com.owners.gravitas.domain;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is base class of all domain classes.
 *
 * @author Harshad Shinde
 *
 */

public class BaseDomain implements Serializable {

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

    /**
     * To audit map.
     *
     * @return the map
     */
    public Map< String, Object > toAuditMap() {
        return new ObjectMapper().setSerializationInclusion( Include.NON_EMPTY ).convertValue( this, Map.class );
    }

}
