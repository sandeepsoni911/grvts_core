package com.owners.gravitas.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeZoneDetails {

    String timezone;
    String key;
    String state;
    String stateName;

    /**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(final String state) {
		this.state = state;
	}

	public String getTimezone() {
        return timezone;
    }

    public void setTimezone(final String timezone) {
        this.timezone = timezone;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TimeZoneDetails [timezone=" + timezone + ", key=" + key + ", state=" + state + ", stateName=" + stateName + "]";
	}

    /**
     * @return the stateName
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * @param stateName
     *            the stateName to set
     */
    public void setStateName( final String stateName ) {
        this.stateName = stateName;
    }

   

}
