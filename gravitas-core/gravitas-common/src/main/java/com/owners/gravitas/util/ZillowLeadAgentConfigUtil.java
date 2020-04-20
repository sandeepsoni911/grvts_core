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
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service to fetch zillow lead agent configuration
 * 
 * @author javeedsy
 *
 */
@Component
public class ZillowLeadAgentConfigUtil {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( ZillowLeadAgentConfigUtil.class );

    private final String APPLICATION_CONFIG_DIR = GRAVITAS_CONFIG_DIR + File.separatorChar;
    private static final String ZILLOW_LEAD_STATE_CONFIG = "zillow_lead_agent_config.json";
    private final String filePath = APPLICATION_CONFIG_DIR + ZILLOW_LEAD_STATE_CONFIG;

    Map< String, String > agentNameToStateMapping = new HashMap<>();

    @PostConstruct
    public void init() {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final File file = new File( filePath );
            final List< ZillowAgentDetails > zillowAgentList = objectMapper.readValue( file,
                    new TypeReference< List< ZillowAgentDetails > >() {
                    } );
            for ( final ZillowAgentDetails zillowAgent : zillowAgentList ) {
                agentNameToStateMapping.put( StringUtils.deleteWhitespace( zillowAgent.getAgentName() ).toUpperCase(),
                        zillowAgent.getState().toUpperCase() );
            }
        } catch ( final Exception e ) {
            LOGGER.error( "Error while getting zillow lead agent config mapping", e );
        }
        LOGGER.info( "\n agentNameToStateMapping Count : {} \n agentNameToStateMapping : {} ",
                agentNameToStateMapping.size(), JsonUtil.toJson( agentNameToStateMapping ) );
    }

    /**
     * Gets the agent name to state mapping.
     *
     * @return the agent name to state mapping
     */
    public Map< String, String > getAgentNameToStateMapping() {
        return agentNameToStateMapping;
    }

    /**
     * Sets the agent name to state mapping.
     *
     * @param agentNameToStateMapping
     *            the agent name to state mapping
     */
    public void setAgentNameToStateMapping( final Map< String, String > agentNameToStateMapping ) {
        this.agentNameToStateMapping = agentNameToStateMapping;
    }
}
