package com.owners.gravitas.dto.request;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.LiveVoxMatchingCampaignDateRange;
import com.owners.gravitas.dto.LiveVoxServiceId;
import com.owners.gravitas.enums.LiveVoxCampaignStatus;

@JsonIgnoreProperties( ignoreUnknown = true )
public class LiveVoxMatchingCampaignRequest {

    private LiveVoxMatchingCampaignDateRange dateRange;
    private List< LiveVoxCampaignStatus > state;
    private LiveVoxServiceId service;

    /**
     * @return the dateRange
     */
    public LiveVoxMatchingCampaignDateRange getDateRange() {
        return dateRange;
    }

    /**
     * @param dateRange
     *            the dateRange to set
     */
    public void setDateRange( LiveVoxMatchingCampaignDateRange dateRange ) {
        this.dateRange = dateRange;
    }

    /**
     * @return the state
     */
    public List< LiveVoxCampaignStatus > getState() {
        return state;
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState( List< LiveVoxCampaignStatus > state ) {
        this.state = state;
    }

    /**
     * @return the service
     */
    public LiveVoxServiceId getService() {
        return service;
    }

    /**
     * @param service
     *            the service to set
     */
    public void setService( LiveVoxServiceId service ) {
        this.service = service;
    }

}
