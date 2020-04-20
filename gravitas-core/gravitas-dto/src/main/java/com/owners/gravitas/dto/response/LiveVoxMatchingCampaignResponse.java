package com.owners.gravitas.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.LiveVoxCampaign;

@JsonIgnoreProperties( ignoreUnknown = true )
public class LiveVoxMatchingCampaignResponse extends BaseResponse {

    private static final long serialVersionUID = -2636416909215791521L;
    private List< LiveVoxCampaign > campaign;

    /**
     * @return the campaign
     */
    public List< LiveVoxCampaign > getCampaign() {
        return campaign;
    }

    /**
     * @param campaign
     *            the campaign to set
     */
    public void setCampaign( List< LiveVoxCampaign > campaign ) {
        this.campaign = campaign;
    }

}
