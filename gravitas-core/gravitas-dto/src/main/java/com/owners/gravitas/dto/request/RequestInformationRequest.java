package com.owners.gravitas.dto.request;

import static com.owners.gravitas.enums.LeadRequestType.REQUEST_INFORMATION;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class ScheduleTourRequest.
 *
 * @author vishwanathm
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class RequestInformationRequest extends RequestTypeLeadRequest {

    /**
     * Gets the request type.
     *
     * @return the request type
     */
    public String getRequestType() {
        return REQUEST_INFORMATION.toString();
    }
}
