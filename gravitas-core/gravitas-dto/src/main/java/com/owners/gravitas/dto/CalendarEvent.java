package com.owners.gravitas.dto;

import java.util.List;

public class CalendarEvent extends BaseDTO {

    private static final long serialVersionUID = -4825462852263943283L;
    private Long startTime;
    private Long endTime;
    private String description;
    private String summary;
    private String location;
    private String taskType;
    private String source;
    private String taskStatus;
    private List< String > attendeesName;

    /**
     * @return the startTime
     */
    public Long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     *            the startTime to set
     */
    public void setStartTime( Long startTime ) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Long getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     *            the endTime to set
     */
    public void setEndTime( Long endTime ) {
        this.endTime = endTime;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription( String description ) {
        this.description = description;
    }

    /**
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary
     *            the summary to set
     */
    public void setSummary( String summary ) {
        this.summary = summary;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation( String location ) {
        this.location = location;
    }
    
	/**
	 * @return the taskType
	 */
	public String getTaskType() {
		return taskType;
	}

	/**
	 * @param taskType
	 *            the taskType to set
	 */
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
     * @return the attendeesName
     */
    public List< String > getAttendeesName() {
        return attendeesName;
    }

    /**
     * @param attendeesName
     *            the attendeesName to set
     */
    public void setAttendeesName( List< String > attendeesName ) {
        this.attendeesName = attendeesName;
    }

    /**
     * @return the taskStatus
     */
    public String getTaskStatus() {
        return taskStatus;
    }

    /**
     * @param taskStatus the taskStatus to set
     */
    public void setTaskStatus( String taskStatus ) {
        this.taskStatus = taskStatus;
    }
    
}
