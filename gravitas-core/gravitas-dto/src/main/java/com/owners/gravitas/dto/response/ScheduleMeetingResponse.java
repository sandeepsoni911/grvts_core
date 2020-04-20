package com.owners.gravitas.dto.response;

import java.util.List;

/**
 * The Class CheckinDetailResponse.
 *
 * @author kushwaja
 */
public class ScheduleMeetingResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6205827189923002385L;

    private List< ScheduleMeetingDetails > scheduleMeetingDetails;

    public List< ScheduleMeetingDetails > getScheduleMeetingDetails() {
        return scheduleMeetingDetails;
    }

    public void setScheduleMeetingDetails( List< ScheduleMeetingDetails > scheduleMeetingDetails ) {
        this.scheduleMeetingDetails = scheduleMeetingDetails;
    }

}
