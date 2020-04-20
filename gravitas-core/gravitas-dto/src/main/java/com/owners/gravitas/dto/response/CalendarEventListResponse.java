package com.owners.gravitas.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.owners.gravitas.dto.CalendarEvent;

public class CalendarEventListResponse extends BaseResponse {

    private static final long serialVersionUID = -2523250322974092914L;
    private List< CalendarEvent > calendarEvents;
    private String timezone;
    
    /**
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * @param timezone the timezone to set
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	/**
     * 
     */
    public CalendarEventListResponse() {
        
    }
    
    /***
     * 
     * @param message
     */
    public CalendarEventListResponse( final String message ) {
        super( Status.SUCCESS, message );
        this.calendarEvents = new ArrayList<>();
    }

    /**
     * @return the calendarEvents
     */
    public List< CalendarEvent > getCalendarEvents() {
        return calendarEvents;
    }

    /**
     * @param calendarEvents
     *            the calendarEvents to set
     */
    public void setCalendarEvents( List< CalendarEvent > calendarEvents ) {
        this.calendarEvents = calendarEvents;
    }
}
