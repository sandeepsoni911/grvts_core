package com.owners.gravitas.dto.response;

/**
 * The Class CheckinDetailResponse.
 *
 * @author kushwaja
 */
public class ScheduleMeetingDetails {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6205827189923002385L;

    private String agentName;

    private String agentEmailId;

    private String agentPhoneNo;

    private String buyerName;

    private String buyerEmailId;

    private String buyerPhoneNo;

    private String meetingDate;

    private String meetingLocation;

    private String meetingstatus;
    
    private String agentState;
    
    private String opportunityId;
    
    private String stage;
    
    private String status;
    
    private String availability;

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName( final String agentName ) {
        this.agentName = agentName;
    }

    public String getAgentEmailId() {
        return agentEmailId;
    }

    public void setAgentEmailId( final String agentEmailId ) {
        this.agentEmailId = agentEmailId;
    }

    public String getAgentPhoneNo() {
        return agentPhoneNo;
    }

    public void setAgentPhoneNo( final String agentPhoneNo ) {
        this.agentPhoneNo = agentPhoneNo;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName( final String buyerName ) {
        this.buyerName = buyerName;
    }

    public String getBuyerEmailId() {
        return buyerEmailId;
    }

    public void setBuyerEmailId( final String buyerEmailId ) {
        this.buyerEmailId = buyerEmailId;
    }

    public String getBuyerPhoneNo() {
        return buyerPhoneNo;
    }

    public void setBuyerPhoneNo( final String buyerPhoneNo ) {
        this.buyerPhoneNo = buyerPhoneNo;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate( final String meetingDate ) {
        this.meetingDate = meetingDate;
    }

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public void setMeetingLocation( final String meetingLocation ) {
        this.meetingLocation = meetingLocation;
    }

    public String getMeetingstatus() {
        return meetingstatus;
    }

    public void setMeetingstatus( final String meetingstatus ) {
        this.meetingstatus = meetingstatus;
    }

    /**
     * @return the agentState
     */
    public String getAgentState() {
        return agentState;
    }

    /**
     * @param agentState the agentState to set
     */
    public void setAgentState( final String agentState ) {
        this.agentState = agentState;
    }

	/**
	 * @return the opportunityId
	 */
	public String getOpportunityId() {
		return opportunityId;
	}

	/**
	 * @param opportunityId the opportunityId to set
	 */
	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}

	/**
	 * @return the stage
	 */
	public String getStage() {
		return stage;
	}

	/**
	 * @param stage the stage to set
	 */
	public void setStage(String stage) {
		this.stage = stage;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the availability
	 */
	public String getAvailability() {
		return availability;
	}

	/**
	 * @param availability the availability to set
	 */
	public void setAvailability(String availability) {
		this.availability = availability;
	}	
	
}
