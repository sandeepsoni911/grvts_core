package com.owners.gravitas.dto.request;

import static com.owners.gravitas.enums.LeadRequestType.SCHEDULE_TOUR;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class ScheduleTourRequest.
 *
 * @author vishwanathm
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class ScheduleTourRequest extends RequestTypeLeadRequest {

    /** The property tour information. */
    @Size( min = 0, max = 2048, message = "error.lead.propertyTourInformation.size" )
    private String propertyTourInformation;

    /**
     * Gets the request type.
     *
     * @return the request type
     */
    public String getRequestType() {
        return SCHEDULE_TOUR.toString();
    }

    /**
     * Gets the property tour information.
     *
     * @return the property tour information
     */
    public String getPropertyTourInformation() {
        return propertyTourInformation;
    }

    /**
     * Sets the property tour information.
     *
     * @param propertyTourInformation
     *            the new property tour information
     */
    public void setPropertyTourInformation( final String propertyTourInformation ) {
        this.propertyTourInformation = propertyTourInformation;
    }
}
