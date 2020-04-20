package com.owners.gravitas.dto.response;

import java.util.LinkedHashSet;

import com.owners.gravitas.dto.CheckinDetailsDTO;

/**
 * The Class CheckinDetailResponse.
 *
 * @author raviz
 */
public class CheckinDetailResponse extends BaseResponse {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6205827189923002385L;

    /** The agent checkin details dto. */
    private LinkedHashSet< CheckinDetailsDTO > checkinDetailsDTO = new LinkedHashSet< CheckinDetailsDTO >();

    /**
     * Instantiates a new checkin detail response.
     */
    public CheckinDetailResponse() {
        super();
    }

    /**
     * Gets the agent checkin details dto.
     *
     * @return the agent checkin details dto
     */
    public LinkedHashSet< CheckinDetailsDTO > getCheckinDetailsDTO() {
        return checkinDetailsDTO;
    }

    /**
     * Sets the agent checkin details dto.
     *
     * @param agentCheckinDetailsDtoList
     *            the new agent checkin details dto
     */
    public void setCheckinDetailsDTO( final LinkedHashSet< CheckinDetailsDTO > agentCheckinDetailsDtoList ) {
        this.checkinDetailsDTO = agentCheckinDetailsDtoList;
    }

}
