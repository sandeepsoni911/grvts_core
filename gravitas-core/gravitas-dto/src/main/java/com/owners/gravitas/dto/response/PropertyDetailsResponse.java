/**
 *
 */
package com.owners.gravitas.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.PropertyData;

/**
 * @author harshads
 *
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class PropertyDetailsResponse {

    /** The data. */
    private PropertyData data;

    /**
     * @return the data
     */
    public PropertyData getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData( final PropertyData data ) {
        this.data = data;
    }
}
