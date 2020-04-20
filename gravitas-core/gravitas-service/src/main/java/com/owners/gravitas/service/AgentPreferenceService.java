package com.owners.gravitas.service;

import java.util.Map;

import com.owners.gravitas.domain.AgentPreference;

/**
 * 
 * @author gururasm
 *
 */
public interface AgentPreferenceService {

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

    /**
     * Gets the fb preference path.
     *
     * @param agentId
     *            the agent id
     * @param path
     *            the path
     * @param agentSpecific
     *            the agent specific
     * @return the fb preference path
     */
    String getFbPreferencePath( String agentId, String path, boolean agentSpecific );
}
