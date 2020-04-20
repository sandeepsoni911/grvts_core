package com.owners.gravitas.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Wrapper class for {@link ScheduleTourLeadRequest}
 * @author imranmoh
 *
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class ScheduleTourMeetingRequest {
    
    private List<ScheduleTourLeadRequest> meetingRequest;

    /**
     * @return the meetingRequest
     */
    public List< ScheduleTourLeadRequest > getMeetingRequest() {
        return meetingRequest;
    }

    /**
     * @param meetingRequest the meetingRequest to set
     */
    public void setMeetingRequest( final List< ScheduleTourLeadRequest > meetingRequest ) {
        this.meetingRequest = meetingRequest;
    }
}
