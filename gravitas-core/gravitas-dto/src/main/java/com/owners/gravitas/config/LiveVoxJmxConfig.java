package com.owners.gravitas.config;

import static com.owners.gravitas.constants.Constants.COMMA;
import static com.owners.gravitas.constants.Constants.NA_WITHOUT_DIVIDER;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

@ManagedResource(objectName = "com.owners.gravitas:name=LiveVoxJmxConfig")
@Component
public class LiveVoxJmxConfig {
    
    private static final Logger LOGGER = LoggerFactory.getLogger( LiveVoxJmxConfig.class );

	/** The buyer farming enabled. */
	@Value("${livevox.service.enabled:false}")
	private boolean liveVoxServiceEnabled;

	/** The office hour start time. */
	@Value("${livevox.officeAfterHour.start}")
	private int officeAfterHourStart;

	/** The office mins start. */
	@Value("${livevox.officeAfterMins.start}")
	private int officeAfterMinsStart;

	/** The office hour end time. */
	@Value("${livevox.officeAfterHour.end}")
	private int officeAfterHourEnd;

	/** The office mins end. */
	@Value("${livevox.officeAfterMins.end}")
	private int officeAfterMinsEnd;

	/** The time zone id. */
	@Value("${livevox.officeAfterHour.timeZone}")
	private String timeZoneId;

	/** The livevox enabled states. */
	@Value("${livevox.enabled.states}")
	private String liveVoxEnabledStates;

	/** The livevox cache enabled. */
	@Value("${livevox.cache.enabled:false}")
	private boolean liveVoxCacheEnabled;
	
	/** The livevox enabled state values **/
	private Set<String> liveVoxStateValues;
	
	private boolean liveVoxEnabledForNullState = false;

	/**
	 * Inits the data builder.
	 */
	@PostConstruct
	private void initDataBuilder() {
		populateLiveVoxEnabledStates();
	}

	/** The property writer. */
	@Autowired
	private PropertyWriter propertyWriter;

	/**
	 * Checks if is live vox service enabled.
	 *
	 * @return true, if is buyer farming enabled
	 */
	@ManagedAttribute
	public boolean isLiveVoxServiceEnabled() {
		return liveVoxServiceEnabled;
	}

	/**
	 * Sets the livevox service enabled.
	 *
	 * @param liveVoxServiceEnabled
	 *            the new livevox service enabled
	 */
	@ManagedAttribute
	public void setLiveVoxServiceEnabled(final boolean liveVoxServiceEnabled) {
		this.liveVoxServiceEnabled = liveVoxServiceEnabled;
		propertyWriter.saveJmxProperty("livevox.service.enabled", liveVoxServiceEnabled);
	}

	/**
	 * Gets the office after hour start.
	 *
	 * @return the office after hour start
	 */
	@ManagedAttribute
	public int getOfficeAfterHourStart() {
		return officeAfterHourStart;
	}

	/**
	 * Sets the office after hour start.
	 *
	 * @param officeAfterHourStart
	 *            the new office after hour start
	 */
	@ManagedAttribute
	public void setOfficeAfterHourStart(final int officeAfterHourStart) {
		this.officeAfterHourStart = officeAfterHourStart;
		propertyWriter.saveJmxProperty("livevox.officeAfterHour.start", officeAfterHourStart);
	}

	/**
	 * Gets the office after mins start.
	 *
	 * @return the office after mins start
	 */
	@ManagedAttribute
	public int getOfficeAfterMinsStart() {
		return officeAfterMinsStart;
	}

	/**
	 * Sets the office after mins start.
	 *
	 * @param officeAfterMinsStart
	 *            the new office after mins start
	 */
	@ManagedAttribute
	public void setOfficeAfterMinsStart(final int officeAfterMinsStart) {
		this.officeAfterMinsStart = officeAfterMinsStart;
		propertyWriter.saveJmxProperty("livevox.officeAfterMins.start", officeAfterMinsStart);
	}

	/**
	 * Gets the office after hour end.
	 *
	 * @return the office after hour end
	 */
	@ManagedAttribute
	public int getOfficeAfterHourEnd() {
		return officeAfterHourEnd;
	}

	/**
	 * Sets the office after hour end.
	 *
	 * @param officeAfterHourEnd
	 *            the new office after hour end
	 */
	@ManagedAttribute
	public void setOfficeAfterHourEnd(final int officeAfterHourEnd) {
		this.officeAfterHourEnd = officeAfterHourEnd;
		propertyWriter.saveJmxProperty("livevox.officeAfterHour.end", officeAfterHourEnd);

	}

