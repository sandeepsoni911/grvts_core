package com.owners.gravitas.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * To get generic data request
 * @author sandeepsoni
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude( JsonInclude.Include.NON_NULL )
public class OpportunityDataRequest {
	
	/* The data */
	private String key;

	private String value;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
