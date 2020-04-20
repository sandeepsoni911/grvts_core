package com.owners.gravitas.dto.response;

import java.util.Map;

import com.owners.gravitas.dto.ClientStatisticsDTO;

/**
 * The Class ClientStatisticsResponse.
 *
 * @author amits
 */
public class ClientStatisticsResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1902890900873357951L;

    /** The cta data. */
    private Map< String, Map< String, ClientStatisticsDTO > > ctaData;

    /**
     * @return the ctaData
     */
    public Map< String, Map< String, ClientStatisticsDTO > > getCtaData() {
        return ctaData;
    }

    /**
     * @param ctaData
     *            the ctaData to set
     */
    public void setCtaData( Map< String, Map< String, ClientStatisticsDTO > > ctaData ) {
        this.ctaData = ctaData;
    }

}
