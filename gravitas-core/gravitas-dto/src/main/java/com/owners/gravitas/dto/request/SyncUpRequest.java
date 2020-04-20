package com.owners.gravitas.dto.request;

import java.util.Arrays;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class SyncUpRequest.
 *
 * @author kushwaja
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SyncUpRequest {

	/** The attribute. */
	private String attribute;

	/** The fromDate. */
	private String fromDate;

	/** The toDate. */
	private String toDate;
		
	private Date[] dates;

	public SyncUpRequest() {
	}

	public SyncUpRequest(String attribute) {
		this.attribute = attribute;
	}
	
	public SyncUpRequest(String attribute, String fromDate, String toDate) {
		this.attribute = attribute;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}
	
	
	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	

	public Date[] getDates() {
		return dates;
	}

	public void setDates(Date[] dates) {
		this.dates = dates;
	}

	@Override
	public String toString() {
		return "SyncUpRequest [attribute=" + attribute + ", fromDate=" + fromDate + ", toDate=" + toDate + ", dates="
				+ Arrays.toString(dates) + "]";
	}

}
