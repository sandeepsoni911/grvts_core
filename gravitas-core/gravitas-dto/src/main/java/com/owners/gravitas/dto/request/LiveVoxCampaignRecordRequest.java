package com.owners.gravitas.dto.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owners.gravitas.dto.LiveVoxRecord;

@JsonIgnoreProperties( ignoreUnknown = true )
public class LiveVoxCampaignRecordRequest implements Serializable {

    private static final long serialVersionUID = -2952568981637329821L;
    
    private List< LiveVoxRecord > records;

    /**
     * @return the records
     */
    public List< LiveVoxRecord > getRecords() {
        return records;
    }

    /**
     * @param records
     *            the records to set
     */
    public void setRecords( List< LiveVoxRecord > records ) {
        this.records = records;
    }

}
