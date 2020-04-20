package com.owners.gravitas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class LiveVoxMatchingCampaignDateRange {
    
    private long from;
    private long to;
    
    /**
     * @return the from
     */
    public long getFrom() {
        return from;
    }
    /**
     * @param from the from to set
     */
    public void setFrom( long from ) {
        this.from = from;
    }
    /**
     * @return the to
     */
    public long getTo() {
        return to;
    }
    /**
     * @param to the to to set
     */
    public void setTo( long to ) {
        this.to = to;
    }

}
