package com.owners.gravitas.util;

import static com.owners.gravitas.constants.Constants.GRAVITAS_CONFIG_DIR;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service to fetch timezone for input hours difference respectively
 * 
 * @author parakhsh
 *
 */
@Component
public class TimeZoneUtil {

	/** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( TimeZoneUtil.class );

    private final String APPLICATION_CONFIG_DIR = GRAVITAS_CONFIG_DIR
             + File.separatorChar ;
    private static final String ADDRESS_SERVICE_TIMEZONE = "gravitas_timezone.json";
    private final String filePath = APPLICATION_CONFIG_DIR + ADDRESS_SERVICE_TIMEZONE;
   

    Map<String, String> hourToTimezoneMapping = new HashMap<>();
    Map<String, String> timezoneToHourMapping = new HashMap<>();
    Map<String, String> stateToTimezoneMapping = new HashMap<>();
    Map<String, String> stateToStateNameMapping = new HashMap<>();
    
    @Value( "${agent.calendar.default.timezone:EST}" )
    private String defaultTimeZoneForAgents;
    
    @PostConstruct
    public void init() {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final File file = new File(filePath);
            final List<TimeZoneDetails> listOfTimeZoneDetails = objectMapper.readValue(file,
                    new TypeReference<List<TimeZoneDetails>>() {
                    });
            for (final TimeZoneDetails timezoneDetails : listOfTimeZoneDetails) {
                hourToTimezoneMapping.put(timezoneDetails.getKey().toUpperCase(),
                        timezoneDetails.getTimezone().toUpperCase());
                timezoneToHourMapping.put(timezoneDetails.getTimezone().toUpperCase(),
                        timezoneDetails.getKey().toUpperCase());
                stateToTimezoneMapping.put(timezoneDetails.getState().toUpperCase(),
                		timezoneDetails.getTimezone().toUpperCase());
                stateToStateNameMapping.put( timezoneDetails.getState().toUpperCase(),
                        timezoneDetails.getStateName().toUpperCase() );
            }
        } catch (final Exception e) {
            LOGGER.error("Error while getting state mapping", e);
        }
        LOGGER.info("\nstateNameToAbbrevationMapping Count : {} \nstateNameToAbbrevationMapping : {} ",
                hourToTimezoneMapping.size(), JsonUtil.toJson(hourToTimezoneMapping));
        LOGGER.info("\n stateAbbrevationToNameMapping Count : {} \nstateAbbrevationToNameMapping : {} ",
                timezoneToHourMapping.size(), JsonUtil.toJson(timezoneToHourMapping));
        LOGGER.info("\n stateAbbrevationToTimeZone Count : {} \nstateAbbrevationToNameMapping : {} ",
        		stateToTimezoneMapping.size(), JsonUtil.toJson(stateToTimezoneMapping));
        LOGGER.info("\n stateToStateNameMapping Count : {} \nstateToStateNameMapping : {} ",
                stateToStateNameMapping.size(), JsonUtil.toJson(stateToStateNameMapping));
    }

    public String getTimezone(final String hoursDifference) {
        String timeZone = null;
        if (hoursDifference != null && hoursDifference.trim().length() > 0) {
            timeZone = hourToTimezoneMapping.get(hoursDifference.toUpperCase());
        }
        LOGGER.debug("Timezone for hoursDifference : {} is {}", hoursDifference, timeZone);
        return timeZone;
    }

    public String getHoursDifference(final String timeZone) {
        String hoursDifference = null;
        if (timeZone != null && timeZone.trim().length() > 0) {
            hoursDifference = timezoneToHourMapping.get(timeZone.toUpperCase());
        }
        LOGGER.debug("HoursDifference for Timezone : {} is {}", timeZone, hoursDifference);
        return hoursDifference;
    }
    
    public String getTimeZoneByState(final String state) {
    	String timeZone = null;
        if (state != null && state.trim().length() > 0) {
            timeZone = stateToTimezoneMapping.get(state.toUpperCase());
        }
        if(StringUtils.isBlank( timeZone )) {
            timeZone = defaultTimeZoneForAgents;//instead of null setting default timeZone(EST)
        }
        LOGGER.debug("Timezone for state : {} is {}", state, timeZone);
        return timeZone;
    }
    
    /***
     * Method to return a map containing key value pair of state code with state
     * name
     * 
     * @return
     */
    public Map< String, String > getStateToStateNameMapping() {
        return stateToStateNameMapping;
    }
}
