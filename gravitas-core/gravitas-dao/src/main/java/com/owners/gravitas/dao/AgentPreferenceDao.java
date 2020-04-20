package com.owners.gravitas.dao;

import java.util.Map;

import com.owners.gravitas.domain.AgentPreference;

/**
 * stores agent preferences
 * 
 * @author gururasm
 *
 */
public interface AgentPreferenceDao {

    /**
     * stores agent preferences in FB
     * 
     * @param agentId
     * @param agentPreference
     */
    AgentPreference saveAgentPreferences( String agentId, AgentPreference agentPreference );

    /**
     * Save agent preferences data.
     *
     * @param fbPath
     *            the fb path
     * @param agentPreference
     *            the agent preference
     * @return the agent preference
     */
    Object saveAgentPreferencesData( String fbPath, Map< String, Object > agentPreference );
}
