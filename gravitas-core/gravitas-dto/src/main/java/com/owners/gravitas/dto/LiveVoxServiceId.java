package com.owners.gravitas.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class LiveVoxServiceId {
    
    private List<LiveVoxServiceIdList> service;

    /**
     * @return the service
     */
    public List< LiveVoxServiceIdList > getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService( List< LiveVoxServiceIdList > service ) {
        this.service = service;
    }
    
}