	/**
	 * Gets the office after mins end.
	 *
	 * @return the office after mins end
	 */
	@ManagedAttribute
	public int getOfficeAfterMinsEnd() {
		return officeAfterMinsEnd;
	}

	/**
	 * Sets the office after mins end.
	 *
	 * @param officeAfterMinsEnd
	 *            the new office after mins end
	 */
	@ManagedAttribute
	public void setOfficeAfterMinsEnd(final int officeAfterMinsEnd) {
		this.officeAfterMinsEnd = officeAfterMinsEnd;
		propertyWriter.saveJmxProperty("livevox.officeAfterMins.end", officeAfterMinsEnd);
	}

	/**
	 * Gets the time zone id.
	 *
	 * @return the time zone id
	 */
	@ManagedAttribute
	public String getTimeZoneId() {
		return timeZoneId;
	}

	/**
	 * Sets the time zone id.
	 *
	 * @param timeZoneId
	 *            the new time zone id
	 */
	@ManagedAttribute
	public void setTimeZoneId(final String timeZoneId) {
		this.timeZoneId = timeZoneId;
		propertyWriter.saveJmxProperty("livevox.officeAfterHour.timeZone", timeZoneId);
	}

	/**
	 * Gets the liveVoxEnabledStates.
	 *
	 * @return the liveVoxEnabledStates
	 */
	@ManagedAttribute
	public String getLiveVoxEnabledStates() {
		return liveVoxEnabledStates;
	}

	/**
	 * Sets the liveVoxEnabledStates.
	 *
	 * @param liveVoxEnabledStates
	 *            the new liveVoxEnabledStates
	 */
	@ManagedAttribute
	public void setLiveVoxEnabledStates(String livevoxEnabledStates) {
		this.liveVoxEnabledStates = livevoxEnabledStates;
		propertyWriter.saveJmxProperty("livevox.enabled.states", livevoxEnabledStates);
		populateLiveVoxEnabledStates();
	}

	/**
	 * Checks if liveVox cache is enabled.
	 *
	 * @return true, if is liveVox cache is enabled
	 */
	@ManagedAttribute
	public boolean isLiveVoxCacheEnabled() {
		return liveVoxCacheEnabled;
	}


	/**
	 * Sets liveVox cache flag.
	 *
	 * @param liveVoxCacheEnabled
	 *            the liveVox cache flag
	 */
	@ManagedAttribute
	public void setLiveVoxCacheEnabled(boolean liveVoxCacheEnabled) {
		this.liveVoxCacheEnabled = liveVoxCacheEnabled;
	}
	
	/**
	 * Gets the liveVoxStateValues.
	 *
	 * @return the liveVoxStateValues
	 */
	public Set<String> getLiveVoxStateValues() {
		return liveVoxStateValues;
	}

	/**
	 * Sets the liveVoxEnabledStatesValues.
	 *
	 * @param liveVoxEnabledStatesValues
	 *            the new liveVoxEnabledStatesValues
	 */
	public void setLiveVoxStateValues(Set<String> liveVoxStateValues) {
		this.liveVoxStateValues = liveVoxStateValues;
	}
	
	public boolean isLiveVoxEnabledForNullState() {
        return liveVoxEnabledForNullState;
    }

    public void setLiveVoxEnabledForNullState(boolean liveVoxEnabledForNullState) {
        this.liveVoxEnabledForNullState = liveVoxEnabledForNullState;
    }

    /**
	 * Populates liveVoxStateValues.
	 */
    private void populateLiveVoxEnabledStates() {
        LOGGER.info("Updating Livevox values for : {}", liveVoxEnabledStates);
        liveVoxStateValues = new HashSet<>();
        liveVoxEnabledForNullState = false;
        if (StringUtils.isNotBlank(liveVoxEnabledStates)) {
            String[] livevoxEnabledStates = liveVoxEnabledStates.split(COMMA);
            for (String state : livevoxEnabledStates) {
                if (state.trim().equalsIgnoreCase(NA_WITHOUT_DIVIDER)) {
                    liveVoxEnabledForNullState = true;
                } else {
                    liveVoxStateValues.add(state.trim());
                }
            }
        } else {
            liveVoxEnabledForNullState = true;
        }
        LOGGER.info("Current values for liveVoxStateValues : {}, liveVoxEnabledForNullState : {}", liveVoxStateValues,
                liveVoxEnabledForNullState);
    }
}
